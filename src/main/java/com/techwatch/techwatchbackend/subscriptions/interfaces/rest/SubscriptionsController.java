package com.techwatch.techwatchbackend.subscriptions.interfaces.rest;

import com.techwatch.techwatchbackend.shared.application.result.ApplicationError;
import com.techwatch.techwatchbackend.shared.application.result.Result;
import com.techwatch.techwatchbackend.subscriptions.application.commandservices.SubscriptionCommandService;
import com.techwatch.techwatchbackend.subscriptions.domain.model.aggregates.Subscription;
import com.techwatch.techwatchbackend.subscriptions.interfaces.rest.resources.RenewSubscriptionResource;
import com.techwatch.techwatchbackend.subscriptions.interfaces.rest.transform.ApplicationErrorResponseAssembler;
import com.techwatch.techwatchbackend.subscriptions.interfaces.rest.transform.CancelSubscriptionCommandFromResourceAssembler;
import com.techwatch.techwatchbackend.subscriptions.interfaces.rest.transform.RenewSubscriptionCommandFromResourceAssembler;
import com.techwatch.techwatchbackend.subscriptions.interfaces.rest.transform.SubscriptionResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for subscriptions.
 */
@RestController
@RequestMapping("/api/v1/subscriptions")
@Tag(name = "Subscriptions", description = "Subscription management endpoints")
public class SubscriptionsController {

    private final SubscriptionCommandService subscriptionCommandService;

    public SubscriptionsController(SubscriptionCommandService subscriptionCommandService) {
        this.subscriptionCommandService = subscriptionCommandService;
    }

    /**
     * Renews an existing subscription.
     *
     * @param subscriptionId subscription id
     * @param resource renewal request body
     * @return renewed subscription resource or application error
     */
    @Operation(
            summary = "Renew a subscription",
            description = "Extends the end date of an existing subscription by the requested number of months."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subscription renewed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid renewal request"),
            @ApiResponse(responseCode = "404", description = "Subscription not found"),
            @ApiResponse(responseCode = "409", description = "Subscription cannot be renewed due to a business rule"),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    @PostMapping("/{subscriptionId}/renew")
    public ResponseEntity<?> renewSubscription(
            @PathVariable Long subscriptionId,
            @Valid @RequestBody RenewSubscriptionResource resource
    ) {
        var command = RenewSubscriptionCommandFromResourceAssembler.toCommandFromResource(
                subscriptionId,
                resource
        );

        var result = subscriptionCommandService.handle(command);

        return switch (result) {
            case Result.Success<Subscription, ApplicationError> success ->
                    ResponseEntity.ok(
                            SubscriptionResourceFromEntityAssembler.toResourceFromEntity(success.value())
                    );

            case Result.Failure<Subscription, ApplicationError> failure ->
                    ApplicationErrorResponseAssembler.toResponseFromError(failure.error());
        };
    }

    /**
     * Cancels an existing subscription.
     *
     * @param subscriptionId subscription id
     * @return canceled subscription resource or application error
     */
    @Operation(
            summary = "Cancel a subscription",
            description = "Cancels an active subscription and updates its status to CANCELED."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subscription canceled successfully"),
            @ApiResponse(responseCode = "404", description = "Subscription not found"),
            @ApiResponse(responseCode = "409", description = "Subscription cannot be canceled due to a business rule"),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    @PostMapping("/{subscriptionId}/cancel")
    public ResponseEntity<?> cancelSubscription(
            @PathVariable Long subscriptionId
    ) {
        var command = CancelSubscriptionCommandFromResourceAssembler.toCommandFromResource(subscriptionId);

        var result = subscriptionCommandService.handle(command);

        return switch (result) {
            case Result.Success<Subscription, ApplicationError> success ->
                    ResponseEntity.ok(
                            SubscriptionResourceFromEntityAssembler.toResourceFromEntity(success.value())
                    );

            case Result.Failure<Subscription, ApplicationError> failure ->
                    ApplicationErrorResponseAssembler.toResponseFromError(failure.error());
        };
    }
}