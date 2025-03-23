package com.drinkhere.drinklycoupon.application.service;

import com.drinkhere.drinklycoupon.domain.entity.Coupon;
import com.drinkhere.drinklycoupon.domain.repository.CouponRepository;
import com.drinkhere.drinklycoupon.dto.CreateCouponRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponCommandService {

    private final CouponRepository couponRepository;

    @Transactional
    public Long createCoupon(CreateCouponRequestDto requestDto) {
        Coupon coupon = new Coupon(
                requestDto.getStoreId(),
                requestDto.getTitle(),
                requestDto.getDescription(),
                requestDto.getCount(),
                requestDto.getExpirationDateTime()
        );
        coupon = couponRepository.save(coupon);

        return coupon.getId();
    }
}
