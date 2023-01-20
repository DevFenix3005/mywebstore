package com.rebirth.mywebstore.utils;

import com.rebirth.mywebstore.services.utils.OperationsUtilComponent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.stream.Stream;

@SpringBootTest
class OperationsUtilComponentTests {

    @Autowired
    OperationsUtilComponent operationsUtilComponent;

    public static Stream<Arguments> monthAndFirstEndDayAndFirstEndHourExpected() {
        return Stream.of(
                Arguments.arguments(Month.JANUARY, 1, 31, 0, 23),
                Arguments.arguments(Month.FEBRUARY, 1, 28, 0, 23),
                Arguments.arguments(Month.MAY, 1, 31, 0, 23),
                Arguments.arguments(Month.NOVEMBER, 1, 30, 0, 23)
        );
    }

    @ParameterizedTest
    @MethodSource("monthAndFirstEndDayAndFirstEndHourExpected")
    void calculateFirstAndLastDateOfThisMonth(Month month, int firstDay, int lastDay, int firstHour, int lastHour) {
        LocalDateTime[] dates = operationsUtilComponent.getFirstAndLastDateOfTheMonth(month);
        LocalDateTime first = dates[0];
        LocalDateTime last = dates[1];
        Assertions.assertEquals(firstDay, first.getDayOfMonth());
        Assertions.assertEquals(lastDay, last.getDayOfMonth());

        Assertions.assertEquals(month, first.getMonth());
        Assertions.assertEquals(month, last.getMonth());

        Assertions.assertEquals(firstHour, first.getHour());
        Assertions.assertEquals(lastHour, last.getHour());
    }

    @ParameterizedTest
    @MethodSource("purchaseAmountAndExpectedAwardsPoints")
    void calculateAwardsPoints(int purchaseAmount, int expectedAwardsPoints) {
        int awardsPoints = operationsUtilComponent.calculateAwardsPoints(purchaseAmount);
        Assertions.assertEquals(expectedAwardsPoints, awardsPoints);
    }

    static Stream<Arguments> purchaseAmountAndExpectedAwardsPoints() {
        return Stream.of(
                Arguments.arguments(120, 90),
                Arguments.arguments(30, 0),
                Arguments.arguments(50, 0),
                Arguments.arguments(60, 10),
                Arguments.arguments(3050, 5950)
        );
    }

}
