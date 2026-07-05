package com.techwatch.techwatchbackend.profiles.interfaces.rest.resources;

public record UpdatePreferencesResource(String language, String theme, Boolean notificationsEnabled) {
    public UpdatePreferencesResource {
        if (language == null || language.isBlank()) {
            throw new IllegalArgumentException("language is required");
        }
        if (theme == null || theme.isBlank()) {
            throw new IllegalArgumentException("theme is required");
        }
        if (notificationsEnabled == null) {
            throw new IllegalArgumentException("notificationsEnabled is required");
        }
    }
}
