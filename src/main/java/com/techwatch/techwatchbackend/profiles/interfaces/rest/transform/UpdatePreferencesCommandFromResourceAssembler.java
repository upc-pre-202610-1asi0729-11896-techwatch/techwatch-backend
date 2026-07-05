package com.techwatch.techwatchbackend.profiles.interfaces.rest.transform;

import com.techwatch.techwatchbackend.profiles.domain.model.commands.UpdatePreferencesCommand;
import com.techwatch.techwatchbackend.profiles.interfaces.rest.resources.UpdatePreferencesResource;

public class UpdatePreferencesCommandFromResourceAssembler {
    public static UpdatePreferencesCommand toCommandFromResource(Long profileId, UpdatePreferencesResource resource) {
        return new UpdatePreferencesCommand(profileId, resource.language(), resource.theme(),
                resource.notificationsEnabled());
    }
}
