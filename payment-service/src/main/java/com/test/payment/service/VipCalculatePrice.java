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
                return 750;
            case "Medium":
                return 1250;
            case "Heavy":
                return 1750 ;
            default:
                return 0;
        }
    }
}
