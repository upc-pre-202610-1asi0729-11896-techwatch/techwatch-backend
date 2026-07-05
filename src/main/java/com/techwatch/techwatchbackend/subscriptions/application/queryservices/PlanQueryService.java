package com.techwatch.techwatchbackend.subscriptions.application.queryservices;

import com.techwatch.techwatchbackend.subscriptions.domain.model.aggregates.Plan;
import com.techwatch.techwatchbackend.subscriptions.domain.model.queries.GetAllPlansQuery;

import java.util.List;

public interface PlanQueryService {
    List<Plan> handle(GetAllPlansQuery query);
}
