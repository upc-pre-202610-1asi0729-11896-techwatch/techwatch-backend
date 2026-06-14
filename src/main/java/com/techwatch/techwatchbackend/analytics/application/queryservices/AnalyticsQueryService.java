package com.techwatch.techwatchbackend.analytics.application.queryservices;

import com.techwatch.techwatchbackend.analytics.domain.model.aggregates.ConsumptionAlert;
import com.techwatch.techwatchbackend.analytics.domain.model.aggregates.ConsumptionMetric;
import com.techwatch.techwatchbackend.analytics.domain.model.queries.GetAlertsByUserIdQuery;
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

    /**
     * Handles retrieval of the consumption alerts of a user.
     *
     * @param query user-id query
     * @return list of alerts for the user
     * @see GetAlertsByUserIdQuery
     */
    List<ConsumptionAlert> handle(GetAlertsByUserIdQuery query);
}
