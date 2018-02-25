package de.pfann.budgetmanager.server.persistens.core;

import org.hibernate.criterion.DetachedCriteria;

import java.util.List;
import java.util.Map;

public abstract class AbstractDao {

	/*
	 * Members
	 */
	private final DbWriter dbWriter;

	private final DbReader dbReader;

	/*
	 * Constructors
	 */
	protected AbstractDao(DbWriter _dbWriter, DbReader _dbReader) {
		dbWriter = _dbWriter;
		dbReader = _dbReader;
	}

	/*
	 * Template methods
	 */
	public Object save(Object _object) {
		assertEntityClass(_object);
		return doSave(_object);
	}

	public void delete(Object _object){
		assertEntityClass(_object);
		doDelete(_object);
	}

	public long countAll() {
		return dbReader.count(getCriteria());
	}

	/*
	 * Extension interface
	 */
	protected abstract Class<?> getEntityClass();

	/*
	 * Helpers
	 */
	protected DetachedCriteria getCriteria() {
		return DetachedCriteria.forClass(getEntityClass());
	}

	protected DetachedCriteria getCriteria(String aliasName) {
		return DetachedCriteria.forClass(getEntityClass(),aliasName);
	}

	/*
	 * doGet***() methods
	 */
	protected List<?> doGetAll() {
		DetachedCriteria criteria = getCriteria();
		return dbReader.get(criteria);
	}

	protected List<?> doGet(DetachedCriteria _criteria) {
		return dbReader.get(_criteria);
	}

	protected List<?> doGet(String _query) {
		return dbReader.get(getEntityClass(), _query, null);
	}

	protected List<?> doGet(String _query, Map<String, Object> _parameters) {
		return dbReader.get(getEntityClass(), _query, _parameters);
	}

	protected Object doGetUnique(DetachedCriteria _criteria) {
		return dbReader.getUnique(_criteria);
	}

	/*
	 * doSave() method
	 */
	protected Object doSave(Object _object) {
		return dbWriter.save(_object);
	}

	/*
	 * doDelete() method
	 */
	protected void doDelete(Object _object){
		dbWriter.delete(_object);
	}

	/*
	 * Assertion
	 */
	private void assertEntityClass(Object _object) {
		if (!_object.getClass().equals(getEntityClass())) {
			throw new IllegalArgumentException("_object is not of type " + getEntityClass().getSimpleName());
		}
	}

}
