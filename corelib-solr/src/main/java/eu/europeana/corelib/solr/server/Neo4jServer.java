package eu.europeana.corelib.solr.server;

import eu.europeana.corelib.definitions.solr.entity.AbstractEdmEntity;

public interface Neo4jServer extends AbstractServer{

	void save(AbstractEdmEntity entity);
	
	<T extends AbstractEdmEntity> T find(String ID,T obj);
		
	void delete(String ID);
}
