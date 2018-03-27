package de.pfann.budgetmanager.server.rotationjobs;

import de.pfann.budgetmanager.server.model.RotationEntry;

import java.time.LocalDate;
import java.util.Date;

public class MonthlyRotationEntry implements RotationEntryPattern {


    public static final String PATTERN_NBR = "66122";

    @Override
    public boolean isValidPattern(RotationEntry aEntry) {
        String[] values = aEntry.getRotation_strategy().split(":");

        if(values[0] != null && values[0].equals(PATTERN_NBR)){
            return true;
        }

        return false;
    }

    @Override
    public boolean isExecutable(LocalDate aToday, RotationEntry aEntry) {

        if(!isValidPattern(aEntry)){
            return false;
        }

        // nach beginn

        if(aEntry.getStart_at().after(aToday)){
            return false;
        }

        Date lastExecuted = aEntry.getLast_executed();

        LocalDate now = LocalDate.now();



        return true;


    }
}
