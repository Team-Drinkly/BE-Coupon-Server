package com.drinkhere.drinklycoupon.application.presentation;

import com.drinkhere.drinklycoupon.application.service.NormalCouponService;
import com.drinkhere.drinklycoupon.common.response.ApplicationResponse;
import com.drinkhere.drinklycoupon.dto.NormalCouponDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/coupon/m")
@RequiredArgsConstructor
public class CouponUserController {

    private final NormalCouponService couponService;

    // 쿠폰 발급 (Kafka Producer는 Service 내부에서 처리)
    @PostMapping("/issue/{couponId}")
    public ApplicationResponse<String> issueCoupon(
            @RequestHeader("member-id") String memberId,
            @PathVariable Long couponId) {

        couponService.issueCoupon(Long.valueOf(memberId), couponId);
        return ApplicationResponse.ok("쿠폰이 지급되었습니다.");
    }

    // 쿠폰 사용
    @PatchMapping("/use/{couponId}")
    public ApplicationResponse<String> useCoupon(
            @RequestHeader("member-id") String memberId,
            @PathVariable Long couponId) {

        couponService.useCoupon(Long.valueOf(memberId), couponId);
        return ApplicationResponse.ok("쿠폰 사용 완료");
    }

    // 사용 가능한 쿠폰 조회
    @GetMapping("/available")
    public ApplicationResponse<List<NormalCouponDto>> getAvailableCoupons(
            @RequestHeader("member-id") String memberId) {

        return ApplicationResponse.ok(couponService.getAvailableCoupons(Long.valueOf(memberId)));
    }

    // 사용 완료된 쿠폰 조회
    @GetMapping("/used")
    public ApplicationResponse<List<NormalCouponDto>> getUsedCoupons(
            @RequestHeader("member-id") String memberId) {

        return ApplicationResponse.ok(couponService.getUsedCoupons(Long.valueOf(memberId)));
    }

    // 특정 가게의 진행중인 쿠폰 조회
    @GetMapping("/store/{storeId}")
    public ApplicationResponse<List<NormalCouponDto>> getStoreActiveCoupons(
            @PathVariable Long storeId,
            @RequestHeader("member-id") String memberId) {

        return ApplicationResponse.ok(couponService.getActiveCouponsByStore(storeId, Long.valueOf(memberId)));
    }

}
