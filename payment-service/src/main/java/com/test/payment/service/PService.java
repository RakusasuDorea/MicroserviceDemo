package com.test.payment.service;

import com.test.payment.model.Payment;

import java.util.List;
import java.util.Optional;

public interface PService {

    Payment createPayment(Payment payment);

    List<Payment> getAllPayments();

    Optional<Payment> getPaymentById(Long id);
}
