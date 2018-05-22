package de.pfann.budgetmanager.server.core.jobengine;

import de.pfann.budgetmanager.server.common.util.DateUtil;
import de.pfann.budgetmanager.server.common.util.LogUtil;
import de.pfann.budgetmanager.server.core.rotationjobs.Job;

import java.time.LocalTime;
import java.util.*;

public class JobEngine extends TimerTask {

    private ExecutionTime startTime;
    private TimeInterval timeInterval;
    private Collection<Job> jobs;

    private JobEngine(ExecutionTime aStartTime, TimeInterval aTimeInterval){
        startTime = aStartTime;
        timeInterval = aTimeInterval;
    }

    public void start(){
        LogUtil.info(this.getClass(),"[Start JobEngine] with");
        LogUtil.info(this.getClass(),"   StartTime: " + startTime.getExecutionTime());
        LogUtil.info(this.getClass(),"   Interval : " + DateUtil.convertMilliSecondsToHours(timeInterval.getTimePerMilliSecond()) + " h");
        Timer timer = new Timer();
        long delay = DateUtil.getMilliSecToNextDayTime(LocalTime.now(),startTime.getExecutionTime());
        timer.schedule(this, delay, timeInterval.getTimePerMilliSecond());
    }

    @Override
    public void run() {

        for(Job job : jobs){
            //job.preExecution(new Run());
            //job.execute();
            //job.postExecution();
        }

    }



}
