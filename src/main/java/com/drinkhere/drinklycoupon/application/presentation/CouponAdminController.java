package com.drinkhere.drinklycoupon.application.presentation;

import com.drinkhere.drinklycoupon.application.service.CouponCommandService;
import com.drinkhere.drinklycoupon.application.service.CouponQueryService;
import com.drinkhere.drinklycoupon.common.response.ApplicationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/coupon/o")
@RequiredArgsConstructor
public class CouponAdminController {

    private final CouponCommandService couponCommandService;
    private final CouponQueryService couponQueryService;

    @PostMapping("/create")
    public ApplicationResponse<Long> createCoupon(@RequestParam String name, @RequestParam int count) {

        return ApplicationResponse.created(couponCommandService.createCoupon(name, count));
    }

}
