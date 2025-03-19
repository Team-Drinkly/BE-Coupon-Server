package com.drinkhere.drinklycoupon.dto;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class CreateCouponRequestDto {

    private Long storeId;
    private String title;
    private String description;
    private int count;
    private String expirationDate;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public LocalDateTime getExpirationDateTime() {
        return LocalDate.parse(expirationDate, FORMATTER).atStartOfDay();
    }
}

