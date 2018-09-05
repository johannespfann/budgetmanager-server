package de.pfann.budgetmanager.server.jobengine.core;

import java.time.LocalTime;

public class ThreeOClockAM implements ExecutionTime {

    @Override
    public LocalTime getExecutionTime() {
        return LocalTime.of(5,0);
    }

}
