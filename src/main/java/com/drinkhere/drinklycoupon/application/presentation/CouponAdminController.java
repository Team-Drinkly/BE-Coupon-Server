package com.drinkhere.drinklycoupon.application.presentation;

import com.drinkhere.drinklycoupon.application.service.CouponCommandService;
import com.drinkhere.drinklycoupon.application.service.CouponQueryService;
import com.drinkhere.drinklycoupon.common.response.ApplicationResponse;
import com.drinkhere.drinklycoupon.dto.CouponDto;
import com.drinkhere.drinklycoupon.dto.CreateCouponRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/coupon/o")
@RequiredArgsConstructor
public class CouponAdminController {

    private final CouponCommandService couponCommandService;
    private final CouponQueryService couponQueryService;

    @PostMapping("/create")
    public ApplicationResponse<Long> createCoupon(@RequestBody CreateCouponRequestDto requestDto) {
        return ApplicationResponse.created(couponCommandService.createCoupon(requestDto));
    }

    @GetMapping("/list")
    public ApplicationResponse<List<CouponDto>> getAllCoupons() {
        return ApplicationResponse.ok(couponQueryService.getAllCoupons());
    }

    @GetMapping("/count")
    public ApplicationResponse<Long> getAvailableCouponCount() {
        return ApplicationResponse.ok(couponQueryService.countAvailableCoupons());
    }

}
