package com.techwatch.techwatchbackend.subscriptions.interfaces.rest.resources;

public record PlanResource(
        Long id,
        String name,
        String type,
        Double priceAmount,
        String priceCurrency,
        String billingCycle,
        Integer maxDevices,
        Boolean hasAdvancedMetrics,
        Boolean hasCustomReports,
        Boolean hasAlerts,
        Boolean hasUnlimitedHistory,
        Boolean isActive) {
}
