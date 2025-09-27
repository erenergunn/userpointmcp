package com.userpoint.config;

import com.userpoint.entity.*;
import com.userpoint.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RewardRepository rewardRepository;
    private final PointRepository pointRepository;
    private final PointEarningRepository pointEarningRepository;
    private final PointSpendingRepository pointSpendingRepository;
    private final UserRewardRepository userRewardRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            createSampleData();
        }
    }

    private void createSampleData() {
        User user1 = new User("ahmet123", "ahmet@example.com", passwordEncoder.encode("123456"));
        User user2 = new User("mehmet456", "mehmet@example.com", passwordEncoder.encode("123456"));
        User user3 = new User("ayse789", "ayse@example.com", passwordEncoder.encode("123456"));

        user1.setPointEarnings(new ArrayList<>());
        user1.setPointSpendings(new ArrayList<>());
        user1.setUserRewards(new ArrayList<>());

        user2.setPointEarnings(new ArrayList<>());
        user2.setPointSpendings(new ArrayList<>());
        user2.setUserRewards(new ArrayList<>());

        user3.setPointEarnings(new ArrayList<>());
        user3.setPointSpendings(new ArrayList<>());
        user3.setUserRewards(new ArrayList<>());

        userRepository.saveAll(Arrays.asList(user1, user2, user3));

        Point point1 = new Point(null, user1, 1000);
        Point point2 = new Point(null, user2, 2500);
        Point point3 = new Point(null, user3, 500);

        pointRepository.saveAll(Arrays.asList(point1, point2, point3));

        Reward reward1 = new Reward("5₺ Kahve İndirimi", 100);
        Reward reward2 = new Reward("15₺ Yemek İndirimi", 250);
        Reward reward3 = new Reward("50₺ Alışveriş Kuponu", 500);
        Reward reward4 = new Reward("100₺ Market İndirimi", 1000);
        Reward reward5 = new Reward("Premium Üyelik (1 Ay)", 2000);

        rewardRepository.saveAll(Arrays.asList(reward1, reward2, reward3, reward4, reward5));

        PointEarning earning1 = new PointEarning(user1, 500, "Hoşgeldin Bonusu");
        PointEarning earning2 = new PointEarning(user1, 250, "Arkadaş Davet Bonusu");
        PointEarning earning3 = new PointEarning(user2, 1000, "Hoşgeldin Bonusu");
        PointEarning earning4 = new PointEarning(user2, 500, "İlk Alışveriş Bonusu");
        PointEarning earning5 = new PointEarning(user3, 500, "Hoşgeldin Bonusu");

        pointEarningRepository.saveAll(Arrays.asList(earning1, earning2, earning3, earning4, earning5));

        PointSpending spending1 = new PointSpending(user1, 100, "Kahve İndirimi Kullanımı");
        PointSpending spending2 = new PointSpending(user2, 250, "Yemek İndirimi Kullanımı");
        PointSpending spending3 = new PointSpending(user2, 1000, "Market İndirimi Kullanımı");

        pointSpendingRepository.saveAll(Arrays.asList(spending1, spending2, spending3));

        UserReward userReward1 = new UserReward(user1, reward1);
        UserReward userReward2 = new UserReward(user2, reward2);
        UserReward userReward3 = new UserReward(user2, reward4);

        userRewardRepository.saveAll(Arrays.asList(userReward1, userReward2, userReward3));
    }
}
