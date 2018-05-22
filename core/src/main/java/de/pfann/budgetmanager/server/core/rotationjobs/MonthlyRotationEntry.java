package de.pfann.budgetmanager.server.core.rotationjobs;

import de.pfann.budgetmanager.server.common.util.DateUtil;
import de.pfann.budgetmanager.server.persistens.model.RotationEntry;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class MonthlyRotationEntry implements RotationEntryPattern {


    public static final String PATTERN_NBR = "66122";

    @Override
    public boolean isValidPattern(RotationEntry aEntry) {
        String[] values = aEntry.getRotation_strategy().split(":");

        if (values[0] != null && values[0].equals(PATTERN_NBR)) {
            return true;
        }

        return false;
    }

    @Override
    public boolean isExecutable(LocalDateTime aToday, RotationEntry aRotationEntry) {

        LocalDateTime startDate = convert(aRotationEntry.getStart_at());
        LocalDateTime lastExecuted = null;

        if (aRotationEntry.getLast_executed() != null) {
            lastExecuted = convert(aRotationEntry.getLast_executed());
        }

        /**
         * Startzeit muss bereits erreicht sein
         */
        if (isBeforStartTime(startDate, aToday)) {
            return false;
        }

        /**
         * Wurde noch nie ausgeführt
         */
        if (lastExecuted == null) {
            return true;
        }

        /**
         * In diesem Monat wurde es bereits ausgeführt
         */
        if (isAlreadyExecuted(lastExecuted, aToday)) {
            return false;
        }

        /**
         * Heute vor einem oder x-Monate
         */

        if (lastExecuted.getDayOfMonth() == aToday.getDayOfMonth()) {
            return true;
        }

        /**
         * Wenn heute der 28 ist und letzter groeßer ... wegen Februar
         */
        if (lastExecuted.getDayOfMonth() > 28 && aToday.getDayOfMonth() == 28) {
            return true;
        }

        return false;
    }

    private boolean isAlreadyExecuted(LocalDateTime lastExecuted, LocalDateTime aToday) {
        if (lastExecuted.getMonth().equals(aToday.getMonth()) && lastExecuted.getYear() == aToday.getYear()) {
            return true;
        }
        return false;
    }

    private boolean isBeforStartTime(LocalDateTime startDate, LocalDateTime aToday) {
        return startDate.isAfter(aToday);
    }

    private LocalDateTime convert(Date aDate) {
        return DateUtil.asLocalDateTime(aDate);
    }

}
