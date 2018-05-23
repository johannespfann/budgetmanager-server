package de.pfann.budgetmanager.server.persistens.daos;

import de.pfann.budgetmanager.server.persistens.core.SessionDistributor;
import org.junit.After;
import org.junit.Test;


public class SessionDistributorIT {

	@After
	public void cleanUp(){
		SessionDistributor.closeSessions();
	}

	@Test
	public void testCreate() {
		SessionDistributor.create();
	}

	@Test
	public void testCreateForIT() {
		SessionDistributor.createForIT();
	}

}
