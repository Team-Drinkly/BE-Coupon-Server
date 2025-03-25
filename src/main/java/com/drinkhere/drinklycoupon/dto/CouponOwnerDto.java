package com.drinkhere.drinklycoupon.dto;

import com.drinkhere.drinklycoupon.domain.entity.Coupon;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class CouponOwnerDto {

    private final Long id;
    private final String title;
    private final String description;
    private final String expirationDate;
    private final int remainingCount;
    private final boolean isAvailable;
    private final int totalCount;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public CouponOwnerDto(Coupon coupon) {
        this.id = coupon.getId();
        this.title = coupon.getTitle();
        this.description = coupon.getDescription();
        this.expirationDate = coupon.getExpirationDate().format(FORMATTER);
        this.remainingCount = coupon.isExpired() ? (coupon.getInitialCount() - coupon.getCount()) : coupon.getCount();
        this.isAvailable = coupon.isAvailable();
        this.totalCount = coupon.getInitialCount();
    }
}

