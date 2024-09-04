package com.test.payment.service;

import com.test.payment.model.Payment;
import com.test.payment.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    private final RestTemplate restTemplate;

    public PaymentServiceImpl(PaymentRepository paymentRepository, RestTemplate restTemplate) {
        this.paymentRepository = paymentRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public Payment createPayment(Payment payment) {
        try {
            // Construct URLs for transport and slot services
//            String transportNameUrl = discoveryClient.getInstances("transport-service").get(0).getUri().toString()
//                    + "/transport/name/" + payment.getTransportId();
//            String transportTypeUrl = discoveryClient.getInstances("transport-service").get(0).getUri().toString()
//                    + "/transport/type/" + payment.getTransportId();
//            String slotNameUrl = discoveryClient.getInstances("parkingslot-service").get(0).getUri().toString()
//                    + "/parkingslot/name/" + payment.getSlotId();
//            String slotAvailabilityUrl = discoveryClient.getInstances("parkingslot-service").get(0).getUri().toString()
//                    + "/parkingslot/availability/" + payment.getSlotId();
            String transportNameUrl = "http://transport-service/transport/name/" + payment.getTransportId();
            String transportTypeUrl = "http://transport-service/transport/type/" + payment.getTransportId();
            String slotNameUrl = "http://parkingslot-service/parkingslot/name/" + payment.getSlotId();
            String slotAvailabilityUrl = "http://parkingslot-service/parkingslot/availability/" + payment.getSlotId();

            String transportName = restTemplate.getForObject(transportNameUrl, String.class);
            String transportType = restTemplate.getForObject(transportTypeUrl, String.class);
            String slotName = restTemplate.getForObject(slotNameUrl, String.class );
            Boolean slotAvailability = restTemplate.getForObject(slotAvailabilityUrl, Boolean.class);

//
            if (Boolean.FALSE.equals(slotAvailability)) {
                throw new RuntimeException("Parking slot is not available.");
            }
            int calculatedPrice = calculatePrice(transportType);
            payment.setPrice(calculatedPrice);

            updateAvailability(payment.getSlotId());

            payment.setTransportName(transportName);
            payment.setSlotName(slotName);

            return paymentRepository.save(payment);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private int calculatePrice(String transportType) {
        switch (transportType) {
            case "Light":
                return 50;
            case "Medium":
                return 100;
            case "Heavy":
                return 150;
            default:
                return 0;
        }
    }

    private void updateAvailability(Long slotId) {
        String slotUpdateUrl = "http://parkingslot-service/parkingslot/update/false/" + slotId;
        try {
            restTemplate.put(slotUpdateUrl, null);
        } catch (RestClientException e) {
            throw new RuntimeException("Error updating parking slot availability", e);
        }
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
        try {
            restTemplate.put(slotUpdateUrl, null);
        } catch (RestClientException e) {
            throw new RuntimeException("Error reverting parking slot availability", e);
        }
    }


}