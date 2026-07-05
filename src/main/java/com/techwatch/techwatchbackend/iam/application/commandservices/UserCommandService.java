package com.techwatch.techwatchbackend.iam.application.commandservices;

import com.techwatch.techwatchbackend.iam.domain.model.aggregates.User;
import com.techwatch.techwatchbackend.iam.domain.model.commands.SignUpCommand;
import com.techwatch.techwatchbackend.shared.application.result.ApplicationError;
import com.techwatch.techwatchbackend.shared.application.result.Result;

public interface UserCommandService {
    Result<Long, ApplicationError> handle(SignUpCommand command);
}
