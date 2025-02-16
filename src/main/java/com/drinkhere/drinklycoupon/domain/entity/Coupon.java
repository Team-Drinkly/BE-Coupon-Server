package com.drinkhere.drinklycoupon.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private int count;  // 발급 가능한 총 개수

    public Coupon(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public void decreaseCount() {
        if (this.count > 0) {
            this.count--;
        } else {
            throw new IllegalStateException("쿠폰이 모두 소진되었습니다.");
        }
    }
}