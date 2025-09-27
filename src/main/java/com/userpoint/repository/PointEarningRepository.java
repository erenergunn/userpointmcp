package com.userpoint.repository;

import com.userpoint.entity.PointEarning;
import com.userpoint.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointEarningRepository extends JpaRepository<PointEarning, Long> {
    List<PointEarning> findByUserOrderByCreatedAtDesc(User user);
    List<PointEarning> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);
    List<PointEarning> findByUserIdOrderByCreatedAtDesc(Long userId);
}
