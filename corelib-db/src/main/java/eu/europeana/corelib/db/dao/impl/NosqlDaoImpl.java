package eu.europeana.corelib.db.dao.impl;

import java.io.Serializable;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.dao.BasicDAO;

import eu.europeana.corelib.db.dao.NosqlDao;
import eu.europeana.corelib.db.entity.nosql.abstracts.NoSqlEntity;

public class NosqlDaoImpl<E extends NoSqlEntity, T extends Serializable> extends BasicDAO<E, T> implements NosqlDao<E, T> {

	private Class<E> clazz;
	
	public NosqlDaoImpl(Class<E> clazz, Datastore datastore) {
		super(clazz, datastore);
		this.clazz = clazz;
	}
	
	@Override
	public void deleteAll() {
		try {
			delete(clazz.newInstance());
		} catch (Exception e) {
		}
	}

}
