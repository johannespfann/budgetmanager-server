package de.pfann.budgetmanager.server.persistens.daos;

import de.pfann.budgetmanager.server.persistens.model.RotationEntry;
import de.pfann.budgetmanager.server.persistens.model.TagTemplate;

public class TagTemplateFacade {

    private TagTemplateDao tagTemplateDao;

    public TagTemplateFacade(){
        tagTemplateDao = TagTemplateDao.create();
    }

    public void findAllByRotationEntry(RotationEntry aRotationEntry){
        tagTemplateDao.findAllByRotationEntry(aRotationEntry);
    }

    public void save(TagTemplate aTagTemplate){
        tagTemplateDao.save(aTagTemplate);
    }

    public void delete(TagTemplate aTagTemplate){
        tagTemplateDao.delete(aTagTemplate);
    }
}
