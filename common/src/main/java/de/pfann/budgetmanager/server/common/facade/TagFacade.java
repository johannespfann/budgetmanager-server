package de.pfann.budgetmanager.server.common.facade;

import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.common.model.Entry;
import de.pfann.budgetmanager.server.common.model.Tag;

import java.util.Set;

public interface TagFacade {
    void persistTag(Tag aTag);

    Set<Tag> getTags(Entry aEntry);

    Set<Tag> getTags(AppUser aUser);
}
