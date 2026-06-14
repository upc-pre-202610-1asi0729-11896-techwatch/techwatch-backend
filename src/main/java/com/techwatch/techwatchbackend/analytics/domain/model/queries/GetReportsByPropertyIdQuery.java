package com.techwatch.techwatchbackend.analytics.domain.model.queries;

import com.techwatch.techwatchbackend.analytics.domain.model.valueobjects.PropertyId;

/**
 * Query to retrieve all the consumption reports of a given property.
 *
 * @param propertyId The id of the property. Cannot be null.
 */
public record GetReportsByPropertyIdQuery(PropertyId propertyId) {
    /**
     * Compact constructor for GetReportsByPropertyIdQuery.
     * @throws IllegalArgumentException if the propertyId is null.
     */
    public GetReportsByPropertyIdQuery {
        if (propertyId == null) {
            throw new IllegalArgumentException("propertyId cannot be null");
        }
    }
}
