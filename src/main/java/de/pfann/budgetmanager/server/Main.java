package de.pfann.budgetmanager.server;

import de.pfann.budgetmanager.server.model.AppUser;
import de.pfann.budgetmanager.server.model.Entry;
import de.pfann.budgetmanager.server.persistens.AppUserDao;
import de.pfann.budgetmanager.server.persistens.EntryDao;


public class Main {

    public static void main(String[] args){

        AppUser user = new AppUser();
        user.setName("user1");
        user.setEmail("email");

        AppUser user2 = new  AppUser();
        user2.setEmail("email");
        user2.setName("user2");

        AppUserDao userDao = AppUserDao.create();

        userDao.save(user);
        userDao.save(user2);


        EntryDao dao = EntryDao.create();

        Entry entry = new Entry();
        entry.setHash("asdfasdf");
        entry.setMemo("memo");
        entry.setAmount(123);
        entry.setAppUser(user);

        dao.save(entry);


        Entry entry2 = new Entry();
        entry.setHash("asdf");
        entry.setMemo("memo");
        entry.setAppUser(user2);
        entry.setAmount(-123);



        System.out.println("anzahl entries: " + dao.countAll());






    }
}
