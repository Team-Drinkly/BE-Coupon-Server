package com.drinkhere.drinklycoupon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CouponRequest {
    private Long userId;   // 쿠폰을 신청한 사용자 ID
    private Long couponId; // 요청한 쿠폰 ID
}
