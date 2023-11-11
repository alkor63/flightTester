package com.gridnine.testing;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Flight> flightList = FlightBuilder.createFlights();
        for (Flight flight:flightList) {
            System.out.println(flight);
        }

    List<Flight> validFlights = FlightService.validateFlight(flightList);
        for (Flight flight:validFlights) {
            System.out.println(flight);
        }
    }
}
