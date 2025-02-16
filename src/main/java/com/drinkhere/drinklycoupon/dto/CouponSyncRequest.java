package com.drinkhere.drinklycoupon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CouponSyncRequest {

    private Long couponId;
    private String name;
    private int count;
}
