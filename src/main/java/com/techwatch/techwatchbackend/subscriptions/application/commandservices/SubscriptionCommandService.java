package com.techwatch.techwatchbackend.subscriptions.application.commandservices;

import com.techwatch.techwatchbackend.shared.application.result.ApplicationError;
import com.techwatch.techwatchbackend.shared.application.result.Result;
import com.techwatch.techwatchbackend.subscriptions.domain.model.aggregates.Subscription;
import com.techwatch.techwatchbackend.subscriptions.domain.model.commands.RenewSubscriptionCommand;

/**
 * Application service contract for commands over the {@code Subscription} aggregate.
 */
public interface SubscriptionCommandService {

    /**
     * Handles renewing an existing subscription.
     *
     * @param command command containing the subscription id and renewal period
     * @return the updated subscription aggregate or an application error
     * @see RenewSubscriptionCommand
     */
    Result<Subscription, ApplicationError> handle(RenewSubscriptionCommand command);
}
