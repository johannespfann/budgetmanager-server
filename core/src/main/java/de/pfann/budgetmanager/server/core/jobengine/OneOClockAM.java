package de.pfann.budgetmanager.server.core.jobengine;

import java.time.LocalTime;

public class OneOClockAM implements ExecutionTime {

    @Override
    public LocalTime getExecutionTime() {
        return LocalTime.of(1,0);
    }
}
