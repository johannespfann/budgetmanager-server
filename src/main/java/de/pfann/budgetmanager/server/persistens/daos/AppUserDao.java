package de.pfann.budgetmanager.server.persistens.daos;

import de.pfann.budgetmanager.server.model.AppUser;
import de.pfann.budgetmanager.server.persistens.core.AbstractDao;
import de.pfann.budgetmanager.server.persistens.core.DbReader;
import de.pfann.budgetmanager.server.persistens.core.DbWriter;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class AppUserDao extends AbstractDao {

    protected static AppUserDao create() {
        return new AppUserDao(DbWriter.create(), DbReader.create());
    }

    protected AppUserDao(DbWriter _dbWriter, DbReader _dbReader) {
        super(_dbWriter, _dbReader);
    }

    @Override
    protected Class<?> getEntityClass() {
        return AppUser.class;
    }

    public AppUser getUserByName(String aUserName) throws NoUserFoundException{
        DetachedCriteria criteria = getCriteria();
        criteria.add(Restrictions.eq("name", aUserName));
        List<AppUser> appUsers = (List<AppUser>) doGet(criteria);
        if(appUsers.size() == 0){
            throw new NoUserFoundException("No user found with name: " + aUserName);
        } // TODO What happens when more then two user were found
        return appUsers.get(0);
    }

    public AppUser getUserByEmail(String aEmail) throws NoUserFoundException {
        DetachedCriteria criteria = getCriteria();
        criteria.add(Restrictions.eq("email", aEmail));
        List<AppUser> appUsers = (List<AppUser>) doGet(criteria);
        if(appUsers.size() == 0){
            throw new NoUserFoundException("No user found with email: " + aEmail);
        } // TODO What happens when more then two user were found
        return appUsers.get(0);
    }

    public AppUser getUserByNameOrEmail(String aIdentifier) throws NoUserFoundException {
        DetachedCriteria nameCriteria = getCriteria();
        nameCriteria.add(Restrictions.eq("name", aIdentifier));
        List<AppUser> appUsers = (List<AppUser>) doGet(nameCriteria);

        if(appUsers.size() > 0){
            return appUsers.get(0);
        }

        DetachedCriteria emailCriteria = getCriteria();
        emailCriteria.add(Restrictions.eq("email", aIdentifier));
        appUsers = (List<AppUser>) doGet(emailCriteria);

        if(appUsers.size() > 0){
            return appUsers.get(0);
        }

        throw new NoUserFoundException("Dont find any user by email or name: " + aIdentifier);

    }

    public List<AppUser> getUsersByEmail(String aEmail) {
        DetachedCriteria criteria = getCriteria();
        criteria.add(Restrictions.eq("email", aEmail));
        List<AppUser> appUsers = (List<AppUser>) doGet(criteria);
        return appUsers;
    }

    public List<AppUser> getAll(){
        return (List<AppUser>)doGetAll();
    }
}
