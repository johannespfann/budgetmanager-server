package de.pfann.budgetmanager.server.rotationjobs;

import sun.awt.image.ImageWatched;

import java.time.LocalDate;
import java.time.Period;
import java.util.LinkedList;
import java.util.List;

public class JobExecuterEngine {

    private List<Job> rotationJobs;

    private RunFacade runFacade;

    private JobExecuterEngine(){
        // default
    }

    private JobExecuterEngine(List<Job> aRotationjobs, RunFacade aRunFacade) {
        rotationJobs = aRotationjobs;
        runFacade = aRunFacade;
    }


    public void start(){

        System.out.println("Start engine");
        List<Run> runs = prepareRuns();

        for(Run run : runs){
            System.out.println(" - Execute for run: " + run.getExecuted_at());
            executeRotationJobs(run);
        }

        System.out.println("Finished runs");

    }

    private void executeRotationJobs(Run aRun) {
        List<RunInfo> runInfosDone = new LinkedList<>();

        for(Job job : rotationJobs){

            RunInfo runInfo = new RunInfo(aRun,job.getIdentifier());

            runInfo.start();

            job.preExecution(aRun);
            job.execute(aRun);
            job.postExecution(aRun);

            runInfo.stop();

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

        LocalDate today = LocalDate.now();
        LocalDate dayOfLastRun = null;

        if(run != null) {
            dayOfLastRun = run.getExecuted_at();
        }

        if(dayOfLastRun == null){
            dayOfLastRun = today.minusDays(1);
        }

        Period distance = Period.between(dayOfLastRun,today);

        int daysBetween = distance.getDays();


        for(int index = 1; index == daysBetween; index++){
            dayOfLastRun = dayOfLastRun.plusDays(1);
            Run newRun = new Run(dayOfLastRun);
            runs.add(newRun);
        }

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
