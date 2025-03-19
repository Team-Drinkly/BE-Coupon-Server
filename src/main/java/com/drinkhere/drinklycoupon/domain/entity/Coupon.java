package com.drinkhere.drinklycoupon.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long storeId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int count;  // 현재 남은 개수

    @Column(nullable = false)
    private int initialCount; // 최초 발행 개수

    @Column(nullable = false)
    private LocalDateTime expirationDate;  // 유효기간

    public Coupon(Long storeId, String title, String description, int count, LocalDateTime expirationDate) {
        this.storeId = storeId;
        this.title = title;
        this.description = description;
        this.count = count;
        this.initialCount = count; // 초기 발행 개수 저장
        this.expirationDate = expirationDate;
    }

    public void decreaseCount() {
        if (this.count > 0) {
            this.count--;
        } else {
            throw new IllegalStateException("쿠폰이 모두 소진되었습니다.");
        }
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expirationDate);
    }

    public boolean isAvailable() {
        return !isExpired() && this.count > 0;
    }
}
