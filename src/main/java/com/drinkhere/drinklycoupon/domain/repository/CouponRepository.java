package com.drinkhere.drinklycoupon.domain.repository;

import com.drinkhere.drinklycoupon.domain.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    boolean existsByIdAndCountGreaterThan(long couponId, int count);

    @Modifying
    @Transactional
    @Query("UPDATE Coupon c SET c.count = c.count - 1 WHERE c.id = :couponId AND c.count > 0")
    int decreaseStock(Long couponId);

    List<Coupon> findByStoreId(Long storeId);
}
