package de.pfann.budgetmanager.server.core.jobengine;

import de.pfann.budgetmanager.server.persistens.model.Run;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class RunProviderImpl implements RunProvider {

    private TimeInterval timeInterval;
    private Run lastRun;
    private Run currentRun;

    public RunProviderImpl(Run aLastRun, Run aCurrentRun, TimeInterval aTimeInterval){
        timeInterval = aTimeInterval;
        lastRun = aLastRun;
        currentRun = aCurrentRun;
    }

    @Override
    public List<Run> prepareRuns() {

        LocalDateTime timeOfLastRun = lastRun.getExecuted_at();
        LocalDateTime timeOfCurrentRun = currentRun.getExecuted_at();

        long durationInMilli = ChronoUnit.MILLIS.between(timeOfLastRun,timeOfCurrentRun);

        long amount = durationInMilli / timeInterval.getTimePerMilliSecond();

        List<Run> runs = new ArrayList<>();
        LocalDateTime startTime = timeOfLastRun;

        for(long index = 0; index < amount; index++){
            Run run = new Run(startTime.plusSeconds(timeInterval.getTimePerMilliSecond() / 1000));
            startTime = run.getExecuted_at();
            runs.add(run);
        }

        return runs;

    }
}
