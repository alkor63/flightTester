package com.gridnine.testing;

import java.util.List;

public class Main {
    public static void main(String[] args) {
//  получаем сгенерированный для нас список перелётов
        List<Flight> flightList = FlightBuilder.createFlights();
        System.out.println(" **** тестовый набор перелётов ****");
        for (Flight flight:flightList) {
            System.out.println(flight);
//  выводим этот список в консоль (для справки)
        }
        FlightService flightService = new FlightServiceImpl();
        System.out.println("1) Исключаем из тестового набора перелёты с вылетом до текущего момента времени");
//  Вызываем метод, который показывает все перелеты, доступные с текущего момента времени
        flightService.departureFromNow(flightList).forEach(System.out::println);

        List<Flight> correctFlights = flightService.flightsToFuture(flightList);
// *** убираем из списка сегменты ("рейсы") у которых время посадки предшествует времени взлёта ***
        System.out.println("2) убираем из списка сегменты (рейсы) у которых время посадки предшествует времени взлёта");
        correctFlights.forEach(System.out::println);

        System.out.println("3) перелеты, где общее время, проведённое на земле, не превышает два часа");
        flightService.travelWithWaitingLessThen2hours(flightList).forEach(System.out::println);
// ---------------------------------------------------------------------------------------------------------
        System.out.println("=================================================");
        System.out.println("В списке есть сегмент длительностью 6 суток");
        System.out.println("кроме того 'рейс в прошлое' тоже остался в списке ");
        System.out.println("Предлагаю оставить в списке только корректные перелёты ");
        System.out.println("и далее Правила тестировать на этом корректном списке");

        System.out.println("-------------------------------------------------");
        System.out.println("------ Проводим валидацию списка перелётов ------");
        List<Flight> validFlights = flightService.validateFlight(flightList);
// Проводим валидацию списка перелётов, в т.ч.
// *** убираем из списка сегменты ("рейсы") у которых время посадки предшествует времени взлёта ***
        System.out.println("Список корректных перелётов: ");
        validFlights.forEach(System.out::println);
//  Вызываем метод, который показывает все перелеты, доступные с текущего момента времени
        System.out.println("Список перелётов, доступных в данный момент:");
        flightService.departureFromNow(validFlights).forEach(System.out::println);

//  Вызываем метод, который показывает перелеты, где общее время, проведённое на земле, не превышает два часа
        System.out.println("Список перелётов, где общее время, проведённое на земле, не превышает 2 часа \n !!! (минимальное время на пересадку не лимитировано) !!!");
        flightService.travelWithWaitingLessThen2hours(validFlights).forEach(System.out::println);
    }
}
