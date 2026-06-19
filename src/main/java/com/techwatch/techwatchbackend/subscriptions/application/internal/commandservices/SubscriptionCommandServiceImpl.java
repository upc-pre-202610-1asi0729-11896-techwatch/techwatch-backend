package com.techwatch.techwatchbackend.subscriptions.application.internal.commandservices;

import com.techwatch.techwatchbackend.shared.application.result.ApplicationError;
import com.techwatch.techwatchbackend.shared.application.result.Result;
import com.techwatch.techwatchbackend.subscriptions.application.commandservices.SubscriptionCommandService;
import com.techwatch.techwatchbackend.subscriptions.domain.model.aggregates.Subscription;
import com.techwatch.techwatchbackend.subscriptions.domain.model.commands.RenewSubscriptionCommand;
import com.techwatch.techwatchbackend.subscriptions.domain.repositories.SubscriptionRepository;
import org.springframework.stereotype.Service;

/**
 * Application service that executes subscription commands.
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
            return Result.failure(ApplicationError.notFound(
                    "Subscription",
                    command.subscriptionId().toString()
            ));
        }

        var subscription = result.get();

        try {
            var updated = subscriptionRepository.save(subscription.renew(command.months()));
            return Result.success(updated);
        } catch (IllegalStateException e) {
            return Result.failure(ApplicationError.businessRuleViolation(
                    "renew-subscription",
                    e.getMessage()
            ));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected(
                    "renew-subscription",
                    e.getMessage()
            ));
        }
    }
}
