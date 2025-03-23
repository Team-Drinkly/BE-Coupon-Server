package com.drinkhere.drinklycoupon.common.exception.coupon;

import lombok.Getter;

/**
 * 쿠폰 관련 커스텀 예외
 */
@Getter
public class CouponException extends RuntimeException {
    
    private final CouponErrorCode errorCode;

    public CouponException(CouponErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
