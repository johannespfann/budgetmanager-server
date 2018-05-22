package de.pfann.budgetmanager.server.core.rotationjobs;

import de.pfann.budgetmanager.server.common.util.LogUtil;
import de.pfann.budgetmanager.server.persistens.daos.RunFacade;
import de.pfann.budgetmanager.server.persistens.model.Run;
import de.pfann.budgetmanager.server.persistens.model.RunInfo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;
import java.util.TimerTask;

import static java.time.temporal.ChronoUnit.DAYS;


public class JobExecuterEngine extends TimerTask {

    private List<Job> rotationJobs;

    private RunFacade runFacade;

    private JobExecuterEngine(){
        // default
    }

    @Override
    public void run() {
        start();
    }

    private JobExecuterEngine(List<Job> aRotationjobs, RunFacade aRunFacade) {
        rotationJobs = aRotationjobs;
        runFacade = aRunFacade;
    }


    public void start(){

        LogUtil.info(this.getClass(),"[Start engine]");

        if(isFirstExecution()){
            LocalDateTime today = LocalDateTime.now();
            Run run = new Run(today.minusDays(1));
            LogUtil.info(this.getClass(),"Persist first Run: " + run.toString());
            runFacade.persist(run);
        }

        List<Run> runs = prepareRuns();

        for(Run run : runs){

            LogUtil.info(this.getClass(),"[Execute for run: " +run.getExecuted_at() +"]");
            executeRotationJobs(run);
        }

        LogUtil.info(this.getClass(),"[Finished runs]");
    }

    private boolean isFirstExecution() {
        Run run = runFacade.getLastRun();

        if(run == null){
            return true;
        }
        return false;
    }

    private void executeRotationJobs(Run aRun) {
        List<RunInfo> runInfosDone = new LinkedList<>();

        for(Job job : rotationJobs){

            RunInfo runInfo = new RunInfo(aRun,job.getIdentifier());

            runInfo.start();

            job.preExecution(aRun);
            job.execute(aRun);
            job.postExecution(aRun);

            runInfo.stop("asdf");

            runInfosDone.add(runInfo);

        }

        runFacade.persist(aRun);

        for(RunInfo collectedRunInfo : runInfosDone){
            runFacade.persist(collectedRunInfo);
        }

    }

    private List<Run> prepareRuns() {
        List<Run> runs = new LinkedList<>();

        Run run = runFacade.getLastRun();
        LogUtil.info(this.getClass(),"[LastRun: " + run.getExecuted_at() + "]");
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime dayOfLastRun = null;

        if(run != null) {
            dayOfLastRun = run.getExecuted_at();
        }

        if(dayOfLastRun == null){
            dayOfLastRun = today.minusDays(1);
        }
        LogUtil.info(this.getClass(),"[dayOfLastRun: " + run.getExecuted_at() + "]");
        LogUtil.info(this.getClass(),"[today: " + today + "]");

        int daysBetween = (int) DAYS.between(dayOfLastRun, today);
        LogUtil.info(this.getClass(),"[duration: " + daysBetween + "]");

        for(int index = 0; index != daysBetween; index++){

            dayOfLastRun = dayOfLastRun.plusDays(1);
            Run newRun = new Run(dayOfLastRun);
            LogUtil.info(this.getClass(),"[create: " + newRun.getExecuted_at() + "]");
            runs.add(newRun);
        }
        LogUtil.info(this.getClass(),"[RunsToToday: " + runs.size() + "]");
        return runs;
    }

    public static JobExecuterEngineBuilder builder(){
        return new JobExecuterEngineBuilder();
    }


    public static class JobExecuterEngineBuilder {

        private List<Job> rotationjobs;

        private RunFacade runFacade;

        private JobExecuterEngineBuilder(){
            rotationjobs = new LinkedList<>();
        }

        public JobExecuterEngineBuilder addJob(Job aJob){
            rotationjobs.add(aJob);
            return this;
        }

        public JobExecuterEngineBuilder withRunFacade(RunFacade aFacade){
            runFacade = aFacade;
            return this;
        }

        public JobExecuterEngine build(){
            assertNotNull(runFacade);
            return new JobExecuterEngine(rotationjobs, runFacade);
        }

        private void assertNotNull(RunFacade aRunFacade) {
            if(aRunFacade == null){
                throw new IllegalArgumentException("No facade to store run-object found!");
            }
        }

    }
}
