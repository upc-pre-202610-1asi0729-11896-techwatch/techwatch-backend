package com.techwatch.techwatchbackend.profiles.domain.model.valueobjects;

public record PersonName(String firstName, String lastName) {
    public PersonName {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("firstName cannot be null or blank");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("lastName cannot be null or blank");
        }
    }

    public String getFullName() {
        return "%s %s".formatted(firstName, lastName);
    }
}
