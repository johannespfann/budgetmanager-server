package de.pfann.server.logging.formatter;

import de.pfann.budgetmanager.server.common.util.DateUtil;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class RunLogFormatter extends Formatter {

    public RunLogFormatter(){
        // default
    }

    @Override
    public String format(LogRecord record) {
        LocalDateTime localDateTime = DateUtil.asLocalDateTime(new Date(record.getMillis()));
        StringBuilder stringBuilder = new StringBuilder()
                .append("["+ record.getThreadID() +"]")
                .append(" ")
                .append("[ ")
                .append(localDateTime.getYear() + "-" + localDateTime.getMonth().getValue() + "-" + localDateTime.getDayOfMonth())
                .append(" ")
                .append(localDateTime.getHour() + ":" +localDateTime.getMinute() + ":" + localDateTime.getSecond())
                .append(" ]")
                .append(" - ")
                .append(record.getSourceClassName())
                .append(" - ")
                .append(record.getMessage());
        return stringBuilder.toString() + "\n";
    }
}
