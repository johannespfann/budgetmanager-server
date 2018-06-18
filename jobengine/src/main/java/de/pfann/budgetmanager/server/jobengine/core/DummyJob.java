package de.pfann.budgetmanager.server.jobengine.core;

import de.pfann.budgetmanager.server.common.util.LogUtil;
import de.pfann.budgetmanager.server.persistens.model.Run;

public class DummyJob implements Job{
    @Override
    public String getIdentifier() {
        return "DummyJob";
    }

    @Override
    public void preExecution(Run aRun) throws JobException {
        LogUtil.info(this.getClass(),"pre");
    }

    @Override
    public void execute(Run aRun) throws JobException {
        LogUtil.info(this.getClass(),"exe " + " -> " + aRun.getExecuted_at());
    }

    @Override
    public void postExecution(Run aRun) throws JobException {
        LogUtil.info(this.getClass(),"post");
    }
}
