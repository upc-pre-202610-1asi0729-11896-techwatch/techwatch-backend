package com.techwatch.techwatchbackend.analytics.infrastructure.persistence.jpa.embeddables;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.time.LocalDateTime;

/**
 * Persistence representation for a consumption period value object.
 */
@Embeddable
public class ConsumptionPeriodPersistenceEmbeddable {

    @Column(name = "period_start")
    private LocalDateTime startDate;

    @Column(name = "period_end")
    private LocalDateTime endDate;

    public ConsumptionPeriodPersistenceEmbeddable() {
    }

    public ConsumptionPeriodPersistenceEmbeddable(LocalDateTime startDate, LocalDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
