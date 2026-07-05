package com.techwatch.techwatchbackend.profiles.interfaces.rest.resources;

public record CreateProfileResource(Long userId, String firstName, String lastName,
                                    String phoneNumber, String profileImageUrl) {
    public CreateProfileResource {
        if (userId == null) {
            throw new IllegalArgumentException("userId is required");
        }
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("firstName is required");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("lastName is required");
        }
    }
}
