package de.pfann.budgetmanager.server.jobengine.core;

import java.time.LocalTime;

public class SecStartTime implements ExecutionTime{

    private long DELOAYINSECONDS;

    public SecStartTime(long aDelayInSeconds){
        DELOAYINSECONDS = aDelayInSeconds;
    }

    @Override
    public LocalTime getExecutionTime() {
        LocalTime startTime = LocalTime.now();

        startTime = startTime.plusSeconds(DELOAYINSECONDS);

        return startTime;
    }
}
