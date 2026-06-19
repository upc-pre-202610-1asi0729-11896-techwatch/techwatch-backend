package com.techwatch.techwatchbackend.subscriptions.interfaces.rest.resources;

import com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects.SubscriptionStatus;

import java.time.LocalDate;

/**
 * Resource returned by the subscriptions API.
 *
 * @param id subscription id
 * @param userId owner user id
 * @param planName subscription plan name
 * @param startDate subscription start date
 * @param endDate subscription end date
 * @param status subscription status
 */
public record SubscriptionResource(
        Long id,
        Long userId,
        String planName,
        LocalDate startDate,
        LocalDate endDate,
        SubscriptionStatus status
) {
}