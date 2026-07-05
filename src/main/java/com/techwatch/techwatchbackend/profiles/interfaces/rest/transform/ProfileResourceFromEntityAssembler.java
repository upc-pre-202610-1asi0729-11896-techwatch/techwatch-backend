package com.techwatch.techwatchbackend.profiles.interfaces.rest.transform;

import com.techwatch.techwatchbackend.profiles.domain.model.aggregates.Profile;
import com.techwatch.techwatchbackend.profiles.interfaces.rest.resources.PreferencesResource;
import com.techwatch.techwatchbackend.profiles.interfaces.rest.resources.ProfileResource;

public class ProfileResourceFromEntityAssembler {
    public static ProfileResource toResourceFromEntity(Profile entity) {
        return new ProfileResource(
                entity.getId(),
                entity.getUserId().userId(),
                entity.getFullName().firstName(),
                entity.getFullName().lastName(),
                entity.getPhoneNumber(),
                entity.getProfileImageUrl(),
                new PreferencesResource(entity.getPreferences().language(),
                        entity.getPreferences().theme(),
                        entity.getPreferences().notificationsEnabled()));
    }
}
