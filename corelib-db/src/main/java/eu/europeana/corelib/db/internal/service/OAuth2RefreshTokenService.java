package eu.europeana.corelib.db.internal.service;

import eu.europeana.corelib.db.entity.nosql.RefreshToken;
import eu.europeana.corelib.db.service.abstracts.AbstractNoSqlService;

/**
 * Interface representing the OAuth2 refresh token service
 * @deprecated January 2018 OAuth and MyEuropeana are no longer being used
 */
@Deprecated
public interface OAuth2RefreshTokenService extends AbstractNoSqlService<RefreshToken, String> {
	
}
