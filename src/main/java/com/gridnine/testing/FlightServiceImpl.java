package com.gridnine.testing;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class FlightServiceImpl implements FlightService{
//    сервисный класс для проверки соответствия авиарейсов нашим требованиям

//    Метод для проверки того, что время взлёта предшествует времени посадки
// true ==> всё в порядке, false ==> в сегменгте указано некорректное время
    @Override
    public boolean departureBeforeArrival(Segment segment) {
        if (segment.getDepartureDate().isBefore(segment.getArrivalDate())) return true;
        else {
            System.out.println("Некорректная информация - прибытие раньше взлёта: " + segment + " *** Illegal departure/arrival Date");
            return false;
        }
    }

    // Метод проверяет, что длительность полёта не превышает рекордные на сегодня 19 часов 14 минут
    // false ==> всё в порядке, true ==> в сегменгте указано некорректное время (слишком длительный перелёт)
    @Override
    public boolean veryLongFlight(Segment segment) {
        long flightTimeInMinutes = ChronoUnit.MINUTES.between(segment.getDepartureDate(), segment.getArrivalDate());
        if (flightTimeInMinutes > (19 * 60 + 14)) {
            System.out.println("!!! слишком длительный полёт: " + segment + "  !!!");
            System.out.println("+++ для справки: самый длительный перелёт длился 19 часов 14 минут +++");
            return true;
        }
        return false;
    }

//  Метод для проверки валидности списка перелётов
//  Формируем новый список, в который "не пропускаем" сегменты ("рейсы")
//  имеющие некорректное время вылета/посадки
    @Override
    public List<Flight> validateFlight(List<Flight> flightList) {
        List<Flight> validFlights = new ArrayList<>();
        for (Flight flight : flightList) {
            boolean correctFlight = true;
            for (Segment segment : flight.getSegments()) {
                if (!departureBeforeArrival(segment) || veryLongFlight(segment)) {
                    correctFlight = false;
 //                   break;
                }
            }
            if (correctFlight) {
                validFlights.add(flight);
            }
        }
        return validFlights;
    }

    //  Метод для проверки валидности списка перелётов
//  Формируем новый список, в который "не пропускаем" сегменты ("рейсы")
//  имеющие время посадки раньше времени вылета
    @Override
    public List<Flight> flightsToFuture(List<Flight> flightList) {
        List<Flight> validFlights = new ArrayList<>();
        for (Flight flight : flightList) {
            boolean correctFlight = true;
            for (Segment segment : flight.getSegments()) {
                if (!departureBeforeArrival(segment)) {
                    correctFlight = false;
 //                   break;
                }
            }
            if (correctFlight) validFlights.add(flight);
        }
        return validFlights;
    }

//  Метод выводит в консоль список перелётов, доступных в данный момент
    @Override
    public List<Flight> departureFromNow(List<Flight> flightList) {
        List<Flight> validFlights = new ArrayList<>();
        for (Flight flight : flightList) {
            boolean correctFlight = true;
                Segment firstSegment = flight.getSegments().get(0);
                    if (firstSegment.getDepartureDate().isBefore(LocalDateTime.now())) {
                correctFlight = false;
            }
            if (correctFlight) validFlights.add(flight);
}
        return validFlights;
                }
    //  Метод, который показывает перелеты, где общее время, проведённое на земле, не превышает два часа
    @Override
    public  List<Flight> travelWithWaitingLessThen2hours(List<Flight> flightList) {
        List<Flight> validFlights = new ArrayList<>();
            for (Flight flight : flightList) {
                int numSeg = flight.getSegments().size();
                if (numSeg > 1) {   // перелёты, состоящие из 2 и более сегментов
                    long waitingTime = 0;
                    List<Segment> segments = new ArrayList<>(flight.getSegments());  //разбираем перелёт на составные сегменты
                    for (int i = 0; i < numSeg - 1; i++) {
                        waitingTime = waitingTime + ChronoUnit.MINUTES.between(segments.get(i).getArrivalDate(), segments.get(i + 1).getDepartureDate());
                    }  //  определили суммарное время "на земле"
                    if (waitingTime <= 2*60)   // те самые 2 часа
                        validFlights.add(flight);
                }
            }
        return validFlights;
    }
}
