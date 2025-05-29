package com.temple.repo;


import com.temple.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepo extends JpaRepository<PaymentEntity, Integer> {
    PaymentEntity findByRazorPayOrderId(String razorPayOrderId);
}