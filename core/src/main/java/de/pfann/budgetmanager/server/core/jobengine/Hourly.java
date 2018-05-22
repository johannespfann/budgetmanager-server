package de.pfann.budgetmanager.server.core.jobengine;

public class Hourly implements TimeInterval {

    private static final long HOUR_IN_MILLISECONDS = 1000 * 60 * 60;

    @Override
    public long getTimePerMilliSecond() {
        return HOUR_IN_MILLISECONDS;
    }
}
