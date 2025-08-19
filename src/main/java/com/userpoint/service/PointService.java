package com.userpoint.service;

import com.userpoint.dto.PointEarningDto;
import com.userpoint.dto.PointSpendingDto;
import com.userpoint.entity.*;
import com.userpoint.repository.*;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PointService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PointRepository pointRepository;

    @Autowired
    private PointEarningRepository pointEarningRepository;

    @Autowired
    private PointSpendingRepository pointSpendingRepository;

    @Autowired
    private RewardRepository rewardRepository;

    @Autowired
    private UserRewardRepository userRewardRepository;

    @Resource
    private UserService userService;

    public Integer getUserPoints(Long userId) {
        Point point = pointRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User points not found"));
        return point.getTotalPoints();
    }

    public Integer getCurrentUserPoints() {
        User user = userService.getCurrentUser();
        return getUserPoints(user.getId());
    }

    @Transactional
    public PointEarning earnPoints(PointEarningDto earningDto) {
        User user = userService.getCurrentUser();

        // Create point earning record
        PointEarning earning = new PointEarning(user, earningDto.getAmount(), earningDto.getDescription());
        PointEarning savedEarning = pointEarningRepository.save(earning);

        // Update user's total points
        Point point = pointRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("User points not found"));
        point.addPoints(earningDto.getAmount());
        pointRepository.save(point);

        return savedEarning;
    }

    @Transactional
    public PointSpending spendPoints(PointSpendingDto spendingDto) {
        User user = userService.getCurrentUser();

        // Check if user has enough points
        Point point = pointRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("User points not found"));

        if (!point.canSpend(spendingDto.getAmount())) {
            throw new RuntimeException("Insufficient points");
        }

        // Create point spending record
        PointSpending spending = new PointSpending(user, spendingDto.getAmount(), spendingDto.getDescription());
        PointSpending savedSpending = pointSpendingRepository.save(spending);

        // Update user's total points
        point.spendPoints(spendingDto.getAmount());
        pointRepository.save(point);

        return savedSpending;
    }

    public List<PointEarning> getLastPointEarnings(int limit) {
        User user = userService.getCurrentUser();
        return pointEarningRepository.findByUserOrderByCreatedAtDesc(user, PageRequest.of(0, limit));
    }

    public List<PointEarning> getUserLastPointEarnings(Long userId, int limit) {
        return pointEarningRepository.findByUserIdOrderByCreatedAtDesc(userId, PageRequest.of(0, limit));
    }

    public Integer getTotalPointsSpent() {
        User user = userService.getCurrentUser();
        Integer total = pointSpendingRepository.getTotalPointsSpentByUser(user.getId());
        return total != null ? total : 0;
    }

    @Transactional
    public UserReward redeemReward(String rewardName) {
        User user = userService.getCurrentUser();

        // Find the reward
        Reward reward = rewardRepository.findByNameIgnoreCase(rewardName)
                .orElseThrow(() -> new RuntimeException("Reward not found: " + rewardName));

        // Check if user has enough points
        Point point = pointRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("User points not found"));

        if (!point.canSpend(reward.getCostInPoints())) {
            throw new RuntimeException("Insufficient points to redeem " + rewardName);
        }

        // Spend points and create spending record
        PointSpending spending = new PointSpending(user, reward.getCostInPoints(), "Redeemed reward: " + rewardName);
        pointSpendingRepository.save(spending);

        // Update user's total points
        point.spendPoints(reward.getCostInPoints());
        pointRepository.save(point);

        // Create user reward record
        UserReward userReward = new UserReward(user, reward);
        return userRewardRepository.save(userReward);
    }

    public boolean canRedeemReward(String rewardName) {
        User user = userService.getCurrentUser();

        try {
            Reward reward = rewardRepository.findByNameIgnoreCase(rewardName)
                    .orElse(null);

            if (reward == null) {
                return false;
            }

            Point point = pointRepository.findByUser(user)
                    .orElse(null);

            return point != null && point.canSpend(reward.getCostInPoints());
        } catch (Exception e) {
            return false;
        }
    }

}
