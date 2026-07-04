package com.techwatch.techwatchbackend.subscriptions.application.commandservices;

import com.techwatch.techwatchbackend.shared.application.result.ApplicationError;
import com.techwatch.techwatchbackend.shared.application.result.Result;
import com.techwatch.techwatchbackend.subscriptions.domain.model.commands.CreateSubscriptionCommand;

public interface SubscriptionCommandService {
    Result<Long, ApplicationError> handle(CreateSubscriptionCommand command);
}
