package com.techwatch.techwatchbackend.profiles.application.internal.commandservices;

import com.techwatch.techwatchbackend.profiles.application.commandservices.ProfileCommandService;
import com.techwatch.techwatchbackend.profiles.domain.model.aggregates.Profile;
import com.techwatch.techwatchbackend.profiles.domain.model.commands.CreateProfileCommand;
import com.techwatch.techwatchbackend.profiles.domain.model.valueobjects.UserId;
import com.techwatch.techwatchbackend.profiles.domain.repositories.ProfileRepository;
import com.techwatch.techwatchbackend.shared.application.result.ApplicationError;
import com.techwatch.techwatchbackend.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class ProfileCommandServiceImpl implements ProfileCommandService {

    private final ProfileRepository profileRepository;

    public ProfileCommandServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Result<Long, ApplicationError> handle(CreateProfileCommand command) {
        if (profileRepository.existsByUserId(new UserId(command.userId()))) {
            return Result.failure(ApplicationError.conflict("Profile",
                    "User with id %s already has a profile".formatted(command.userId())));
        }
        var profile = new Profile(command);
        try {
            profile = profileRepository.save(profile);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("create-profile", e.getMessage()));
        }
        return Result.success(profile.getId());
    }
}
