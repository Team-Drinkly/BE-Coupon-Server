package com.drinkhere.drinklycoupon.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    // 공통 ProducerFactory 생성 메서드 (중복 제거)
    private ProducerFactory<String, String> createProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    // CouponRequest ProducerFactory & KafkaTemplate
    @Bean
    public ProducerFactory<String, String> couponRequestProducerFactory() {
        return createProducerFactory();
    }

    @Bean(name = "couponRequestKafkaTemplate")
    public KafkaTemplate<String, String> couponRequestKafkaTemplate() {
        return new KafkaTemplate<>(couponRequestProducerFactory());
    }

    // CouponSyncRequest ProducerFactory & KafkaTemplate
    @Bean
    public ProducerFactory<String, String> couponSyncRequestProducerFactory() {
        return createProducerFactory();
    }

    @Bean(name = "couponSyncRequestKafkaTemplate")
    public KafkaTemplate<String, String> couponSyncRequestKafkaTemplate() {
        return new KafkaTemplate<>(couponSyncRequestProducerFactory());
    }

    // CouponAvailabilityResponse ProducerFactory & KafkaTemplate
    @Bean
    public ProducerFactory<String, String> couponAvailabilityProducerFactory() {
        return createProducerFactory();
    }

    @Bean(name = "couponAvailabilityKafkaTemplate")
    public KafkaTemplate<String, String> couponAvailabilityKafkaTemplate() {
        return new KafkaTemplate<>(couponAvailabilityProducerFactory());
    }
}
