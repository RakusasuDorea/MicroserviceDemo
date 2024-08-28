package com.test.parkingslot.service;

import com.test.parkingslot.model.ParkingSlot;
import com.test.parkingslot.repository.ParkingSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PSlotService {

    @Autowired
    private ParkingSlotRepository parkingSlotRepository;

    public ParkingSlot createParkingSlot(ParkingSlot parkingSlot) {
        if (parkingSlot.getName() == null || parkingSlot.getAvailability() == null) {
            throw new IllegalArgumentException("Name and availability must not be null.");
        }
        return parkingSlotRepository.save(parkingSlot);
    }
    public ParkingSlot updateParkingSlot(Long id, ParkingSlot updatedSlot) {
        ParkingSlot existingSlot = parkingSlotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parking Slot not found with id " + id));
        existingSlot.setName(updatedSlot.getName());
        existingSlot.setAvailability(updatedSlot.getAvailability());
        return parkingSlotRepository.save(existingSlot);
    }
    public List<ParkingSlot> getAllParkingSlots() {
        return parkingSlotRepository.findAll();
    }

}
