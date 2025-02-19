package com.drinkhere.drinklycoupon.application.presentation;

import com.drinkhere.drinklycoupon.common.response.ApplicationResponse;
import com.drinkhere.drinklycoupon.dto.CouponRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/coupon/m")
public class CouponUserController {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public CouponUserController(
            @Qualifier("couponRequestKafkaTemplate") KafkaTemplate<String, String> kafkaTemplate,
            ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/issue/{couponId}")
    public ApplicationResponse<String> issueCoupon(
            @RequestHeader("member-id") String memberId,
            @PathVariable Long couponId) throws JsonProcessingException {

        // Kafka 토픽에 쿠폰 발급 요청 메시지 전송
        String jsonRequest = objectMapper.writeValueAsString(new CouponRequest(Long.valueOf(memberId), couponId));
        kafkaTemplate.send("coupon-issue", jsonRequest);

        return ApplicationResponse.ok("쿠폰 발급 요청이 접수되었습니다.");
    }
}
