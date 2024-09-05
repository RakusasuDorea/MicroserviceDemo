package com.test.payment.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class DefaultCalculatePrice implements CalculatePriceInterface {

    @Override
    public int calculatePrice(String transportType) {
        switch (transportType) {
            case "Light":
                return 50;
            case "Medium":
                return 100;
            case "Heavy":
                return 150;
            default:
                return 0;
        }
    }
}
