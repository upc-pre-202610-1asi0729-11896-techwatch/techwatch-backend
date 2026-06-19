package com.techwatch.techwatchbackend.subscriptions.infrastructure.persistence.jpa.repositories;

import com.techwatch.techwatchbackend.subscriptions.domain.model.aggregates.Subscription;
import com.techwatch.techwatchbackend.subscriptions.domain.repositories.SubscriptionRepository;
import com.techwatch.techwatchbackend.subscriptions.infrastructure.persistence.jpa.entities.SubscriptionJpaEntity;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA adapter for the subscription repository port.
 */
@Repository
public class JpaSubscriptionRepositoryAdapter implements SubscriptionRepository {

    private final SubscriptionJpaRepository subscriptionJpaRepository;
    private final ApplicationEventPublisher eventPublisher;

    public JpaSubscriptionRepositoryAdapter(
            SubscriptionJpaRepository subscriptionJpaRepository,
            ApplicationEventPublisher eventPublisher
    ) {
        this.subscriptionJpaRepository = subscriptionJpaRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Optional<Subscription> findById(Long id) {
        return subscriptionJpaRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public List<Subscription> findAll() {
        return subscriptionJpaRepository.findAll()
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public List<Subscription> findAllByUserId(Long userId) {
        return subscriptionJpaRepository.findAllByUserId(userId)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public Subscription save(Subscription subscription) {
        var entity = toEntity(subscription);
        var savedEntity = subscriptionJpaRepository.save(entity);

        subscription.domainEvents().forEach(eventPublisher::publishEvent);
        subscription.clearDomainEvents();

        return toDomain(savedEntity);
    }

    @Override
    public boolean existsById(Long id) {
        return subscriptionJpaRepository.existsById(id);
    }

    private Subscription toDomain(SubscriptionJpaEntity entity) {
        var subscription = new Subscription();

        subscription.setId(entity.getId());
        subscription.setUserId(entity.getUserId());
        subscription.setPlanName(entity.getPlanName());
        subscription.setStartDate(entity.getStartDate());
        subscription.setEndDate(entity.getEndDate());
        subscription.setStatus(entity.getStatus());

        return subscription;
    }

    private SubscriptionJpaEntity toEntity(Subscription subscription) {
        var entity = new SubscriptionJpaEntity();

        entity.setId(subscription.getId());
        entity.setUserId(subscription.getUserId());
        entity.setPlanName(subscription.getPlanName());
        entity.setStartDate(subscription.getStartDate());
        entity.setEndDate(subscription.getEndDate());
        entity.setStatus(subscription.getStatus());

        return entity;
    }
}