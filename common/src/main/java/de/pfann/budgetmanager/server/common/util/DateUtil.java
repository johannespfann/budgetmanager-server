package de.pfann.budgetmanager.server.common.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

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
}
