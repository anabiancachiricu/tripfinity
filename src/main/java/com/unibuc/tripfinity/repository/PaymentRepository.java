package com.unibuc.tripfinity.repository;

import com.unibuc.tripfinity.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
