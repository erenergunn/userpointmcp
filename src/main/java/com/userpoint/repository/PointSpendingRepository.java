package com.userpoint.repository;

import com.userpoint.entity.PointSpending;
import com.userpoint.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointSpendingRepository extends JpaRepository<PointSpending, Long> {
    List<PointSpending> findByUserOrderByCreatedAtDesc(User user);
    List<PointSpending> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);
    List<PointSpending> findByUserIdOrderByCreatedAtDesc(Long userId);

    @Query("SELECT SUM(ps.amount) FROM PointSpending ps WHERE ps.user.id = :userId")
    Integer getTotalPointsSpentByUser(Long userId);
}
