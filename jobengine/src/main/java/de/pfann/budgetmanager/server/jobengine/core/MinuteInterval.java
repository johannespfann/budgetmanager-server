package de.pfann.budgetmanager.server.jobengine.core;

public class MinuteInterval implements TimeInterval {

    private long INTERVAL_IN_MINUTE = 10;

    public MinuteInterval(long aIntervalInMin){
        INTERVAL_IN_MINUTE = aIntervalInMin;
    }

    @Override
    public long getTimePerMilliSecond() {
        return INTERVAL_IN_MINUTE * 60 * 1000;
    }
}
