package de.pfann.budgetmanager.server.persistenscouchdb.facade;

import de.pfann.budgetmanager.server.model.Entry;
import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.GenerateView;
import org.ektorp.support.View;
import org.ektorp.support.Views;

import java.util.List;
@Views({
        @View(name = "lasthalfyear", map = "function(doc) { " +
                "const newDate = new Date(doc.createdAt)\n" +
                "const todayDate = new Date()\n" +
                "\n" +
                "const newDateTime = newDate.getTime()\n" +
                "const todayTime = todayDate.getTime()\n" +
                "\n" +
                "if(todayTime > newDateTime) {\n" +
                " emit(doc.username, doc._id)\n" +
                "}  " +
                "}")
})
public class EntrySupport extends CouchDbRepositorySupport<Entry> {


    public EntrySupport(CouchDbConnector db) {
        super(Entry.class, db, true);
        initStandardDesignDocument();
    }

    @GenerateView
    public List<Entry> findByHash() {
        return queryView("by_hash", "7285d23eb35a166bc906748c9719b9056d4414f9");
    }

}
