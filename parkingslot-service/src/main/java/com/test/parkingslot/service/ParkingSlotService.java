package com.test.parkingslot.service;

import com.test.parkingslot.model.ParkingSlot;

import java.util.List;
import java.util.Optional;

public interface ParkingSlotService {
    ParkingSlot createParkingSlot(ParkingSlot parkingSlot);

    ParkingSlot updateAvailabilityTrue(Long id);

    ParkingSlot updateAvailabilityFalse(Long id);

    List<ParkingSlot> getAllParkingSlots();

    Optional<ParkingSlot> getParkingSlotById(Long id);

}
