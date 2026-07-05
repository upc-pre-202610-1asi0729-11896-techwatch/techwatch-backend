package com.techwatch.techwatchbackend.subscriptions.application.internal.outboundservices.payments;

import java.util.Optional;

/**
 * Outbound port towards the external payment service (e.g. Stripe).
 */
public interface ExternalPaymentService {
    /**
     * Request the external provider to charge the given amount.
     *
     * @param subscriptionId The subscription the payment belongs to
     * @param amount The amount to charge
     * @param currency The currency of the amount
     * @return The external payment id if the charge succeeded, empty otherwise
     */
    Optional<String> processPayment(Long subscriptionId, Double amount, String currency);
}
