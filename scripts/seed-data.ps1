<#
.SYNOPSIS
    Seeds the TechWatch backend with full, coherent sample data through the public
    REST API so every table has realistic content for a live demo / presentation.

.DESCRIPTION
    Uses the same endpoints the frontend uses, so all domain rules are respected.
    For each property it creates spaces and devices, then runs a simulation session
    recording device actions. Those actions generate usage data which, via domain
    events, produce consumption metrics and threshold-based alerts (LOW/MEDIUM/HIGH).
    Finally it generates a consumption report per property and marks one alert as read.

    Tables populated: properties, spaces, devices, simulation_sessions, device_actions,
    usage_data_records, consumption_metrics, consumption_alerts, consumption_reports,
    report_items.

    The script is idempotent: existing entities are reused (by name), and the
    simulation is skipped for a property that already has metrics, so re-running it
    will not duplicate analytics data.

.PARAMETER BaseUrl
    Backend base URL. Defaults to the Railway deployment. Use http://localhost:8080
    to seed a local backend instead.

.PARAMETER UserId
    Owner user id for the created data. Defaults to 1 (what the frontend uses).

.PARAMETER SkipSimulation
    Only seed properties/spaces/devices, without the simulation/analytics flow.

.EXAMPLE
    ./seed-data.ps1
    ./seed-data.ps1 -BaseUrl http://localhost:8080
    ./seed-data.ps1 -SkipSimulation
#>
param(
    [string]$BaseUrl = "https://techwatch-backend-production.up.railway.app",
    [long]$UserId = 1,
    [switch]$SkipSimulation
)

$api = "$BaseUrl/api/v1"

# Report period: the full current calendar month. We use the whole month (not
# "now") so the period comfortably contains metrics regardless of the server's
# timezone (the backend records timestamps in UTC).
$now = Get-Date
$lastDay = [DateTime]::DaysInMonth($now.Year, $now.Month)
$reportStart = (Get-Date -Year $now.Year -Month $now.Month -Day 1 -Hour 0 -Minute 0 -Second 0).ToString("yyyy-MM-ddTHH:mm:ss")
$reportEnd = (Get-Date -Year $now.Year -Month $now.Month -Day $lastDay -Hour 23 -Minute 59 -Second 59).ToString("yyyy-MM-ddTHH:mm:ss")

# ---------------------------------------------------------------------------
# HTTP helpers
# ---------------------------------------------------------------------------

# Returns the HTTP status code of a failed web request, or 0.
function Get-StatusCode($err) {
    try { return [int]$err.Exception.Response.StatusCode } catch { return 0 }
}

# JSON request helper. Uses Invoke-WebRequest + ConvertFrom-Json on purpose:
# Windows PowerShell 5.1's Invoke-RestMethod mangles JSON arrays of objects
# (it collapses them into a single object with array-valued fields).
function Invoke-Json($method, $url, $body) {
    $params = @{ Method = $method; Uri = $url; UseBasicParsing = $true }
    if ($null -ne $body) {
        $params.ContentType = "application/json"
        $params.Body = ($body | ConvertTo-Json)
    }
    $resp = Invoke-WebRequest @params
    if ([string]::IsNullOrWhiteSpace($resp.Content)) { return $null }
    return ($resp.Content | ConvertFrom-Json)
}

# ---------------------------------------------------------------------------
# Idempotent create-or-get helpers (lookup existing by name on 409 conflict)
# ---------------------------------------------------------------------------
function Get-OrCreateProperty($name, $address, $type) {
    try {
        return Invoke-Json "Post" "$api/properties" @{ userId = $UserId; name = $name; address = $address; type = $type }
    } catch {
        if ((Get-StatusCode $_) -ne 409) { throw }
    }
    foreach ($item in @(Invoke-Json "Get" "$api/properties?userId=$UserId" $null)) {
        if ($item.name -eq $name) { return $item }
    }
    throw "Property '$name' reported as conflict but was not found on lookup"
}
function Get-OrCreateSpace($propertyId, $name, $description) {
    try {
        return Invoke-Json "Post" "$api/properties/$propertyId/spaces" @{ name = $name; description = $description }
    } catch {
        if ((Get-StatusCode $_) -ne 409) { throw }
    }
    $prop = Invoke-Json "Get" "$api/properties/$propertyId" $null
    foreach ($item in @($prop.spaces)) { if ($item.name -eq $name) { return $item } }
    throw "Space '$name' reported as conflict but was not found on lookup"
}
function Get-OrCreateDevice($spaceId, $name, $brand, $model, $type, $powerWatts) {
    try {
        return Invoke-Json "Post" "$api/devices" @{ spaceId = $spaceId; name = $name; brand = $brand; model = $model; type = $type; powerWatts = $powerWatts }
    } catch {
        if ((Get-StatusCode $_) -ne 409) { throw }
    }
    foreach ($item in @(Invoke-Json "Get" "$api/devices?spaceId=$spaceId" $null)) {
        if ($item.name -eq $name) { return $item }
    }
    throw "Device '$name' reported as conflict but was not found on lookup"
}

# ---------------------------------------------------------------------------
# Simulation / analytics helpers
# ---------------------------------------------------------------------------
function Get-MetricCount($propertyId) {
    return @(Invoke-Json "Get" "$api/metrics?propertyId=$propertyId" $null).Count
}
function Start-Session($propertyId) {
    try {
        return Invoke-Json "Post" "$api/simulation-sessions" @{ userId = $UserId; propertyId = $propertyId }
    } catch {
        if ((Get-StatusCode $_) -ne 409) { throw }
        # A session is already active for this user: end it, then start fresh.
        try {
            $active = Invoke-Json "Get" "$api/simulation-sessions/active?userId=$UserId" $null
            if ($active) { Invoke-Json "Post" "$api/simulation-sessions/$($active.id)/end" $null | Out-Null }
        } catch { }
        return Invoke-Json "Post" "$api/simulation-sessions" @{ userId = $UserId; propertyId = $propertyId }
    }
}
function Record-Action($sessionId, $deviceId, $action, $minutes, $paramName, $paramValue) {
    $body = @{ deviceId = $deviceId; actionType = $action; durationMinutes = $minutes }
    if ($paramName) { $body.parameterName = $paramName; $body.parameterValue = $paramValue }
    Invoke-Json "Post" "$api/simulation-sessions/$sessionId/actions" $body | Out-Null
}

# ---------------------------------------------------------------------------
# Catalog: properties -> spaces -> devices, plus a simulation plan per property.
# Consumption (Wh) = device powerWatts * minutes / 60. Alert thresholds: 50/150/300.
# ---------------------------------------------------------------------------
$catalog = @(
    @{
        name = "Casa Principal"; address = "Av. Javier Prado 123, San Isidro"; type = "HOUSE"
        spaces = @(
            @{ name = "Sala"; description = "Sala principal"; devices = @(
                @{ name = "Luz de techo"; brand = "Philips"; model = "Hue"; type = "LIGHT"; powerWatts = 9.5 }
                @{ name = "Aire acondicionado"; brand = "LG"; model = "Dual Inverter"; type = "AIR_CONDITIONER"; powerWatts = 1200 }
                @{ name = "Enchufe Smart TV"; brand = "TP-Link"; model = "Kasa"; type = "SMART_PLUG"; powerWatts = 120 }
            )}
            @{ name = "Cocina"; description = "Cocina integrada"; devices = @(
                @{ name = "Refrigeradora"; brand = "Samsung"; model = "RT38"; type = "SMART_PLUG"; powerWatts = 150 }
                @{ name = "Luz cocina"; brand = "Philips"; model = "LED"; type = "LIGHT"; powerWatts = 12 }
            )}
            @{ name = "Dormitorio"; description = "Dormitorio principal"; devices = @(
                @{ name = "Termostato"; brand = "Nest"; model = "Learning"; type = "THERMOSTAT"; powerWatts = 5 }
                @{ name = "Camara"; brand = "Ezviz"; model = "C6"; type = "CAMERA"; powerWatts = 4 }
                @{ name = "Lampara"; brand = "Xiaomi"; model = "Mi LED"; type = "LIGHT"; powerWatts = 8 }
            )}
        )
        sim = @(
            @{ device = "Aire acondicionado"; action = "TURN_ON"; minutes = 60 }                                  # 1200 Wh -> HIGH
            @{ device = "Refrigeradora"; action = "TURN_ON"; minutes = 80 }                                       #  200 Wh -> MEDIUM
            @{ device = "Enchufe Smart TV"; action = "TURN_ON"; minutes = 45 }                                    #   90 Wh -> LOW
            @{ device = "Luz de techo"; action = "TURN_ON"; minutes = 120 }                                       #   19 Wh -> none
            @{ device = "Termostato"; action = "SET_TEMPERATURE"; minutes = 180; paramName = "temperature"; paramValue = "22" }  # 15 Wh -> none
        )
    }
    @{
        name = "Departamento Miraflores"; address = "Calle Lima 456, Miraflores"; type = "APARTMENT"
        spaces = @(
            @{ name = "Estudio"; description = "Espacio de trabajo"; devices = @(
                @{ name = "Luz escritorio"; brand = "Philips"; model = "LED"; type = "LIGHT"; powerWatts = 10 }
                @{ name = "Cerradura"; brand = "Yale"; model = "Smart Lock"; type = "DOOR_LOCK"; powerWatts = 3 }
                @{ name = "Enchufe PC"; brand = "TP-Link"; model = "Kasa"; type = "SMART_PLUG"; powerWatts = 300 }
            )}
            @{ name = "Balcon"; description = "Balcon exterior"; devices = @(
                @{ name = "Camara exterior"; brand = "Reolink"; model = "E1"; type = "CAMERA"; powerWatts = 4 }
            )}
        )
        sim = @(
            @{ device = "Enchufe PC"; action = "TURN_ON"; minutes = 90 }            # 450 Wh -> HIGH
            @{ device = "Luz escritorio"; action = "TURN_ON"; minutes = 240 }       #  40 Wh -> none
            @{ device = "Camara exterior"; action = "TURN_ON"; minutes = 300 }      #  20 Wh -> none
        )
    }
)

# ---------------------------------------------------------------------------
# Execution
# ---------------------------------------------------------------------------
Write-Host "Seeding $api (userId=$UserId)..." -ForegroundColor Cyan

foreach ($p in $catalog) {
    try {
        $prop = Get-OrCreateProperty $p.name $p.address $p.type
        Write-Host ("Property #{0}: {1}" -f $prop.id, $prop.name) -ForegroundColor Green
    } catch {
        Write-Warning ("Property '{0}': {1}" -f $p.name, $_.Exception.Message); continue
    }

    $deviceIdByName = @{}
    foreach ($s in $p.spaces) {
        try {
            $space = Get-OrCreateSpace $prop.id $s.name $s.description
            Write-Host ("  Space #{0}: {1}" -f $space.id, $space.name) -ForegroundColor DarkGreen
        } catch {
            Write-Warning ("  Space '{0}': {1}" -f $s.name, $_.Exception.Message); continue
        }
        foreach ($d in $s.devices) {
            try {
                $dev = Get-OrCreateDevice $space.id $d.name $d.brand $d.model $d.type $d.powerWatts
                $deviceIdByName[$d.name] = $dev.id
                Write-Host ("    Device #{0}: {1} [{2}, {3}W]" -f $dev.id, $dev.name, $dev.type, $dev.powerWatts)
            } catch {
                Write-Warning ("    Device '{0}': {1}" -f $d.name, $_.Exception.Message)
            }
        }
    }

    if ($SkipSimulation) { continue }

    if ((Get-MetricCount $prop.id) -gt 0) {
        Write-Host "  (simulation skipped: property already has metrics)" -ForegroundColor DarkGray
        continue
    }

    try {
        $session = Start-Session $prop.id
        Write-Host ("  Simulation session #{0} started" -f $session.id) -ForegroundColor Magenta
        foreach ($a in $p.sim) {
            $deviceId = $deviceIdByName[$a.device]
            if (-not $deviceId) { Write-Warning ("    action skipped, unknown device '{0}'" -f $a.device); continue }
            Record-Action $session.id $deviceId $a.action $a.minutes $a.paramName $a.paramValue
            Write-Host ("    Action: {0} on '{1}' for {2} min" -f $a.action, $a.device, $a.minutes) -ForegroundColor DarkMagenta
        }
        $report = Invoke-Json "Post" "$api/reports" @{ userId = $UserId; propertyId = $prop.id; startDate = $reportStart; endDate = $reportEnd }
        Write-Host ("  Report #{0} generated ({1} items)" -f $report.id, @($report.items).Count) -ForegroundColor Blue
        Invoke-Json "Post" "$api/simulation-sessions/$($session.id)/end" $null | Out-Null
        Write-Host "  Simulation session ended" -ForegroundColor Magenta
    } catch {
        Write-Warning ("  Simulation: {0}" -f $_.Exception.Message)
    }
}

# Mark the first alert as read, to showcase the read/unread state in the demo.
if (-not $SkipSimulation) {
    try {
        $alerts = @(Invoke-Json "Get" "$api/alerts?userId=$UserId" $null)
        Write-Host ("Alerts generated: {0}" -f $alerts.Count) -ForegroundColor Yellow
        $unread = $alerts | Where-Object { -not $_.read } | Select-Object -First 1
        if ($unread) {
            Invoke-Json "Put" "$api/alerts/$($unread.id)/read" $null | Out-Null
            Write-Host ("  Alert #{0} marked as read" -f $unread.id) -ForegroundColor Yellow
        }
    } catch {
        Write-Warning ("Alerts: {0}" -f $_.Exception.Message)
    }
}

Write-Host "Done." -ForegroundColor Cyan
