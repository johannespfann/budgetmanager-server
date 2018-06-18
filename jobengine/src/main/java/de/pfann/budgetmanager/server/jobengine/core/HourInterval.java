package de.pfann.budgetmanager.server.jobengine.core;

public class HourInterval implements TimeInterval {

    private static final long HOUR_IN_MILLISECONDS = 1000 * 60 * 60;
    private final int hours;

    public HourInterval(int aHours){
            hours = aHours;
    }

    @Override
    public long getTimePerMilliSecond() {
        return hours * HOUR_IN_MILLISECONDS;
    }
}
