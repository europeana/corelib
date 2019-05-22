package eu.europeana.corelib.db.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import eu.europeana.corelib.db.entity.relational.TokenImpl;
import eu.europeana.corelib.db.exception.DatabaseException;
import eu.europeana.corelib.db.service.TokenService;
import eu.europeana.corelib.db.service.abstracts.AbstractServiceImpl;
import eu.europeana.corelib.definitions.db.entity.relational.Token;
import eu.europeana.corelib.web.exception.ProblemType;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * 
 * @see eu.europeana.corelib.db.service.TokenService
 * @deprecated January 2018 OAuth and MyEuropeana are no longer being used*
 */
@Transactional
@Deprecated
public class TokenServiceImpl extends AbstractServiceImpl<Token> implements
		TokenService {

	private final Logger log = Logger.getLogger(getClass().getName());

	/**
	 * Overriding the findByID() method to handle expiration
	 * NB implemented missing ELSE branch of the token age IF - luthien
	 * @throws DatabaseException
	 */
	@Override
	public Token findByID(Serializable id) throws DatabaseException {
		Token token;
		try {
			token = super.findByID(id);
		} catch (DatabaseException e) {
			log.severe("DatabaseException: " + e.getLocalizedMessage());
			return null;
		}
		if (token != null) {
			long age = System.currentTimeMillis() - token.getCreated();
			if (age <= MAX_TOKEN_AGE) {
				return token;
			} else {
			getDao().delete(token);
			log.severe("Token is outdated, so deleted: " + id);
			throw new DatabaseException(ProblemType.TOKEN_OUTDATED);
		}
		}
		return null;
	}

	@Override
	public Token create(String email, String redirect) throws DatabaseException {
		if (StringUtils.isBlank(email) || StringUtils.isBlank(redirect)) {
			log.severe("DatabaseException: email or redirect URL cannot be null [create token]. " + ProblemType.INVALIDARGUMENTS);
			throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
		}
		TokenImpl token = new TokenImpl();
		token.setCreated(new Date());
		token.setEmail(email);
		token.setRedirect(redirect);
		token.setToken(createRandomToken());
		return getDao().insert(token);
	}

	@Override
	public String createRandomToken() {
		String token = UUID.randomUUID().toString();
		return StringUtils.remove(token, "-");
	}
}
