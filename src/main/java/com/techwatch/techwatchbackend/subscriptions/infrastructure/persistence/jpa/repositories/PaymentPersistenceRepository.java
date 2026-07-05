package com.techwatch.techwatchbackend.subscriptions.infrastructure.persistence.jpa.repositories;

import com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects.SubscriptionId;
import com.techwatch.techwatchbackend.subscriptions.infrastructure.persistence.jpa.entities.PaymentPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentPersistenceRepository extends JpaRepository<PaymentPersistenceEntity, Long> {
    List<PaymentPersistenceEntity> findAllBySubscriptionId(SubscriptionId subscriptionId);
}
