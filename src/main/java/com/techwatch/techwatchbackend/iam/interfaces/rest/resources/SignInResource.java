package com.techwatch.techwatchbackend.iam.interfaces.rest.resources;

public record SignInResource(String email, String password) {
    public SignInResource {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("email is required");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("password is required");
        }
    }
}
