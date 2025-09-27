package com.userpoint.service.tools;

import com.userpoint.dto.PointEarningDto;
import com.userpoint.dto.PointSpendingDto;
import com.userpoint.entity.PointEarning;
import com.userpoint.entity.PointSpending;
import com.userpoint.service.PointService;
import com.userpoint.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PointToolService {

    private final PointService pointService;
    private final UserService userService;

    @Tool(
            name = "getCurrentUserPoints",
            description = "Retrieves the current user's points balance."
    )
    public Integer getCurrentUserPoints(Long userId) {
        return pointService.getUserPoints(userId);
    }

    @Tool(
            name = "getTotalPointsSpent",
            description = "Retrieves the total points spent by the user."
    )
    public Integer getTotalPointsSpent() {
        return pointService.getTotalPointsSpent();
    }

    @Tool(
            name = "canRedeemReward",
            description = "Checks if the user can redeem a specific reward based on their points balance."
    )
    public boolean canRedeemReward(String rewardName) {
        return pointService.canRedeemReward(rewardName);
    }

    @Tool(
            name = "getPointSpendingHistory",
            description = "Retrieves the point spending history of the user."
    )
    public List<PointSpendingDto> getPointSpendingHistory(Long userId) {
        List<PointSpending> userPointSpending = pointService.getUserPointSpending(userId);
        List<PointSpendingDto> pointSpendingDtos = new ArrayList<>();
        for (PointSpending pointSpending : userPointSpending) {
            PointSpendingDto dto = new PointSpendingDto();
            dto.setDescription(pointSpending.getDescription());
            dto.setAmount(pointSpending.getAmount());
            pointSpendingDtos.add(dto);
        }
        return pointSpendingDtos;
    }

    @Tool(
            name = "getPointEarningHistory",
            description = "Retrieves the point earning history of the user."
    )
    public List<PointEarningDto> getPointEarningHistory(Long userId) {
        List<PointEarning> userPointEarnings = pointService.getUserPointEarnings(userId);
        List<PointEarningDto> pointEarningDtos = new ArrayList<>();
        userPointEarnings.forEach(pointEarning -> {
            PointEarningDto dto = new PointEarningDto();
            dto.setDescription(pointEarning.getDescription());
            dto.setAmount(pointEarning.getAmount());
            pointEarningDtos.add(dto);
        });
        return pointEarningDtos;
    }

}