package com.techwatch.techwatchbackend.iam.domain.model.valueobjects;

import java.util.regex.Pattern;

public record EmailAddress(String address) {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    public EmailAddress {
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("Email address cannot be null or blank");
        }
        if (!EMAIL_PATTERN.matcher(address).matches()) {
            throw new IllegalArgumentException("Email address must be a valid format");
        }
    }
}
