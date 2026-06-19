package com.techwatch.techwatchbackend.subscriptions.interfaces.rest;

import com.techwatch.techwatchbackend.shared.application.result.ApplicationError;
import com.techwatch.techwatchbackend.shared.application.result.Result;
import com.techwatch.techwatchbackend.subscriptions.application.commandservices.SubscriptionCommandService;
import com.techwatch.techwatchbackend.subscriptions.domain.model.aggregates.Subscription;
import com.techwatch.techwatchbackend.subscriptions.interfaces.rest.resources.RenewSubscriptionResource;
import com.techwatch.techwatchbackend.subscriptions.interfaces.rest.transform.RenewSubscriptionCommandFromResourceAssembler;
import com.techwatch.techwatchbackend.subscriptions.interfaces.rest.transform.SubscriptionResourceFromEntityAssembler;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for subscriptions.
 */
@RestController
@RequestMapping("/api/v1/subscriptions")
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
                    buildErrorResponse(failure.error());
        };
    }

    private ResponseEntity<ApplicationError> buildErrorResponse(ApplicationError error) {
        if (error.code().endsWith("_NOT_FOUND")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        if (error.code().equals("BUSINESS_RULE_VIOLATION")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }

        if (error.code().equals("VALIDATION_ERROR")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}