package com.techwatch.techwatchbackend.profiles.domain.model.valueobjects;

public record Preferences(String language, String theme, Boolean notificationsEnabled) {
    public Preferences {
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

    public static Preferences defaults() {
        return new Preferences("es", "light", true);
    }
}
