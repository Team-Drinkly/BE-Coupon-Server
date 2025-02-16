package com.drinkhere.drinklycoupon.domain.repository;

import com.drinkhere.drinklycoupon.domain.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    boolean existsByIdAndCountGreaterThan(long couponId, int count);
}
