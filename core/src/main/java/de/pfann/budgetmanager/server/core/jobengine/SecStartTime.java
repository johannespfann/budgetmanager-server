package de.pfann.budgetmanager.server.core.jobengine;

import de.pfann.budgetmanager.server.core.jobengine.ExecutionTime;

import java.time.LocalDateTime;
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
