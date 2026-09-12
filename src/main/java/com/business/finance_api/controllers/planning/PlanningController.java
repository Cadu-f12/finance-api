package com.business.finance_api.controllers.planning;

import com.business.finance_api.dto.planning.*;
import com.business.finance_api.services.planning.PlanningService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/planning")
public class PlanningController {

    private final PlanningService planningService;

    public PlanningController(PlanningService planningService) {
        this.planningService = planningService;
    }

    @PostMapping("/liquidity")
    public ResponseEntity<LiquidityResponse> calculateLiquidity(@Valid @RequestBody LiquidityRequest request) {
        LiquidityResponse response = planningService.calculateLiquidity(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/distribution")
    public ResponseEntity<DistributionResponse> calculateDistribution(@Valid @RequestBody DistributionRequest request) {
        DistributionResponse response = planningService.calculateDistribution(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/investments")
    public ResponseEntity<InvestmentResponse> calculateInvestment(@Valid @RequestBody InvestmentRequest request) {
        InvestmentResponse response = planningService.calculateInvestment(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
