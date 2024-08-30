package com.test.transport.service;

import com.test.transport.model.Transport;

import java.util.List;
import java.util.Optional;

public interface TService {
    Transport createTransport(Transport transport);

    List<Transport> getAllTransports();

    Optional<Transport> getTransportById(Long id);
}
