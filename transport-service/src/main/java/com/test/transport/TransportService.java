package com.test.transport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class TransportService {
    public static void main(String[] args) {
        SpringApplication.run(TransportService.class, args);
    }

}
