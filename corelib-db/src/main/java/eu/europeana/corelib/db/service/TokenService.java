package eu.europeana.corelib.db.service;

import eu.europeana.corelib.db.entity.Token;

public interface TokenService extends AbstractService<Token> {
	
	Token create(String email);

}
