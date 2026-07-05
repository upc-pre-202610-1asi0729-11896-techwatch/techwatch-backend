package com.techwatch.techwatchbackend.profiles.application.acl;

import com.techwatch.techwatchbackend.profiles.application.commandservices.ProfileCommandService;
import com.techwatch.techwatchbackend.profiles.application.queryservices.ProfileQueryService;
import com.techwatch.techwatchbackend.profiles.domain.model.aggregates.Profile;
import com.techwatch.techwatchbackend.profiles.domain.model.commands.CreateProfileCommand;
import com.techwatch.techwatchbackend.profiles.domain.model.queries.GetProfileByUserIdQuery;
import com.techwatch.techwatchbackend.profiles.domain.model.valueobjects.UserId;
import com.techwatch.techwatchbackend.profiles.interfaces.acl.ProfilesContextFacade;
import com.techwatch.techwatchbackend.shared.application.result.ApplicationError;
import com.techwatch.techwatchbackend.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class ProfilesContextFacadeImpl implements ProfilesContextFacade {

    private final ProfileCommandService profileCommandService;
    private final ProfileQueryService profileQueryService;

    public ProfilesContextFacadeImpl(ProfileCommandService profileCommandService,
                                     ProfileQueryService profileQueryService) {
        this.profileCommandService = profileCommandService;
        this.profileQueryService = profileQueryService;
    }

    @Override
    public Long createProfile(Long userId, String firstName, String lastName) {
        var result = profileCommandService.handle(new CreateProfileCommand(userId, firstName, lastName, null, null));
        return switch (result) {
            case Result.Success<Long, ApplicationError> success -> success.value();
            case Result.Failure<Long, ApplicationError> ignored -> 0L;
        };
    }

    @Override
    public Long fetchProfileIdByUserId(Long userId) {
        return profileQueryService.handle(new GetProfileByUserIdQuery(new UserId(userId)))
                .map(Profile::getId)
                .orElse(0L);
    }
}
