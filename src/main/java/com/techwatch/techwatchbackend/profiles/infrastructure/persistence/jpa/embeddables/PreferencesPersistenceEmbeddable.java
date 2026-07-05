package com.techwatch.techwatchbackend.profiles.infrastructure.persistence.jpa.embeddables;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * Persistence representation for a preferences value object.
 */
@Embeddable
public class PreferencesPersistenceEmbeddable {

    @Column(name = "language")
    private String language;

    @Column(name = "theme")
    private String theme;

    @Column(name = "notifications_enabled")
    private Boolean notificationsEnabled;

    public PreferencesPersistenceEmbeddable() {
    }

    public PreferencesPersistenceEmbeddable(String language, String theme, Boolean notificationsEnabled) {
        this.language = language;
        this.theme = theme;
        this.notificationsEnabled = notificationsEnabled;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Boolean getNotificationsEnabled() {
        return notificationsEnabled;
    }

    public void setNotificationsEnabled(Boolean notificationsEnabled) {
        this.notificationsEnabled = notificationsEnabled;
    }
}
