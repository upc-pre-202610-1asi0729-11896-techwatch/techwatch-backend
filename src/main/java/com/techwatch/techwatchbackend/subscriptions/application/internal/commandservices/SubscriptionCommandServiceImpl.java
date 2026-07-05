package com.techwatch.techwatchbackend.subscriptions.application.internal.commandservices;

import com.techwatch.techwatchbackend.shared.application.result.ApplicationError;
import com.techwatch.techwatchbackend.shared.application.result.Result;
import com.techwatch.techwatchbackend.subscriptions.application.commandservices.SubscriptionCommandService;
import com.techwatch.techwatchbackend.subscriptions.domain.model.aggregates.Subscription;
import com.techwatch.techwatchbackend.subscriptions.domain.model.commands.CancelSubscriptionCommand;
import com.techwatch.techwatchbackend.subscriptions.domain.model.commands.ChangeSubscriptionPlanCommand;
import com.techwatch.techwatchbackend.subscriptions.domain.model.commands.CreateSubscriptionCommand;
import com.techwatch.techwatchbackend.subscriptions.domain.model.commands.RenewSubscriptionCommand;
import com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects.PlanId;
import com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects.UserId;
import com.techwatch.techwatchbackend.subscriptions.domain.repositories.SubscriptionRepository;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionCommandServiceImpl implements SubscriptionCommandService {

    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionCommandServiceImpl(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public Result<Long, ApplicationError> handle(CreateSubscriptionCommand command) {
        var activeSubscription = subscriptionRepository.findActiveByUserId(new UserId(command.userId()));
        if (activeSubscription.isPresent()) {
            return Result.failure(ApplicationError.conflict("Subscription",
                    "User with id %s already has an active subscription".formatted(command.userId())));
        }
        var subscription = new Subscription(command);
        try {
            subscription = subscriptionRepository.save(subscription);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("create-subscription", e.getMessage()));
        }
        return Result.success(subscription.getId());
    }

    @Override
    public Result<Subscription, ApplicationError> handle(RenewSubscriptionCommand command) {
        var subscription = subscriptionRepository.findById(command.subscriptionId());
        if (subscription.isEmpty()) {
            return Result.failure(ApplicationError.notFound("Subscription", command.subscriptionId().toString()));
        }
        try {
            var renewedSubscription = subscriptionRepository.save(subscription.get().renew(command.months()));
            return Result.success(renewedSubscription);
        } catch (IllegalStateException e) {
            return Result.failure(ApplicationError.businessRuleViolation("renew-subscription", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("renew-subscription", e.getMessage()));
        }
    }

    @Override
    public Result<Subscription, ApplicationError> handle(CancelSubscriptionCommand command) {
        var subscription = subscriptionRepository.findById(command.subscriptionId());
        if (subscription.isEmpty()) {
            return Result.failure(ApplicationError.notFound("Subscription", command.subscriptionId().toString()));
        }
        try {
            var cancelledSubscription = subscriptionRepository.save(subscription.get().cancel());
            return Result.success(cancelledSubscription);
        } catch (IllegalStateException e) {
            return Result.failure(ApplicationError.businessRuleViolation("cancel-subscription", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("cancel-subscription", e.getMessage()));
        }
    }

    @Override
    public Result<Subscription, ApplicationError> handle(ChangeSubscriptionPlanCommand command) {
        var subscription = subscriptionRepository.findById(command.subscriptionId());
        if (subscription.isEmpty()) {
            return Result.failure(ApplicationError.notFound("Subscription", command.subscriptionId().toString()));
        }
        try {
            var updatedSubscription = subscriptionRepository.save(
                    subscription.get().changePlan(new PlanId(command.newPlanId())));
            return Result.success(updatedSubscription);
        } catch (IllegalStateException e) {
            return Result.failure(ApplicationError.businessRuleViolation("change-subscription-plan", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("change-subscription-plan", e.getMessage()));
        }
    }
}
