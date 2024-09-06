package com.test.parkingslot.controller;

import com.test.parkingslot.model.ParkingSlot;
import com.test.parkingslot.service.ParkingSlotServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/parkingslot")
public class ParkingSlotController {

    private final ParkingSlotServiceImpl parkingSlotService;


    public ParkingSlotController(ParkingSlotServiceImpl parkingSlotService) {
        this.parkingSlotService = parkingSlotService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createParkingSlot(@RequestBody ParkingSlot parkingSlot) {
        try {
            ParkingSlot createdParkingSlot = parkingSlotService.createParkingSlot(parkingSlot);
            String message = "Parking slot created successfully with ID: " + createdParkingSlot.getId();
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } catch (Exception e) {
            String errorMessage = "Error creating parking slot: " + e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/true/{id}")
    public ResponseEntity<ParkingSlot> updateAvailabilityTrue(@PathVariable Long id) {
        ParkingSlot updatedSlot = parkingSlotService.updateAvailabilityTrue(id);
        return new ResponseEntity<>(updatedSlot, HttpStatus.OK);
    }

    @PutMapping("/update/false/{id}")
    public ResponseEntity<ParkingSlot> updateAvailabilityFalse(@PathVariable Long id) {
        ParkingSlot updatedSlot = parkingSlotService.updateAvailabilityFalse(id);
        return new ResponseEntity<>(updatedSlot, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ParkingSlot>> getAllParkingSlots() {
        List<ParkingSlot> slots = parkingSlotService.getAllParkingSlots();
        return new ResponseEntity<>(slots, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingSlot> getParkingSlotById(@PathVariable Long id) {
        Optional<ParkingSlot> parkingSlot = parkingSlotService.getParkingSlotById(id);
        return parkingSlot.map(slot -> new ResponseEntity<>(slot, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
