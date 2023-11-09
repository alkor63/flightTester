package com.gridnine.testing;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Flight> flightList = FlightBuilder.createFlights();
        for (Flight flight:flightList) {
            System.out.println(flight);
        }

    }
}
