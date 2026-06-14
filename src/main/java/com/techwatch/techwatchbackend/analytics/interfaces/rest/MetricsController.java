package com.techwatch.techwatchbackend.analytics.interfaces.rest;

import com.techwatch.techwatchbackend.analytics.application.queryservices.AnalyticsQueryService;
import com.techwatch.techwatchbackend.analytics.domain.model.queries.GetMetricsByPropertyIdQuery;
import com.techwatch.techwatchbackend.analytics.domain.model.valueobjects.PropertyId;
import com.techwatch.techwatchbackend.analytics.interfaces.rest.resources.ConsumptionMetricResource;
import com.techwatch.techwatchbackend.analytics.interfaces.rest.transform.ConsumptionMetricResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller that exposes consumption metrics lookup endpoints.
 */
@RestController
@RequestMapping(value = "/api/v1/metrics", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Metrics", description = "Consumption metrics endpoints")
public class MetricsController {
    private final AnalyticsQueryService analyticsQueryService;

    public MetricsController(AnalyticsQueryService analyticsQueryService) {
        this.analyticsQueryService = analyticsQueryService;
    }

    /**
     * Get all the consumption metrics of a property.
     *
     * @param propertyId The property id
     * @return The list of {@link ConsumptionMetricResource} resources for the property
     */
    @GetMapping
    @Operation(summary = "Get metrics by property", description = "Retrieves all the consumption metrics calculated for the given property.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Metrics retrieved successfully",
                    content = @Content(schema = @Schema(implementation = ConsumptionMetricResource.class))
            )
    })
    public ResponseEntity<List<ConsumptionMetricResource>> getMetricsByPropertyId(
            @RequestParam
            @Parameter(description = "Property id", example = "1", required = true)
            Long propertyId
    ) {
        var metrics = analyticsQueryService.handle(new GetMetricsByPropertyIdQuery(new PropertyId(propertyId)));
        var resources = metrics.stream()
                .map(ConsumptionMetricResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }
}
