package com.techwatch.techwatchbackend.analytics.domain.repositories;

import com.techwatch.techwatchbackend.analytics.domain.model.aggregates.ConsumptionAlert;
import com.techwatch.techwatchbackend.analytics.domain.model.valueobjects.UserId;

import java.util.List;
import java.util.Optional;

/**
 * Consumption alert repository port.
 */
public interface ConsumptionAlertRepository {
    Optional<ConsumptionAlert> findById(Long id);
    List<ConsumptionAlert> findByUserId(UserId userId);
    ConsumptionAlert save(ConsumptionAlert alert);
}
