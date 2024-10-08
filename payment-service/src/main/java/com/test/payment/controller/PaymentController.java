package com.test.payment.controller;

import com.test.payment.model.Payment;
import com.test.payment.service.PaymentServiceImpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentServiceImpl paymentService;

    public PaymentController(PaymentServiceImpl paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createPayment(@RequestBody Payment payment) {
            Payment createdPayment = paymentService.createPayment(payment);
            String message = "Payment created successfully with ID: " + createdPayment.getId();
            return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        Optional<Payment> payment = paymentService.getPaymentById(id);
        return payment.map(slot -> new ResponseEntity<>(slot, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

        @DeleteMapping("/delete/{id}")
        public ResponseEntity<String> deletePayment(@PathVariable Long id) {
                paymentService.deletePayment(id);
                return new ResponseEntity<>("Payment deleted successfully", HttpStatus.OK);
        }
}

