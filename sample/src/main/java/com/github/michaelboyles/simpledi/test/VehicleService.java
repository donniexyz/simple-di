package com.github.michaelboyles.simpledi.test;

import lombok.extern.slf4j.Slf4j;
import com.example.SimpleDIContext;

@Slf4j
public class VehicleService {

    public void executeVehicle() {
        SimpleDIContext simpleDIContext = new SimpleDIContext();
        Car car = (Car) simpleDIContext.getBeanByName("car");
        log.info("Car: {}", car);
    }
}
