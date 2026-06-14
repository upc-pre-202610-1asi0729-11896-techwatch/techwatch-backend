package com.techwatch.techwatchbackend.analytics.application.internal.queryservices;

import com.techwatch.techwatchbackend.analytics.application.queryservices.AnalyticsQueryService;
import com.techwatch.techwatchbackend.analytics.domain.model.aggregates.ConsumptionAlert;
import com.techwatch.techwatchbackend.analytics.domain.model.aggregates.ConsumptionMetric;
import com.techwatch.techwatchbackend.analytics.domain.model.queries.GetAlertsByUserIdQuery;
import com.techwatch.techwatchbackend.analytics.domain.model.queries.GetMetricsByPropertyIdQuery;
import com.techwatch.techwatchbackend.analytics.domain.repositories.ConsumptionAlertRepository;
import com.techwatch.techwatchbackend.analytics.domain.repositories.ConsumptionMetricRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Application service that resolves Analytics read queries.
 */
@Service
public class AnalyticsQueryServiceImpl implements AnalyticsQueryService {
    private final ConsumptionMetricRepository consumptionMetricRepository;
    private final ConsumptionAlertRepository consumptionAlertRepository;

    public AnalyticsQueryServiceImpl(ConsumptionMetricRepository consumptionMetricRepository,
                                     ConsumptionAlertRepository consumptionAlertRepository) {
        this.consumptionMetricRepository = consumptionMetricRepository;
        this.consumptionAlertRepository = consumptionAlertRepository;
    }

    @Override
    public List<ConsumptionMetric> handle(GetMetricsByPropertyIdQuery query) {
        return consumptionMetricRepository.findByPropertyId(query.propertyId());
    }

    @Override
    public List<ConsumptionAlert> handle(GetAlertsByUserIdQuery query) {
        return consumptionAlertRepository.findByUserId(query.userId());
    }
}
