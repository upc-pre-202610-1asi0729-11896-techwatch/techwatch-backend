package com.techwatch.techwatchbackend.analytics.application.queryservices;

import com.techwatch.techwatchbackend.analytics.domain.model.aggregates.ConsumptionMetric;
import com.techwatch.techwatchbackend.analytics.domain.model.queries.GetMetricsByPropertyIdQuery;

import java.util.List;

/**
 * Application service contract for Analytics read queries.
 */
public interface AnalyticsQueryService {
    /**
     * Handles retrieval of the consumption metrics of a property.
     *
     * @param query property-id query
     * @return list of metrics for the property
     * @see GetMetricsByPropertyIdQuery
     */
    List<ConsumptionMetric> handle(GetMetricsByPropertyIdQuery query);
}
