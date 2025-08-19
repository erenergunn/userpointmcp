package com.userpoint.service.tools;

import com.userpoint.dto.PointEarningDto;
import com.userpoint.dto.PointSpendingDto;
import com.userpoint.entity.PointSpending;
import com.userpoint.entity.User;
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
        User currentUser = userService.getCurrentUser();
        return pointService.getUserPoints(currentUser.getId());
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
        User user = userService.getCurrentUser();
        List<PointSpendingDto> pointSpendingDtos = new ArrayList<>();
        List<PointSpending> pointSpendings = user.getPointSpendings();
        for (PointSpending pointSpending : pointSpendings) {
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
        User user = userService.getCurrentUser();
        List<PointEarningDto> pointEarningDtos = new ArrayList<>();
        user.getPointEarnings().forEach(pointEarning -> {
            PointEarningDto dto = new PointEarningDto();
            dto.setDescription(pointEarning.getDescription());
            dto.setAmount(pointEarning.getAmount());
            pointEarningDtos.add(dto);
        });
        return pointEarningDtos;
    }

}