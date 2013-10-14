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

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import eu.europeana.corelib.db.entity.relational.SavedItemImpl;
import eu.europeana.corelib.db.entity.relational.SavedSearchImpl;
import eu.europeana.corelib.db.entity.relational.SocialTagImpl;
import eu.europeana.corelib.db.entity.relational.UserImpl;
import eu.europeana.corelib.db.entity.relational.custom.TagCloudItem;
import eu.europeana.corelib.db.exception.DatabaseException;
import eu.europeana.corelib.db.service.ApiKeyService;
import eu.europeana.corelib.db.service.TokenService;
import eu.europeana.corelib.db.service.UserService;
import eu.europeana.corelib.db.service.abstracts.AbstractServiceImpl;
import eu.europeana.corelib.definitions.db.entity.RelationalDatabase;
import eu.europeana.corelib.definitions.db.entity.relational.SavedItem;
import eu.europeana.corelib.definitions.db.entity.relational.SocialTag;
import eu.europeana.corelib.definitions.db.entity.relational.Token;
import eu.europeana.corelib.definitions.db.entity.relational.User;
import eu.europeana.corelib.definitions.db.entity.relational.abstracts.EuropeanaUserObject;
import eu.europeana.corelib.definitions.exception.ProblemType;
import eu.europeana.corelib.definitions.solr.beans.FullBean;
import eu.europeana.corelib.definitions.solr.entity.Aggregation;
import eu.europeana.corelib.definitions.solr.entity.Proxy;
import eu.europeana.corelib.solr.exceptions.MongoDBException;
import eu.europeana.corelib.solr.service.SearchService;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * 
 * @see eu.europeana.corelib.db.service.UserService
 * @see eu.europeana.corelib.db.entity.relational.UserImpl
 */
@Transactional
public class UserServiceImpl extends AbstractServiceImpl<User> implements
		UserService {

	@Resource(type = TokenService.class)
	private TokenService tokenService;

	@Resource(type = SearchService.class)
	private SearchService searchService;

	@Resource
	private ApiKeyService apiKeyService;

	@Override
	public User create(String tokenString, String username, String password)
			throws DatabaseException {
		return create(tokenString, username, password, false, null, null, null, null, null, null, null, null);
	}

	@Override
	public User create(String tokenString, String username, String password,
			boolean isApiRegistration, String company, String country,
			String firstName, String lastName, String website, String address, 
			String phone, String fieldOfWork)
			throws DatabaseException {

		if (StringUtils.isBlank(tokenString)) {
			throw new DatabaseException(ProblemType.UNKNOWN_TOKEN);
		}

		if (StringUtils.isBlank(username)) {
			throw new DatabaseException(ProblemType.NO_USERNAME);
		}

		if (!isApiRegistration && StringUtils.isBlank(password)) {
			throw new DatabaseException(ProblemType.NO_PASSWORD);
		}

		if (StringUtils.isBlank(tokenString)
				|| StringUtils.isBlank(username)
				|| (!isApiRegistration && StringUtils.isBlank(password))) {
			throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
		}

		Token token = tokenService.findByID(tokenString);
		if (token == null) {
			throw new DatabaseException(ProblemType.TOKEN_INVALID);
		}

		User user = new UserImpl();
		user.setEmail(token.getEmail());
		user.setUserName(username);
		user.setPassword(StringUtils.isEmpty(password) ? null : hashPassword(password));
		user.setRegistrationDate(new Date());
		user.setCompany(company);
		user.setCountry(country);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setWebsite(website);
		user.setAddress(address);
		user.setPhone(phone);
		user.setFieldOfWork(fieldOfWork);

		user = getDao().insert(user);

		return user;
	}

	@Override
	public User findByEmail(String email) {
		if (StringUtils.isNotBlank(email)) {
			return getDao().findOneByNamedQuery(User.QUERY_FINDBY_EMAIL,
					StringUtils.lowerCase(email));
		}
		return null;
	}

	@Override
	public User findByApiKey(String apiKey) {
		if (StringUtils.isNotBlank(apiKey)) {
			return getDao().findOneByNamedQuery(User.QUERY_FINDBY_APIKEY,
					apiKey);
		}
		return null;
	}

	@Override
	public User findByName(String userName) {
		if (StringUtils.isNotBlank(userName)) {
			return getDao().findOneByNamedQuery(User.QUERY_FINDBY_NAME,
					userName);
		}
		return null;
	}

	@Override
	public User authenticateUser(String email, String password) {
		User user = findByEmail(email);
		if ((user != null)
				&& StringUtils.equals(user.getPassword(),
						hashPassword(password))) {
			return user;
		}
		return null;
	}

	@Override
	public User registerApiUserForMyEuropeana(Long userId, String userName, String password) throws DatabaseException {
		if (userId == null) {
			throw new DatabaseException(ProblemType.NO_USER_ID);
		}

		User user = getDao().findByPK(userId);
		if (user == null) {
			throw new DatabaseException(ProblemType.NO_USER);
		}

		if (StringUtils.isBlank(userName)) {
			throw new DatabaseException(ProblemType.NO_USERNAME);
		}

		if (StringUtils.isBlank(password)) {
			throw new DatabaseException(ProblemType.NO_PASSWORD);
		}

		user.setUserName(userName);
		user.setPassword(hashPassword(password));

		return user;
	}

	@Override
	public User changePassword(Long userId, String oldPassword, String newPassword) throws DatabaseException {

		if (userId == null) {
			throw new DatabaseException(ProblemType.NO_USER_ID);
		}

		/*
		if (StringUtils.isBlank(oldPassword)) {
			throw new DatabaseException(ProblemType.NO_OLD_PASSWORD);
		}
		*/

		if (StringUtils.isBlank(newPassword)) {
			throw new DatabaseException(ProblemType.NO_PASSWORD);
		}

		if ((userId == null) || StringUtils.isBlank(oldPassword)
				|| StringUtils.isBlank(newPassword)) {
			throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
		}
		User user = getDao().findByPK(userId);

		if (user == null) {
			throw new DatabaseException(ProblemType.NO_USER);
		}

		if (!StringUtils.equals(user.getPassword(), oldPassword)) {
			throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
		}

		user.setPassword(hashPassword(newPassword));
		return user;
	}

	@Override
	public User createSavedSearch(Long userId, String query, String queryString)
			throws DatabaseException {

		if ((userId == null) || StringUtils.isBlank(query)
				|| StringUtils.isBlank(queryString)) {
			throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
		}

		User user = getDao().findByPK(userId);
		if (user == null) {
			throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
		}

		SavedSearchImpl savedSearch = new SavedSearchImpl();
		savedSearch.setUser(user);
		savedSearch.setDateSaved(new Date());
		savedSearch.setQuery(query);
		savedSearch.setQueryString(queryString);

		user.getSavedSearches().add(savedSearch);
		return user;
	}

	@Override
	public User createSavedItem(Long userId, String europeanaObjectId)
			throws DatabaseException {

		if ((userId == null) || StringUtils.isBlank(europeanaObjectId)) {
			throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
		}

		User user = getDao().findByPK(userId);
		if (user == null) {
			throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
		}
		
		if (findSavedItemByEuropeanaId(userId, europeanaObjectId) != null) {
			throw new DatabaseException(ProblemType.DUPLICATE);
		}
		
		SavedItem savedItem = new SavedItemImpl();
		FullBean bean = null;

		bean = populateEuropeanaUserObject(user, europeanaObjectId, savedItem);

		if (bean != null) {
			List<? extends Proxy> proxies = bean.getProxies();
			Proxy proxy = proxies.get(0);
			if (proxy != null && proxy.getDcPublisher() != null) {
				savedItem.setAuthor(StringUtils.abbreviate(proxy
						.getDcPublisher().values().iterator().next().get(0),
						RelationalDatabase.FIELDSIZE_AUTHOR));
			}
		}
		return user;
	}

	@Override
	public User createSocialTag(Long userId, String europeanaObjectId,
			String tag) throws DatabaseException {

		if ((userId == null) || StringUtils.isBlank(europeanaObjectId)
				|| StringUtils.isBlank(tag)) {
			throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
		}

		User user = getDao().findByPK(userId);
		if (user == null) {
			throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
		}

		SocialTag socialTag = new SocialTagImpl();
		populateEuropeanaUserObject(user, europeanaObjectId, socialTag);
		socialTag.setTag(StringUtils.abbreviate(tag,
				RelationalDatabase.FIELDSIZE_TAG));
		return user;
	}


	@Override
	public void removeSavedSearch(Long userId, Long savedSearchId)
			throws DatabaseException {
		SavedSearchImpl savedSearch = getDao().findByPK(SavedSearchImpl.class,
				savedSearchId);
		if ((savedSearch != null)
				&& savedSearch.getUser().getId().equals(userId)) {
			savedSearch.getUser().getSavedSearches().remove(savedSearch);
		}
	}

	@Override
	public void removeSavedItem(Long userId, Long savedItemId)
			throws DatabaseException {
		SavedItem savedItem = getDao().findByPK(SavedItemImpl.class,
				savedItemId);
		if ((savedItem != null) && savedItem.getUser().getId().equals(userId)) {
			savedItem.getUser().getSavedItems().remove(savedItem);
		}
	}
	
	@Override
	public void removeSavedItem(Long userId, String europeanaId) throws DatabaseException {
		SavedItem savedItem = findSavedItemByEuropeanaId(userId, europeanaId); 
		if (savedItem != null) {
			savedItem.getUser().getSavedItems().remove(savedItem);
		}
	}

	@Override
	public void removeSocialTag(Long userId, Long socialTagId)
			throws DatabaseException {
		SocialTag socialTag = getDao().findByPK(SocialTagImpl.class,
				socialTagId);
		if ((socialTag != null) && socialTag.getUser().getId().equals(userId)) {
			socialTag.getUser().getSocialTags().remove(socialTag);
		}
	}
	
	@Override
	public void removeSocialTag(Long userId, String europeanaId, String tag) throws DatabaseException {
		List<SocialTag> results = null;
		if (StringUtils.isNotBlank(europeanaId) && StringUtils.isNotBlank(tag)) {
			results = getDao().findByNamedQuery(SocialTag.class, SocialTag.QUERY_FINDBY_USER_TAG_EUROPEANAID, userId, StringUtils.lowerCase(tag), europeanaId);
			
		} else if (StringUtils.isNotBlank(tag)) {
			results = findSocialTagsByTag(userId, tag);
		} else if (StringUtils.isNotBlank(europeanaId)) {
			results = findSocialTagsByEuropeanaId(userId, europeanaId);
		}
		if ( (results != null) && !results.isEmpty()) {
			results.get(0).getUser().getSocialTags().removeAll(results);
		}
		
	}
	
	@Override
	public List<TagCloudItem> createSocialTagCloud(Long userId) throws DatabaseException {
		if (userId == null) {
			throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
		}
		return getDao().findByNamedQueryCustom(TagCloudItem.class, SocialTag.QUERY_CREATECLOUD_BYUSER, userId);
	}
	
	@Override
	public List<SocialTag> findSocialTagsByTag(Long userId, String tag) throws DatabaseException {
		if ( (userId == null) || StringUtils.isBlank(tag)) {
			throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
		}
		return getDao().findByNamedQuery(SocialTag.class, SocialTag.QUERY_FINDBY_USER_TAG, userId, StringUtils.lowerCase(tag));
	}
	
	@Override
	public List<SocialTag> findSocialTagsByEuropeanaId(Long userId, String europeanaId) throws DatabaseException {
		if ( (userId == null) || StringUtils.isBlank(europeanaId)) {
			throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
		}
		return getDao().findByNamedQuery(SocialTag.class, SocialTag.QUERY_FINDBY_USER_EUROPEANAID, userId, europeanaId);
	}
	
	@Override
	public SavedItem findSavedItemByEuropeanaId(Long userId, String europeanaId) throws DatabaseException {
		if ( (userId == null) || StringUtils.isBlank(europeanaId)) {
			throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
		}
		return getDao().findOneByNamedQuery(SavedItemImpl.class, SavedItem.QUERY_FINDBY_OBJECTID, userId, europeanaId);
	}

	private FullBean populateEuropeanaUserObject(User user,
			String europeanaObjectId, EuropeanaUserObject instance)
			throws DatabaseException {

		FullBean bean;
		try {
			bean = searchService.findById(europeanaObjectId,false);
		} catch (MongoDBException e) {
			throw new DatabaseException(e, ProblemType.UNKNOWN);
		}

		if ((user == null) || (bean == null)) {
			throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
		}

		instance.setEuropeanaUri(bean.getAbout());
		String edmObject = null;
		for (Aggregation aggregation : bean.getAggregations()) {
			if (!StringUtils.isBlank(aggregation.getEdmObject())) {
				edmObject = aggregation.getEdmObject();
			}
		}
		instance.setEuropeanaObject(edmObject);

		instance.setDateSaved(new Date());

		String title = null;
		if (bean.getTitle() != null && bean.getTitle().length > 0) {
			for (String _title : bean.getTitle()) {
				if (!StringUtils.isBlank(_title)) {
					title = _title;
					break;
				}
			}
		}
		if (title == null) {
			for (Proxy proxy : bean.getProxies()) {
				if (proxy == null) {
					continue;
				}
				if (proxy.getDcTitle() != null) {
					for (List<String> items : proxy.getDcTitle().values()) {
						for (String item : items) {
							if (!StringUtils.isBlank(item)) {
								title = item;
								break;
							}
						}
					}
				}
				if (title == null && proxy.getDcDescription() != null) {
					for (List<String> items : proxy.getDcDescription().values()) {
						for (String item : items) {
							if (!StringUtils.isBlank(item)) {
								title = item;
								break;
							}
						}
					}
				}
			}
		}
		if (title == null) {
			title = europeanaObjectId;
		}

		instance.setTitle(StringUtils.abbreviate(title, RelationalDatabase.FIELDSIZE_TITLE));
		instance.setDocType(bean.getType());
		instance.setUser(user);
		if (instance instanceof SavedItemImpl) {
			user.getSavedItems().add((SavedItem) instance);
		} else {
			user.getSocialTags().add((SocialTag) instance);
		}
		return bean;
	}

	/**
	 * Hashing password using ShaPasswordEncoder.
	 * 
	 * @param password
	 *            The password in initial form.
	 * @return Hashed password as to be stored in database
	 */
	private String hashPassword(String password) {
		if (StringUtils.isNotBlank(password)) {
			return new ShaPasswordEncoder().encodePassword(password, null);
		}
		return null;
	}
}
