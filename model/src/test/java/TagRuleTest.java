import de.pfann.budgetmanager.server.model.TagRule;
import org.junit.Assert;
import org.junit.Test;

public class TagRuleTest {

    @Test
    public void testTagRuleBuilder() {
        String expectedWhenTag = "when";
        String expectedThenTagA = "thenA";
        String expectedThenTagB = "thenB";

        TagRule tagRule = TagRule.create().withWhenTag(expectedWhenTag)
                .withThenTag(expectedThenTagA)
                .withThenTag(expectedThenTagB)
                .build();

        Assert.assertEquals(expectedWhenTag, tagRule.getWhenTag());
        Assert.assertEquals(2, tagRule.getThenTags().size());
        Assert.assertTrue(tagRule.getThenTags().stream().anyMatch( value -> value.equals(expectedThenTagA)));
        Assert.assertTrue(tagRule.getThenTags().stream().anyMatch( value -> value.equals(expectedThenTagB)));
    }
}
