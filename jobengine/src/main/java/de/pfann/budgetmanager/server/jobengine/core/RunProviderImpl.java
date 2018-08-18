package de.pfann.budgetmanager.server.jobengine.core;

import de.pfann.budgetmanager.server.common.model.Run;
import de.pfann.budgetmanager.server.common.util.DateUtil;
import de.pfann.budgetmanager.server.common.util.LogUtil;

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

        LogUtil.info(this.getClass(),"Prepare runs for ");
        LogUtil.info(this.getClass()," TimeOfLastRun    :  " + timeOfLastRun);
        LogUtil.info(this.getClass()," TimeOfCurrentRun :  " + timeOfCurrentRun);

        long durationInMilli = ChronoUnit.MILLIS.between(timeOfLastRun,timeOfCurrentRun);

        long amount = durationInMilli / timeInterval.getTimePerMilliSecond();

        List<Run> runs = new ArrayList<>();
        LocalDateTime startTime = timeOfLastRun;

        for(long index = 0; index < amount; index++){
            Run run = new Run(startTime.plusSeconds(timeInterval.getTimePerMilliSecond() / 1000));
            startTime = run.getExecuted_at();
            runs.add(run);
        }

        LogUtil.info(this.getClass()," Duraten in Milli  :  " + durationInMilli);
        LogUtil.info(this.getClass()," interval in Milli :  " + timeInterval.getTimePerMilliSecond());

        LogUtil.info(this.getClass()," Duraten in min  :  " + DateUtil.convertMilliSecondsToMinutes(durationInMilli));
        LogUtil.info(this.getClass()," interval in min :  " + DateUtil.convertMilliSecondsToMinutes(timeInterval.getTimePerMilliSecond()));

        LogUtil.info(this.getClass(),"dedected " + runs.size() + " runs");
        return runs;

    }
}
