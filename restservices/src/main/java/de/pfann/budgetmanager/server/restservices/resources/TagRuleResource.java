package de.pfann.budgetmanager.server.restservices.resources;

import de.pfann.budgetmanager.server.model.TagRule;
import de.pfann.budgetmanager.server.restservices.resources.core.CrossOriginFilter;
import de.pfann.budgetmanager.server.restservices.resources.core.Logged;
import de.pfann.budgetmanager.server.restservices.resources.util.TagRuleJsonMapper;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("tagrule/")
public class TagRuleResource {


    private TagRuleResourceFacade tagRuleResourceFacade;

    public TagRuleResource(TagRuleResourceFacade aTagRuleResourceFacade) {
        tagRuleResourceFacade = aTagRuleResourceFacade;
    }

    @GET
    @Logged
    @CrossOriginFilter
    @Produces(MediaType.APPLICATION_JSON)
    @Path("owner/{owner}/account/{account}/all")
    public String getTagRules(
            @PathParam("owner") String aOwner,
            @PathParam("account") String aAccountHash) {
        List<TagRule> tagRules = tagRuleResourceFacade.getAllTagRules(aOwner, aAccountHash);
        String json = TagRuleJsonMapper.convertToJson(tagRules);
        return json;
    }

    @POST
    @Logged
    @CrossOriginFilter
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("owner/{owner}/account/{account}/add")
    public void saveTagRule(
            @PathParam("owner") String aOwner,
            @PathParam("account") String aAccountHash,
            String aTagRule) {
        TagRule tagRule = TagRuleJsonMapper.convertToTagRule(aTagRule);
        tagRuleResourceFacade.saveTagRule(aOwner,aAccountHash,tagRule);
    }

    @PATCH
    @Logged
    @CrossOriginFilter
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("owner/{owner}/account/{account}/update")
    public void updateTagRule(
            @PathParam("owner") String aOwner,
            @PathParam("account") String aAccountHash,
            String aTagRule) {
        TagRule tagRule = TagRuleJsonMapper.convertToTagRule(aTagRule);
        tagRuleResourceFacade.updateTagRule(aOwner,aAccountHash,tagRule);

    }

    @DELETE
    @Logged
    @CrossOriginFilter
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("owner/{owner}/account/{account}/delete/{thentag}")
    public void deleteTagRule(
            @PathParam("owner") String aOwner,
            @PathParam("account") String aAccountHash,
            @PathParam("thentag") String aThenTagName) {
        tagRuleResourceFacade.deleteTagRule(aOwner, aAccountHash, aThenTagName);
    }

}
