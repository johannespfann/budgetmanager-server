package de.pfann.budgetmanager.server.restservices.resources.email;

import de.pfann.budgetmanager.server.common.facade.TagRuleFacade;
import de.pfann.budgetmanager.server.model.TagRule;
import de.pfann.budgetmanager.server.persistenscouchdb.facade.CDBTagRuleFacade;
import de.pfann.budgetmanager.server.restservices.resources.TagRuleResourceFacade;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CDBTagRuleResourceFacadeTest {

    private TagRuleFacade tagRuleFacade;

    private TagRuleResourceFacade cut;

    @Before
    public void setUp() {
        tagRuleFacade = mock(CDBTagRuleFacade.class);
        cut = new TagRuleResourceFacade(tagRuleFacade);
    }


    @Test
    public void shouldSaveTagRule() {
        // prepared
        String expectedWhenTag = "whentagparameter";
        String expectedThenTagA = "a";
        String expectedThenTagB = "b";
        String expectedThenTagC = "c";
        List<String> expectedThenTags = new LinkedList<>();
        expectedThenTags.add(expectedThenTagA);
        expectedThenTags.add(expectedThenTagB);
        expectedThenTags.add(expectedThenTagC);
        TagRule tagRule = new TagRule(expectedWhenTag, expectedThenTags);

        // exeucte
        cut.saveTagRule("owner","hash", tagRule);


        // validate
        ArgumentCaptor<TagRule> tagRuleParameter = ArgumentCaptor.forClass(TagRule.class);
        verify(tagRuleFacade).addTagRule(any(),any(), tagRuleParameter.capture());

        Assert.assertTrue(tagRuleParameter.getValue().getWhenTag().equals(expectedWhenTag));
        Assert.assertEquals(3, tagRuleParameter.getValue().getThenTags().size());
        Assert.assertTrue(tagRuleParameter.getValue().getThenTags().stream().anyMatch(value -> value.equals("a")));
        Assert.assertTrue(tagRuleParameter.getValue().getThenTags().stream().anyMatch(value -> value.equals("b")));
        Assert.assertTrue(tagRuleParameter.getValue().getThenTags().stream().anyMatch(value -> value.equals("c")));

    }

    @Test
    public void shouldDeleteDuplicatesInThenTags() {
        // prepared
        String expectedWhenTag = "whentagparameter";
        String expectedThenTagA = "a";
        String expectedThenTagB = "b";
        String expectedThenTagC = "b";
        List<String> expectedThenTags = new LinkedList<>();
        expectedThenTags.add(expectedThenTagA);
        expectedThenTags.add(expectedThenTagB);
        expectedThenTags.add(expectedThenTagC);
        TagRule tagRule = new TagRule(expectedWhenTag, expectedThenTags);

        // exeucte
        cut.saveTagRule("owner","hash", tagRule);


        // validate
        ArgumentCaptor<TagRule> tagRuleParameter = ArgumentCaptor.forClass(TagRule.class);
        verify(tagRuleFacade).addTagRule(any(),any(), tagRuleParameter.capture());

        Assert.assertTrue(tagRuleParameter.getValue().getWhenTag().equals(expectedWhenTag));
        Assert.assertEquals(2, tagRuleParameter.getValue().getThenTags().size());
        Assert.assertTrue(tagRuleParameter.getValue().getThenTags().stream().anyMatch(value -> value.equals("a")));
        Assert.assertTrue(tagRuleParameter.getValue().getThenTags().stream().anyMatch(value -> value.equals("b")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectSaveRuleIfWhenTagIsInThenTags() {
        // prepared
        String expectedWhenTag = "whentagparameter";
        String expectedThenTagA = "a";
        String expectedThenTagB = "whentagparameter";
        String expectedThenTagC = "b";
        List<String> expectedThenTags = new LinkedList<>();
        expectedThenTags.add(expectedThenTagA);
        expectedThenTags.add(expectedThenTagB);
        expectedThenTags.add(expectedThenTagC);
        TagRule tagRule = new TagRule(expectedWhenTag, expectedThenTags);

        // exeucte
        cut.saveTagRule("owner", "hash", tagRule);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectThenTagOverLenghtLimit() {
        // prepared
        String expectedWhenTag = "whentagparameter";
        String expectedThenTagA = "a";
        String expectedThenTagB = "whentagparametersadfasdfasdfasfasfasfsafsafsfasfsafs";
        String expectedThenTagC = "b";
        List<String> expectedThenTags = new LinkedList<>();
        expectedThenTags.add(expectedThenTagA);
        expectedThenTags.add(expectedThenTagB);
        expectedThenTags.add(expectedThenTagC);
        TagRule tagRule = new TagRule(expectedWhenTag, expectedThenTags);

        // exeucte
        cut.saveTagRule("owner", "hash", tagRule);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectWhenTagOverLenghtLimit() {
        // prepared
        String expectedWhenTag = "whentagparametersadfasdfasdfasfasfasfsafsafsfasfsafs";
        String expectedThenTagA = "a";
        String expectedThenTagB = "c";
        String expectedThenTagC = "b";
        List<String> expectedThenTags = new LinkedList<>();
        expectedThenTags.add(expectedThenTagA);
        expectedThenTags.add(expectedThenTagB);
        expectedThenTags.add(expectedThenTagC);
        TagRule tagRule = new TagRule(expectedWhenTag, expectedThenTags);

        // exeucte
        cut.saveTagRule("owner", "hash", tagRule);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectThenTagWithInvalidSign() {
        // prepared
        String expectedWhenTag = "whentagpter";
        String expectedThenTagA = "a";
        String expectedThenTagB = "c!";
        String expectedThenTagC = "b";
        List<String> expectedThenTags = new LinkedList<>();
        expectedThenTags.add(expectedThenTagA);
        expectedThenTags.add(expectedThenTagB);
        expectedThenTags.add(expectedThenTagC);
        TagRule tagRule = new TagRule(expectedWhenTag, expectedThenTags);

        // exeucte
        cut.saveTagRule("owner", "hash", tagRule);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectWhenTagWithInvalidSign() {
        // prepared
        String expectedWhenTag = "whentagpt!er";
        String expectedThenTagA = "a";
        String expectedThenTagB = "c";
        String expectedThenTagC = "b";
        List<String> expectedThenTags = new LinkedList<>();
        expectedThenTags.add(expectedThenTagA);
        expectedThenTags.add(expectedThenTagB);
        expectedThenTags.add(expectedThenTagC);
        TagRule tagRule = new TagRule(expectedWhenTag, expectedThenTags);

        // exeucte
        cut.saveTagRule("owner", "hash", tagRule);
    }


}
