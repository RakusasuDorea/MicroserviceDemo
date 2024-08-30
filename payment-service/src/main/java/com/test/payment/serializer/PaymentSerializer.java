package com.test.payment.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.test.payment.model.Payment;

import java.io.IOException;

public class PaymentSerializer extends JsonSerializer<Payment> {

    @Override
    public void serialize(Payment payment, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("transportName", payment.getTransportName());
        gen.writeStringField("slotName", payment.getSlotName());
        gen.writeNumberField("price", payment.getPrice());
        gen.writeEndObject();
    }
}
