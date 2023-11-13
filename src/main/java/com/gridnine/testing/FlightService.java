package com.gridnine.testing;

import java.util.List;

public interface FlightService {
    //    Метод для проверки того, что время взлёта предшествует времени посадки
    // true ==> всё в порядке, false ==> в сегменгте указано некорректное время
    boolean departureBeforeArrival(Segment segment);

    // Метод проверяет, что длительность полёта не превышает рекордные на сегодня 19 часов 14 минут
    // false ==> всё в порядке, true ==> в сегменгте указано некорректное время (слишком длительный перелёт)
    boolean veryLongFlight(Segment segment);

    //  Метод для проверки валидности списка перелётов
    //  Формируем новый список, в который "не пропускаем" сегменты ("рейсы")
    //  имеющие некорректное время вылета/посадки
    List<Flight> validateFlight(List<Flight> flightList);

    //  Метод для проверки валидности списка перелётов
//  Формируем новый список, в который "не пропускаем" сегменты ("рейсы")
//  имеющие время посадки раньше времени вылета
    List<Flight> flightsToFuture(List<Flight> flightList);

    //  Метод выводит в консоль список перелётов, доступных в данный момент
    List<Flight> departureFromNow(List<Flight> flightList);

    //  Метод, который показывает перелеты, где общее время, проведённое на земле, не превышает два часа
    List<Flight> travelWithWaitingLessThen2hours(List<Flight> flightList);
}
