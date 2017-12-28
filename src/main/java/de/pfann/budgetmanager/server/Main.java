package de.pfann.budgetmanager.server;

import de.pfann.budgetmanager.server.model.Entry;
import de.pfann.budgetmanager.server.persistens.EntryDao;
import de.pfann.budgetmanager.server.persistens.SessionDistributor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {

    public static void main(String[] args){

        /*
        SessionDistributor distributor = SessionDistributor.create();

            SessionFactory sessionFactory = new Configuration().configure()
                    .buildSessionFactory();
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            Entry entry = new Entry();
            entry.setMemo("test memo");

            session.save(entry);

            session.getTransaction().commit();
            session.close();

        session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Entry entry2 = new Entry();
        entry2.setMemo("test memo");

        session.save(entry2);

        session.getTransaction().commit();
        session.close();
*/


        EntryDao dao = EntryDao.create();
        System.out.println("Start saving object");
        //System.out.println("anzahl entries: " + dao.countAll());

        Entry entry = new Entry();
        entry.setHash("asdfasdf");
        entry.setMemo("memo");
        entry.setAmount(123);

        dao.save(entry);

        System.out.println("anzahl entries: " + dao.countAll());






    }
}
