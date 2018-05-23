package de.pfann.budgetmanager.server.core.rotationjobs;

import de.pfann.budgetmanager.server.common.util.DateUtil;
import de.pfann.budgetmanager.server.common.util.LogUtil;
import de.pfann.budgetmanager.server.persistens.model.RotationEntry;

import java.time.LocalDateTime;
import java.util.Date;

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
    public boolean isExecutable(LocalDateTime aToday, RotationEntry aEntry) {

        LocalDateTime now = aToday;
        LocalDateTime startTime = DateUtil.asLocalDateTime(aEntry.getStart_at());
        LocalDateTime endTime = DateUtil.asLocalDateTime(aEntry.getEnd_at());
        LocalDateTime lastExecuted = DateUtil.asLocalDateTime(aEntry.getLast_executed());
        LocalDateTime nextTime = lastExecuted.plusMonths(3);

        LogUtil.info(this.getClass(),"Now       : " + now);
        LogUtil.info(this.getClass(),"StartTime : " + startTime);
        LogUtil.info(this.getClass(),"EndTime   : " + endTime);
        LogUtil.info(this.getClass(),"LastExe   : " + lastExecuted);
        LogUtil.info(this.getClass(),"NextTime  : " + nextTime);


        // ist vor startzeit
        if(startTime.isAfter(now)){
            LogUtil.info(this.getClass(),"Vor Startzeit -> false");
            return false;
        }

        // ist abgelaufen
        if(endTime.isBefore(now)){
            LogUtil.info(this.getClass(),"Ist abgelaufen -> false");
            return false;
        }

        // wurde noch nie ausgeführt
        if(startTime.isAfter(lastExecuted)){
            LogUtil.info(this.getClass(),"Noch nie ausgeführt -> true");
            return true;
        }

        // ist seit dem letzten mal nicht ausgeführt worden

        if(nextTime.isBefore(now)){
            LogUtil.info(this.getClass(),"wurde in diesem quartal noch nicht ausgeführt -> true");
            return true;
        }

        // wurde in diesem quartal bereits ausgeführt
        if(nextTime.isBefore(now)){
            LogUtil.info(this.getClass(),"wurde in diesem quartal wurde bereits ausgeführt -> false");
            return false;
        }

        LogUtil.info(this.getClass(),"default -> false");
        return false;
    }

    @Override
    public LocalDateTime getExecutionDate(LocalDateTime aStartTime, LocalDateTime aTimeOfCurrentRun) {
        return null;
    }

}
