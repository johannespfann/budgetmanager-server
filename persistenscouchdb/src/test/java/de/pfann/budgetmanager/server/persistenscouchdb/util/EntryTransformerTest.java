package de.pfann.budgetmanager.server.persistenscouchdb.util;

import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.common.model.Entry;
import de.pfann.budgetmanager.server.common.model.Tag;
import de.pfann.budgetmanager.server.common.util.DateUtil;
import de.pfann.budgetmanager.server.persistenscouchdb.model.CDBEntry;
import de.pfann.budgetmanager.server.persistenscouchdb.model.CDBTag;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class EntryTransformerTest {

    /**
     * attributes for cdb
     */

    private CDBEntry cdbEntry;
    private CDBTag cdbFirstTag;
    private CDBTag cdbSecondTag;
    private List<CDBTag> cdbTagList;


    /**
     * attributes for entry
     */
    private Entry entry;
    private LinkedList<Tag> tagList;
    private String tagname;

    /**
     * shared attributes
     */

    private String secondTag;
    private LocalDateTime cdbCreatedAt;
    private String cdbentryId;
    private String amount;
    private String hash;
    private String memo;
    private String username;
    private String firstTag;



    @Before
    public void setUp(){
        cdbEntry = new CDBEntry();
        cdbentryId = "cdbentry_id";
        cdbEntry.setId(cdbentryId);
        amount = "amount";
        cdbEntry.setAmount(amount);
        hash = "hash";
        cdbEntry.setHash(hash);
        memo = "memo";
        cdbEntry.setMemo(memo);
        username = "username";
        cdbEntry.setUsername(username);

        cdbFirstTag = new CDBTag();
        firstTag = "firstTag";
        cdbFirstTag.setName(firstTag);
        cdbSecondTag = new CDBTag();
        secondTag = "secondTag";
        cdbSecondTag.setName(secondTag);

        cdbTagList = new LinkedList<>();
        cdbTagList.add(cdbFirstTag);
        cdbTagList.add(cdbSecondTag);

        cdbEntry.setTags(cdbTagList);
        cdbCreatedAt = LocalDateTime.now();
        cdbEntry.setCreated_at(DateUtil.asDate(cdbCreatedAt));

        entry = new Entry();
        AppUser appUser = new AppUser();
        appUser.setName(username);
        entry.setAppUser(appUser);
        entry.setCreated_at(DateUtil.asDate(cdbCreatedAt));
        entry.setHash(hash);
        entry.setMemo(memo);
        entry.setAmount(amount);

        Tag tag = new Tag();
        tagname = "tagname";
        tag.setName(tagname);

        tagList = new LinkedList<Tag>();
        tagList.add(tag);
        entry.setTags(tagList);

    }

    @Test
    public void testTransformToCDBEntry(){
        // prepare
        CDBEntry newCDBEntry = new CDBEntry();
        AppUser user = new AppUser();
        user.setName(username);

        // execute
        newCDBEntry = CDBEntryTransformer.updateCDBEntry(entry, newCDBEntry);

        // validate
        Assert.assertEquals(newCDBEntry.getAmount(),amount);
        Assert.assertEquals(newCDBEntry.getHash(),hash);
        Assert.assertEquals(newCDBEntry.getMemo(),memo);
        Assert.assertEquals(newCDBEntry.getUsername(),username);
        Assert.assertEquals(newCDBEntry.getTags().get(0).getName(),tagname);
        Assert.assertEquals(DateUtil.asLocalDateTime(newCDBEntry.getCreated_at()).getDayOfMonth(),cdbCreatedAt.getDayOfMonth());
    }

    @Test
    public void testTransformToEntry(){
        // prepare

        // execute
        Entry entry = CDBEntryTransformer.createEntry(cdbEntry);

        // validate
        Assert.assertEquals(entry.getHash(), hash);
        Assert.assertEquals(entry.getMemo(), memo);
        Assert.assertEquals(entry.getAmount(), amount);
        Assert.assertEquals(entry.getTags().get(0).getName(), firstTag);
        Assert.assertEquals(entry.getTags().get(1).getName(), secondTag);
    }

}
