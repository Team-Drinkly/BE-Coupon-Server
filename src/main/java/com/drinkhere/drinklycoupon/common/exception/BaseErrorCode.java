package com.drinkhere.drinklycoupon.common.exception;

import com.drinkhere.drinklycoupon.common.response.ApplicationResponse;
import org.springframework.http.HttpStatus;

public interface BaseErrorCode {

    HttpStatus getHttpStatus();

    String getMessage();

    ApplicationResponse<String> toResponseEntity();
}