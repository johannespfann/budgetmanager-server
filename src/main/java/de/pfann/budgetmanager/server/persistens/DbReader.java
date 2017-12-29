package de.pfann.budgetmanager.server.persistens;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;

import java.util.List;
import java.util.Map;

public class DbReader {

	private final SessionDistributor sessionDistributor;

	public static DbReader create() {
		return new DbReader(SessionDistributor.create());
	}

	public DbReader(SessionDistributor aSessionDistributor) {
		sessionDistributor = aSessionDistributor;
	}

	@SuppressWarnings("rawtypes")
	public List get(DetachedCriteria aCriteria) {
		Session session = doPrepareSession();
		List results = aCriteria.getExecutableCriteria(session).list();
		doCloseSession(session);
		return results;
	}

	@SuppressWarnings("rawtypes")
	public List get(Class<?> aClass, String aQuery, Map<String, Object> aParameters) {
		Session session = doPrepareSession();

		SQLQuery query = session.createSQLQuery(aQuery);
		query.addEntity(aClass);

		// Iterate through Map and add parameters
		if (aParameters != null) {
			for (String key : aParameters.keySet()) {
				query.setParameter(key, aParameters.get(key));
			}
		}

		List results = query.list();
		doCloseSession(session);
		return results;
	}

	public Object getUnique(DetachedCriteria aCriteria) {
		Session session = doPrepareSession();
		Object result = aCriteria.getExecutableCriteria(session).uniqueResult();
		doCloseSession(session);
		return result;
	}

	public long count(DetachedCriteria aCriteria) {
		Session session = doPrepareSession();
		Criteria criteria = aCriteria.getExecutableCriteria(session).setProjection(Projections.rowCount());
		long result = (long) criteria.uniqueResult();
		doCloseSession(session);
		return result;
	}

	protected void doCloseSession(Session aSession) {
		aSession.getTransaction().commit();
	}

	protected Session doPrepareSession() {
		Session session = sessionDistributor.getCurrentSession();
		session.beginTransaction();
		return session;
	}

}
