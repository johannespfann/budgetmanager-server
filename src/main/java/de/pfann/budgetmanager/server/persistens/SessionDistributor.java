package de.pfann.budgetmanager.server.persistens;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionDistributor {

	private static SessionFactory sessionFactory = null;


	/*
	 * Creation logic (considering integration tests and regular environment)
	 */
	public static SessionDistributor create() {
		Configuration configuration = getBaseConfig();

		//ConfigManager cm = ConfigManager.getInstance();
		//configuration.setProperty("hibernate.connection.url", cm.get(SessionDistributor.class, "url"));
		//configuration.setProperty("hibernate.connection.username", cm.get(SessionDistributor.class, "username"));
		//configuration.setProperty("hibernate.connection.password", cm.get(SessionDistributor.class, "password"));

		return new SessionDistributor(configuration);
	}

	public static SessionDistributor createForIT() {
		Configuration configuration = getBaseConfig();

		//ConfigManager cm = ConfigManager.getInstance();
		//configuration.setProperty("hibernate.connection.url", cm.get(SessionDistributor.class, "iturl"));
		//configuration.setProperty("hibernate.connection.username", cm.get(SessionDistributor.class, "itusername"));
		//configuration.setProperty("hibernate.connection.password", cm.get(SessionDistributor.class, "itpassword"));

		return new SessionDistributor(configuration);
	}

	private static Configuration getBaseConfig() {
		Configuration configuration = new Configuration();
		configuration.configure();
		return configuration;
	}

	protected SessionDistributor(Configuration _configuration) {
		System.out.println("Init SessionDistributer");
		if (sessionFactory == null) {
			synchronized (this) {
				if (sessionFactory == null) {
					sessionFactory = buildSessionFactory(_configuration);
					System.out.println("### ->  sessionfactory was null and is now initialized");
				}
			}
		}
		else{
			System.out.println("### -> Called again and session was not null");
		}
	}

	private synchronized SessionFactory buildSessionFactory(Configuration _configuration) {
		try {
			//ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
			//		.applySettings(_configuration.getProperties()).build();
//
//			return _configuration.buildSessionFactory(serviceRegistry);
			System.out.println("Get SessionFactory from new bla bla");
			return new Configuration().configure()
					.buildSessionFactory();

		} catch (Throwable e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	public Session getCurrentSession() {
		System.out.println("Befor session");

		System.out.println("session was open");
		Session session = sessionFactory.getCurrentSession();

		if(session == null){
			System.out.println("Session was null");
			return sessionFactory.openSession();
		}
		return sessionFactory.getCurrentSession();
	}

}
