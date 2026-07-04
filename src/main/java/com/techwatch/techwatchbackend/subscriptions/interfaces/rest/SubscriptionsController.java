package com.techwatch.techwatchbackend.subscriptions.interfaces.rest;

import com.techwatch.techwatchbackend.shared.application.result.ApplicationError;
import com.techwatch.techwatchbackend.shared.application.result.Result;
import com.techwatch.techwatchbackend.shared.interfaces.rest.transform.ResponseEntityAssembler;
import com.techwatch.techwatchbackend.subscriptions.application.commandservices.SubscriptionCommandService;
import com.techwatch.techwatchbackend.subscriptions.domain.model.aggregates.Subscription;
import com.techwatch.techwatchbackend.subscriptions.domain.repositories.SubscriptionRepository;
import com.techwatch.techwatchbackend.subscriptions.interfaces.rest.resources.CreateSubscriptionResource;
import com.techwatch.techwatchbackend.subscriptions.interfaces.rest.resources.SubscriptionResource;
import com.techwatch.techwatchbackend.subscriptions.interfaces.rest.transform.CreateSubscriptionCommandFromResourceAssembler;
import com.techwatch.techwatchbackend.subscriptions.interfaces.rest.transform.SubscriptionResourceFromEntityAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/subscriptions", produces = APPLICATION_JSON_VALUE)
public class SubscriptionsController {

    private final SubscriptionCommandService subscriptionCommandService;
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionsController(SubscriptionCommandService subscriptionCommandService,
                                   SubscriptionRepository subscriptionRepository) {
        this.subscriptionCommandService = subscriptionCommandService;
        this.subscriptionRepository = subscriptionRepository;
    }

    @PostMapping
    public ResponseEntity<?> createSubscription(@RequestBody CreateSubscriptionResource resource) {
        var command = CreateSubscriptionCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = subscriptionCommandService.handle(command)
                .flatMap(subscriptionId -> subscriptionRepository.findById(subscriptionId)
                        .<Result<Subscription, ApplicationError>>map(Result::success)
                        .orElseGet(() -> Result.failure(
                                ApplicationError.notFound("Subscription", subscriptionId.toString()))));

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                SubscriptionResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED);
    }
}
