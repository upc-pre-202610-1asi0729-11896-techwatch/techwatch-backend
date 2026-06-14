package com.techwatch.techwatchbackend.analytics.domain.model.queries;

/**
 * Query to retrieve a consumption report by its unique identifier.
 *
 * @param reportId The id of the report. Cannot be null or less than 1.
 */
public record GetReportByIdQuery(Long reportId) {
    /**
     * Compact constructor for GetReportByIdQuery.
     * @throws IllegalArgumentException if the reportId is null or less than 1.
     */
    public GetReportByIdQuery {
        if (reportId == null || reportId < 1) {
            throw new IllegalArgumentException("reportId cannot be null or less than 1");
        }
    }
}
