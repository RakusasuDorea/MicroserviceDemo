package com.test.payment.service;

import com.test.payment.model.Payment;
import com.test.payment.repository.PaymentRepository;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {


    private final PaymentRepository paymentRepository;


    private final DiscoveryClient discoveryClient;


    private final RestTemplate restTemplate;


    private final ObjectMapper objectMapper;

    public PaymentServiceImpl(PaymentRepository paymentRepository, DiscoveryClient discoveryClient,
                              RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.paymentRepository = paymentRepository;
        this.discoveryClient = discoveryClient;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public Payment createPayment(Payment payment) {
        try {
            // Construct URLs for transport and slot services
            String transportUrl = discoveryClient.getInstances("transport-service").get(0).getUri().toString() + "/transport";
            String slotUrl = discoveryClient.getInstances("parkingslot-service").get(0).getUri().toString() + "/parkingslot";

            // Fetch and parse responses
            String transportResponse = restTemplate.getForObject(transportUrl, String.class);
            String slotResponse = restTemplate.getForObject(slotUrl, String.class);

            System.out.println("Transport URL: " + transportUrl);
            System.out.println("Slot URL: " + slotUrl);

            // Parse JSON responses
            JsonNode transportArray = objectMapper.readTree(transportResponse);
            JsonNode slotArray = objectMapper.readTree(slotResponse);

            // Find the correct transport by ID
            String transportName = "Unknown";
            String transportType = "Unknown";
            for (JsonNode transportNode : transportArray) {
                if (transportNode.get("id").asLong() == payment.getTransportId()) {
                    transportName = transportNode.get("name").asText();
                    transportType = transportNode.get("type").asText();
                    break;
                }
            }

            // Find the correct slot by ID
            String slotName = "Unknown";
            boolean slotAvailable = false;
            for (JsonNode slotNode : slotArray) {
                if (slotNode.get("id").asLong() == payment.getSlotId()) {
                    slotName = slotNode.get("name").asText();
                    slotAvailable = slotNode.get("availability").asBoolean();
                    break;
                }
            }
            if (!slotAvailable) {
                throw new RuntimeException("Parking slot is not available.");
            }
            int calculatedPrice = calculatePrice(transportType);
            payment.setPrice(calculatedPrice);

            markSlotAsOccupied(payment.getSlotId());

            payment.setTransportName(transportName);
            payment.setSlotName(slotName);

            return paymentRepository.save(payment);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
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

    private void markSlotAsOccupied(Long slotId) {
        String slotUpdateUrl = discoveryClient.getInstances("parkingslot-service").get(0).getUri().toString()
                + "parkingslot/update/" + slotId;
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("availability", false);
        try {
            restTemplate.put(slotUpdateUrl, requestBody);
        } catch (RestClientException e) {
            throw new RuntimeException("Error updating parking slot availability", e);
        }
    }

}