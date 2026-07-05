package com.techwatch.techwatchbackend.profiles.interfaces.rest.resources;

public record ProfileResource(
        Long id,
        Long userId,
        String firstName,
        String lastName,
        String phoneNumber,
        String profileImageUrl,
        PreferencesResource preferences) {
}
