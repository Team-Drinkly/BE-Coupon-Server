package com.drinkhere.drinklycoupon.domain.repository;

import com.drinkhere.drinklycoupon.domain.entity.IssuedCoupon;
import com.drinkhere.drinklycoupon.dto.CouponStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IssuedCouponRepository extends JpaRepository<IssuedCoupon, Long> {

    Optional<IssuedCoupon> findByMemberIdAndCouponIdAndStatus(Long memberId, Long couponId, CouponStatus status);

    List<IssuedCoupon> findByMemberIdAndStatus(Long memberId, CouponStatus status);

    boolean existsByMemberIdAndCouponId(Long memberId, Long couponId);

    Optional<IssuedCoupon> findByMemberIdAndCouponId(Long memberId, Long couponId);

}
