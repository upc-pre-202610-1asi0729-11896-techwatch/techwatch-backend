package com.techwatch.techwatchbackend.subscriptions.application.commandservices;

import com.techwatch.techwatchbackend.shared.application.result.ApplicationError;
import com.techwatch.techwatchbackend.shared.application.result.Result;
import com.techwatch.techwatchbackend.subscriptions.domain.model.aggregates.Subscription;
import com.techwatch.techwatchbackend.subscriptions.domain.model.commands.CancelSubscriptionCommand;
import com.techwatch.techwatchbackend.subscriptions.domain.model.commands.RenewSubscriptionCommand;

/**
 * Application service contract for subscription commands.
 */
public interface SubscriptionCommandService {

    Result<Subscription, ApplicationError> handle(RenewSubscriptionCommand command);

    Result<Subscription, ApplicationError> handle(CancelSubscriptionCommand command);
}