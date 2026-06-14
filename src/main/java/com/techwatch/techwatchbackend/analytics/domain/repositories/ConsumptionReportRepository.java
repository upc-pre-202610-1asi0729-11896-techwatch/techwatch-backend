package com.techwatch.techwatchbackend.analytics.domain.repositories;

import com.techwatch.techwatchbackend.analytics.domain.model.aggregates.ConsumptionReport;
import com.techwatch.techwatchbackend.analytics.domain.model.valueobjects.PropertyId;

import java.util.List;
import java.util.Optional;

/**
 * Consumption report repository port.
 */
public interface ConsumptionReportRepository {
    Optional<ConsumptionReport> findById(Long id);
    List<ConsumptionReport> findByPropertyId(PropertyId propertyId);
    ConsumptionReport save(ConsumptionReport report);
}
