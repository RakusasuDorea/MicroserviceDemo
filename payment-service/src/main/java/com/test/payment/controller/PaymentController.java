package com.test.payment.controller;

import com.test.payment.model.Payment;
import com.test.payment.service.PService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
public class PaymentController {
    private PService paymentService;

    @PostMapping("/create")
    public ResponseEntity<String> createPayment(@RequestBody Payment payment) {
        try {
            Payment createdPayment = paymentService.createPayment(payment);
            String message = "Payment created successfully with ID: " + createdPayment.getId();
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } catch (Exception e) {
            String errorMessage = "Error creating payment: " + e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public List<Payment> getAllPayments() {
        return paymentService.getAllPayments();
    }
}
