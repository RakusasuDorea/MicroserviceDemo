package com.test.parkingslot.service;

import com.test.parkingslot.model.ParkingSlot;
import com.test.parkingslot.repository.ParkingSlotRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PSlotServiceImpl implements PSlotService {

    private final ParkingSlotRepository parkingSlotRepository;

    public PSlotServiceImpl(ParkingSlotRepository parkingSlotRepository) {
        this.parkingSlotRepository = parkingSlotRepository;
    }

    @Override
    public ParkingSlot createParkingSlot(ParkingSlot parkingSlot) {
        if (parkingSlot.getName() == null || parkingSlot.getAvailability() == null) {
            throw new IllegalArgumentException("Name and availability must not be null.");
        }
        return parkingSlotRepository.save(parkingSlot);
    }

    @Override
    public ParkingSlot updateAvailability(Long id, boolean availability) {
        ParkingSlot slot = parkingSlotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parking slot not found with id " + id));

        slot.setAvailability(availability);
        return parkingSlotRepository.save(slot);
    }

    @Override
    public List<ParkingSlot> getAllParkingSlots() {
        return parkingSlotRepository.findAll();
    }

    @Override
    public Optional<ParkingSlot> getParkingSlotById(Long id) {
        return parkingSlotRepository.findById(id);
    }
}
