package com.drinkhere.drinklycoupon.application.service;

import com.drinkhere.drinklycoupon.common.exception.coupon.CouponErrorCode;
import com.drinkhere.drinklycoupon.common.exception.coupon.CouponException;
import com.drinkhere.drinklycoupon.domain.entity.Coupon;
import com.drinkhere.drinklycoupon.domain.entity.IssuedCoupon;
import com.drinkhere.drinklycoupon.domain.repository.CouponRepository;
import com.drinkhere.drinklycoupon.domain.repository.IssuedCouponRepository;
import com.drinkhere.drinklycoupon.dto.CouponRequest;
import com.drinkhere.drinklycoupon.dto.CouponStatus;
import com.drinkhere.drinklycoupon.dto.NormalCouponDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NormalCouponService {

    private final CouponRepository couponRepository;
    private final IssuedCouponRepository issuedCouponRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public NormalCouponService(
            CouponRepository couponRepository,
            IssuedCouponRepository issuedCouponRepository,
            @Qualifier("couponRequestKafkaTemplate") KafkaTemplate<String, String> kafkaTemplate,
            ObjectMapper objectMapper
    ) {
        this.couponRepository = couponRepository;
        this.issuedCouponRepository = issuedCouponRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void issueCoupon(Long memberId, Long couponId) {
        // 중복 발급 방지
        boolean exists = issuedCouponRepository.existsByMemberIdAndCouponId(memberId, couponId);
        if (exists) {
            throw new CouponException(CouponErrorCode.COUPON_ALREADY_ISSUED);
        }

        try {
            String json = objectMapper.writeValueAsString(new CouponRequest(memberId, couponId));
            kafkaTemplate.send("coupon-issue", json);
        } catch (JsonProcessingException e) {
            throw new CouponException(CouponErrorCode.COUPON_ISSUANCE_FAILED);
        }
    }

    @Transactional
    public void useCoupon(Long memberId, Long couponId) {

        IssuedCoupon coupon = issuedCouponRepository.findByMemberIdAndCouponIdAndStatus(memberId, couponId, CouponStatus.AVAILABLE)
                .orElseThrow(() -> new CouponException(CouponErrorCode.COUPON_NOT_FOUND));

        coupon.use();
    }

    @Transactional(readOnly = true)
    public List<NormalCouponDto> getAvailableCoupons(Long memberId) {

        return issuedCouponRepository.findByMemberIdAndStatus(memberId, CouponStatus.AVAILABLE)
                .stream()
                .map(ic -> {
                    Coupon coupon = couponRepository.findById(ic.getCouponId())
                            .orElseThrow(() -> new CouponException(CouponErrorCode.COUPON_NOT_FOUND));
                    return new NormalCouponDto(coupon, ic);
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public List<NormalCouponDto> getUsedCoupons(Long memberId) {

        return issuedCouponRepository.findByMemberIdAndStatus(memberId, CouponStatus.USED)
                .stream()
                .map(ic -> {
                    Coupon coupon = couponRepository.findById(ic.getCouponId())
                            .orElseThrow(() -> new CouponException(CouponErrorCode.COUPON_NOT_FOUND));
                    return new NormalCouponDto(coupon, ic);
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public List<NormalCouponDto> getActiveCouponsByStore(Long storeId) {
        return couponRepository.findByStoreId(storeId).stream()
                .filter(Coupon::isAvailable)
                .map(coupon -> new NormalCouponDto(coupon, new IssuedCoupon(null, coupon.getId()))) // 가상의 IssuedCoupon 객체
                .collect(Collectors.toList());
    }
}
