package com.rebirth.mywebstore.services.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.Month;

@Component
public class OperationsUtilComponent {

    public LocalDateTime[] getFirstAndLastDateOfTheMonth(Month month) {
        LocalDateTime firstDate = LocalDateTime.of(2023, month, 1, 0, 0);
        LocalDateTime lastDate = firstDate.plusMonths(1).minusSeconds(1);
        return new LocalDateTime[]{firstDate, lastDate};
    }

    public int calculateAwardsPoints(int purchaseAmount) {
        int points = 0;
        if (purchaseAmount > 100) {
            points += 2 * (purchaseAmount - 100);
            purchaseAmount = 100;
        }
        if (purchaseAmount > 50) {
            points += (purchaseAmount - 50);
        }
        return points;
    }

}
