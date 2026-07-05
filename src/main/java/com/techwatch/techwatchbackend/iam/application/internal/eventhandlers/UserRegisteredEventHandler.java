package com.techwatch.techwatchbackend.iam.application.internal.eventhandlers;

import com.techwatch.techwatchbackend.iam.domain.model.events.UserRegisteredEvent;
import com.techwatch.techwatchbackend.iam.interfaces.events.UserRegisteredIntegrationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Internal application-layer handler for the {@link UserRegisteredEvent} domain event.
 *
 * <p>Translates the internal domain event into a {@link UserRegisteredIntegrationEvent} and
 * re-publishes it on the Spring event bus. Other bounded contexts (e.g. {@code profiles}) must
 * subscribe to {@link UserRegisteredIntegrationEvent}, never to the internal domain event.</p>
 */
@Service("iamUserRegisteredEventHandler")
public class UserRegisteredEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    public UserRegisteredEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    public void on(UserRegisteredEvent event) {
        eventPublisher.publishEvent(UserRegisteredIntegrationEvent.from(event));
    }
}
