package com.techwatch.techwatchbackend.profiles.interfaces.acl;

/**
 * Facade exposed by the Profiles context to other bounded contexts (published language).
 */
public interface ProfilesContextFacade {
    /**
     * Create a profile for the given user.
     *
     * @param userId The user id
     * @param firstName The first name
     * @param lastName The last name
     * @return The created profile id, or 0 if the profile could not be created
     */
    Long createProfile(Long userId, String firstName, String lastName);

    /**
     * Fetch the profile id of the given user.
     *
     * @param userId The user id
     * @return The profile id, or 0 if the user has no profile
     */
    Long fetchProfileIdByUserId(Long userId);
}
