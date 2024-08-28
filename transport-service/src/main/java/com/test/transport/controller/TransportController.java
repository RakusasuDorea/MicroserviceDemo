package com.test.transport.controller;

import com.test.transport.model.Transport;
import com.test.transport.repository.TransportRepository;
import com.test.transport.service.TService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
public class TransportController {
    private final TService transportService;
    private final TransportRepository transportRepository;

    @PostMapping
    public ResponseEntity<String> createTransport(@RequestBody Transport transport) {
        try {
            Transport createdTransport = transportService.createTransport(transport);
            String message = "Transport created successfully with ID: " + createdTransport.getId();
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } catch (Exception e) {
            String errorMessage = "Error creating transport: " + e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public List<Transport> getAllTransports() {
        return transportService.getAllTransports();
    }


}
