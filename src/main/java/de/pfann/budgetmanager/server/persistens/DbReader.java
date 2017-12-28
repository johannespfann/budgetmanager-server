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

	public DbReader(SessionDistributor _sessionDistributor) {
		sessionDistributor = _sessionDistributor;
	}

	@SuppressWarnings("rawtypes")
	public List get(DetachedCriteria _criteria) {
		Session session = doPrepareSession();
		List results = _criteria.getExecutableCriteria(session).list();
		doCloseSession(session);
		return results;
	}

	@SuppressWarnings("rawtypes")
	public List get(Class<?> _class, String _query, Map<String, Object> _parameters) {
		Session session = doPrepareSession();

		SQLQuery query = session.createSQLQuery(_query);
		query.addEntity(_class);

		// Iterate through Map and add parameters
		if (_parameters != null) {
			for (String key : _parameters.keySet()) {
				query.setParameter(key, _parameters.get(key));
			}
		}

		List results = query.list();
		doCloseSession(session);
		return results;
	}

	public Object getUnique(DetachedCriteria _criteria) {
		Session session = doPrepareSession();
		Object result = _criteria.getExecutableCriteria(session).uniqueResult();
		doCloseSession(session);
		return result;
	}

	public long count(DetachedCriteria _criteria) {
		Session session = doPrepareSession();
		Criteria criteria = _criteria.getExecutableCriteria(session).setProjection(Projections.rowCount());
		long result = (long) criteria.uniqueResult();
		doCloseSession(session);
		return result;
	}

	protected void doCloseSession(Session _session) {
		_session.getTransaction().commit();
	}

	protected Session doPrepareSession() {
		Session session = sessionDistributor.getCurrentSession();
		session.beginTransaction();
		return session;
	}

}
