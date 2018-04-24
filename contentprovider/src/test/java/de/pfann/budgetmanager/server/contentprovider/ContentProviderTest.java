package de.pfann.budgetmanager.server.contentprovider;

import org.hibernate.annotations.SelectBeforeUpdate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ContentProviderTest {

    /**
     * class under test
     */

    private ContentProvider contentProvider;

    @Before
    public void setUp(){

    }

    @Test
    public void showAll(){
        ContentProvider provider = new ContentProvider("C:\\Users\\Johannes\\projects\\budgetmanager-server\\contentprovider\\src\\test\\resources\\entry.xml");
    }

    @After
    public void cleanUp(){

    }
}
