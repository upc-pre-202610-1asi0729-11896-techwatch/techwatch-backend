package com.techwatch.techwatchbackend.analytics.application.internal.commandservices;

import com.techwatch.techwatchbackend.analytics.application.commandservices.AnalyticsCommandService;
import com.techwatch.techwatchbackend.analytics.domain.model.aggregates.ConsumptionMetric;
import com.techwatch.techwatchbackend.analytics.domain.model.commands.CalculateMetricsCommand;
import com.techwatch.techwatchbackend.analytics.domain.repositories.ConsumptionMetricRepository;
import com.techwatch.techwatchbackend.shared.application.result.ApplicationError;
import com.techwatch.techwatchbackend.shared.application.result.Result;
import org.springframework.stereotype.Service;

/**
 * Application service that executes Analytics commands.
 */
@Service
public class AnalyticsCommandServiceImpl implements AnalyticsCommandService {
    private final ConsumptionMetricRepository consumptionMetricRepository;

    public AnalyticsCommandServiceImpl(ConsumptionMetricRepository consumptionMetricRepository) {
        this.consumptionMetricRepository = consumptionMetricRepository;
    }

    @Override
    public Result<Long, ApplicationError> handle(CalculateMetricsCommand command) {
        var metric = new ConsumptionMetric(command);
        try {
            metric = consumptionMetricRepository.save(metric);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("calculate-metrics", e.getMessage()));
        }
        return Result.success(metric.getId());
    }
}
