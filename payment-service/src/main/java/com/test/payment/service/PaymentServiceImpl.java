package com.test.payment.service;

import com.test.payment.model.Payment;
import com.test.payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Optional;

import com.test.transport.model.Transport;
import com.test.parkingslot.model.ParkingSlot;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final RestTemplate restTemplate;

    private final CalculatePriceInterface normalPricing;

    private final CalculatePriceInterface vipPricing;

    public PaymentServiceImpl(PaymentRepository paymentRepository, RestTemplate restTemplate,
                              CalculatePriceInterface normalPricing, @Qualifier("vipPricing") CalculatePriceInterface vipPricing) {
        this.paymentRepository = paymentRepository;
        this.restTemplate = restTemplate;
        this.normalPricing = normalPricing;
        this.vipPricing = vipPricing;
    }

    @Override
    public Payment createPayment(Payment payment) {
        try {
            String transportUrl = "http://transport-service/transport/" + payment.getTransportId();
            String slotUrl = "http://parkingslot-service/parkingslot/" + payment.getSlotId();

            Transport transport = restTemplate.getForObject(transportUrl, Transport.class);
            ParkingSlot parkingSlot =  restTemplate.getForObject(slotUrl, ParkingSlot.class);

            if(null == transport || null == parkingSlot) {
                throw new RuntimeException("Transport or slot not found");
            }

            if (!parkingSlot.getAvailability()) {
                throw new RuntimeException("Parking slot is not available.");
            }

            CalculatePriceInterface calculatePrice = Boolean.TRUE.equals(parkingSlot.getVip()) ? vipPricing : normalPricing;

            int calculatedPrice = calculatePrice.calculatePrice(transport.getType());
            payment.setPrice(calculatedPrice);

            payment.setTransportName(transport.getName());
            payment.setSlotName(parkingSlot.getName());

            updateAvailability(payment.getSlotId());

            return paymentRepository.save(payment);
        }catch (Exception e){
            throw new RuntimeException("Failed to create payment."+ e.getMessage(), e);
        }
    }

    private void updateAvailability(Long slotId) {
        String slotUpdateUrl = "http://parkingslot-service/parkingslot/update/false/" + slotId;
        restTemplate.put(slotUpdateUrl, null);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    @Override
    public void deletePayment(Long id) {
        Optional<Payment> paymentOptional = paymentRepository.findById(id);
        if (paymentOptional.isPresent()) {
            Payment payment = paymentOptional.get();
            revertAvailability(payment.getSlotId());
            paymentRepository.deleteById(id);
        } else {
            throw new RuntimeException("Payment not found with id " + id);
        }
    }

    private void revertAvailability(Long slotId) {
        String slotUpdateUrl = "http://parkingslot-service/parkingslot/update/true/" + slotId;
        restTemplate.put(slotUpdateUrl, null);
    }
}
