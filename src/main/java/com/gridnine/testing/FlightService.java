package com.gridnine.testing;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class FlightService {
//    сервисный класс для проверки соответствия авиарейсов нашим требованиям

//    Метод для проверки того, что время взлёта предшествует времени посадки
// true ==> всё в порядке, false ==> в сегменгте указано некорректное время
    public static boolean departureBeforeArrival(Segment segment) {
        if (segment.getDepartureDate().isBefore(segment.getArrivalDate())) return true;
        else {
            System.out.println("Некорректная информация - прибытие раньше взлёта: " + segment + " *** Illegal departure/arrival Date");
            return false;
        }
    }

    // Метод проверяет, что длительность полёта не превышает рекордные на сегодня 19 часов 14 минут
    // false ==> всё в порядке, true ==> в сегменгте указано некорректное время (слишком длительный перелёт)
    public static boolean veryLongFlight(Segment segment) {
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
    public static List<Flight> validateFlight(List<Flight> flightList) {
        List<Flight> validFlights = new ArrayList<>();
        for (Flight flight : flightList) {
            List<Segment> segments = new ArrayList<>();
            for (Segment segment : flight.getSegments()) {
                if (!veryLongFlight(segment) && departureBeforeArrival(segment)) {
                    segments.add(segment);
                }
            }
            if (segments.size() > 0) {
                validFlights.add(new Flight(segments));
            }
        }
        return validFlights;
    }

    //  Метод для проверки валидности списка перелётов
//  Формируем новый список, в который "не пропускаем" сегменты ("рейсы")
//  имеющие время посадки раньше времени вылета
    public static List<Flight> flightsToFuture(List<Flight> flightList) {
        List<Flight> validFlights = new ArrayList<>();
        for (Flight flight : flightList) {
            List<Segment> segments = new ArrayList<>();
            for (Segment segment : flight.getSegments()) {
                if (departureBeforeArrival(segment)) {
                    segments.add(segment);
                }
            }
            if (segments.size() > 0) {
                validFlights.add(new Flight(segments));
            }
        }
        return validFlights;
    }

//  Метод выводит в консоль список перелётов, доступных в данный момент
    public static void departureFromNow(List<Flight> flightList) {
        if (flightList != null) {
            System.out.println("Список перелётов, доступных в данный момент:");
            for (Flight flight : flightList) {
                Segment firstSegment = flight.getSegments().get(0);
                    if (firstSegment.getDepartureDate().isAfter(LocalDateTime.now())) {
                        System.out.println(flight);
                    }
                }
            }
        }

    //  Метод, который показывает перелеты, где общее время, проведённое на земле, не превышает два часа
    public static void travelWithWaitingLessThen2hours(List<Flight> flightList) {
        if (flightList != null) {
            System.out.println("Список перелётов, где общее время, проведённое на земле, не превышает 2 часа \n !!! (минимальное время на пересадку не лимитировано) !!!");
            for (Flight flight : flightList) {
                int numSeg = flight.getSegments().size();
                if (numSeg > 1) {   // перелёты, состоящие из 2 и более сегментов
                    long waitingTime = 0;
                    List<Segment> segments = new ArrayList<>(flight.getSegments());  //разбираем перелёт на составные сегменты
                    for (int i = 0; i < numSeg - 1; i++) {
                        waitingTime = waitingTime + ChronoUnit.MINUTES.between(segments.get(i).getArrivalDate(), segments.get(i + 1).getDepartureDate());
                    }  //  определили суммарное время "на земле"
                    if (waitingTime <= 2*60)   // те самые 2 часа
                        System.out.println(segments);
                }
            }
        }
    }
}
