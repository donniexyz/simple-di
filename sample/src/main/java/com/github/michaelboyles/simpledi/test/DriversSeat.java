package com.github.michaelboyles.simpledi.test;

import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Singleton
@Named("driver")
public class DriversSeat implements Seat {
    @Override
    public String getPosition() {
        return "front right";
    }
}
