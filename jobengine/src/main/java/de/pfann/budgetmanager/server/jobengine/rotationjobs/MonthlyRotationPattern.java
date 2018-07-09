package de.pfann.budgetmanager.server.jobengine.rotationjobs;

import de.pfann.budgetmanager.server.common.model.StandingOrder;
import de.pfann.budgetmanager.server.common.util.DateUtil;
import de.pfann.budgetmanager.server.common.util.LogUtil;

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
            LogUtil.debug(this.getClass(),"CurrentTime " + aToday + " is before startTime " + startTime + " -> return false");
            return false;
        }

        // nach der Endzeit -> false

        if(aToday.isAfter(endTime)){
            LogUtil.debug(this.getClass(),"CurrentTime " + aToday + " is after endTime " + endTime + " -> return false");
            return false;
        }

        // vor der executionTimeOfCurrentMonth -> false

        if(aToday.isBefore(executionTimeForCurrentMonth)){
            LogUtil.debug(this.getClass(),"CurrentTime " + aToday + " is before executionTimeOfCurrentMonth " + executionTimeForCurrentMonth + " -> return false");
            return false;
        }

        // wurde bereits ausgeführt -> false

        if(isAlreadyExecuted(lastExecuted,aToday)){
            LogUtil.debug(this.getClass(), "Was already executed for this month: LastExecuted: " + lastExecuted + " CurrentTime: " + aToday);
            return false;
        }

        // ist nach der executionTime && und wurde noch nicht ausgeführt -> true
        if(aToday.isAfter(executionTimeForCurrentMonth)){
            LogUtil.debug(this.getClass(), "CurrentTime " + aToday + " is after executionTimeOfCurrentMonth " + executionTimeForCurrentMonth + " and lastExecuted is " + lastExecuted + " - return true");
            return true;
        }

        // default -> false
        LogUtil.debug(this.getClass(),"default -> return false ");
        LogUtil.debug(this.getClass(),"CurrentTime             : " + aToday);
        LogUtil.debug(this.getClass(),"starTime                : " + startTime);
        LogUtil.debug(this.getClass(),"endTime                 : " + endTime);
        LogUtil.debug(this.getClass(),"LastExecuted            : " + lastExecuted);
        LogUtil.debug(this.getClass(),"ExecutionTimeOfThisMonth: " + executionTimeForCurrentMonth);

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
