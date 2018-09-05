package de.pfann.budgetmanager.server.jobengine.rotationjobs;

import de.pfann.budgetmanager.server.common.model.StandingOrder;
import de.pfann.budgetmanager.server.common.util.DateUtil;

import java.time.LocalDateTime;

public class MonthlyRotationPattern implements RotationEntryPattern {

    public static final String PATTERN_NBR = "66122";

    @Override
    public boolean isValidPattern(StandingOrder aEntry) {
        String[] values = aEntry.getRotation_strategy().split(":");

        if (values[0] != null && values[0].equals(PATTERN_NBR)) {
            return true;
        }

        return false;
    }

    @Override
    public boolean isExecutable(LocalDateTime aToday, StandingOrder aEntry) {
        if(aEntry.getLast_executed() == null){
            aEntry.setLast_executed(DateUtil.getMinimumDate());
        }
        LocalDateTime startTime = DateUtil.asLocalDateTime(aEntry.getStart_at());
        LocalDateTime endTime = DateUtil.asLocalDateTime(aEntry.getEnd_at());
        LocalDateTime lastExecuted = DateUtil.asLocalDateTime((aEntry.getLast_executed()));
        LocalDateTime executionTimeForCurrentMonth = getExecutionDateForCurrentMonth(aToday, startTime);

        // vor der Startzeit -> false
        if(aToday.isBefore(startTime)){
            return false;
        }

        // nach der Endzeit -> false

        if(aToday.isAfter(endTime)){
            return false;
        }

        // vor der executionTimeOfCurrentMonth -> false

        if(aToday.isBefore(executionTimeForCurrentMonth)){
            return false;
        }

        // wurde bereits ausgeführt -> false

        if(isAlreadyExecuted(lastExecuted,aToday)){
            return false;
        }

        // ist nach der executionTime && und wurde noch nicht ausgeführt -> true
        if(aToday.isAfter(executionTimeForCurrentMonth)){
            return true;
        }

        return false;
    }

    @Override
    public LocalDateTime getExecutionDate(LocalDateTime aStartTime, LocalDateTime aTimeOfCurrentRun) {
        return getExecutionDateForCurrentMonth(aTimeOfCurrentRun,aStartTime);
    }

    private boolean isAlreadyExecuted(LocalDateTime lastExecuted, LocalDateTime aToday) {
        if (lastExecuted.getMonth().equals(aToday.getMonth()) && lastExecuted.getYear() == aToday.getYear()) {
            return true;
        }
        return false;
    }


    private LocalDateTime getExecutionDateForCurrentMonth(LocalDateTime aToday, LocalDateTime aSartTime) {
        int newDayOfMonth = 1;

        if(aSartTime.getDayOfMonth() > 28){
            newDayOfMonth = 28;
        }

        if(aSartTime.getDayOfMonth() <= 28){
            newDayOfMonth = aSartTime.getDayOfMonth();
        }

        LocalDateTime executionTimeForCurrentMonth = LocalDateTime.of(
                aToday.getYear(),
                aToday.getMonth(),
                newDayOfMonth,
                aSartTime.getHour(),
                aSartTime.getMinute());

        return executionTimeForCurrentMonth;
    }
}
