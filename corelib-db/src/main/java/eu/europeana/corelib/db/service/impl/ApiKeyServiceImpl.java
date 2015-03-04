/*
 * Copyright 2007-2012 The Europeana Foundation
 *
 *  Licenced under the EUPL, Version 1.1 (the "Licence") and subsequent versions as approved 
 *  by the European Commission;
 *  You may not use this work except in compliance with the Licence.
 *  
 *  You may obtain a copy of the Licence at:
 *  http://joinup.ec.europa.eu/software/page/eupl
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under 
 *  the Licence is distributed on an "AS IS" basis, without warranties or conditions of 
 *  any kind, either express or implied.
 *  See the Licence for the specific language governing permissions and limitations under 
 *  the Licence.
 */
package eu.europeana.corelib.db.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import eu.europeana.corelib.db.entity.relational.ApiKeyImpl;
import eu.europeana.corelib.db.exception.DatabaseException;
import eu.europeana.corelib.db.exception.LimitReachedException;
import eu.europeana.corelib.db.service.ApiKeyService;
import eu.europeana.corelib.db.service.ApiLogService;
import eu.europeana.corelib.db.service.UserService;
import eu.europeana.corelib.db.service.abstracts.AbstractServiceImpl;
import eu.europeana.corelib.definitions.db.entity.relational.ApiKey;
import eu.europeana.corelib.definitions.db.entity.relational.User;
import eu.europeana.corelib.definitions.exception.ProblemType;
import eu.europeana.corelib.utils.DateIntervalUtils;

/**
 * Implementation of the {@link ApiKeyService}
 *
 */
public class ApiKeyServiceImpl extends AbstractServiceImpl<ApiKey> implements ApiKeyService {

	@Resource
	private ApiLogService apiLogService;

	@Resource
	private UserService userService;

	@Override
	public List<ApiKey> findAllSortByDate(boolean asc) {
		String namedQuery = asc ? ApiKey.QUERY_SORT_BY_DATE_ASC : ApiKey.QUERY_SORT_BY_DATE_DESC;
		return getDao().findByNamedQuery(namedQuery);
	}

	@Override
	public List<ApiKey> findAllSortByDate(boolean asc, int offset, int limit) {
		String namedQuery = asc ? ApiKey.QUERY_SORT_BY_DATE_ASC : ApiKey.QUERY_SORT_BY_DATE_DESC;
		return getDao().findByNamedQueryLimited(namedQuery, offset, limit);
	}

	@Override
	public long checkReachedLimit(ApiKey apiKey) throws DatabaseException, LimitReachedException {
		if (apiKey == null) {
			throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
		}

		long requestNumber = apiLogService.countByIntervalAndApiKey(DateIntervalUtils.getLast24Hours(), apiKey.getId());
		if (apiKey.getUsageLimit() <= requestNumber) {
			throw new LimitReachedException(apiKey.getUsageLimit(), requestNumber);
		}
		return requestNumber;
	}
	
	@Override
	public void updateApplicationName(Long userId, String apiKey, String applicationName) throws DatabaseException {
		ApiKey key = getDao().findByPK(apiKey);
		if ((key != null) && key.getUser().getId().equals(userId)) {
			key.setApplicationName(StringUtils.trimToNull(applicationName));
		}
	}

	@Override
	public ApiKey createApiKey(String token, String email, String apiKey,
			String privateKey, Long limit, String appName, String username,
			String company, String country, String firstName, String lastName, 
			String website, String address, String phone, String fieldOfWork)
			throws DatabaseException {

		User user = userService.findByEmail(email);
		if (user == null) {
			user = userService.create(token, username, null, true,
					company, country, firstName, lastName, website, address, phone, fieldOfWork);
		} else {
			user.setCompany(company);
			user.setCountry(country);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setWebsite(website);
			user.setAddress(address);
			user.setPhone(phone);
			user.setFieldOfWork(fieldOfWork);
			userService.store(user);
		}

		ApiKey api = new ApiKeyImpl();
		api.setApiKey(apiKey);
		api.setPrivateKey(privateKey);
		api.setUsageLimit(limit);
		api.setUser(user);
		api.setApplicationName(appName);
		user.getApiKeys().add(api);
		return api;
	}

	@Override
	public void removeApiKey(Long userId, String apiKeyId)
			throws DatabaseException {
		ApiKey apiKey = getDao().findByPK(apiKeyId);
		if ((apiKey != null) && apiKey.getUser().getId().equals(userId)) {
			apiKey.getUser().getApiKeys().remove(apiKey);
		}
	}

}