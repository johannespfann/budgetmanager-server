package de.pfann.budgetmanager.server.jobengine.rotationjobs;

import de.pfann.budgetmanager.server.common.util.DateUtil;
import de.pfann.budgetmanager.server.common.util.LogUtil;
import de.pfann.budgetmanager.server.persistens.model.RotationEntry;

import java.time.LocalDateTime;

public class QuarterRotationEntryPattern implements RotationEntryPattern {

    public static final String PATTERN_NBR = "36133";

    @Override
    public boolean isValidPattern(RotationEntry aEntry) {
        String[] values = aEntry.getRotation_strategy().split(":");

        if (values[0] != null && values[0].equals(PATTERN_NBR)) {
            return true;
        }

        return false;
    }

    @Override
    public boolean isExecutable(LocalDateTime aCurrentDate, RotationEntry aEntry) {

        LocalDateTime currentDate = aCurrentDate;
        LocalDateTime startTime = DateUtil.asLocalDateTime(aEntry.getStart_at());

        if(aEntry.getLast_executed() == null){
            aEntry.setLast_executed(DateUtil.getMinimumDate());
        }

        LocalDateTime endTime = DateUtil.asLocalDateTime(aEntry.getEnd_at());
        LocalDateTime lastExecuted = DateUtil.asLocalDateTime(aEntry.getLast_executed());
        LocalDateTime executionTimeOfCurrentQuartal = getExecutionDateOfCurrentQuartal(startTime,aCurrentDate);




        // ist vor startzeit
        if(currentDate.isBefore(startTime)){
            LogUtil.debug(this.getClass(),"Vor Startzeit -> false");
            return false;
        }

        // ist abgelaufen
        if(currentDate.isAfter(endTime)){
            LogUtil.debug(this.getClass(),"Ist abgelaufen -> false");
            return false;
        }


        // ist vor der ausführungszeit
        if(currentDate.isBefore(executionTimeOfCurrentQuartal)){
            LogUtil.debug(this.getClass(),"Aktuelle zeit ist vor der Ausführungszeit -> false");
            return false;
        }

        // wurde bereits in diesem Quartal ausgeführt
        if(isAlreadyExecutedInThisQuartal(lastExecuted,currentDate)){
            LogUtil.debug(this.getClass(),"wurde in diesem quartal bereits ausgeführt -> false");
            return false;
        }

        if(currentDate.isAfter(executionTimeOfCurrentQuartal)){
            LogUtil.debug(this.getClass(),"wurde in diesem quartal noch nicht ausgeführt -> true");
            return true;
        }


        LogUtil.debug(this.getClass(),"Now                            : " + currentDate);
        LogUtil.debug(this.getClass(),"StartTime                      : " + startTime);
        LogUtil.debug(this.getClass(),"EndTime                        : " + endTime);
        LogUtil.debug(this.getClass(),"LastExe                        : " + lastExecuted);
        LogUtil.debug(this.getClass(),"ExecutionTimeOfCurrentQuartal  : " + executionTimeOfCurrentQuartal);

        LogUtil.info(this.getClass(),"default -> false");
        return false;
    }

    private boolean isAlreadyExecutedInThisQuartal(LocalDateTime aLastExecuted, LocalDateTime aCurrentDate) {
        int yearOfCurrentDate = aCurrentDate.getYear();
        int yearOfLastExecuted = aLastExecuted.getYear();

        if(yearOfCurrentDate != yearOfLastExecuted){
            return false;
        }

        int quartalOfCurrentDate = getQuartalNumber(aCurrentDate);
        int quartalOfLastExecuted = getQuartalNumber(aLastExecuted);

        if(quartalOfCurrentDate == quartalOfLastExecuted){
            return true;
        }

        return false;
    }

    @Override
    public LocalDateTime getExecutionDate(LocalDateTime aStartTime, LocalDateTime aTimeOfCurrentRun) {
        return getExecutionDateOfCurrentQuartal(aStartTime,aTimeOfCurrentRun);
    }


    private LocalDateTime getExecutionDateOfCurrentQuartal(LocalDateTime aSartTime, LocalDateTime aCurrentDate){
        LocalDateTime executionDateOfCurrentQuartal;

        int mothOfExecutionDate = getMonthOfExecutionDate(aSartTime,aCurrentDate);
        int dayOfExecutionDate = getDayOfExecutionDate(aSartTime);

        executionDateOfCurrentQuartal = LocalDateTime.of(
                aCurrentDate.getYear(),
                mothOfExecutionDate,
                dayOfExecutionDate,
                aSartTime.getHour(),
                aSartTime.getMinute());

        return executionDateOfCurrentQuartal;

    }

    private int getMonthOfExecutionDate(LocalDateTime aSartTime,LocalDateTime aCurrentDate) {
        int quartalNumberOfStartTime = getQuartalNumber(aSartTime);

        int currentQuartal = getQuartalNumber(aCurrentDate);

        if(quartalNumberOfStartTime == 1){
            if(currentQuartal == 1){
                return aSartTime.getMonth().getValue();
            }
            if(currentQuartal == 2){
                return aSartTime.getMonth().getValue() + 3;
            }
            if(currentQuartal == 3){
                return aSartTime.getMonth().getValue() + 6;
            }
            if(currentQuartal == 4){
                return aSartTime.getMonth().getValue() + 9;
            }
        }
        if(quartalNumberOfStartTime == 2){
            if(currentQuartal == 1){
                return (aSartTime.getMonth().getValue() + 9 )% 12;
            }
            if(currentQuartal == 2){
                return aSartTime.getMonth().getValue();
            }
            if(currentQuartal == 3){
                return aSartTime.getMonth().getValue() + 3;
            }
            if(currentQuartal == 4){
                return aSartTime.getMonth().getValue() + 6;
            }
        }
        if(quartalNumberOfStartTime == 3){
            if(currentQuartal == 1){
                return (aSartTime.getMonth().getValue() + 6 )% 12;
            }
            if(currentQuartal == 2){
                return (aSartTime.getMonth().getValue() + 9 )% 12;
            }
            if(currentQuartal == 3){
                return aSartTime.getMonth().getValue();
            }
            if(currentQuartal == 4){
                return aSartTime.getMonth().getValue() + 3;
            }
        }
        if(quartalNumberOfStartTime == 4){
            if(currentQuartal == 1){
                return (aSartTime.getMonth().getValue() + 3 )% 12;
            }
            if(currentQuartal == 2){
                return (aSartTime.getMonth().getValue() + 6 )% 12;
            }
            if(currentQuartal == 3){
                return (aSartTime.getMonth().getValue() + 9 )% 12;
            }
            if(currentQuartal == 4){
                return aSartTime.getMonth().getValue();
            }
        }
        throw new IllegalArgumentException("Quartal could not defined");
    }

    private int getQuartalNumber(LocalDateTime aDate){
        int monthOfCurrentDate = aDate.getMonth().getValue();

        if(isBetween(monthOfCurrentDate,1,3)){
            return 1;
        }

        if(isBetween(monthOfCurrentDate,4,6)){
            return 2;
        }

        if(isBetween(monthOfCurrentDate,7,9)){
            return 3;
        }

        if(isBetween(monthOfCurrentDate,10,12)){
            return 4;
        }
        throw new IllegalArgumentException("Current date was out of the year");
    }

    public boolean isBetween(int aValue,int aFirst,int aSecond){
        if(aFirst <= aValue && aValue <= aSecond){
            return true;
        }
        return false;
    }

    private int getDayOfExecutionDate(LocalDateTime aSartTime) {
        if(aSartTime.getDayOfMonth() > 28){
            return  28;
        }
        return aSartTime.getDayOfMonth();
    }

}
