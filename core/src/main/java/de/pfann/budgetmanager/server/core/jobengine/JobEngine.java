package de.pfann.budgetmanager.server.core.jobengine;

import de.pfann.budgetmanager.server.common.util.DateUtil;
import de.pfann.budgetmanager.server.common.util.LogUtil;
import de.pfann.budgetmanager.server.core.rotationjobs.Job;
import de.pfann.budgetmanager.server.persistens.model.Run;

import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class JobEngine extends TimerTask {

    private ExecutionTime startTime;
    private TimeInterval timeInterval;
    private Collection<Job> jobs;

    private JobEngine(ExecutionTime aStartTime, TimeInterval aTimeInterval, Collection<Job> aJobs){
        startTime = aStartTime;
        timeInterval = aTimeInterval;
        jobs = aJobs;
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
            job.preExecution(new Run());
            job.execute();
            job.postExecution();
        }

    }

    public static void main(String[] args) {
        JobEngine job = new JobEngine(new OneOClockAM(),new Daily());
        job.start();
    }

}
