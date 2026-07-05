package com.techwatch.techwatchbackend.profiles.interfaces.rest.resources;

public record UpdateProfileResource(String firstName, String lastName,
                                    String phoneNumber, String profileImageUrl) {
    public UpdateProfileResource {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("firstName is required");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("lastName is required");
        }
    }
}
