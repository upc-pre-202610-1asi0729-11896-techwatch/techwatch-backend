package com.techwatch.techwatchbackend.iam.interfaces.events;

import com.techwatch.techwatchbackend.iam.domain.model.events.UserRegisteredEvent;

/**
 * Integration event published by the {@code iam} bounded context when a user registers.
 *
 * <p>This is the <em>published language</em> of the {@code iam} context. Other bounded contexts
 * (e.g. {@code profiles}) must listen to this event rather than to the internal domain event
 * {@link UserRegisteredEvent}.</p>
 *
 * @param userId The id of the registered user.
 * @param email The email of the registered user.
 * @param firstName The first name provided at sign-up.
 * @param lastName The last name provided at sign-up.
 */
public record UserRegisteredIntegrationEvent(Long userId, String email, String firstName, String lastName) {

    public static UserRegisteredIntegrationEvent from(UserRegisteredEvent event) {
        return new UserRegisteredIntegrationEvent(event.userId(), event.email(), event.firstName(), event.lastName());
    }
}
