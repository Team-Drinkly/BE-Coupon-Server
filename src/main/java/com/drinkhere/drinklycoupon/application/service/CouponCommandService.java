package com.drinkhere.drinklycoupon.application.service;

import com.drinkhere.drinklycoupon.domain.entity.Coupon;
import com.drinkhere.drinklycoupon.domain.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponCommandService {

    private final CouponRepository couponRepository;

    @Transactional
    public Long createCoupon(String name, int count) {
        Coupon coupon = new Coupon(name, count);
        coupon = couponRepository.save(coupon);

        return coupon.getId();
    }
}
