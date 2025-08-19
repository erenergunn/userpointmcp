package com.userpoint.service;

import com.userpoint.repository.RewardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RewardService {

    private final RewardRepository rewardRepository;

    public List<String> getRewardNames() {
        return rewardRepository.findAllRewardNames();
    }

}
