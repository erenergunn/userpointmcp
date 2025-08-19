package com.userpoint.config;

import com.userpoint.entity.Reward;
import com.userpoint.repository.RewardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RewardRepository rewardRepository;

    @Override
    public void run(String... args) throws Exception {
        // Initialize some sample rewards if none exist
        if (rewardRepository.count() == 0) {
            rewardRepository.save(new Reward("Coffee", 50));
            rewardRepository.save(new Reward("Lunch", 150));
            rewardRepository.save(new Reward("Movie Ticket", 200));
            rewardRepository.save(new Reward("Gaming Console", 5000));
            rewardRepository.save(new Reward("Vacation", 10000));

            System.out.println("Sample rewards initialized!");
        }
    }
}
