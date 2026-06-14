package com.techwatch.techwatchbackend.analytics.domain.model.commands;

/**
 * Command to mark a consumption alert as read.
 *
 * @param alertId The id of the alert to mark as read. Cannot be null or less than 1.
 */
public record MarkAlertAsReadCommand(Long alertId) {
    /**
     * Compact constructor for MarkAlertAsReadCommand.
     * @throws IllegalArgumentException if the alertId is null or less than 1.
     */
    public MarkAlertAsReadCommand {
        if (alertId == null || alertId < 1) {
            throw new IllegalArgumentException("alertId cannot be null or less than 1");
        }
    }
}
