package com.techwatch.techwatchbackend.subscriptions.application.commandservices;

import com.techwatch.techwatchbackend.shared.application.result.ApplicationError;
import com.techwatch.techwatchbackend.shared.application.result.Result;
import com.techwatch.techwatchbackend.subscriptions.domain.model.aggregates.Payment;
import com.techwatch.techwatchbackend.subscriptions.domain.model.aggregates.Subscription;
import com.techwatch.techwatchbackend.subscriptions.domain.model.commands.CancelSubscriptionCommand;
import com.techwatch.techwatchbackend.subscriptions.domain.model.commands.ChangeSubscriptionPlanCommand;
import com.techwatch.techwatchbackend.subscriptions.domain.model.commands.CreateSubscriptionCommand;
import com.techwatch.techwatchbackend.subscriptions.domain.model.commands.ProcessPaymentCommand;
import com.techwatch.techwatchbackend.subscriptions.domain.model.commands.RenewSubscriptionCommand;

public interface SubscriptionCommandService {
    Result<Long, ApplicationError> handle(CreateSubscriptionCommand command);
    Result<Subscription, ApplicationError> handle(RenewSubscriptionCommand command);
    Result<Subscription, ApplicationError> handle(CancelSubscriptionCommand command);
    Result<Subscription, ApplicationError> handle(ChangeSubscriptionPlanCommand command);
    Result<Payment, ApplicationError> handle(ProcessPaymentCommand command);
}
