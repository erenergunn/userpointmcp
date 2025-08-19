package com.userpoint.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "point_earnings")
@Data
@NoArgsConstructor
public class PointEarning {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Positive
    @Column(nullable = false)
    private Integer amount;

    @NotBlank
    private String description;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public PointEarning(User user, Integer amount, String description) {
        this.user = user;
        this.amount = amount;
        this.description = description;
        this.createdAt = LocalDateTime.now();
    }
}
