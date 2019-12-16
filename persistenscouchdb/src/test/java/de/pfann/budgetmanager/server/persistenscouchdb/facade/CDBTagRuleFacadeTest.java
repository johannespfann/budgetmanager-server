package de.pfann.budgetmanager.server.persistenscouchdb.facade;

import de.pfann.budgetmanager.server.model.Account;
import de.pfann.budgetmanager.server.model.TagRule;
import de.pfann.budgetmanager.server.model.User;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBUserDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CDBTagRuleFacadeTest {

    /**
     * test data
     */

    private User user;
    private List<Account> accounts;
    private List<TagRule> tagRules;

    String userHash = "specificUserHash";
    String accountHash = "specificUserHash";

    /**
     * mocks
     */

    private CDBUserDao userDao = mock(CDBUserDao.class);

    /**
     * class under test
     */
    private CDBTagRuleFacade cut;


    @Before
    public void setUp() {
        TagRule tagRuleA = TagRule.create()
                .withWhenTag("a")
                .withThenTag("1")
                .withThenTag("2")
                .build();

        TagRule tagRuleB = TagRule.create()
                .withWhenTag("b")
                .withThenTag("3")
                .withThenTag("4")
                .build();

        List<TagRule> tagRules = new LinkedList<>();
        tagRules.add(tagRuleA);
        tagRules.add(tagRuleB);

        accounts = new LinkedList<>();
        accounts.add(Account.create()
                .withHash(accountHash)
                .withTagRules(tagRules)
                .build());
        user = new User();
        user.setId(userHash);
        user.setKontos(accounts);

        when(userDao.get(any())).thenReturn(user);

        cut = new CDBTagRuleFacade(userDao);
    }

    @Test
    public void testGetAllRules() {
        // execute
        List<TagRule> tagRules = cut.getTagRules(userHash,accountHash);

        // validate
        Assert.assertEquals(2, tagRules.size());
    }

    @Test
    public void testAddTagRule() {
        TagRule tagRuleC = TagRule.create()
                .withWhenTag("c")
                .withThenTag("5")
                .withThenTag("6")
                .build();

        cut.addTagRule(userHash,accountHash, tagRuleC);

        ArgumentCaptor<User> parameter = ArgumentCaptor.forClass(User.class);
        verify(userDao).update(parameter.capture());

        Assert.assertEquals(3, parameter.getValue().getKontos().get(0).getTagrules().size());

    }

    @Test
    public void testDeleteTagRule() {
        // prepare
        TagRule tagRuleC = TagRule.create()
                .withWhenTag("a")
                .withThenTag("5")
                .withThenTag("6")
                .build();

        // execute
        cut.deleteTagRule(userHash,accountHash, "a");

        // verify
        ArgumentCaptor<User> parameter = ArgumentCaptor.forClass(User.class);
        verify(userDao).update(parameter.capture());

        Assert.assertEquals(1, parameter.getValue().getKontos().get(0).getTagrules().size());
        Assert.assertEquals("b", parameter.getValue().getKontos().get(0).getTagrules().get(0).getWhenTag());
    }



    @Test
    public void testTagRuleWasDuplicated() {
        // TODO
    }

}
