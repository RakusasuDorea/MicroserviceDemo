package com.test.payment.model;

import com.test.parkingslot.model.ParkingSlot;
import com.test.transport.model.Transport;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Transport transport;

   @ManyToOne
    private ParkingSlot parkingSlot;
    private int price;


}
