package eu.europeana.corelib.db.dao.impl;

import java.io.Serializable;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import eu.europeana.corelib.db.dao.Dao;
import eu.europeana.corelib.db.entity.abstracts.IdentifiedEntity;

public class HibernateDaoImpl<E extends IdentifiedEntity<?>> extends HibernateDaoSupport implements Dao<E> {
	
	private Class<E> domainClazz = null;
	
	public HibernateDaoImpl(Class<E> clazz) {
		domainClazz = clazz;
	}

	public E findByPK(Serializable id) {
		return getHibernateTemplate().get(domainClazz, id);
	}

	public E insert(E object) {
		// TODO Auto-generated method stub
		return null;
	}

	public E update(E object) {
		// TODO Auto-generated method stub
		return null;
	}

	public void delete(E object) {
		// TODO Auto-generated method stub
		
	}

}
