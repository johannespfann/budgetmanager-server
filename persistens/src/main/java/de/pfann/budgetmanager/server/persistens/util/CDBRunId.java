package de.pfann.budgetmanager.server.persistens.util;

import java.time.LocalDateTime;

public class CDBRunId {

    private static final String PREFIX = "run";

    private static final String SEPERRTOR = ":";

    private CDBRunId() {
        // default
    }

    public static String createId(LocalDateTime aLocalDateTime) {
        return PREFIX +
                SEPERRTOR +
                aLocalDateTime.getYear() +
                SEPERRTOR +
                aLocalDateTime.getMonth().getValue() +
                SEPERRTOR +
                aLocalDateTime.getDayOfMonth() +
                SEPERRTOR +
                aLocalDateTime.getHour() +
                SEPERRTOR +
                aLocalDateTime.getMinute() +
                SEPERRTOR +
                aLocalDateTime.getSecond();
    }
}
