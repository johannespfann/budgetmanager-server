package de.pfann.budgetmanager.server.persistenscouchdb.facade;

import de.pfann.budgetmanager.server.common.facade.StandingOrderFacade;
import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.common.model.StandingOrder;
import de.pfann.budgetmanager.server.common.model.Tag;
import de.pfann.budgetmanager.server.common.util.DateUtil;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBStandingOrderDao;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBStandingOrderDaoFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBUserDao;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBUserDaoFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.model.CDBKonto;
import de.pfann.budgetmanager.server.persistenscouchdb.model.CDBStandingOrder;
import de.pfann.budgetmanager.server.persistenscouchdb.model.CDBTag;
import de.pfann.budgetmanager.server.persistenscouchdb.model.CDBUser;
import de.pfann.budgetmanager.server.persistenscouchdb.util.CDBKontoDatabaseId;
import de.pfann.budgetmanager.server.persistenscouchdb.util.CDBStandingOrderId;
import de.pfann.budgetmanager.server.persistenscouchdb.util.CDBUserId;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class CDBStandingOrderFacade implements StandingOrderFacade {

    private CDBStandingOrderDaoFactory standingOrderDaoFactory;
    private CDBUserDao userDao;

    public CDBStandingOrderFacade(CDBStandingOrderDaoFactory aStandingOrderDaoFacade, CDBUserDaoFactory aUserDaoFactory){
        standingOrderDaoFactory = aStandingOrderDaoFacade;
        userDao = aUserDaoFactory.createDao();
    }

    @Override
    public void save(StandingOrder aEntry) {
        CDBUser cdbUser = createCDBUser(aEntry.getUser().getName());
        CDBStandingOrderDao standingOrderDao = createStandingOrderDao(cdbUser);

        CDBStandingOrder standigOrderEntry = new CDBStandingOrder();

        CDBStandingOrderId standingOrderId = CDBStandingOrderId.createBuilder()
                .withUsername(aEntry.getUser().getName())
                .withHash(aEntry.getHash())
                .build();

        standigOrderEntry.setId(standingOrderId.toString());
        standigOrderEntry.setAmount(aEntry.getAmount());
        standigOrderEntry.setHash(aEntry.getHash());
        standigOrderEntry.setCurrency(aEntry.getCurrency());
        standigOrderEntry.setMemo(aEntry.getMemo());
        standigOrderEntry.setRotation_strategy(aEntry.getRotation_strategy());
        standigOrderEntry.setKonto(cdbUser.getKontos().get(0).getHash());
        standigOrderEntry.setUsername(cdbUser.getUsername());
        standigOrderEntry.setStart_at(aEntry.getStart_at());
        standigOrderEntry.setLast_executed(DateUtil.getMinimumDate());
        standigOrderEntry.setEnd_at(DateUtil.getMaximumDate());

        List<CDBTag> cdbTags = new LinkedList<>();
        for(Tag tagTemplate : aEntry.getTags()){
            CDBTag cdbTag = new CDBTag();
            cdbTag.setName(tagTemplate.getName());
            cdbTags.add(cdbTag);
        }
        standigOrderEntry.setTags(cdbTags);

        standingOrderDao.add(standigOrderEntry);
    }

    @Override
    public void update(StandingOrder aEntry) {
        CDBUser cdbUser = createCDBUser(aEntry.getUser().getName());
        CDBStandingOrderDao standingOrderDao = createStandingOrderDao(cdbUser);

        CDBStandingOrderId standingOrderId = CDBStandingOrderId.createBuilder()
                .withUsername(cdbUser.getUsername())
                .withHash(aEntry.getHash())
                .build();

        CDBStandingOrder standingOrder = standingOrderDao.get(standingOrderId.toString());
        standingOrder = updateStandingOrder(standingOrder,aEntry);
        standingOrderDao.update(standingOrder);
    }

    @Override
    public List<StandingOrder> getRotationEntries(AppUser aUser) {
        CDBUser cdbUser = createCDBUser(aUser.getName());
        CDBStandingOrderDao standingOrderDao = createStandingOrderDao(cdbUser);

        List<CDBStandingOrder> standigOrderEntries = standingOrderDao.getAll();
        List<StandingOrder> rotationEntries = new LinkedList<>();

        for(CDBStandingOrder entry : standigOrderEntries){

            StandingOrder rotationEntry = createRotationEntry(aUser, entry);
            rotationEntries.add(rotationEntry);
        }

        return rotationEntries;
    }


    @Override
    public StandingOrder getRotationEntryByHash(AppUser appUser, String aHash) {
        CDBUser cdbUser = createCDBUser(appUser.getName());
        CDBStandingOrderDao standingOrderDao = createStandingOrderDao(cdbUser);

        CDBStandingOrderId standingOrderId = CDBStandingOrderId.createBuilder()
                .withUsername(cdbUser.getUsername())
                .withHash(aHash).build();

        CDBStandingOrder cdbStandigOrderEntry = standingOrderDao.get(standingOrderId.toString());
        return createRotationEntry(appUser, cdbStandigOrderEntry);
    }

    @Override
    public void delete(StandingOrder aRotationEntry) {
        CDBUser cdbUser = createCDBUser(aRotationEntry.getUser().getName());
        CDBStandingOrderDao standingOrderDao = createStandingOrderDao(cdbUser);


        CDBStandingOrderId standingOrderId = CDBStandingOrderId.createBuilder()
                .withUsername(cdbUser.getUsername())
                .withHash(aRotationEntry.getHash())
                .build();

        CDBStandingOrder cdbStandigOrderEntry = standingOrderDao.get(standingOrderId.toString());
        standingOrderDao.remove(cdbStandigOrderEntry);
    }

    @Override
    public void validateRotationEntry(StandingOrder aRotationEntry) {

    }

    private CDBStandingOrderDao createStandingOrderDao(CDBUser cdbUser) {
        CDBKontoDatabaseId kontoDatabaseId = CDBKontoDatabaseId.builder()
                .withUsername(cdbUser.getUsername())
                .withKontoHash(cdbUser.getKontos().get(0).getHash())
                .withObjectTyp(CDBKonto.ORDER_KONTO)
                .build();
        return standingOrderDaoFactory.createDao(kontoDatabaseId.toString());
    }

    private CDBUser createCDBUser(String aUserName) {
        CDBUser cdbUser;CDBUserId userId = CDBUserId.create(aUserName);
        cdbUser = userDao.get(userId.toString());
        return cdbUser;
    }

    private StandingOrder createRotationEntry(AppUser aAppUser, CDBStandingOrder aStandingOrder){
        StandingOrder rotationEntry = new StandingOrder();
        rotationEntry.setAmount(aStandingOrder.getAmount());
        rotationEntry.setCurrency(aStandingOrder.getCurrency());
        rotationEntry.setHash(aStandingOrder.getHash());
        rotationEntry.setMemo(aStandingOrder.getMemo());
        rotationEntry.setRotation_strategy(aStandingOrder.getRotation_strategy());
        aAppUser.setPassword("");
        rotationEntry.setUser(aAppUser);

        List<Tag> tagTemplates = new ArrayList<>();

        for(CDBTag cdbTag : aStandingOrder.getTags()){
            Tag tag = new Tag();
            tag.setName(cdbTag.getName());
            tagTemplates.add(tag);
        }
        rotationEntry.setTags(tagTemplates);
        rotationEntry.setStart_at(aStandingOrder.getStart_at());
        rotationEntry.setLast_executed(aStandingOrder.getLast_executed());
        rotationEntry.setEnd_at(aStandingOrder.getEnd_at());
        return rotationEntry;
    }

    private CDBStandingOrder updateStandingOrder(CDBStandingOrder cdbStandigOrder, StandingOrder aRotationEntry){
        cdbStandigOrder.setMemo(aRotationEntry.getMemo());
        cdbStandigOrder.setAmount(aRotationEntry.getAmount());
        cdbStandigOrder.setCurrency(aRotationEntry.getCurrency());
        cdbStandigOrder.setRotation_strategy(aRotationEntry.getRotation_strategy());
        cdbStandigOrder.setLast_executed(aRotationEntry.getLast_executed());
        cdbStandigOrder.setStart_at(aRotationEntry.getStart_at());
        cdbStandigOrder.setEnd_at(aRotationEntry.getEnd_at());

        List<CDBTag> cdbTags = new LinkedList<>();
        for(Tag tagTemplate : aRotationEntry.getTags()){
            CDBTag cdbTag = new CDBTag();
            cdbTag.setName(tagTemplate.getName());
            cdbTags.add(cdbTag);
        }

        cdbStandigOrder.setTags(cdbTags);
        return cdbStandigOrder;
    }
}
