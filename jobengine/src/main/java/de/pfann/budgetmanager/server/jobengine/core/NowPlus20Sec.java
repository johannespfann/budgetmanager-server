package de.pfann.budgetmanager.server.jobengine.core;

import java.time.LocalTime;

public class NowPlus20Sec implements ExecutionTime {

    @Override
    public LocalTime getExecutionTime() {
        return LocalTime.now().plusSeconds(10);
    }
}
