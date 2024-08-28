package com.test.parkingslot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ParkingSlotService {
    public static void main(String[] args) {
        SpringApplication.run(ParkingSlotService.class, args);
    }
}
