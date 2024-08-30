package com.test.transport.service;

import com.test.transport.model.Transport;
import com.test.transport.repository.TransportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TServiceImpl implements TService{


    private final TransportRepository transportRepository;

    public TServiceImpl(TransportRepository transportRepository) {
        this.transportRepository = transportRepository;
    }

    @Override
    public Transport createTransport(Transport transport) {
        return transportRepository.save(transport);
    }
    @Override
    public List<Transport> getAllTransports() {
        return transportRepository.findAll();
    }
    @Override
    public Optional<Transport> getTransportById(Long id) {
        return transportRepository.findById(id);
    }
}
