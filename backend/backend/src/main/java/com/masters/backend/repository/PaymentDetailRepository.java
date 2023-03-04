package com.masters.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masters.backend.controller.PaymentDetail;

public interface PaymentDetailRepository extends JpaRepository<PaymentDetail, Long> {

}
