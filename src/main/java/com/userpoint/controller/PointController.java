package com.userpoint.controller;

import com.userpoint.dto.PointEarningDto;
import com.userpoint.dto.PointSpendingDto;
import com.userpoint.entity.PointEarning;
import com.userpoint.entity.PointSpending;
import com.userpoint.entity.UserReward;
import com.userpoint.service.PointService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/points")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Points Management", description = "Endpoints for managing user points")
public class PointController {

    @Autowired
    private PointService pointService;

    @GetMapping("/balance")
    @Operation(summary = "Get current user's point balance")
    public ResponseEntity<Map<String, Integer>> getPointBalance() {
        try {
            Integer points = pointService.getCurrentUserPoints();
            return ResponseEntity.ok(Map.of("points", points));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/earn")
    @Operation(summary = "Earn points")
    public ResponseEntity<PointEarning> earnPoints(@Valid @RequestBody PointEarningDto earningDto) {
        try {
            PointEarning earning = pointService.earnPoints(earningDto);
            return ResponseEntity.ok(earning);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/spend")
    @Operation(summary = "Spend points")
    public ResponseEntity<PointSpending> spendPoints(@Valid @RequestBody PointSpendingDto spendingDto) {
        try {
            PointSpending spending = pointService.spendPoints(spendingDto);
            return ResponseEntity.ok(spending);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/earnings")
    @Operation(summary = "Get recent point earnings")
    public ResponseEntity<List<PointEarning>> getRecentEarnings(@RequestParam(defaultValue = "10") int limit) {
        try {
            List<PointEarning> earnings = pointService.getLastPointEarnings(limit);
            return ResponseEntity.ok(earnings);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/spent")
    @Operation(summary = "Get total points spent")
    public ResponseEntity<Map<String, Integer>> getTotalPointsSpent() {
        try {
            Integer totalSpent = pointService.getTotalPointsSpent();
            return ResponseEntity.ok(Map.of("totalSpent", totalSpent));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/redeem/{rewardName}")
    @Operation(summary = "Redeem a reward")
    public ResponseEntity<UserReward> redeemReward(@PathVariable String rewardName) {
        try {
            UserReward userReward = pointService.redeemReward(rewardName);
            return ResponseEntity.ok(userReward);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/can-redeem/{rewardName}")
    @Operation(summary = "Check if user can redeem a reward")
    public ResponseEntity<Map<String, Boolean>> canRedeemReward(@PathVariable String rewardName) {
        try {
            boolean canRedeem = pointService.canRedeemReward(rewardName);
            return ResponseEntity.ok(Map.of("canRedeem", canRedeem));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
