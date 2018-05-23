package de.pfann.budgetmanager.server.core.rotationjobs;

import de.pfann.budgetmanager.server.common.util.DateUtil;
import de.pfann.budgetmanager.server.common.util.LogUtil;
import de.pfann.budgetmanager.server.persistens.model.RotationEntry;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class MonthlyRotationEntryPattern implements RotationEntryPattern {


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
            LogUtil.info(this.getClass(),"Decided executable because: startzeit noch nicht erreicht -> false ");
            return false;
        }

        /**
         * Wurde noch nie ausgeführt
         */
        if (lastExecuted == null) {
            LogUtil.info(this.getClass(),"Decided executable because: noch nie ausgeführt -> true ");
            return true;
        }



        /**
         * In diesem Monat wurde es bereits ausgeführt
         */
        if (isAlreadyExecuted(lastExecuted, aToday)) {
            LogUtil.info(this.getClass(),"Decided executable because: Wurde diesem Monat bereits ausgeführt -> false ");
            return false;
        }

        if (!isAlreadyExecuted(lastExecuted, aToday)) {
            LogUtil.info(this.getClass(),"Decided executable because: Wurde diesem Monat noch nicht ausgeführt -> true ");
            return true;
        }

        /**
         * Heute vor einem oder x-Monate
         */

        if (lastExecuted.getDayOfMonth() == aToday.getDayOfMonth()) {
            LogUtil.info(this.getClass(),"Decided executable because: heute vor einem oder x-monate -> true ");
            return true;
        }

        /**
         * Wenn heute der 28 ist und letzter groeßer ... wegen Februar
         */
        if (lastExecuted.getDayOfMonth() > 28 && aToday.getDayOfMonth() == 28) {
            LogUtil.info(this.getClass(),"Decided executable because: nach 28 -> true  ");
            return true;
        }
        LogUtil.info(this.getClass(),"Decided executable because: default -> false");
        return false;
    }

    @Override
    public LocalDateTime getExecutionDate(LocalDateTime aStartTime, LocalDateTime aTimeOfCurrentRun) {
        return null;
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
