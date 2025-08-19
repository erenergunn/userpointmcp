package com.userpoint.repository;

import com.userpoint.entity.UserReward;
import com.userpoint.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRewardRepository extends JpaRepository<UserReward, Long> {
    List<UserReward> findByUserOrderByRedeemedAtDesc(User user);
    List<UserReward> findByUserIdOrderByRedeemedAtDesc(Long userId);
}
