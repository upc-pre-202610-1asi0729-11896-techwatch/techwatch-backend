package com.techwatch.techwatchbackend.iam.domain.model.commands;

public record SignInCommand(String email, String password) {
    public SignInCommand {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("email cannot be null or blank");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("password cannot be null or blank");
        }
    }
}
