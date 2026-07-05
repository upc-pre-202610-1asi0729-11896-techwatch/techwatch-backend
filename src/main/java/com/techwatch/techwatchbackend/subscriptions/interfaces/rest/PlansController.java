package com.techwatch.techwatchbackend.subscriptions.interfaces.rest;

import com.techwatch.techwatchbackend.subscriptions.application.queryservices.PlanQueryService;
import com.techwatch.techwatchbackend.subscriptions.domain.model.queries.GetAllPlansQuery;
import com.techwatch.techwatchbackend.subscriptions.interfaces.rest.resources.PlanResource;
import com.techwatch.techwatchbackend.subscriptions.interfaces.rest.transform.PlanResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller that exposes the plans catalog endpoints.
 */
@RestController
@RequestMapping(value = "/api/v1/plans", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Plans", description = "Plans catalog endpoints")
public class PlansController {

    private final PlanQueryService planQueryService;

    public PlansController(PlanQueryService planQueryService) {
        this.planQueryService = planQueryService;
    }

    /**
     * Get all the available plans.
     *
     * @return The list of {@link PlanResource} resources
     */
    @GetMapping
    @Operation(summary = "Get all plans", description = "Retrieves the freemium plans catalog.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Plans retrieved successfully",
                    content = @Content(schema = @Schema(implementation = PlanResource.class))
            )
    })
    public ResponseEntity<List<PlanResource>> getAllPlans() {
        var plans = planQueryService.handle(new GetAllPlansQuery());
        var resources = plans.stream()
                .map(PlanResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }
}
