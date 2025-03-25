package com.drinkhere.drinklycoupon.application.service;

import com.drinkhere.drinklycoupon.domain.entity.Coupon;
import com.drinkhere.drinklycoupon.domain.entity.IssuedCoupon;
import com.drinkhere.drinklycoupon.domain.repository.CouponRepository;
import com.drinkhere.drinklycoupon.domain.repository.IssuedCouponRepository;
import com.drinkhere.drinklycoupon.dto.CouponAvailabilityRequest;
import com.drinkhere.drinklycoupon.dto.CouponDto;
import com.drinkhere.drinklycoupon.dto.CouponOwnerDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CouponQueryService {

    private final CouponRepository couponRepository;
    private final IssuedCouponRepository issuedCouponRepository;

    @Qualifier("couponAvailabilityKafkaTemplate")
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Transactional
    public void handleCouponAvailabilityRequest(CouponAvailabilityRequest request) throws JsonProcessingException {
        boolean isAvailable = couponRepository.existsByIdAndCountGreaterThan(request.getCouponId(), 0);

        // 쿠폰 재고 감소 처리 추가
        if (isAvailable) {
            couponRepository.decreaseStock(request.getCouponId());
        }

        // 발행 개인 기록 저장
        IssuedCoupon issuedCoupon = new IssuedCoupon(request.getUserId(), request.getCouponId());
        issuedCouponRepository.save(issuedCoupon);
    }

    @Transactional(readOnly = true)
    public List<CouponOwnerDto> getAllCoupons() {

        return couponRepository.findAll()
                .stream()
                .map(CouponOwnerDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public long countAvailableCoupons() {
        return couponRepository.findAll()
                .stream()
                .filter(Coupon::isAvailable)
                .count();
    }
}
