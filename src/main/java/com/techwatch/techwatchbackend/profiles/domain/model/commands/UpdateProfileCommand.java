package com.techwatch.techwatchbackend.profiles.domain.model.commands;

public record UpdateProfileCommand(Long profileId, String firstName, String lastName,
                                   String phoneNumber, String profileImageUrl) {
    public UpdateProfileCommand {
        if (profileId == null || profileId < 1) {
            throw new IllegalArgumentException("profileId cannot be null or less than 1");
        }
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("firstName cannot be null or blank");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("lastName cannot be null or blank");
        }
    }
}
