package com.techwatch.techwatchbackend.profiles.application.commandservices;

import com.techwatch.techwatchbackend.profiles.domain.model.aggregates.Profile;
import com.techwatch.techwatchbackend.profiles.domain.model.commands.CreateProfileCommand;
import com.techwatch.techwatchbackend.profiles.domain.model.commands.UpdateProfileCommand;
import com.techwatch.techwatchbackend.shared.application.result.ApplicationError;
import com.techwatch.techwatchbackend.shared.application.result.Result;

public interface ProfileCommandService {
    Result<Long, ApplicationError> handle(CreateProfileCommand command);
    Result<Profile, ApplicationError> handle(UpdateProfileCommand command);
}
