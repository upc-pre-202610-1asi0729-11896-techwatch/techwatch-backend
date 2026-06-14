package com.techwatch.techwatchbackend.analytics.interfaces.rest;

import com.techwatch.techwatchbackend.analytics.application.queryservices.AnalyticsQueryService;
import com.techwatch.techwatchbackend.analytics.domain.model.queries.GetAlertsByUserIdQuery;
import com.techwatch.techwatchbackend.analytics.domain.model.valueobjects.UserId;
import com.techwatch.techwatchbackend.analytics.interfaces.rest.resources.ConsumptionAlertResource;
import com.techwatch.techwatchbackend.analytics.interfaces.rest.transform.ConsumptionAlertResourceFromEntityAssembler;
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
 * REST controller that exposes consumption alerts lookup endpoints.
 */
@RestController
@RequestMapping(value = "/api/v1/alerts", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Alerts", description = "Consumption alerts endpoints")
public class AlertsController {
    private final AnalyticsQueryService analyticsQueryService;

    public AlertsController(AnalyticsQueryService analyticsQueryService) {
        this.analyticsQueryService = analyticsQueryService;
    }

    /**
     * Get all the consumption alerts of a user.
     *
     * @param userId The user id
     * @return The list of {@link ConsumptionAlertResource} resources for the user
     */
    @GetMapping
    @Operation(summary = "Get alerts by user", description = "Retrieves all the consumption alerts of the given user.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Alerts retrieved successfully",
                    content = @Content(schema = @Schema(implementation = ConsumptionAlertResource.class))
            )
    })
    public ResponseEntity<List<ConsumptionAlertResource>> getAlertsByUserId(
            @RequestParam
            @Parameter(description = "User id", example = "1", required = true)
            Long userId
    ) {
        var alerts = analyticsQueryService.handle(new GetAlertsByUserIdQuery(new UserId(userId)));
        var resources = alerts.stream()
                .map(ConsumptionAlertResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }
}
