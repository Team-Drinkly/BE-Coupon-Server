package com.drinkhere.drinklycoupon.application.consumer;

import com.drinkhere.drinklycoupon.application.service.CouponQueryService;
import com.drinkhere.drinklycoupon.dto.CouponAvailabilityRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponAvailabilityConsumer {

    private final CouponQueryService couponService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "coupon-availability", groupId = "coupon-availability-group")
    public void consumeCouponAvailabilityRequest(String message) throws JsonProcessingException {

        CouponAvailabilityRequest request = objectMapper.readValue(message, CouponAvailabilityRequest.class);
        couponService.handleCouponAvailabilityRequest(request);
    }
}
