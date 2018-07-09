package de.pfann.budgetmanager.server.jobengine.rotationjobs;

import de.pfann.budgetmanager.server.common.model.StandingOrder;
import de.pfann.budgetmanager.server.common.util.DateUtil;
import de.pfann.budgetmanager.server.common.util.LogUtil;

import java.time.LocalDateTime;

public class YearlyRotationPattern implements RotationEntryPattern {

    public static final String PATTERN_NBR = "5679";

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
        LocalDateTime startTime = DateUtil.asLocalDateTime(aEntry.getStart_at());
        LocalDateTime endTime = DateUtil.asLocalDateTime(aEntry.getEnd_at());

        if(aEntry.getLast_executed() == null){
            aEntry.setLast_executed(DateUtil.getMinimumDate());
        }

        LocalDateTime lastExecuted = DateUtil.asLocalDateTime((aEntry.getLast_executed()));
        LocalDateTime executionTimeForCurrentYear = getExecutionDateForCurrentYear(aToday, startTime);

        // vor der startzeit
        if(aToday.isBefore(startTime)){
            LogUtil.debug(this.getClass(),"CurrentTime " + aToday + " is before startTime " + startTime + " -> return false");
            return false;
        }
        // nach der endzeit
        if(aToday.isAfter(endTime)){
            LogUtil.debug(this.getClass(),"CurrentTime " + aToday + " is after endTime " + endTime + " -> return false");
            return false;
        }
        // vor der executionTimeOfCurrentYear
        if(aToday.isBefore(executionTimeForCurrentYear)){
            LogUtil.debug(this.getClass(),"CurrentTime " + aToday + " is before executionTimeOfCurrentYear " + executionTimeForCurrentYear + " -> return false");
            return false;
        }
        // wurde bereits ausgeführt
        if(isAlreadyExecuted(lastExecuted,aToday)){
            LogUtil.debug(this.getClass(), "Was already executed for this year: LastExecuted: " + lastExecuted + " CurrentTime: " + aToday);
            return false;
        }
        // ist nach der executiontime
        if(aToday.isAfter(executionTimeForCurrentYear)){
            LogUtil.debug(this.getClass(), "CurrentTime " + aToday + " is after executionTimeOfCurrentYear " + executionTimeForCurrentYear + " and lastExecuted is " + lastExecuted + " - return true");
            return true;
        }

        // default -> false
        LogUtil.debug(this.getClass(),"default -> return false ");
        LogUtil.debug(this.getClass(),"CurrentTime             : " + aToday);
        LogUtil.debug(this.getClass(),"starTime                : " + startTime);
        LogUtil.debug(this.getClass(),"endTime                 : " + endTime);
        LogUtil.debug(this.getClass(),"LastExecuted            : " + lastExecuted);
        LogUtil.debug(this.getClass(),"ExecutionTimeOfThisYear : " + executionTimeForCurrentYear);
        return false;
    }

    @Override
    public LocalDateTime getExecutionDate(LocalDateTime aStartTime, LocalDateTime aTimeOfCurrentRun) {
        return getExecutionDateForCurrentYear(aTimeOfCurrentRun,aStartTime);
    }

    private boolean isAlreadyExecuted(LocalDateTime aLastExecutedDate, LocalDateTime aCurrentDate) {
        if (aLastExecutedDate.getYear() == aCurrentDate.getYear()) {
            return true;
        }
        return false;
    }

    private LocalDateTime getExecutionDateForCurrentYear(LocalDateTime aCurrentDate, LocalDateTime aSartDate) {
        int newDayOfMonth = 1;

        if(aSartDate.getDayOfMonth() > 28){
            newDayOfMonth = 28;
        }

        if(aSartDate.getDayOfMonth() <= 28){
            newDayOfMonth = aSartDate.getDayOfMonth();
        }

        LocalDateTime executionTimeForCurrentYear = LocalDateTime.of(
                aCurrentDate.getYear(),
                aSartDate.getMonth(),
                newDayOfMonth,
                aSartDate.getHour(),
                aSartDate.getMinute());

        return executionTimeForCurrentYear;
    }
}
