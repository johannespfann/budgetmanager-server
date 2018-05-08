package de.pfann.budgetmanager.server.persistens.core;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class SessionDistributor {

	private static SessionFactory sessionFactory = null;


	/*
	 * Creation logic (considering integration tests and regular environment)
	 */
	public static SessionDistributor create() {
		Configuration configuration = getBaseConfig();
		;
		configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/budgetdb?autoReconnect=true&useSSL=false&createDatabaseIfNotExist=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
		configuration.setProperty("hibernate.connection.username", "budgetmaster");
		configuration.setProperty("hibernate.connection.password", "keymaster");

		configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");

		return new SessionDistributor(configuration);
	}

	public static SessionDistributor createForIT() {
		Configuration configuration = getBaseConfig();

		configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/budgetdb_test?autoReconnect=true&useSSL=false&createDatabaseIfNotExist=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
		configuration.setProperty("hibernate.connection.username", "budgetmaster");
		configuration.setProperty("hibernate.connection.password", "keymaster");
		configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");

		return new SessionDistributor(configuration);
	}

	public static SessionDistributor createForProd(){
		Configuration configuration = getBaseConfig();

		configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/budgetdb?autoReconnect=true&useSSL=false&createDatabaseIfNotExist=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
		configuration.setProperty("hibernate.connection.username", "budgetmaster");
		configuration.setProperty("hibernate.connection.password", "keymaster");

		configuration.setProperty("hibernate.hbm2ddl.auto", "validate");

		return new SessionDistributor(configuration);
	}

	private static Configuration getBaseConfig() {
		Configuration configuration = new Configuration();
		configuration.configure();
		return configuration;
	}

	protected SessionDistributor(Configuration _configuration) {
		if (sessionFactory == null) {
			synchronized (this) {
				if (sessionFactory == null) {
					sessionFactory = buildSessionFactory(_configuration);
				}
			}
		}
	}

	private synchronized SessionFactory buildSessionFactory(Configuration _configuration) {
		try {
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
					.applySettings(_configuration.getProperties()).build();

			return _configuration.buildSessionFactory(serviceRegistry);

		} catch (Throwable e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

    public static void closeSessions() {
		if(sessionFactory != null){
			sessionFactory.close();
		}
		sessionFactory = null;
    }
}
