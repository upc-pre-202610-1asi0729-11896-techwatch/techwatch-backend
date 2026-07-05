package com.techwatch.techwatchbackend.subscriptions.infrastructure.persistence.jpa.repositories;

import com.techwatch.techwatchbackend.subscriptions.infrastructure.persistence.jpa.entities.SubscriptionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for subscription entities.
 */
public interface SubscriptionJpaRepository extends JpaRepository<SubscriptionJpaEntity, Long> {
    List<SubscriptionJpaEntity> findAllByUserId(Long userId);
}
