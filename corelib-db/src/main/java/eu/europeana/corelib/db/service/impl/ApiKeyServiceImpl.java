package eu.europeana.corelib.db.service.impl;

import java.util.List;

import javax.annotation.Resource;

import eu.europeana.corelib.db.entity.relational.ApiKeyImpl;
import eu.europeana.corelib.db.exception.DatabaseException;
import eu.europeana.corelib.db.service.ApiKeyService;
import eu.europeana.corelib.db.service.ApiLogService;
import eu.europeana.corelib.db.service.UserService;
import eu.europeana.corelib.db.service.abstracts.AbstractServiceImpl;
import eu.europeana.corelib.definitions.db.entity.relational.ApiKey;
import eu.europeana.corelib.definitions.db.entity.relational.User;

public class ApiKeyServiceImpl extends AbstractServiceImpl<ApiKey> implements ApiKeyService {
	
	@Resource
	private ApiLogService apiLogService;
	
	@Resource
	private UserService userService;

	@Override
	public List<ApiKey> findAllSortByDate(boolean asc) {
		String namedQuery = asc ? ApiKeyImpl.QUERY_SORT_BY_DATE_ASC : ApiKeyImpl.QUERY_SORT_BY_DATE_DESC;
		return getDao().findByNamedQuery(namedQuery);
	}

	@Override
	public List<ApiKey> findAllSortByDate(boolean asc, int offset, int limit) {
		String namedQuery = asc ? ApiKeyImpl.QUERY_SORT_BY_DATE_ASC : ApiKeyImpl.QUERY_SORT_BY_DATE_DESC;
		return getDao().findByNamedQueryLimited(namedQuery, offset, limit);
	}
	
	@Override
	public boolean checkReachedLimit(ApiKey apiKey) {
		if (apiKey != null) {
			return apiKey.getUsageLimit() <= apiLogService.countByApiKey(apiKey.getId());
		}
		return false;
	}


	@Override
	public ApiKey createApiKey(String token, String email, String apiKey,
			String privateKey, Long limit, String username,
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