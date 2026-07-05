package com.techwatch.techwatchbackend.iam.infrastructure.persistence.jpa.adapters;

import com.techwatch.techwatchbackend.iam.domain.model.aggregates.User;
import com.techwatch.techwatchbackend.iam.domain.model.events.UserRegisteredEvent;
import com.techwatch.techwatchbackend.iam.domain.model.valueobjects.EmailAddress;
import com.techwatch.techwatchbackend.iam.domain.repositories.UserRepository;
import com.techwatch.techwatchbackend.iam.infrastructure.persistence.jpa.assemblers.UserPersistenceAssembler;
import com.techwatch.techwatchbackend.iam.infrastructure.persistence.jpa.repositories.UserPersistenceRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserPersistenceRepository userPersistenceRepository;
    private final ApplicationEventPublisher eventPublisher;

    public UserRepositoryImpl(UserPersistenceRepository userPersistenceRepository,
                              ApplicationEventPublisher eventPublisher) {
        this.userPersistenceRepository = userPersistenceRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Optional<User> findById(Long id) {
        return userPersistenceRepository.findById(id).map(UserPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<User> findByEmail(EmailAddress email) {
        return userPersistenceRepository.findByEmail(email).map(UserPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public boolean existsByEmail(EmailAddress email) {
        return userPersistenceRepository.existsByEmail(email);
    }

    @Override
    public User save(User user) {
        var saved = userPersistenceRepository.save(UserPersistenceAssembler.toPersistenceFromDomain(user));
        var result = UserPersistenceAssembler.toDomainFromPersistence(saved);
        // Publish domain events registered on the aggregate (e.g. UserRegisteredEvent on sign-up) once
        // persisted, substituting the userId placeholder with the generated id.
        user.domainEvents().forEach(event -> eventPublisher.publishEvent(
                event instanceof UserRegisteredEvent registered
                        ? new UserRegisteredEvent(result.getId(), registered.email(), registered.firstName(), registered.lastName())
                        : event));
        user.clearDomainEvents();
        return result;
    }
}
