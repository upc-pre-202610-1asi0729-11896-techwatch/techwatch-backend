package com.techwatch.techwatchbackend.subscriptions.interfaces.rest;

import com.techwatch.techwatchbackend.shared.application.result.ApplicationError;
import com.techwatch.techwatchbackend.shared.application.result.Result;
import com.techwatch.techwatchbackend.shared.interfaces.rest.transform.ResponseEntityAssembler;
import com.techwatch.techwatchbackend.subscriptions.application.commandservices.SubscriptionCommandService;
import com.techwatch.techwatchbackend.subscriptions.application.queryservices.SubscriptionQueryService;
import com.techwatch.techwatchbackend.subscriptions.domain.model.aggregates.Subscription;
import com.techwatch.techwatchbackend.subscriptions.domain.model.queries.GetActiveSubscriptionByUserIdQuery;
import com.techwatch.techwatchbackend.subscriptions.domain.model.queries.GetSubscriptionByIdQuery;
import com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects.UserId;
import com.techwatch.techwatchbackend.subscriptions.interfaces.rest.resources.ChangeSubscriptionPlanResource;
import com.techwatch.techwatchbackend.subscriptions.interfaces.rest.resources.CreateSubscriptionResource;
import com.techwatch.techwatchbackend.subscriptions.interfaces.rest.resources.RenewSubscriptionResource;
import com.techwatch.techwatchbackend.subscriptions.interfaces.rest.resources.SubscriptionResource;
import com.techwatch.techwatchbackend.subscriptions.interfaces.rest.transform.CancelSubscriptionCommandFromResourceAssembler;
import com.techwatch.techwatchbackend.subscriptions.interfaces.rest.transform.ChangeSubscriptionPlanCommandFromResourceAssembler;
import com.techwatch.techwatchbackend.subscriptions.interfaces.rest.transform.CreateSubscriptionCommandFromResourceAssembler;
import com.techwatch.techwatchbackend.subscriptions.interfaces.rest.transform.RenewSubscriptionCommandFromResourceAssembler;
import com.techwatch.techwatchbackend.subscriptions.interfaces.rest.transform.SubscriptionResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller that exposes subscriptions endpoints.
 */
@RestController
@RequestMapping(value = "/api/v1/subscriptions", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Subscriptions", description = "Subscription management endpoints")
public class SubscriptionsController {

    private final SubscriptionCommandService subscriptionCommandService;
    private final SubscriptionQueryService subscriptionQueryService;

    public SubscriptionsController(SubscriptionCommandService subscriptionCommandService,
                                   SubscriptionQueryService subscriptionQueryService) {
        this.subscriptionCommandService = subscriptionCommandService;
        this.subscriptionQueryService = subscriptionQueryService;
    }

    /**
     * Create a new subscription for a user.
     *
     * @param resource The {@link CreateSubscriptionResource} with the subscription data
     * @return The created {@link SubscriptionResource} resource
     */
    @PostMapping
    @Operation(summary = "Create a subscription", description = "Subscribes a user to a plan.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Subscription created successfully"),
            @ApiResponse(responseCode = "409", description = "User already has an active subscription")
    })
    public ResponseEntity<?> createSubscription(@RequestBody CreateSubscriptionResource resource) {
        var command = CreateSubscriptionCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = subscriptionCommandService.handle(command)
                .flatMap(subscriptionId -> subscriptionQueryService.handle(new GetSubscriptionByIdQuery(subscriptionId))
                        .<Result<Subscription, ApplicationError>>map(Result::success)
                        .orElseGet(() -> Result.failure(
                                ApplicationError.notFound("Subscription", subscriptionId.toString()))));

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                SubscriptionResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED);
    }

    /**
     * Get the active subscription of a user.
     *
     * @param userId The user id
     * @return The {@link SubscriptionResource} resource for the active subscription
     */
    @GetMapping
    @Operation(summary = "Get active subscription by user", description = "Retrieves the active subscription of the given user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Active subscription retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "The user has no active subscription")
    })
    public ResponseEntity<?> getActiveSubscriptionByUserId(
            @RequestParam
            @Parameter(description = "User id", example = "1", required = true)
            Long userId
    ) {
        return subscriptionQueryService.handle(new GetActiveSubscriptionByUserIdQuery(new UserId(userId)))
                .map(subscription -> ResponseEntity.ok(
                        SubscriptionResourceFromEntityAssembler.toResourceFromEntity(subscription)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Renew an existing subscription.
     *
     * @param subscriptionId The subscription id
     * @param resource The {@link RenewSubscriptionResource} with the months to extend
     * @return The {@link SubscriptionResource} resource for the renewed subscription
     */
    @PutMapping("/{subscriptionId}/renew")
    @Operation(summary = "Renew a subscription", description = "Extends the end date of a subscription by the requested number of months.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subscription renewed successfully"),
            @ApiResponse(responseCode = "404", description = "Subscription not found"),
            @ApiResponse(responseCode = "422", description = "Subscription cannot be renewed")
    })
    public ResponseEntity<?> renewSubscription(
            @PathVariable
            @Parameter(description = "Unique subscription identifier", example = "1", required = true)
            Long subscriptionId,
            @RequestBody RenewSubscriptionResource resource
    ) {
        var command = RenewSubscriptionCommandFromResourceAssembler.toCommandFromResource(subscriptionId, resource);
        var result = subscriptionCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                SubscriptionResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK);
    }

    /**
     * Cancel an existing subscription.
     *
     * @param subscriptionId The subscription id
     * @return The {@link SubscriptionResource} resource for the cancelled subscription
     */
    @PutMapping("/{subscriptionId}/cancel")
    @Operation(summary = "Cancel a subscription", description = "Cancels an active subscription.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subscription cancelled successfully"),
            @ApiResponse(responseCode = "404", description = "Subscription not found"),
            @ApiResponse(responseCode = "422", description = "Subscription cannot be cancelled")
    })
    public ResponseEntity<?> cancelSubscription(
            @PathVariable
            @Parameter(description = "Unique subscription identifier", example = "1", required = true)
            Long subscriptionId
    ) {
        var command = CancelSubscriptionCommandFromResourceAssembler.toCommandFromResource(subscriptionId);
        var result = subscriptionCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                SubscriptionResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK);
    }

    /**
     * Change the plan of an existing subscription.
     *
     * @param subscriptionId The subscription id
     * @param resource The {@link ChangeSubscriptionPlanResource} with the new plan id
     * @return The {@link SubscriptionResource} resource for the updated subscription
     */
    @PutMapping("/{subscriptionId}/plan")
    @Operation(summary = "Change subscription plan", description = "Changes the plan of an active subscription.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subscription plan changed successfully"),
            @ApiResponse(responseCode = "404", description = "Subscription not found"),
            @ApiResponse(responseCode = "422", description = "Subscription plan cannot be changed")
    })
    public ResponseEntity<?> changeSubscriptionPlan(
            @PathVariable
            @Parameter(description = "Unique subscription identifier", example = "1", required = true)
            Long subscriptionId,
            @RequestBody ChangeSubscriptionPlanResource resource
    ) {
        var command = ChangeSubscriptionPlanCommandFromResourceAssembler.toCommandFromResource(subscriptionId, resource);
        var result = subscriptionCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                SubscriptionResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK);
    }
}
