package com.techwatch.techwatchbackend.profiles.interfaces.rest.transform;

import com.techwatch.techwatchbackend.profiles.domain.model.commands.UpdateProfileCommand;
import com.techwatch.techwatchbackend.profiles.interfaces.rest.resources.UpdateProfileResource;

public class UpdateProfileCommandFromResourceAssembler {
    public static UpdateProfileCommand toCommandFromResource(Long profileId, UpdateProfileResource resource) {
        return new UpdateProfileCommand(profileId, resource.firstName(), resource.lastName(),
                resource.phoneNumber(), resource.profileImageUrl());
    }
}
