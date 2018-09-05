package de.pfann.budgetmanager.server.jobengine.core;

import de.pfann.budgetmanager.server.common.util.DateUtil;
import de.pfann.server.logging.core.LogUtil;
import de.pfann.server.logging.core.RunLog;


import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;

public class JobScheduler extends TimerTask {

    private ExecutionTime startTime;
    private TimeInterval timeInterval;
    private JobEngine jobEngine;

    public JobScheduler(ExecutionTime aStartTime, TimeInterval aTimeInterval, JobEngine aJobEngine){
        startTime = aStartTime;
        timeInterval = aTimeInterval;
        jobEngine = aJobEngine;
    }

    public void start(){
        RunLog.info(this.getClass(),"[Start JobEngine] with");
        RunLog.info(this.getClass(),"   StartTimer at       : " + LocalTime.now());
        RunLog.info(this.getClass(),"   with first Start at : " + startTime.getExecutionTime());
        RunLog.info(this.getClass(),"   Interval            : " + DateUtil.convertMilliSecondsToHours(timeInterval.getTimePerMilliSecond()) + " h");
        RunLog.info(this.getClass(),"                       : " + DateUtil.convertMilliSecondsToMinutes(timeInterval.getTimePerMilliSecond()) + " min");
        Timer timer = new Timer();
        long delay = DateUtil.getMilliSecToNextDayTime(LocalTime.now(),startTime.getExecutionTime());
        timer.schedule(this, delay, timeInterval.getTimePerMilliSecond());
    }

    @Override
    public void run() {
        RunLog.info(this.getClass(),"############## - >Invoke start of jobengine at time: " + LocalTime.now());
        jobEngine.start();
    }

}
