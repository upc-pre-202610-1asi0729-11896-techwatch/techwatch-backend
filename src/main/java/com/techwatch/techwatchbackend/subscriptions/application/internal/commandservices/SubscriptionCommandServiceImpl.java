package com.techwatch.techwatchbackend.subscriptions.application.internal.commandservices;

import com.techwatch.techwatchbackend.shared.application.result.ApplicationError;
import com.techwatch.techwatchbackend.shared.application.result.Result;
import com.techwatch.techwatchbackend.subscriptions.application.commandservices.SubscriptionCommandService;
import com.techwatch.techwatchbackend.subscriptions.domain.model.aggregates.Subscription;
import com.techwatch.techwatchbackend.subscriptions.domain.model.commands.CancelSubscriptionCommand;
import com.techwatch.techwatchbackend.subscriptions.domain.model.commands.ChangeSubscriptionPlanCommand;
import com.techwatch.techwatchbackend.subscriptions.domain.model.commands.RenewSubscriptionCommand;
import com.techwatch.techwatchbackend.subscriptions.domain.repositories.SubscriptionRepository;
import org.springframework.stereotype.Service;

/**
 * Application service implementation for subscription commands.
 */
@Service
public class SubscriptionCommandServiceImpl implements SubscriptionCommandService {

    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionCommandServiceImpl(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public Result<Subscription, ApplicationError> handle(RenewSubscriptionCommand command) {
        var result = subscriptionRepository.findById(command.subscriptionId());

        if (result.isEmpty()) {
            return Result.failure(ApplicationError.notFound("Subscription", command.subscriptionId().toString()));
        }

        var subscription = result.get();

        try {
            var renewedSubscription = subscriptionRepository.save(subscription.renew(command.months()));
            return Result.success(renewedSubscription);
        } catch (IllegalStateException e) {
            return Result.failure(ApplicationError.businessRuleViolation("renew-subscription", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("renew-subscription", e.getMessage()));
        }
    }

    @Override
    public Result<Subscription, ApplicationError> handle(CancelSubscriptionCommand command) {
        var result = subscriptionRepository.findById(command.subscriptionId());

        if (result.isEmpty()) {
            return Result.failure(ApplicationError.notFound("Subscription", command.subscriptionId().toString()));
        }

        var subscription = result.get();

        try {
            var canceledSubscription = subscriptionRepository.save(subscription.cancel());
            return Result.success(canceledSubscription);
        } catch (IllegalStateException e) {
            return Result.failure(ApplicationError.businessRuleViolation("cancel-subscription", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("cancel-subscription", e.getMessage()));
        }
    }

    @Override
    public Result<Subscription, ApplicationError> handle(ChangeSubscriptionPlanCommand command) {
        var result = subscriptionRepository.findById(command.subscriptionId());

        if (result.isEmpty()) {
            return Result.failure(ApplicationError.notFound("Subscription", command.subscriptionId().toString()));
        }

        var subscription = result.get();

        try {
            var updatedSubscription = subscriptionRepository.save(subscription.changePlan(command.newPlanName()));
            return Result.success(updatedSubscription);
        } catch (IllegalStateException e) {
            return Result.failure(ApplicationError.businessRuleViolation("change-subscription-plan", e.getMessage()));
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.businessRuleViolation("change-subscription-plan", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("change-subscription-plan", e.getMessage()));
        }
    }
}