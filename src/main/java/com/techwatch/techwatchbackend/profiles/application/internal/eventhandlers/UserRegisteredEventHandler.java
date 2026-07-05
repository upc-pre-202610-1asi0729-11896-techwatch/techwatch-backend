package com.techwatch.techwatchbackend.profiles.application.internal.eventhandlers;

import com.techwatch.techwatchbackend.iam.interfaces.events.UserRegisteredIntegrationEvent;
import com.techwatch.techwatchbackend.profiles.application.commandservices.ProfileCommandService;
import com.techwatch.techwatchbackend.profiles.domain.model.commands.CreateProfileCommand;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Profiles event handler that reacts to a user registering in the IAM context.
 *
 * <p>Subscribes to the {@link UserRegisteredIntegrationEvent} (the published language of the
 * {@code iam} context) and automatically creates the corresponding profile.</p>
 */
@Service("profilesUserRegisteredEventHandler")
public class UserRegisteredEventHandler {

    private final ProfileCommandService profileCommandService;

    public UserRegisteredEventHandler(ProfileCommandService profileCommandService) {
        this.profileCommandService = profileCommandService;
    }

    @EventListener
    public void on(UserRegisteredIntegrationEvent event) {
        profileCommandService.handle(new CreateProfileCommand(
                event.userId(), event.firstName(), event.lastName(), null, null));
    }
}
