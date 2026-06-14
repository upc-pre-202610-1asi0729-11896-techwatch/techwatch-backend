package com.techwatch.techwatchbackend.analytics.application.internal.commandservices;

import com.techwatch.techwatchbackend.analytics.application.commandservices.AnalyticsCommandService;
import com.techwatch.techwatchbackend.analytics.domain.model.aggregates.ConsumptionAlert;
import com.techwatch.techwatchbackend.analytics.domain.model.aggregates.ConsumptionMetric;
import com.techwatch.techwatchbackend.analytics.domain.model.commands.CalculateMetricsCommand;
import com.techwatch.techwatchbackend.analytics.domain.model.commands.MarkAlertAsReadCommand;
import com.techwatch.techwatchbackend.analytics.domain.model.commands.TriggerConsumptionAlertCommand;
import com.techwatch.techwatchbackend.analytics.domain.repositories.ConsumptionAlertRepository;
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
    private final ConsumptionAlertRepository consumptionAlertRepository;

    public AnalyticsCommandServiceImpl(ConsumptionMetricRepository consumptionMetricRepository,
                                       ConsumptionAlertRepository consumptionAlertRepository) {
        this.consumptionMetricRepository = consumptionMetricRepository;
        this.consumptionAlertRepository = consumptionAlertRepository;
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

    @Override
    public Result<Long, ApplicationError> handle(TriggerConsumptionAlertCommand command) {
        var alert = new ConsumptionAlert(command);
        try {
            alert = consumptionAlertRepository.save(alert);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("trigger-consumption-alert", e.getMessage()));
        }
        return Result.success(alert.getId());
    }

    @Override
    public Result<ConsumptionAlert, ApplicationError> handle(MarkAlertAsReadCommand command) {
        var result = consumptionAlertRepository.findById(command.alertId());
        if (result.isEmpty())
            return Result.failure(ApplicationError.notFound("ConsumptionAlert", command.alertId().toString()));
        var alert = result.get();
        try {
            var updated = consumptionAlertRepository.save(alert.markAsRead());
            return Result.success(updated);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("mark-alert-as-read", e.getMessage()));
        }
    }
}
