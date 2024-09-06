package com.test.payment.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("vipPricing")
public class VipCalculatePrice implements CalculatePriceInterface{

    @Override
    public int calculatePrice(String transportType) {
        switch (transportType) {
            case "Light":
                return 300;
            case "Medium":
                return 600;
            case "Heavy":
                return 900 ;
            default:
                return 0;
        }
    }
}
