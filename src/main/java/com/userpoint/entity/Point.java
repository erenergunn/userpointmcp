package com.userpoint.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "points")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "total_points", nullable = false)
    private Integer totalPoints = 0;

    public Point(User user, Integer totalPoints) {
        this.user = user;
        this.totalPoints = totalPoints;
    }

    public boolean canSpend(Integer points) {
        return points != null && points > 0 && totalPoints >= points;
    }

    public void spendPoints(Integer points) {
        if (canSpend(points)) {
            totalPoints -= points;
        } else {
            throw new IllegalArgumentException("Insufficient points to spend");
        }
    }

    public void addPoints(Integer points) {
        if (points != null && points > 0) {
            totalPoints += points;
        } else {
            throw new IllegalArgumentException("Points to add must be greater than zero");
        }
    }
}
