package com.userpoint.controller;

import com.userpoint.entity.Reward;
import com.userpoint.repository.RewardRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/rewards")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Rewards Management", description = "Endpoints for managing rewards")
public class RewardController {

    @Autowired
    private RewardRepository rewardRepository;

    @GetMapping
    @Operation(summary = "Get all available rewards")
    public ResponseEntity<List<Reward>> getAllRewards() {
        try {
            List<Reward> rewards = rewardRepository.findAll();
            return ResponseEntity.ok(rewards);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    @Operation(summary = "Create a new reward (Admin)")
    public ResponseEntity<Reward> createReward(@RequestBody Reward reward) {
        try {
            Reward savedReward = rewardRepository.save(reward);
            return ResponseEntity.ok(savedReward);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get reward by ID")
    public ResponseEntity<Reward> getRewardById(@PathVariable Long id) {
        try {
            Reward reward = rewardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reward not found"));
            return ResponseEntity.ok(reward);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
