package de.pfann.budgetmanager.server;

import de.pfann.budgetmanager.server.model.Entry;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {

    public static void main(String[] args){

            SessionFactory sessionFactory = new Configuration().configure()
                    .buildSessionFactory();
            Session session = sessionFactory.openSession();
            session.beginTransaction();

            Entry entry = new Entry();
            entry.setMemo("test memo");

            session.save(entry);

            session.getTransaction().commit();
            session.close();

    }
}
