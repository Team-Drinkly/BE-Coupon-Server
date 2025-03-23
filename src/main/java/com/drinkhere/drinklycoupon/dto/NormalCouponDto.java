package com.drinkhere.drinklycoupon.dto;

import com.drinkhere.drinklycoupon.domain.entity.Coupon;
import com.drinkhere.drinklycoupon.domain.entity.IssuedCoupon;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class NormalCouponDto {

    private final Long id;
    private final Long memberId;
    private final CouponStatus status;
    private final String expirationDate;
    private final String title;
    private final String description;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public NormalCouponDto(Coupon coupon, IssuedCoupon issuedCoupon) {
        this.id = coupon.getId();
        this.memberId = issuedCoupon.getMemberId();
        this.status = issuedCoupon.getStatus();
        this.expirationDate = coupon.getExpirationDate().format(FORMATTER);  // 변경: 포맷 적용
        this.title = coupon.getTitle();
        this.description = coupon.getDescription();
    }
}