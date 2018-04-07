package de.pfann.budgetmanager.server.core.persistens;

import de.pfann.budgetmanager.server.core.model.AppUser;
import de.pfann.budgetmanager.server.core.persistens.core.DbReader;
import de.pfann.budgetmanager.server.core.persistens.core.DbWriter;
import de.pfann.budgetmanager.server.core.persistens.core.SessionDistributor;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

public class DbReaderIT {

	public static final String NAME = "max muster";
	public static final String EMAIL = "max@muster.de";
	public static final String PASSWORD = "password";
	/**
	 * Helper objects
	 */

	private DbWriter dbWriter;

	/**
	 * Class under test
	 */
	private DbReader dbReader;

	@Before
	public void setUp() {
		dbWriter = new DbWriter(SessionDistributor.createForIT());;
		dbReader = new DbReader(SessionDistributor.createForIT());
	}

	@After
	public void cleanUp(){
		SessionDistributor.closeSessions();
	}

	@Test
	public void testGet() {

		AppUser user = new AppUser();
		user.setName(NAME);
		user.setEmail(EMAIL);
		user.setPassword(PASSWORD);
		dbWriter.save(user);

		// Execute
		DetachedCriteria criteria = DetachedCriteria.forClass(AppUser.class);
		criteria.addOrder(Order.asc("name"));

		@SuppressWarnings("unchecked")
		List<AppUser> results = dbReader.get(criteria);

		// Validate
		Iterator<AppUser> it = results.iterator();
		Assert.assertEquals(NAME, it.next().getName());
	}

}
