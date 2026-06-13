package com.techwatch.techwatchbackend.devices.interfaces.rest;

import com.techwatch.techwatchbackend.devices.application.commandservices.DeviceCommandService;
import com.techwatch.techwatchbackend.devices.application.queryservices.DeviceQueryService;
import com.techwatch.techwatchbackend.devices.domain.model.aggregates.Device;
import com.techwatch.techwatchbackend.devices.domain.model.queries.GetDeviceByIdQuery;
import com.techwatch.techwatchbackend.devices.domain.model.queries.GetDevicesBySpaceIdQuery;
import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.SpaceId;
import com.techwatch.techwatchbackend.devices.interfaces.rest.resources.CreateDeviceResource;
import com.techwatch.techwatchbackend.devices.interfaces.rest.resources.DeviceResource;
import com.techwatch.techwatchbackend.devices.interfaces.rest.transform.CreateDeviceCommandFromResourceAssembler;
import com.techwatch.techwatchbackend.devices.interfaces.rest.transform.DeviceResourceFromEntityAssembler;
import com.techwatch.techwatchbackend.shared.application.result.ApplicationError;
import com.techwatch.techwatchbackend.shared.application.result.Result;
import com.techwatch.techwatchbackend.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller that exposes device registration and lookup endpoints.
 */
@RestController
@RequestMapping(value = "/api/v1/devices", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Devices", description = "Device management endpoints")
public class DevicesController {
    private final DeviceCommandService deviceCommandService;
    private final DeviceQueryService deviceQueryService;

    public DevicesController(DeviceCommandService deviceCommandService, DeviceQueryService deviceQueryService) {
        this.deviceCommandService = deviceCommandService;
        this.deviceQueryService = deviceQueryService;
    }

    /**
     * Add a new device to a space.
     *
     * @param resource The {@link CreateDeviceResource} instance
     * @return The {@link DeviceResource} resource for the created device
     */
    @PostMapping
    @Operation(summary = "Add a device to a space", description = "Registers a new device in a space. The device starts in the OFF status.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Device created successfully",
                    content = @Content(schema = @Schema(implementation = DeviceResource.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Device name already exists in the space")
    })
    public ResponseEntity<?> createDevice(@RequestBody CreateDeviceResource resource) {
        var command = CreateDeviceCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = deviceCommandService.handle(command)
                .flatMap(deviceId -> deviceQueryService.handle(new GetDeviceByIdQuery(deviceId))
                        .<Result<Device, ApplicationError>>map(Result::success)
                        .orElseGet(() -> Result.failure(ApplicationError.notFound("Device", deviceId.toString()))));

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                DeviceResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    /**
     * Get a device by id.
     *
     * @param deviceId The device id
     * @return The {@link DeviceResource} resource for the device
     */
    @GetMapping("/{deviceId}")
    @Operation(summary = "Get device by ID", description = "Retrieves a specific device by its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Device found",
                    content = @Content(schema = @Schema(implementation = DeviceResource.class))
            ),
            @ApiResponse(responseCode = "404", description = "Device not found")
    })
    public ResponseEntity<DeviceResource> getDeviceById(
            @PathVariable
            @Parameter(description = "Unique device identifier", example = "1", required = true)
            Long deviceId
    ) {
        var device = deviceQueryService.handle(new GetDeviceByIdQuery(deviceId));
        if (device.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(DeviceResourceFromEntityAssembler.toResourceFromEntity(device.get()));
    }

    /**
     * Get all devices that belong to a space.
     *
     * @param spaceId The space id
     * @return The list of {@link DeviceResource} resources for the space's devices
     */
    @GetMapping
    @Operation(summary = "Get devices by space", description = "Retrieves all devices that belong to the given space.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Devices retrieved successfully",
                    content = @Content(schema = @Schema(implementation = DeviceResource.class))
            )
    })
    public ResponseEntity<List<DeviceResource>> getDevicesBySpaceId(
            @RequestParam
            @Parameter(description = "Owning space id", example = "1", required = true)
            Long spaceId
    ) {
        var devices = deviceQueryService.handle(new GetDevicesBySpaceIdQuery(new SpaceId(spaceId)));
        var resources = devices.stream()
                .map(DeviceResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }
}
