package com.techwatch.techwatchbackend.analytics.domain.model.commands;

import java.time.LocalDateTime;

/**
 * Command to generate a consumption report for a property over a period.
 *
 * @param userId The id of the user requesting the report. Cannot be null or less than 1.
 * @param propertyId The id of the property. Cannot be null or less than 1.
 * @param startDate The start of the report period. Cannot be null.
 * @param endDate The end of the report period. Cannot be null or before the start.
 * @param isAutomatic Whether the report was generated automatically by the system (e.g. when a
 *                    simulation session ends) rather than requested on-demand by the user.
 */
public record GenerateConsumptionReportCommand(
        Long userId, Long propertyId, LocalDateTime startDate, LocalDateTime endDate, Boolean isAutomatic) {
    /**
     * Compact constructor for GenerateConsumptionReportCommand.
     * @throws IllegalArgumentException if any field is invalid.
     */
    public GenerateConsumptionReportCommand {
        if (userId == null || userId < 1) {
            throw new IllegalArgumentException("userId cannot be null or less than 1");
        }
        if (propertyId == null || propertyId < 1) {
            throw new IllegalArgumentException("propertyId cannot be null or less than 1");
        }
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("startDate and endDate cannot be null");
        }
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("endDate cannot be before startDate");
        }
        if (isAutomatic == null) {
            throw new IllegalArgumentException("isAutomatic cannot be null");
        }
    }
}
