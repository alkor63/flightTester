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

        System.out.println("1) Исключаем из тестового набора перелёты с вылетом до текущего момента времени");
//  Вызываем метод, который показывает все перелеты, доступные с текущего момента времени
        FlightService.departureFromNow(flightList);

        List<Flight> correctFlights = FlightService.flightsToFuture(flightList);
// *** убираем из списка сегменты ("рейсы") у которых время посадки предшествует времени взлёта ***
        System.out.println("2) убираем из списка сегменты (рейсы) у которых время посадки предшествует времени взлёта");
        correctFlights.forEach(System.out::println);

        System.out.println("3) перелеты, где общее время, проведённое на земле, не превышает два часа");
        FlightService.travelWithWaitingLessThen2hours(flightList);
// ---------------------------------------------------------------------------------------------------------
        System.out.println("=================================================");
        System.out.println("В списке есть сегмент длительностью 6 суток");
        System.out.println("кроме того 'рейс в прошлое' тоже остался в списке ");
        System.out.println("Предлагаю сначала провести валидацию перелётов ");
        System.out.println("и далее Правила тестировать на корректном списке  ");

        System.out.println("-------------------------------------------------");
        System.out.println("------ Проводим валидацию списка перелётов ------");
        List<Flight> validFlights = FlightService.validateFlight(flightList);
// Проводим валидацию списка перелётов, в т.ч.
// *** убираем из списка сегменты ("рейсы") у которых время посадки предшествует времени взлёта ***
        System.out.println("Список корректных перелётов: ");
        validFlights.forEach(System.out::println);
//  Вызываем метод, который показывает все перелеты, доступные с текущего момента времени
        FlightService.departureFromNow(validFlights);

//  Вызываем метод, который показывает перелеты, где общее время, проведённое на земле, не превышает два часа
        FlightService.travelWithWaitingLessThen2hours(validFlights);
    }
}
