package com.test.parkingslot.service;

import com.test.parkingslot.model.ParkingSlot;

import java.util.List;
import java.util.Optional;

public interface ParkingSlotService {
    ParkingSlot createParkingSlot(ParkingSlot parkingSlot);

    ParkingSlot updateAvailability(Long id, boolean availability);

    List<ParkingSlot> getAllParkingSlots();

    Optional<ParkingSlot> getParkingSlotById(Long id);

    Optional<ParkingSlot> getParkingSlotNameById(Long id);
}
