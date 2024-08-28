package com.test.parkingslot.repository;

import com.test.parkingslot.model.ParkingSlot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Long>{

}
