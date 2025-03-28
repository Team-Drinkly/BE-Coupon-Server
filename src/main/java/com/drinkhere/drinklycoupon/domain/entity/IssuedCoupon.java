package com.drinkhere.drinklycoupon.domain.entity;

import com.drinkhere.drinklycoupon.dto.CouponStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class IssuedCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;
    private Long couponId;
    private CouponStatus status;
    private LocalDateTime issuedAt;

    public IssuedCoupon(Long memberId, Long couponId) {
        this.memberId = memberId;
        this.couponId = couponId;
        this.status = CouponStatus.AVAILABLE;
        this.issuedAt = LocalDateTime.now();
    }

    public IssuedCoupon(Long memberId, Long couponId, CouponStatus status) {
        this.memberId = memberId;
        this.couponId = couponId;
        this.status = status;
    }

    public void use() {
        if (this.status != CouponStatus.AVAILABLE) {
            throw new IllegalStateException("이미 사용된 쿠폰입니다.");
        }
        this.status = CouponStatus.USED;
    }

}