package com.techwatch.techwatchbackend.analytics.domain.model.valueobjects;

import java.time.LocalDateTime;

/**
 * Value object representing the time period a consumption metric or report covers.
 *
 * @param startDate The start of the period. It cannot be null.
 * @param endDate The end of the period. It cannot be null or before the start.
 */
public record ConsumptionPeriod(LocalDateTime startDate, LocalDateTime endDate) {
    /**
     * Compact constructor for ConsumptionPeriod.
     * @throws IllegalArgumentException if dates are null or the end is before the start.
     */
    public ConsumptionPeriod {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("ConsumptionPeriod dates cannot be null");
        }
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("ConsumptionPeriod end date cannot be before the start date");
        }
    }
}
