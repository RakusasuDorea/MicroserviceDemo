package com.test.transport.service;

import com.test.transport.model.Transport;
import com.test.transport.repository.TransportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TService {

    @Autowired
    private TransportRepository transportRepository;

    public Transport createTransport(Transport transport) {
        return transportRepository.save(transport);
    }
    public List<Transport> getAllTransports() {
        return transportRepository.findAll();
    }
}
