package com.techwatch.techwatchbackend.subscriptions.infrastructure.payments;

import com.techwatch.techwatchbackend.subscriptions.application.internal.outboundservices.payments.ExternalPaymentService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * Simulated adapter towards the external payment provider.
 *
 * <p>
 * Stands in for a real HTTP integration (e.g. Stripe): it always approves the charge
 * and returns a generated external payment id.
 * </p>
 */
@Service
public class SimulatedPaymentService implements ExternalPaymentService {

    @Override
    public Optional<String> processPayment(Long subscriptionId, Double amount, String currency) {
        return Optional.of("pay_%s".formatted(UUID.randomUUID()));
    }
}
