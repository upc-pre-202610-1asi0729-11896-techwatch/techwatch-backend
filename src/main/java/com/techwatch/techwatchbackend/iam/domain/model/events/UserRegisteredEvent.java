package com.techwatch.techwatchbackend.iam.domain.model.events;

/**
 * Domain event published when a user registers.
 *
 * <p>Consumed by the Profiles bounded context to automatically create the user's profile.
 * The payload uses primitive values so consumers do not depend on the IAM domain model.</p>
 *
 * @param userId The id of the registered user.
 * @param email The email of the registered user.
 * @param firstName The first name provided at sign-up.
 * @param lastName The last name provided at sign-up.
 */
public record UserRegisteredEvent(Long userId, String email, String firstName, String lastName) {
}
