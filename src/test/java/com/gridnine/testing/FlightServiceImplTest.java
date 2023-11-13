package com.gridnine.testing;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlightServiceImplTest {

    FlightServiceImpl flightService = new FlightServiceImpl();
    //создаём несколько сегментов для тестов
    LocalDateTime dateIsNow = LocalDateTime.now().plusMinutes(2).truncatedTo(ChronoUnit.MINUTES);
    Segment correctSeg1 = new Segment(dateIsNow, dateIsNow.plusHours(1));
    Segment correctSeg2 = new Segment(dateIsNow.plusMinutes(80), dateIsNow.plusHours(2));
    Segment correctSeg3 = new Segment(dateIsNow.plusMinutes(150), dateIsNow.plusHours(3));
    Segment correctSeg4 = new Segment(dateIsNow.plusMinutes(200), dateIsNow.plusHours(4));
    Segment correctSeg5 = new Segment(dateIsNow.minusMinutes(10), dateIsNow.plusHours(2));
    Segment segToPast = new Segment(dateIsNow.plusHours(1), dateIsNow.minusHours(2));
    Segment longTimeSeg = new Segment(dateIsNow, dateIsNow.plusDays(3));

    Flight flight1 = new Flight(Collections.singletonList(correctSeg1));
    Flight flight2 = new Flight(Arrays.asList(correctSeg1, correctSeg2, correctSeg3, correctSeg4));//total time on earth = 70 min
    Flight flight3 = new Flight(Arrays.asList(correctSeg1, correctSeg3));//total time on earth = 90 min
    Flight flight4 = new Flight(Arrays.asList(correctSeg1, correctSeg4));//total time on earth = 140 min (>2h)
    Flight flight5 = new Flight(Collections.singletonList(correctSeg5)); // уже улетел
    Flight flight6 = new Flight(Collections.singletonList(segToPast)); // bad seg
    Flight flight7 = new Flight(Collections.singletonList(longTimeSeg)); // long seg

    List<Flight> testingFlights = Arrays.asList(flight5, flight1, flight3, flight4, flight2, flight6, flight7);
    List<Flight> expectList = new ArrayList<>();
    List<Flight> resultList = new ArrayList<>();
//@AfterEach
//void clearFlight() {
//    expectList.clear();
//    resultList.clear();
//}

    @Test
    public void shouldReturnTrueWhenDepartureBeforeArrival() {
        assertTrue(flightService.departureBeforeArrival(correctSeg1));
    }

    @Test
    public void shouldReturnFalseWhenDepartureBeforeArrival() {
        assertFalse(flightService.departureBeforeArrival(segToPast));
    }

    // Метод проверяет, что длительность полёта не превышает рекордные на сегодня 19 часов 14 минут
    // false ==> всё в порядке, true ==> в сегменгте указано некорректное время (слишком длительный перелёт)
    @Test
    public void shouldReturnTrueWhenVeryLongFlight() {
        assertTrue(flightService.veryLongFlight(longTimeSeg));
    }

    @Test
    public void shouldReturnFalseWhenVeryLongFlight() {
        assertFalse(flightService.veryLongFlight(correctSeg1));
    }

    @Test
    public void shouldReturnValidFlightList() {
        expectList = Arrays.asList(flight1, flight3, flight4, flight2, flight5);
        resultList = flightService.validateFlight(testingFlights);
        assertEquals(resultList.size(), 5);
        assertFalse(resultList.contains(flight6));
        assertFalse(resultList.contains(flight7));
        assertTrue(resultList.contains(flight1));
    }

    @Test
    public void shouldReturnListWithActualFlights() {
        expectList = Arrays.asList(flight1, flight3, flight4, flight2, flight6, flight7);
        resultList = flightService.departureFromNow(testingFlights);
        assertEquals(resultList.size(), 6);
        assertFalse(resultList.contains(flight5));
        assertTrue(resultList.contains(flight7));
        assertTrue(resultList.contains(flight1));
        assertEquals(resultList, expectList);
    }

    @Test
    public void shouldReturnFlightListWithShortWaiting() {
        expectList = Arrays.asList(flight3, flight2);
        resultList = flightService.travelWithWaitingLessThen2hours(testingFlights);
        assertEquals(resultList.size(), 2);
        assertFalse(resultList.contains(flight6));
        assertTrue(resultList.contains(flight2));
        assertEquals(resultList, expectList);
    }
}