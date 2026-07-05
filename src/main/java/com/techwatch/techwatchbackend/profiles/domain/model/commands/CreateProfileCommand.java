package com.techwatch.techwatchbackend.profiles.domain.model.commands;

public record CreateProfileCommand(Long userId, String firstName, String lastName,
                                   String phoneNumber, String profileImageUrl) {
    public CreateProfileCommand {
        if (userId == null || userId < 1) {
            throw new IllegalArgumentException("userId cannot be null or less than 1");
        }
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("firstName cannot be null or blank");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("lastName cannot be null or blank");
        }
    }
}
