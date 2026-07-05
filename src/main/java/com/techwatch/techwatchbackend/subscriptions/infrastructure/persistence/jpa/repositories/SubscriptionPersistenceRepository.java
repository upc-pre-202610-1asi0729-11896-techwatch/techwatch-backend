package com.techwatch.techwatchbackend.subscriptions.infrastructure.persistence.jpa.repositories;

import com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects.SubscriptionStatus;
import com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects.UserId;
import com.techwatch.techwatchbackend.subscriptions.infrastructure.persistence.jpa.entities.SubscriptionPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionPersistenceRepository extends JpaRepository<SubscriptionPersistenceEntity, Long> {
    Optional<SubscriptionPersistenceEntity> findByUserIdAndStatus(UserId userId, SubscriptionStatus status);
}
