package com.techwatch.techwatchbackend.subscriptions.application.internal.queryservices;

import com.techwatch.techwatchbackend.subscriptions.application.queryservices.PlanQueryService;
import com.techwatch.techwatchbackend.subscriptions.domain.model.aggregates.Plan;
import com.techwatch.techwatchbackend.subscriptions.domain.model.queries.GetAllPlansQuery;
import com.techwatch.techwatchbackend.subscriptions.domain.repositories.PlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanQueryServiceImpl implements PlanQueryService {

    private final PlanRepository planRepository;

    public PlanQueryServiceImpl(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    @Override
    public List<Plan> handle(GetAllPlansQuery query) {
        return planRepository.findAll();
    }
}
