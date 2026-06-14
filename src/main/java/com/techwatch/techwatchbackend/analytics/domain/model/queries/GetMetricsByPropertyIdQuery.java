package com.techwatch.techwatchbackend.analytics.domain.model.queries;

import com.techwatch.techwatchbackend.analytics.domain.model.valueobjects.PropertyId;

/**
 * Query to retrieve all the consumption metrics of a given property.
 *
 * @param propertyId The id of the property. Cannot be null.
 */
public record GetMetricsByPropertyIdQuery(PropertyId propertyId) {
    /**
     * Compact constructor for GetMetricsByPropertyIdQuery.
     * @throws IllegalArgumentException if the propertyId is null.
     */
    public GetMetricsByPropertyIdQuery {
        if (propertyId == null) {
            throw new IllegalArgumentException("propertyId cannot be null");
        }
    }
}
