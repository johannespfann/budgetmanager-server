package de.pfann.budgetmanager.server.jobengine.core;

public class Daily implements TimeInterval{

    private static long MILLISECONDS_PER_DAY = 86400000;

    @Override
    public long getTimePerMilliSecond() {
        return MILLISECONDS_PER_DAY;
    }
}
