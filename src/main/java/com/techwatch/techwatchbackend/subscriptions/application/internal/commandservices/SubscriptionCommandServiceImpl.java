package com.techwatch.techwatchbackend.subscriptions.application.internal.commandservices;

import com.techwatch.techwatchbackend.shared.application.result.ApplicationError;
import com.techwatch.techwatchbackend.shared.application.result.Result;
import com.techwatch.techwatchbackend.subscriptions.application.commandservices.SubscriptionCommandService;
import com.techwatch.techwatchbackend.subscriptions.domain.model.aggregates.Subscription;
import com.techwatch.techwatchbackend.subscriptions.domain.model.commands.CreateSubscriptionCommand;
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
        var subscription = new Subscription(command);
        try {
            subscription = subscriptionRepository.save(subscription);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("create-subscription", e.getMessage()));
        }
        return Result.success(subscription.getId());
    }
}
