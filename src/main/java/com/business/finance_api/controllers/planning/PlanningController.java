package com.business.finance_api.controllers.planning;

import com.business.finance_api.dto.planning.LiquidityRequest;
import com.business.finance_api.dto.planning.LiquidityResponse;
import com.business.finance_api.services.PlanningService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
