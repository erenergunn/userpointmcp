package com.userpoint.service.tools;

import com.userpoint.service.RewardService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RewardToolService {

    private final RewardService rewardService;

    @Tool(
            name = "getRewardNames",
            description = "Retrieves the names of all available rewards."
    )
    public List<String> getRewardNames() {
        return rewardService.getRewardNames();
    }

}
