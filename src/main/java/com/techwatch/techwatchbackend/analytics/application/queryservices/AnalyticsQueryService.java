package com.techwatch.techwatchbackend.analytics.application.queryservices;

import com.techwatch.techwatchbackend.analytics.domain.model.aggregates.ConsumptionAlert;
import com.techwatch.techwatchbackend.analytics.domain.model.aggregates.ConsumptionMetric;
import com.techwatch.techwatchbackend.analytics.domain.model.aggregates.ConsumptionReport;
import com.techwatch.techwatchbackend.analytics.domain.model.queries.GetAlertsByUserIdQuery;
import com.techwatch.techwatchbackend.analytics.domain.model.queries.GetMetricsByPropertyIdQuery;
import com.techwatch.techwatchbackend.analytics.domain.model.queries.GetReportByIdQuery;
import com.techwatch.techwatchbackend.analytics.domain.model.queries.GetReportsByPropertyIdQuery;

import java.util.List;
import java.util.Optional;

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

    /**
     * Handles retrieval of the consumption reports of a property.
     *
     * @param query property-id query
     * @return list of reports for the property
     * @see GetReportsByPropertyIdQuery
     */
    List<ConsumptionReport> handle(GetReportsByPropertyIdQuery query);

    /**
     * Handles retrieval of a consumption report by id.
     *
     * @param query report-id query
     * @return matching report, if found
     * @see GetReportByIdQuery
     */
    Optional<ConsumptionReport> handle(GetReportByIdQuery query);
}
