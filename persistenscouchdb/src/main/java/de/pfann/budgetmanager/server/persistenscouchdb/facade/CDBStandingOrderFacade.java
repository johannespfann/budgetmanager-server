package de.pfann.budgetmanager.server.persistenscouchdb.facade;

import de.pfann.budgetmanager.server.common.facade.RotationEntryFacade;
import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.common.model.RotationEntry;
import de.pfann.budgetmanager.server.common.model.TagTemplate;
import de.pfann.budgetmanager.server.common.util.DateUtil;
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
                .withKonto(cdbUser.getKontos().get(0).getHash())
                .build();

        standigOrderEntry.setId(standingOrderId.toString());
        standigOrderEntry.setAmount(aEntry.getAmount());
        standigOrderEntry.setHash(aEntry.getHash());
        standigOrderEntry.setMemo(aEntry.getMemo());
        standigOrderEntry.setRotation_strategy(aEntry.getRotation_strategy());
        standigOrderEntry.setKonto(cdbUser.getKontos().get(0).getHash());
        standigOrderEntry.setUsername(cdbUser.getUsername());
        standigOrderEntry.setStart_at(DateUtil.asLocalDateTime(aEntry.getStart_at()));
        standigOrderEntry.setLast_executed(DateUtil.asLocalDateTime(aEntry.getStart_at()));

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
    }

    @Override
    public List<RotationEntry> getRotationEntries(AppUser aUser) {
        CDBUser cdbUser = createCDBUser(aUser.getName());
        CDBStandingOrderDao standingOrderDao = createStandingOrderDao(cdbUser);



        return null;
    }




    @Override
    public RotationEntry getRotationEntryByHash(String aHash) {
        return null;
    }

    @Override
    public void delete(RotationEntry aRotationEntry) {

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
}
