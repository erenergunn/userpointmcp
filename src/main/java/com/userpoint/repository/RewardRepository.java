package com.userpoint.repository;

import com.userpoint.entity.Reward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RewardRepository extends JpaRepository<Reward, Long> {
    Optional<Reward> findByNameIgnoreCase(String name);

    @Query("SELECT r.name FROM Reward r")
    List<String> findAllRewardNames();
}
