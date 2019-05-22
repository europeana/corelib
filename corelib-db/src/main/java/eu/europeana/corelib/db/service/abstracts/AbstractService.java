package eu.europeana.corelib.db.service.abstracts;

import java.io.Serializable;
import java.util.List;

import eu.europeana.corelib.db.exception.DatabaseException;
import eu.europeana.corelib.definitions.db.entity.relational.abstracts.IdentifiedEntity;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public interface AbstractService<E extends IdentifiedEntity<?>> {

	E store(E object) throws DatabaseException;

	void remove(E object) throws DatabaseException;

	/*
	 * FINDERS
	 */

	/**
	 * Returns a count of all records
	 * 
	 * @return the number of total records
	 */
	long count();

	/**
	 * Find all elements for this service.
	 * 
	 * @return All entities for the defined entity type
	 */
	List<E> findAll();

	E findByID(final Serializable id) throws DatabaseException;


}
