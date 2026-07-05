package com.techwatch.techwatchbackend.subscriptions.infrastructure.persistence.jpa.entities;

import com.techwatch.techwatchbackend.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects.PaymentStatus;
import com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects.SubscriptionId;
import com.techwatch.techwatchbackend.subscriptions.infrastructure.persistence.jpa.converters.SubscriptionIdPersistenceConverter;
import com.techwatch.techwatchbackend.subscriptions.infrastructure.persistence.jpa.embeddables.MoneyPersistenceEmbeddable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
public class PaymentPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Convert(converter = SubscriptionIdPersistenceConverter.class)
    @Column(name = "subscription_id", nullable = false)
    private SubscriptionId subscriptionId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "amount_value", nullable = false)),
            @AttributeOverride(name = "currency", column = @Column(name = "amount_currency", nullable = false))
    })
    private MoneyPersistenceEmbeddable amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @Column(name = "external_payment_id")
    private String externalPaymentId;

    @Column(name = "processed_at")
    private LocalDateTime processedAt;
}
