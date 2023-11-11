package com.gridnine.testing;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class FlightService {
//    сервисный класс для проверки соответствия авиарейсов нашим требованиям

public static boolean departureBeforeArrival(Segment segment) {
    if (segment.getDepartureDate().isBefore(segment.getArrivalDate())) return true;
    else {
        System.out.println("Некорректная информация - прибытие раньше взлёта: "+ segment + " *** Illegal departure/arrival Date");
        return false;
    }
}
public static boolean veryLongFlight(Segment segment) {
    long flightTimeInMinutes = ChronoUnit.MINUTES.between(segment.getDepartureDate(), segment.getArrivalDate());
    if (flightTimeInMinutes > (19*60 + 14)) {
        System.out.println("!!! слишком длительный полёт: "+segment+"  !!!");
        System.out.println("+++ для справки: самый длительный перелёт длился 19 часов 14 минут +++");
        return true;
    }
    return false;
}

public static List<Flight> validateFlight(List<Flight> flightList) {
    List<Flight> validFlights = new ArrayList<>();
    for (Flight flight:flightList) {
        List<Segment> segments = new ArrayList<>();
        for (Segment segment:flight.getSegments()){
            if(!veryLongFlight(segment) && departureBeforeArrival(segment))
            {
                segments.add(segment);
            }
        }
if (segments.size() > 0) {
    validFlights.add(new Flight(segments));
}
    }
    return validFlights;
}
public static void filter1(List<Flight> flightList){
    System.out.println("Список перелётов, доступных в данный момент:");

}
    public static void filter2(List<Flight> flightList){

    }
    public static void filter3(List<Flight> flightList){

    }

}
