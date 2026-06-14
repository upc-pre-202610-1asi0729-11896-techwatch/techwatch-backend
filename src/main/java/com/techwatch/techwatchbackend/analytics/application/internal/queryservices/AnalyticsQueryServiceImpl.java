package com.techwatch.techwatchbackend.analytics.application.internal.queryservices;

import com.techwatch.techwatchbackend.analytics.application.queryservices.AnalyticsQueryService;
import com.techwatch.techwatchbackend.analytics.domain.model.aggregates.ConsumptionMetric;
import com.techwatch.techwatchbackend.analytics.domain.model.queries.GetMetricsByPropertyIdQuery;
import com.techwatch.techwatchbackend.analytics.domain.repositories.ConsumptionMetricRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Application service that resolves Analytics read queries.
 */
@Service
public class AnalyticsQueryServiceImpl implements AnalyticsQueryService {
    private final ConsumptionMetricRepository consumptionMetricRepository;

    public AnalyticsQueryServiceImpl(ConsumptionMetricRepository consumptionMetricRepository) {
        this.consumptionMetricRepository = consumptionMetricRepository;
    }

    @Override
    public List<ConsumptionMetric> handle(GetMetricsByPropertyIdQuery query) {
        return consumptionMetricRepository.findByPropertyId(query.propertyId());
    }
}
