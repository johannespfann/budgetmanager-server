package de.pfann.budgetmanager.server.jobengine.rotationjobs;

import org.junit.Test;

import java.time.LocalDate;

public class DateTest {

    @Test
    public void test() {

        LocalDate date = LocalDate.of(2018, 1, 27);

        LocalDate newDate = date.plusMonths(1);
        System.out.println(date);
        System.out.println(newDate);


    }
}
