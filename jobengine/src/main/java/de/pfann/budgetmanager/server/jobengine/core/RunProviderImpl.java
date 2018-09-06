package de.pfann.budgetmanager.server.jobengine.core;

import de.pfann.budgetmanager.server.common.model.Run;
import de.pfann.budgetmanager.server.common.util.DateUtil;
import de.pfann.server.logging.core.LogUtil;
import de.pfann.server.logging.core.RunLog;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class RunProviderImpl implements RunProvider {

    private TimeInterval timeInterval;

    public RunProviderImpl(TimeInterval aTimeInterval){
        timeInterval = aTimeInterval;
    }

    @Override
    public List<Run> prepareRuns(LocalDateTime timeOfLastRun, LocalDateTime timeOfCurrentRun) {

        RunLog.info(this.getClass(),"Prepare runs for ");
        RunLog.info(this.getClass(),"TimeOfLastRun    :  " + timeOfLastRun);
        RunLog.info(this.getClass(),"TimeOfCurrentRun :  " + timeOfCurrentRun);

        long durationInMilli = ChronoUnit.MILLIS.between(timeOfLastRun,timeOfCurrentRun);

        long amount = durationInMilli / timeInterval.getTimePerMilliSecond();

        List<Run> runs = new ArrayList<>();
        LocalDateTime startTime = timeOfLastRun;
        RunLog.info(this.getClass(), "StartTime: " + startTime);

        for(long index = 0; index < amount; index++){
            LocalDateTime newStartTime = startTime.plusSeconds(timeInterval.getTimePerMilliSecond() / 1000);
            Run run = new Run(newStartTime);
            startTime = run.getExecuted_at();
            runs.add(run);
        }

        RunLog.info(this.getClass(),"Duraten in Milli  :  " + durationInMilli);
        RunLog.info(this.getClass(),"interval in Milli :  " + timeInterval.getTimePerMilliSecond());
        RunLog.info(this.getClass(),"Duraten in min  :  " + DateUtil.convertMilliSecondsToMinutes(durationInMilli));
        RunLog.info(this.getClass(),"interval in min :  " + DateUtil.convertMilliSecondsToMinutes(timeInterval.getTimePerMilliSecond()));
        RunLog.info(this.getClass(),"dedected " + runs.size() + " runs");

        if(runs.size() != 0){
            RunLog.info(this.getClass(), "Created following runs:");
        }

        for(Run run : runs){
            RunLog.info(this.getClass()," " + run.getExecuted_at());
        }

        return runs;

    }
}
