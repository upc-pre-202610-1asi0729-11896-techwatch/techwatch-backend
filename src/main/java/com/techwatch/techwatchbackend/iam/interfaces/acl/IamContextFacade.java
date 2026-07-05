package com.techwatch.techwatchbackend.iam.interfaces.acl;

/**
 * Facade exposed by the IAM context to other bounded contexts (published language).
 */
public interface IamContextFacade {
    /**
     * Fetch the id of the user with the given email.
     *
     * @param email The user email
     * @return The user id, or 0 if no user is found
     */
    Long fetchUserIdByEmail(String email);

    /**
     * Fetch the email of the user with the given id.
     *
     * @param userId The user id
     * @return The user email, or an empty string if no user is found
     */
    String fetchUserEmailById(Long userId);
}
