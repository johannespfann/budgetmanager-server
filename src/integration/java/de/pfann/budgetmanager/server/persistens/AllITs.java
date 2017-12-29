package de.pfann.budgetmanager.server.persistens;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
        DbReaderIT.class,
        SessionDistributorIT.class ,
        AppUserDaoIT.class ,
        CategoryDaoIT.class,
        EntryDaoIT.class,
        TagDaoIT.class})
public class AllITs {

}
