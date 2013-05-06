package eu.europeana.corelib.db.internal.service;

import eu.europeana.corelib.db.entity.nosql.RefreshToken;
import eu.europeana.corelib.db.service.abstracts.AbstractNoSqlService;

public interface OAuth2RefreshTokenService extends AbstractNoSqlService<RefreshToken, String> {
	
}
