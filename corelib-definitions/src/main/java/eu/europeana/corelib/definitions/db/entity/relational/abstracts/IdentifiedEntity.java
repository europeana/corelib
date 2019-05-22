package eu.europeana.corelib.definitions.db.entity.relational.abstracts;

import java.io.Serializable;

/**
 * High level interface for database entities supported by the generic DAO.
 * 
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * 
 * @param <I>
 *            A Serializable type used as primary key, like a Long or String
 */
public interface IdentifiedEntity<I extends Serializable> extends Serializable {

	I getId();

}
