package com.test.parkingslot.service;

import com.test.parkingslot.model.ParkingSlot;
import com.test.parkingslot.repository.ParkingSlotRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingSlotServiceImpl implements ParkingSlotService {

    private final ParkingSlotRepository parkingSlotRepository;

    public ParkingSlotServiceImpl(ParkingSlotRepository parkingSlotRepository) {
        this.parkingSlotRepository = parkingSlotRepository;
    }

    @Override
    public ParkingSlot createParkingSlot(ParkingSlot parkingSlot) {
        if (null == parkingSlot.getName() || null == parkingSlot.getAvailability()) {
            throw new IllegalArgumentException("Name and availability must not be null.");
        }
        return parkingSlotRepository.save(parkingSlot);
    }

    @Override
    public ParkingSlot updateAvailabilityTrue(Long id) {
        ParkingSlot slot = parkingSlotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parking slot not found with id " + id));

        slot.setAvailability(true);
        return parkingSlotRepository.save(slot);
    }

    @Override
    public ParkingSlot updateAvailabilityFalse(Long id) {
        ParkingSlot slot = parkingSlotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parking slot not found with id " + id));

        slot.setAvailability(false);
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
