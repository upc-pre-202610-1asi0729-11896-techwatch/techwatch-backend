package com.techwatch.techwatchbackend.analytics.application.internal.queryservices;

import com.techwatch.techwatchbackend.analytics.application.queryservices.AnalyticsQueryService;
import com.techwatch.techwatchbackend.analytics.domain.model.aggregates.ConsumptionAlert;
import com.techwatch.techwatchbackend.analytics.domain.model.aggregates.ConsumptionMetric;
import com.techwatch.techwatchbackend.analytics.domain.model.aggregates.ConsumptionReport;
import com.techwatch.techwatchbackend.analytics.domain.model.queries.GetAlertsByUserIdQuery;
import com.techwatch.techwatchbackend.analytics.domain.model.queries.GetMetricsByPropertyIdQuery;
import com.techwatch.techwatchbackend.analytics.domain.model.queries.GetReportByIdQuery;
import com.techwatch.techwatchbackend.analytics.domain.model.queries.GetReportsByPropertyIdQuery;
import com.techwatch.techwatchbackend.analytics.domain.repositories.ConsumptionAlertRepository;
import com.techwatch.techwatchbackend.analytics.domain.repositories.ConsumptionMetricRepository;
import com.techwatch.techwatchbackend.analytics.domain.repositories.ConsumptionReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Application service that resolves Analytics read queries.
 */
@Service
public class AnalyticsQueryServiceImpl implements AnalyticsQueryService {
    private final ConsumptionMetricRepository consumptionMetricRepository;
    private final ConsumptionAlertRepository consumptionAlertRepository;
    private final ConsumptionReportRepository consumptionReportRepository;

    public AnalyticsQueryServiceImpl(ConsumptionMetricRepository consumptionMetricRepository,
                                     ConsumptionAlertRepository consumptionAlertRepository,
                                     ConsumptionReportRepository consumptionReportRepository) {
        this.consumptionMetricRepository = consumptionMetricRepository;
        this.consumptionAlertRepository = consumptionAlertRepository;
        this.consumptionReportRepository = consumptionReportRepository;
    }

    @Override
    public List<ConsumptionMetric> handle(GetMetricsByPropertyIdQuery query) {
        return consumptionMetricRepository.findByPropertyId(query.propertyId());
    }

    @Override
    public List<ConsumptionAlert> handle(GetAlertsByUserIdQuery query) {
        return consumptionAlertRepository.findByUserId(query.userId());
    }

    @Override
    public List<ConsumptionReport> handle(GetReportsByPropertyIdQuery query) {
        return consumptionReportRepository.findByPropertyId(query.propertyId());
    }

    @Override
    public Optional<ConsumptionReport> handle(GetReportByIdQuery query) {
        return consumptionReportRepository.findById(query.reportId());
    }
}
