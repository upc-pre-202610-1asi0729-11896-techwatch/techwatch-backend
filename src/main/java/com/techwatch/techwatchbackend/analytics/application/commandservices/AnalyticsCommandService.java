package com.techwatch.techwatchbackend.analytics.application.commandservices;

import com.techwatch.techwatchbackend.analytics.domain.model.commands.CalculateMetricsCommand;
import com.techwatch.techwatchbackend.shared.application.result.ApplicationError;
import com.techwatch.techwatchbackend.shared.application.result.Result;

/**
 * Application service contract for commands over the Analytics aggregates.
 */
public interface AnalyticsCommandService {
    /**
     * Handles the calculation (recording) of a consumption metric.
     *
     * @param command command containing the metric data
     * @return created metric identifier or an application error
     * @see CalculateMetricsCommand
     */
    Result<Long, ApplicationError> handle(CalculateMetricsCommand command);
}
