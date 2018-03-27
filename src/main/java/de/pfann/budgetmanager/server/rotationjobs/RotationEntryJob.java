package de.pfann.budgetmanager.server.rotationjobs;

public class RotationEntryJob implements Job {


    @Override
    public String getIdentifier() {
        return RotationEntryJob.class.getSimpleName();
    }

    @Override
    public void preExecution(Run aRun) {
        System.out.println("####[Start Run]#####");
    }

    @Override
    public void execute(Run aRun) {
        System.out.println("-> execute run: " + aRun.getExecuted_at());
    }

    @Override
    public void postExecution(Run aRun) {
        System.out.println("####[Stop  Run]#####");
    }
}
