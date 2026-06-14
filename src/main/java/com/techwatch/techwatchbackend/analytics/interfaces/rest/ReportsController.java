package com.techwatch.techwatchbackend.analytics.interfaces.rest;

import com.techwatch.techwatchbackend.analytics.application.commandservices.AnalyticsCommandService;
import com.techwatch.techwatchbackend.analytics.application.queryservices.AnalyticsQueryService;
import com.techwatch.techwatchbackend.analytics.domain.model.aggregates.ConsumptionReport;
import com.techwatch.techwatchbackend.analytics.domain.model.queries.GetReportByIdQuery;
import com.techwatch.techwatchbackend.analytics.domain.model.queries.GetReportsByPropertyIdQuery;
import com.techwatch.techwatchbackend.analytics.domain.model.valueobjects.PropertyId;
import com.techwatch.techwatchbackend.analytics.interfaces.rest.resources.ConsumptionReportResource;
import com.techwatch.techwatchbackend.analytics.interfaces.rest.resources.GenerateConsumptionReportResource;
import com.techwatch.techwatchbackend.analytics.interfaces.rest.transform.ConsumptionReportResourceFromEntityAssembler;
import com.techwatch.techwatchbackend.analytics.interfaces.rest.transform.GenerateConsumptionReportCommandFromResourceAssembler;
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
 * REST controller that exposes consumption report endpoints.
 */
@RestController
@RequestMapping(value = "/api/v1/reports", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Reports", description = "Consumption report endpoints")
public class ReportsController {
    private final AnalyticsCommandService analyticsCommandService;
    private final AnalyticsQueryService analyticsQueryService;

    public ReportsController(AnalyticsCommandService analyticsCommandService, AnalyticsQueryService analyticsQueryService) {
        this.analyticsCommandService = analyticsCommandService;
        this.analyticsQueryService = analyticsQueryService;
    }

    /**
     * Generate a new consumption report.
     *
     * @param resource The {@link GenerateConsumptionReportResource} instance
     * @return The {@link ConsumptionReportResource} resource for the generated report
     */
    @PostMapping
    @Operation(summary = "Generate a consumption report", description = "Generates a consumption report for a property over a period, aggregating its metrics per device.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Report generated successfully",
                    content = @Content(schema = @Schema(implementation = ConsumptionReportResource.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<?> generateReport(@RequestBody GenerateConsumptionReportResource resource) {
        var command = GenerateConsumptionReportCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = analyticsCommandService.handle(command)
                .flatMap(reportId -> analyticsQueryService.handle(new GetReportByIdQuery(reportId))
                        .<Result<ConsumptionReport, ApplicationError>>map(Result::success)
                        .orElseGet(() -> Result.failure(ApplicationError.notFound("ConsumptionReport", reportId.toString()))));

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                ConsumptionReportResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    /**
     * Get a consumption report by id.
     *
     * @param reportId The report id
     * @return The {@link ConsumptionReportResource} resource for the report
     */
    @GetMapping("/{reportId}")
    @Operation(summary = "Get report by ID", description = "Retrieves a specific consumption report by its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Report found",
                    content = @Content(schema = @Schema(implementation = ConsumptionReportResource.class))
            ),
            @ApiResponse(responseCode = "404", description = "Report not found")
    })
    public ResponseEntity<ConsumptionReportResource> getReportById(
            @PathVariable
            @Parameter(description = "Unique report identifier", example = "1", required = true)
            Long reportId
    ) {
        var report = analyticsQueryService.handle(new GetReportByIdQuery(reportId));
        if (report.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(ConsumptionReportResourceFromEntityAssembler.toResourceFromEntity(report.get()));
    }

    /**
     * Get all the consumption reports of a property.
     *
     * @param propertyId The property id
     * @return The list of {@link ConsumptionReportResource} resources for the property
     */
    @GetMapping
    @Operation(summary = "Get reports by property", description = "Retrieves all the consumption reports of the given property.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Reports retrieved successfully",
                    content = @Content(schema = @Schema(implementation = ConsumptionReportResource.class))
            )
    })
    public ResponseEntity<List<ConsumptionReportResource>> getReportsByPropertyId(
            @RequestParam
            @Parameter(description = "Property id", example = "1", required = true)
            Long propertyId
    ) {
        var reports = analyticsQueryService.handle(new GetReportsByPropertyIdQuery(new PropertyId(propertyId)));
        var resources = reports.stream()
                .map(ConsumptionReportResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }
}
