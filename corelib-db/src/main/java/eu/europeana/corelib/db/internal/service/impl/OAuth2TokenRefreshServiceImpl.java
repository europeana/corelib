package eu.europeana.corelib.db.internal.service.impl;

import eu.europeana.corelib.db.entity.nosql.RefreshToken;
import eu.europeana.corelib.db.internal.service.OAuth2RefreshTokenService;
import eu.europeana.corelib.db.service.abstracts.AbstractNoSqlServiceImpl;

/**
 * Implementation of the {@link  OAuth2TokenRefreshService}
 * @deprecated January 2018 OAuth and MyEuropeana are no longer being used
 */
@Deprecated
public class OAuth2TokenRefreshServiceImpl extends AbstractNoSqlServiceImpl<RefreshToken, String> implements OAuth2RefreshTokenService {
	
}
