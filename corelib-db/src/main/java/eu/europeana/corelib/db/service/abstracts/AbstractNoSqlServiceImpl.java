package eu.europeana.corelib.db.service.abstracts;

import java.io.Serializable;

import org.springframework.data.repository.CrudRepository;

import eu.europeana.corelib.db.entity.nosql.abstracts.NoSqlEntity;
import eu.europeana.corelib.db.exception.DatabaseException;

public abstract class AbstractNoSqlServiceImpl<E extends NoSqlEntity, T extends Serializable> implements AbstractNoSqlService<E, T> {
	
	protected abstract CrudRepository<E, T> getReposity();
	
	@Override
	public void remove(T id) throws DatabaseException {
		getReposity().delete(id);
	}
	
	@Override
	public E findByID(T id) throws DatabaseException {
		return getReposity().findOne(id);
	}
	
	@Override
	public Iterable<E> findAll() {
		return getReposity().findAll();
	}
	
	@Override
	public E store(E object) throws DatabaseException {
		return getReposity().save(object);
	}
	
	@Override
	public boolean exists(T id) throws DatabaseException {
		return getReposity().exists(id);
	}

}
