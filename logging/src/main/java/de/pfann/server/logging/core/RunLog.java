package de.pfann.server.logging.core;

import de.pfann.budgetmanager.server.common.util.DateUtil;
import de.pfann.server.logging.formatter.RunLogFormatter;

import java.io.File;
import java.io.IOException;
import java.util.logging.*;

public class RunLog {

    private static Logger logger;

    private static Logger getLogger(){
        if(logger == null){
            logger = initLogger(logger);
        }
        return logger;
    }

    private static Logger initLogger(Logger aLogger){
        aLogger = Logger.getLogger("RunLogger");
        aLogger.setUseParentHandlers(false);

        File logDirectory = new File("log/jobengine/");
        if(!logDirectory.exists()){
            logDirectory.mkdirs();
        }

        Handler fileHandler = null;
        Handler consoleHandler = null;
        try {
            fileHandler = new FileHandler("log/jobengine/runlog_%g_%u.log",100000,10,true);
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(new RunLogFormatter());
            consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.ALL);
            consoleHandler.setFormatter(new SimpleFormatter());
        } catch (IOException e) {
            e.printStackTrace();
        }

        aLogger.addHandler(fileHandler);
        aLogger.addHandler(consoleHandler);
        return aLogger;
    }

    public static void info(Class<?> aClass, String aString) {
        LogRecord record = new LogRecord(Level.INFO,aString);
        record.setSourceClassName(aClass.getSimpleName());
        getLogger().log(record);
    }

    public static void error(Class<?> aClass, String aString) {
        LogRecord record = new LogRecord(Level.INFO,aString);
        record.setSourceClassName(aClass.getSimpleName());
        getLogger().log(record);
    }
}
