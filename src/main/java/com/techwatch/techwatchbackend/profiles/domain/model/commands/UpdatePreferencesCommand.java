package com.techwatch.techwatchbackend.profiles.domain.model.commands;

public record UpdatePreferencesCommand(Long profileId, String language, String theme,
                                       Boolean notificationsEnabled) {
    public UpdatePreferencesCommand {
        if (profileId == null || profileId < 1) {
            throw new IllegalArgumentException("profileId cannot be null or less than 1");
        }
        if (language == null || language.isBlank()) {
            throw new IllegalArgumentException("language cannot be null or blank");
        }
        if (theme == null || theme.isBlank()) {
            throw new IllegalArgumentException("theme cannot be null or blank");
        }
        if (notificationsEnabled == null) {
            throw new IllegalArgumentException("notificationsEnabled cannot be null");
        }
    }
}
