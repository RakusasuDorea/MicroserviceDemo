package com.test.payment.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.test.payment.serializer.PaymentSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@JsonSerialize(using = PaymentSerializer.class)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String transportName;
    private String slotName;
    private int price;

    private Long transportId;
    private Long slotId;
}
