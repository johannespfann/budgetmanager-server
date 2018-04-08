package de.pfann.budgetmanager.server.persistens.core;

import org.hibernate.Session;

public class DbWriter {

	private final SessionDistributor sessionDistributor;

	public static DbWriter create() {
		return new DbWriter(SessionDistributor.create());
	}

	public DbWriter(SessionDistributor _sessionDistributor) {
		sessionDistributor = _sessionDistributor;
	}

	public synchronized Object save(Object aObject) {
		Session session = sessionDistributor.getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(aObject);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			throw new DataHandlerException(e);
		}
		return aObject;

	}

	public synchronized void delete(Object _object){
		Session session = sessionDistributor.getCurrentSession();
		try{
			session.beginTransaction();
			session.delete(_object);
			session.getTransaction().commit();
		}catch (Exception e){
			session.getTransaction().rollback();
			e.printStackTrace();
			throw new DataHandlerException(e);
		}
	}

}
