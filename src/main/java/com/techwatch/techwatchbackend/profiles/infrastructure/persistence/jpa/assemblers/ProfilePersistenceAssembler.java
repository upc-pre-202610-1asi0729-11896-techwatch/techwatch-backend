package com.techwatch.techwatchbackend.profiles.infrastructure.persistence.jpa.assemblers;

import com.techwatch.techwatchbackend.profiles.domain.model.aggregates.Profile;
import com.techwatch.techwatchbackend.profiles.domain.model.valueobjects.PersonName;
import com.techwatch.techwatchbackend.profiles.domain.model.valueobjects.Preferences;
import com.techwatch.techwatchbackend.profiles.infrastructure.persistence.jpa.embeddables.PersonNamePersistenceEmbeddable;
import com.techwatch.techwatchbackend.profiles.infrastructure.persistence.jpa.embeddables.PreferencesPersistenceEmbeddable;
import com.techwatch.techwatchbackend.profiles.infrastructure.persistence.jpa.entities.ProfilePersistenceEntity;

public final class ProfilePersistenceAssembler {

    private ProfilePersistenceAssembler() {
    }

    public static Profile toDomainFromPersistence(ProfilePersistenceEntity entity) {
        if (entity == null) return null;

        var profile = new Profile();
        profile.setId(entity.getId());
        profile.setUserId(entity.getUserId());
        profile.setFullName(new PersonName(entity.getFullName().getFirstName(), entity.getFullName().getLastName()));
        profile.setPhoneNumber(entity.getPhoneNumber());
        profile.setProfileImageUrl(entity.getProfileImageUrl());
        profile.setPreferences(new Preferences(entity.getPreferences().getLanguage(),
                entity.getPreferences().getTheme(), entity.getPreferences().getNotificationsEnabled()));
        return profile;
    }

    public static ProfilePersistenceEntity toPersistenceFromDomain(Profile profile) {
        if (profile == null) return null;

        var entity = new ProfilePersistenceEntity();
        if (profile.getId() != null) {
            entity.setId(profile.getId());
        }
        entity.setUserId(profile.getUserId());
        entity.setFullName(new PersonNamePersistenceEmbeddable(
                profile.getFullName().firstName(), profile.getFullName().lastName()));
        entity.setPhoneNumber(profile.getPhoneNumber());
        entity.setProfileImageUrl(profile.getProfileImageUrl());
        entity.setPreferences(new PreferencesPersistenceEmbeddable(profile.getPreferences().language(),
                profile.getPreferences().theme(), profile.getPreferences().notificationsEnabled()));
        return entity;
    }
}
