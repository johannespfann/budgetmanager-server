package de.pfann.budgetmanager.server.common.util;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private static long MILLISECONDS_PER_DAY = 86400000;

    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime asLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static Date firstDayOfYear(int aYear){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,aYear);
        cal.set(Calendar.MONTH,Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH,1);
        return cal.getTime();
    }

    public static Date getDateOfYYMMDD(int aYear, int aMonth, int aDay){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,aYear);
        cal.set(Calendar.MONTH,aMonth);
        cal.set(Calendar.DAY_OF_MONTH,aDay);
        return cal.getTime();
    }

    public static LocalDateTime getCurrentTimeOfBERLIN(){
        LocalDateTime localDateTime = LocalDateTime.now();
        ZoneId zoneId = ZoneId.of("Europe/Berlin");
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, zoneId);
        return zonedDateTime.toLocalDateTime();
    }


    /**
     * Return the time in milli between to LocalTimes.
     * If first time is after second time the duration is
     * from the first time to the second time of the next day
     * @param aTimeNow
     * @param aTimeNext
     * @return the duration in milliseconds
     */
    public static long getMilliSecToNextDayTime(LocalTime aTimeNow, LocalTime aTimeNext) {
        long betweenInMilliSeconds = ChronoUnit.MILLIS.between(aTimeNow, aTimeNext);

        if(betweenInMilliSeconds < 0){
            long millisByDay = 24 * 60 * 60 * 1000;
            return betweenInMilliSeconds + MILLISECONDS_PER_DAY;
        }

        return betweenInMilliSeconds;
    }

    public static long convertMilliSecondsToHours(long milliseconds){
        long timeInSeconds = milliseconds / 1000;
        long timeInMinutes = timeInSeconds / 60;
        return timeInMinutes / 60;
    }

    public static long convertMilliSecondsToMinutes(long timePerMilliSecond) {
        long timeInSeconds = timePerMilliSecond / 1000;
        long timeInMinutes = timeInSeconds / 60;
        return timeInMinutes;
    }

    public static Date getMinimumDate(){
        return new Date(0);
    }

    public static Date getMaximumDate(){
        LocalDateTime localDateTime = LocalDateTime.of(3500,1,1,1,1);
        return asDate(localDateTime);
    }
}
