package eu.europeana.corelib.db.service;

import eu.europeana.corelib.db.exception.DatabaseException;
import eu.europeana.corelib.db.service.abstracts.AbstractService;
import eu.europeana.corelib.definitions.db.entity.relational.Token;

/**
 * Service with dedicated Token related actions.
 * 
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * 
 * @see eu.europeana.corelib.db.entity.relational.TokenImpl
 * @deprecated January 2018 OAuth and MyEuropeana are no longer being used
 */
@Deprecated
public interface TokenService extends AbstractService<Token> {

	// 6 hours
	int MAX_TOKEN_AGE = 1000 * 60 * 60 * 6;

	/**
	 * Creates a new email token
	 * 
	 * @param email
	 * @return The created token.
	 * @throws DatabaseException
	 *             When there is no valid email address provided
	 */
	Token create(String email, String redirect) throws DatabaseException;

	/**
	 * Create a random Token String with 32 characters
	 * 
	 * @return The created token
	 */
	String createRandomToken();
}
