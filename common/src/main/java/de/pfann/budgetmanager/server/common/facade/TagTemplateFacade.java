package de.pfann.budgetmanager.server.common.facade;

import de.pfann.budgetmanager.server.common.model.RotationEntry;
import de.pfann.budgetmanager.server.common.model.TagTemplate;

public interface TagTemplateFacade {
    void findAllByRotationEntry(RotationEntry aRotationEntry);

    void save(TagTemplate aTagTemplate);

    void delete(TagTemplate aTagTemplate);
}
