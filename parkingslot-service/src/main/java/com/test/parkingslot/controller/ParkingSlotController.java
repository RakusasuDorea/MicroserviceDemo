package com.test.parkingslot.controller;

import com.test.parkingslot.model.ParkingSlot;
import com.test.parkingslot.repository.ParkingSlotRepository;
import com.test.parkingslot.service.PSlotService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
public class ParkingSlotController {

    private final PSlotService parkingSlotService;

    private final ParkingSlotRepository parkingSlotRepository;

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

    @PutMapping("/{id}")
    public ResponseEntity<String> updateParkingSlot(@PathVariable Long id, @RequestBody ParkingSlot parkingSlot) {
        try {
            ParkingSlot updatedParkingSlot = parkingSlotService.updateParkingSlot(id, parkingSlot);
            String message = "Parking slot updated successfully with ID: " + updatedParkingSlot.getId();
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Exception e) {
            String errorMessage = "Error updating parking slot: " + e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<ParkingSlot>> getAllParkingSlots() {
        List<ParkingSlot> slots = parkingSlotService.getAllParkingSlots();
        return new ResponseEntity<>(slots, HttpStatus.OK);
    }

}
