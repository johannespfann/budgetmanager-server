package de.pfann.budgetmanager.server.core.jobengine;

import de.pfann.budgetmanager.server.persistens.model.Run;

import java.util.Collection;
import java.util.List;

public interface RunProvider {

    List<Run> prepareRuns();

}
