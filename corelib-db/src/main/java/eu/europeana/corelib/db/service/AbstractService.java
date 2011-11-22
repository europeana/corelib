package eu.europeana.corelib.db.service;

import java.io.Serializable;

import eu.europeana.corelib.db.entity.abstracts.IdentifiedEntity;

public interface AbstractService<E extends IdentifiedEntity<?>> {

	  public void store(E object);

	  public void remove(E object);

	  /*
	   * FINDERS
	   */

	  public E findByID(final Serializable id);

}
