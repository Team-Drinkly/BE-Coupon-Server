package com.drinkhere.drinklycoupon.application.service;

import com.drinkhere.drinklycoupon.domain.repository.CouponRepository;
import com.drinkhere.drinklycoupon.dto.CouponAvailabilityRequest;
import com.drinkhere.drinklycoupon.dto.CouponAvailabilityResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponQueryService {

    private final CouponRepository couponRepository;
    private final ObjectMapper objectMapper;

    @Qualifier("couponAvailabilityKafkaTemplate")
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Transactional
    public void handleCouponAvailabilityRequest(CouponAvailabilityRequest request) throws JsonProcessingException {
        boolean isAvailable = couponRepository.existsByIdAndCountGreaterThan(request.getCouponId(), 0);

        // Kafka 토픽에 개수 확인 결과 전송
        String jsonRequest = objectMapper.writeValueAsString(
                new CouponAvailabilityResponse(request.getCouponId(), request.getUserId(), isAvailable)
        );
        kafkaTemplate.send("coupon-availability-response", jsonRequest);
    }
}
