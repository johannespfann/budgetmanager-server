package de.pfann.budgetmanager.server.persistens.daos;


import de.pfann.budgetmanager.server.common.facade.TagTemplateFacade;
import de.pfann.budgetmanager.server.common.model.RotationEntry;
import de.pfann.budgetmanager.server.common.model.TagTemplate;

public class TagTemplateSQLFacade implements TagTemplateFacade {

    private TagTemplateDao tagTemplateDao;

    public TagTemplateSQLFacade(){
        tagTemplateDao = TagTemplateDao.create();
    }

    @Override
    public void findAllByRotationEntry(RotationEntry aRotationEntry){
        tagTemplateDao.findAllByRotationEntry(aRotationEntry);
    }

    @Override
    public void save(TagTemplate aTagTemplate){
        tagTemplateDao.save(aTagTemplate);
    }

    @Override
    public void delete(TagTemplate aTagTemplate){
        tagTemplateDao.delete(aTagTemplate);
    }
}
