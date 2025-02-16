package com.drinkhere.drinklycoupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaAuditing
@EnableKafka
public class DrinklyCouponApplication {

    public static void main(String[] args) {
        SpringApplication.run(DrinklyCouponApplication.class, args);
    }

}
