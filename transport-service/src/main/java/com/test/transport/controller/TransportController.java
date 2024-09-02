package com.test.transport.controller;

import com.test.transport.model.Transport;
import com.test.transport.service.TransportServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/transport")
public class TransportController {
    private final TransportServiceImpl transportServiceImpl;


    public TransportController(TransportServiceImpl transportServiceImpl) {
        this.transportServiceImpl = transportServiceImpl;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createTransport(@RequestBody Transport transport) {
        try {
            Transport createdTransport = transportServiceImpl.createTransport(transport);
            String message = "Transport created successfully with ID: " + createdTransport.getId();
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } catch (Exception e) {
            String errorMessage = "Error creating transport: " + e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public List<Transport> getAllTransports() {
        return transportServiceImpl.getAllTransports();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transport> getTransportById(@PathVariable Long id) {
        Optional<Transport> transport = transportServiceImpl.getTransportById(id);
        return transport
                .map(t -> new ResponseEntity<>(t, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/name/{id}")
    public ResponseEntity<String> getTransportNameById(@PathVariable Long id) {
        Optional<Transport> transport = transportServiceImpl.getTransportById(id);
        return transport
                .map(t -> new ResponseEntity<>(t.getName(), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("type/{id}")
    public ResponseEntity<String> getTransportTypeById(@PathVariable Long id) {
        Optional<Transport> transport = transportServiceImpl.getTransportById(id);
        return transport
                .map(t -> new ResponseEntity<>(t.getType(), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
