package eu.europeana.corelib.web.service;

import eu.europeana.corelib.db.entity.Token;

public interface EmailService {
	
	void sendToken(Token token);
	
}
