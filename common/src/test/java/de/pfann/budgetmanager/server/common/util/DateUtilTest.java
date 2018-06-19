package de.pfann.budgetmanager.server.common.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;

public class DateUtilTest {

    private LocalTime timeNow;

    private LocalTime timeNext;


    @Before
    public void setUp(){
        // default setup
    }

    @Test
    public void test_getMilliSecToNextDayTime_WithStartTimeBefore(){
        // prepare
        timeNow = LocalTime.of(14,33);
        timeNext = LocalTime.of(14,34);

        // execute
        long result = DateUtil.getMilliSecToNextDayTime(timeNow,timeNext);

        // validate
        Assert.assertEquals(60000, result);
    }

    @Test
    public void test_getMilliSecToNextDayTime_WithStartTimeAfter() {
        // prepare
        timeNow = LocalTime.of(14,33);
        timeNext = LocalTime.of(14,32);

        // execute
        long result = DateUtil.getMilliSecToNextDayTime(timeNow,timeNext);

        // validate
        Assert.assertEquals(86340000, result);
    }

    @Test
    public void test_convertMilliSecondsToHours(){
        // prepare
        long oneHourInMilliSecond = 60 * 60 * 1000;

        // execute
        long hours = DateUtil.convertMilliSecondsToHours(oneHourInMilliSecond);

        // validate
        Assert.assertEquals(hours,1);

    }

    @Test
    public void test_getMimimumDate(){
        System.out.println(DateUtil.getMinimumDate());

    }

    @Test
    public void test_getMaximumDate(){
        System.out.println(DateUtil.getMaximumDate());
    }


}
