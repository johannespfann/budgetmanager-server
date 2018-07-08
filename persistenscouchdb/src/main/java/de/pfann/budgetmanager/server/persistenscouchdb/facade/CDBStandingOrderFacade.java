package de.pfann.budgetmanager.server.persistenscouchdb.facade;

import de.pfann.budgetmanager.server.common.facade.RotationEntryFacade;
import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.common.model.RotationEntry;
import de.pfann.budgetmanager.server.common.model.TagTemplate;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBStandingOrderDao;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBStandingOrderDaoFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBUserDao;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBUserDaoFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.model.CDBStandigOrderEntry;
import de.pfann.budgetmanager.server.persistenscouchdb.model.CDBTag;
import de.pfann.budgetmanager.server.persistenscouchdb.model.CDBUser;
import de.pfann.budgetmanager.server.persistenscouchdb.util.CDBKontoDatabaseId;
import de.pfann.budgetmanager.server.persistenscouchdb.util.CDBStandingOrderId;
import de.pfann.budgetmanager.server.persistenscouchdb.util.CDBUserId;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CDBStandingOrderFacade implements RotationEntryFacade{

    private CDBStandingOrderDaoFactory standingOrderDaoFactory;
    private CDBUserDao userDao;

    public CDBStandingOrderFacade(CDBStandingOrderDaoFactory aStandingOrderDaoFacade, CDBUserDaoFactory aUserDaoFactory){
        standingOrderDaoFactory = aStandingOrderDaoFacade;
        userDao = aUserDaoFactory.createDao();
    }

    @Override
    public void save(RotationEntry aEntry) {
        CDBUser cdbUser = createCDBUser(aEntry.getUser().getName());
        CDBStandingOrderDao standingOrderDao = createStandingOrderDao(cdbUser);

        CDBStandigOrderEntry standigOrderEntry = new CDBStandigOrderEntry();

        CDBStandingOrderId standingOrderId = CDBStandingOrderId.createBuilder()
                .withUsername(aEntry.getUser().getName())
                .withHash(aEntry.getHash())
                .build();

        standigOrderEntry.setId(standingOrderId.toString());
        standigOrderEntry.setAmount(aEntry.getAmount());
        standigOrderEntry.setHash(aEntry.getHash());
        standigOrderEntry.setMemo(aEntry.getMemo());
        standigOrderEntry.setRotation_strategy(aEntry.getRotation_strategy());
        standigOrderEntry.setKonto(cdbUser.getKontos().get(0).getHash());
        standigOrderEntry.setUsername(cdbUser.getUsername());
        standigOrderEntry.setStart_at(aEntry.getStart_at());
        standigOrderEntry.setLast_executed(aEntry.getStart_at());

        List<CDBTag> cdbTags = new LinkedList<>();
        for(TagTemplate tagTemplate : aEntry.getTags()){
            CDBTag cdbTag = new CDBTag();
            cdbTag.setName(tagTemplate.getName());
            cdbTags.add(cdbTag);
        }
        standigOrderEntry.setTags(cdbTags);

        standingOrderDao.add(standigOrderEntry);
    }

    @Override
    public void update(RotationEntry aEntry) {
        CDBUser cdbUser = createCDBUser(aEntry.getUser().getName());
        CDBStandingOrderDao standingOrderDao = createStandingOrderDao(cdbUser);

        CDBStandingOrderId standingOrderId = CDBStandingOrderId.createBuilder()
                .withUsername(cdbUser.getUsername())
                .withHash(aEntry.getHash())
                .build();

        CDBStandigOrderEntry standingOrder = standingOrderDao.get(standingOrderId.toString());
        standingOrder = updateStandingOrder(standingOrder,aEntry);
        standingOrderDao.update(standingOrder);
    }

    @Override
    public List<RotationEntry> getRotationEntries(AppUser aUser) {
        CDBUser cdbUser = createCDBUser(aUser.getName());
        CDBStandingOrderDao standingOrderDao = createStandingOrderDao(cdbUser);

        List<CDBStandigOrderEntry> standigOrderEntries = standingOrderDao.getAll();
        List<RotationEntry> rotationEntries = new LinkedList<>();

        for(CDBStandigOrderEntry entry : standigOrderEntries){
            RotationEntry rotationEntry = createRotationEntry(entry);
            rotationEntries.add(rotationEntry);
        }

        return rotationEntries;
    }


    @Override
    public RotationEntry getRotationEntryByHash(AppUser appUser, String aHash) {
        CDBUser cdbUser = createCDBUser(appUser.getName());
        CDBStandingOrderDao standingOrderDao = createStandingOrderDao(cdbUser);

        CDBStandingOrderId standingOrderId = CDBStandingOrderId.createBuilder()
                .withUsername(cdbUser.getUsername())
                .withHash(aHash).build();

        CDBStandigOrderEntry cdbStandigOrderEntry = standingOrderDao.get(standingOrderId.toString());
        return createRotationEntry(cdbStandigOrderEntry);
    }

    @Override
    public void delete(RotationEntry aRotationEntry) {
        CDBUser cdbUser = createCDBUser(aRotationEntry.getUser().getName());
        CDBStandingOrderDao standingOrderDao = createStandingOrderDao(cdbUser);

        CDBStandingOrderId standingOrderId = CDBStandingOrderId.createBuilder()
                .withUsername(cdbUser.getUsername())
                .withHash(aRotationEntry.getHash()).build();

        CDBStandigOrderEntry cdbStandigOrderEntry = standingOrderDao.get(standingOrderId.toString());
        standingOrderDao.remove(cdbStandigOrderEntry);
    }

    @Override
    public void validateRotationEntry(RotationEntry aRotationEntry) {

    }

    private CDBStandingOrderDao createStandingOrderDao(CDBUser cdbUser) {
        CDBKontoDatabaseId kontoDatabaseId = CDBKontoDatabaseId.builder()
                .withUsername(cdbUser.getUsername())
                .withKontoHash(cdbUser.getKontos().get(0).getHash())
                .build();
        return standingOrderDaoFactory.createDao(kontoDatabaseId.toString());
    }

    private CDBUser createCDBUser(String aUserName) {
        CDBUser cdbUser;CDBUserId userId = CDBUserId.create(aUserName);
        cdbUser = userDao.get(userId.toString());
        return cdbUser;
    }

    private RotationEntry createRotationEntry(CDBStandigOrderEntry aStandingOrder){
        RotationEntry rotationEntry = new RotationEntry();
        rotationEntry.setAmount(aStandingOrder.getAmount());
        rotationEntry.setHash(aStandingOrder.getHash());
        rotationEntry.setMemo(aStandingOrder.getMemo());
        rotationEntry.setRotation_strategy(aStandingOrder.getRotation_strategy());

        List<TagTemplate> tagTemplates = new ArrayList<>();

        for(CDBTag cdbTag : aStandingOrder.getTags()){
            TagTemplate tag = new TagTemplate();
            tag.setName(cdbTag.getName());
            tagTemplates.add(tag);
        }
        rotationEntry.setTags(tagTemplates);
        rotationEntry.setStart_at(aStandingOrder.getStart_at());
        rotationEntry.setLast_executed(aStandingOrder.getLast_executed());
        return rotationEntry;
    }

    private CDBStandigOrderEntry updateStandingOrder(CDBStandigOrderEntry cdbStandigOrder, RotationEntry aRotationEntry){
        cdbStandigOrder.setMemo(aRotationEntry.getMemo());
        cdbStandigOrder.setAmount(aRotationEntry.getAmount());
        cdbStandigOrder.setRotation_strategy(aRotationEntry.getRotation_strategy());
        cdbStandigOrder.setLast_executed(aRotationEntry.getLast_executed());
        cdbStandigOrder.setStart_at(aRotationEntry.getStart_at());
        cdbStandigOrder.setEnd_at(aRotationEntry.getEnd_at());

        List<CDBTag> cdbTags = new LinkedList<>();
        for(TagTemplate tagTemplate : aRotationEntry.getTags()){
            CDBTag cdbTag = new CDBTag();
            cdbTag.setName(tagTemplate.getName());
            cdbTags.add(cdbTag);
        }

        cdbStandigOrder.setTags(cdbTags);
        return cdbStandigOrder;
    }
}
