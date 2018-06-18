package de.pfann.budgetmanager.server.jobengine.core;

import de.pfann.budgetmanager.server.common.util.DateUtil;
import de.pfann.budgetmanager.server.common.util.LogUtil;

import java.time.LocalTime;
import java.util.*;

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
        LogUtil.info(this.getClass(),"[Start JobEngine] with");
        LogUtil.info(this.getClass(),"   StartTimer at       : " + LocalTime.now());
        LogUtil.info(this.getClass(),"   with first Start at : " + startTime.getExecutionTime());
        LogUtil.info(this.getClass(),"   Interval            : " + DateUtil.convertMilliSecondsToHours(timeInterval.getTimePerMilliSecond()) + " h");
        LogUtil.info(this.getClass(),"                       : " + DateUtil.convertMilliSecondsToMinutes(timeInterval.getTimePerMilliSecond()) + " min");
        Timer timer = new Timer();
        long delay = DateUtil.getMilliSecToNextDayTime(LocalTime.now(),startTime.getExecutionTime());
        timer.schedule(this, delay, timeInterval.getTimePerMilliSecond());
    }

    @Override
    public void run() {
        LogUtil.info(this.getClass(),"############## - >Invoke start of jobengine at time: " + LocalTime.now());
        LogUtil.info(this.getClass(),"");
        LogUtil.info(this.getClass(),"");
        jobEngine.start();

    }

}
