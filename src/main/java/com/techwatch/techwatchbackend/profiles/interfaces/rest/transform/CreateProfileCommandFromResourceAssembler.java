package com.techwatch.techwatchbackend.profiles.interfaces.rest.transform;

import com.techwatch.techwatchbackend.profiles.domain.model.commands.CreateProfileCommand;
import com.techwatch.techwatchbackend.profiles.interfaces.rest.resources.CreateProfileResource;

public class CreateProfileCommandFromResourceAssembler {
    public static CreateProfileCommand toCommandFromResource(CreateProfileResource resource) {
        return new CreateProfileCommand(resource.userId(), resource.firstName(), resource.lastName(),
                resource.phoneNumber(), resource.profileImageUrl());
    }
}
