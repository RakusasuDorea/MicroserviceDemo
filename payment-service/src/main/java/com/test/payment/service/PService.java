package com.test.payment.service;

import com.test.parkingslot.model.ParkingSlot;
import com.test.parkingslot.repository.ParkingSlotRepository;
import com.test.transport.model.Transport;
import com.test.payment.model.Payment;
import com.test.payment.repository.PaymentRepository;
import com.test.transport.repository.TransportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.List;
import java.util.Optional;

@Service
public class PService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private TransportRepository transportRepository;

    @Autowired
    private ParkingSlotRepository parkingSlotRepository;

    @Autowired
    private DiscoveryClient discoveryClient;

    public Payment createPayment(Payment payment) {
        Optional<Transport> transport = transportRepository.findById(payment.getTransport().getId());
        Optional<ParkingSlot> parkingSlot = parkingSlotRepository.findById(payment.getParkingSlot().getId());

        if (transport.isPresent() && parkingSlot.isPresent()) {
            payment.setTransport(transport.get());
            payment.setParkingSlot(parkingSlot.get());
            return paymentRepository.save(payment);
        } else {
            throw new IllegalArgumentException("Transport or Parking Slot not found");
        }
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}
