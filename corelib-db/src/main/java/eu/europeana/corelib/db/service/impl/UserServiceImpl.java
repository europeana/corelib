/*
 * Copyright 2007 EDL FOUNDATION
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they 
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "License");
 * you may not use this work except in compliance with the
 * License.
 * You may obtain a copy of the License at:
 *
 * http://ec.europa.eu/idabc/eupl
 *
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the License is
 * distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the License for the specific language governing
 * permissions and limitations under the License.
 */

package eu.europeana.corelib.db.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import eu.europeana.corelib.db.entity.SavedItemImpl;
import eu.europeana.corelib.db.entity.SavedSearchImpl;
import eu.europeana.corelib.db.entity.SocialTagImpl;
import eu.europeana.corelib.db.entity.UserImpl;
import eu.europeana.corelib.db.exception.DatabaseException;
import eu.europeana.corelib.db.service.TokenService;
import eu.europeana.corelib.db.service.UserService;
import eu.europeana.corelib.db.service.abstracts.AbstractServiceImpl;
import eu.europeana.corelib.definitions.db.DatabaseDefinition;
import eu.europeana.corelib.definitions.db.entity.Token;
import eu.europeana.corelib.definitions.db.entity.User;
import eu.europeana.corelib.definitions.db.entity.abstracts.EuropeanaUserObject;
import eu.europeana.corelib.definitions.exception.ProblemType;
import eu.europeana.corelib.solr.bean.FullBean;
import eu.europeana.corelib.solr.service.SearchService;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * 
 * @see eu.europeana.corelib.db.service.UserService
 * @see eu.europeana.corelib.db.entity.UserImpl
 */
@Transactional
public class UserServiceImpl extends AbstractServiceImpl<User> implements UserService {

	@Resource(type = TokenService.class)
	TokenService tokenService;

	@Resource(type = SearchService.class)
	SearchService searchService;

	@Override
	public User create(String tokenString, String username, String password) throws DatabaseException {
		if (StringUtils.isBlank(tokenString) || StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
		}
		Token token = tokenService.findByID(tokenString);
		if (token == null) {
			throw new DatabaseException(ProblemType.TOKEN_INVALID);
		}
		User user = new UserImpl();
		user.setEmail(token.getEmail());
		user.setUserName(username);
		user.setPassword(hashPassword(password));
		user.setRegistrationDate(new Date());
		user = dao.insert(user);
		tokenService.remove((Token) token);
		return user;
	}

	@Override
	public User findByEmail(String email) {
		if (StringUtils.isNotBlank(email)) {
			return dao.findOneByNamedQuery(UserImpl.QUERY_FINDBY_EMAIL, StringUtils.lowerCase(email));
		}
		return null;
	}
	
	@Override
	public User findByApiKey(String apiKey) {
		if (StringUtils.isNotBlank(apiKey)) {
			return dao.findOneByNamedQuery(UserImpl.QUERY_FINDBY_APIKEY, apiKey);
		}
		return null;
	}

	@Override
	public User authenticateUser(String email, String password) {
		User user = findByEmail(email);
		if ((user != null) && StringUtils.equals(user.getPassword(), hashPassword(password))) {
			return user;
		}
		return null;
	}
	
	@Override
	public User changePassword(Long userId, String oldPassword, String newPassword) throws DatabaseException {
		if ((userId == null) || StringUtils.isBlank(oldPassword) || StringUtils.isBlank(newPassword)) {
			throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
		}
		User user = dao.findByPK(userId);
		if ((user == null) || !StringUtils.equals(user.getPassword(), hashPassword(oldPassword))) {
			throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
		}
		user.setPassword(hashPassword(newPassword));
		return user;
	}

	@Override
	public User createSavedSearch(Long userId, String query, String queryString) throws DatabaseException {
		if ((userId == null) || StringUtils.isBlank(query) || StringUtils.isBlank(queryString)) {
			throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
		}
		User user = dao.findByPK(userId);
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
	public User createSavedItem(Long userId, String europeanaObjectId) throws DatabaseException {
		if ((userId == null) || StringUtils.isBlank(europeanaObjectId)) {
			throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
		}
		User user = dao.findByPK(userId);
		if (user == null) {
			throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
		}
		SavedItemImpl savedItem = new SavedItemImpl();
		FullBean bean = populateEuropeanaUserObject(user, europeanaObjectId, savedItem);
		savedItem.setAuthor(StringUtils.abbreviate(bean.getDcPublisher()[0],
				DatabaseDefinition.FIELDSIZE_AUTHOR));
		return user;
	}
	
	@Override
	public User createSocialTag(Long userId, String europeanaObjectId, String tag) throws DatabaseException {
		if ((userId == null) || StringUtils.isBlank(europeanaObjectId) || StringUtils.isBlank(tag)) {
			throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
		}
		User user = dao.findByPK(userId);
		if (user == null) {
			throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
		}
		SocialTagImpl socialTag = new SocialTagImpl();
		populateEuropeanaUserObject(user, europeanaObjectId, socialTag);
		socialTag.setTag(StringUtils.abbreviate(tag,
				DatabaseDefinition.FIELDSIZE_TAG));
		return user;
	}

	@Override
	public void removeSavedSearch(Long savedSearchId) throws DatabaseException {
		SavedSearchImpl savedSearch = dao.findByPK(SavedSearchImpl.class, savedSearchId);
		if (savedSearch != null) {
			savedSearch.getUser().getSavedSearches().remove(savedSearch);
		}
	}

	@Override
	public void removeSavedItem(Long savedItemId) throws DatabaseException {
		SavedItemImpl savedItem = dao.findByPK(SavedItemImpl.class, savedItemId);
		if (savedItem != null) {
			savedItem.getUser().getSavedItems().remove(savedItem);
		}
	}

	@Override
	public void removeSocialTag(Long socialTagId) throws DatabaseException {
		SocialTagImpl socialTag = dao.findByPK(SocialTagImpl.class, socialTagId);
		if (socialTag != null) {
			socialTag.getUser().getSocialTags().remove(socialTag);
		}
	}

	private FullBean populateEuropeanaUserObject(User user, String europeanaObjectId, EuropeanaUserObject instance)
			throws DatabaseException {
		FullBean bean = searchService.findById(europeanaObjectId);
		if ((user == null) || (bean == null)) {
			throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
		}
		instance.setEuropeanaUri(bean.getId());
		instance.setEuropeanaObject(bean.getEdmObject()[0]);
		instance.setDateSaved(new Date());
		instance.setTitle(StringUtils.abbreviate(bean.getTitle(),
				DatabaseDefinition.FIELDSIZE_TITLE));
		instance.setDocType(bean.getType());
		instance.setUser(user);
		if (instance instanceof SavedItemImpl) {
			user.getSavedItems().add((SavedItemImpl) instance);
		} else {
			user.getSocialTags().add((SocialTagImpl) instance);
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
