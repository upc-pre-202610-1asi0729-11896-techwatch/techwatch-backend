package com.techwatch.techwatchbackend.analytics.domain.repositories;

import com.techwatch.techwatchbackend.analytics.domain.model.aggregates.ConsumptionMetric;
import com.techwatch.techwatchbackend.analytics.domain.model.valueobjects.PropertyId;

import java.util.List;
import java.util.Optional;

/**
 * Consumption metric repository port.
 */
public interface ConsumptionMetricRepository {
    Optional<ConsumptionMetric> findById(Long id);
    List<ConsumptionMetric> findByPropertyId(PropertyId propertyId);
    ConsumptionMetric save(ConsumptionMetric metric);
}
