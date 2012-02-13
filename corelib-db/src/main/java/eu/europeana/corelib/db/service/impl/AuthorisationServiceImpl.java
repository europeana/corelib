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
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.solr.client.solrj.SolrServerException;

import eu.europeana.corelib.db.dao.Dao;
import eu.europeana.corelib.db.entity.AuthenticationImpl;
import eu.europeana.corelib.db.exception.DatabaseException;
import eu.europeana.corelib.db.service.AuthorisationService;
import eu.europeana.corelib.db.service.UserService;
import eu.europeana.corelib.definitions.db.entity.Authentication;
import eu.europeana.corelib.definitions.db.entity.User;
import eu.europeana.corelib.solr.exceptions.SolrTypeException;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public class AuthorisationServiceImpl implements AuthorisationService {

	private Dao<Authentication> dao;

	@Resource
	private UserService userService;

	/**
	 * Used by Bean configuration to inject Entity based DAO.
	 * 
	 * @param dao
	 *            DAO object with entity based generic set
	 */
	public final void setDao(Dao<Authentication> dao) {
		this.dao = dao;
	}

	@Override
	public boolean validateApiKey(String apiKey) {
		return userService.findByApiKey(apiKey) != null;
	}

	@Override
	public String authenticateUser(String apiKey, String username, String password) {
		if (validateApiKey(apiKey)) {
			User user = userService.authenticateUser(username, password);
			if (user != null) {
				Authentication auth = new AuthenticationImpl();
				auth.setUser(user);
				auth.setApiKey(apiKey);
				auth.setAuthKey(UUID.randomUUID().toString());
				auth.setExpires(DateUtils.addHours(new Date(), 1));
				dao.insert(auth);
				return auth.getAuthKey();
			}
		}
		return null;
	}

	@Override
	public User getUser(String apiKey, String authKey) throws DatabaseException {
		if (validateApiKey(apiKey)) {
			Authentication auth = dao.findByPK(authKey);
			if ((auth != null) && StringUtils.equals(apiKey, auth.getApiKey()) && auth.getExpires().after(new Date())) {
				return auth.getUser();
			}
		}
		return null;
	}

	@Override
	public User changePassword(String apiKey, String authKey, String oldPassword, String newPassword)
			throws DatabaseException {
		User user = getUser(apiKey, authKey);
		if (user != null) {
			userService.changePassword(user.getId(), oldPassword, newPassword);
		}
		return null;
	}

	@Override
	public User createSavedSearch(String apiKey, String authKey, String query, String queryString)
			throws DatabaseException {
		User user = getUser(apiKey, authKey);
		if (user != null) {
			userService.createSavedSearch(user.getId(), query, queryString);
		}
		return null;
	}

	@Override
	public User createSavedItem(String apiKey, String authKey, String europeanaObjectId) throws DatabaseException, SolrTypeException, SolrServerException {
		User user = getUser(apiKey, authKey);
		if (user != null) {
			userService.createSavedItem(user.getId(), europeanaObjectId);
		}
		return null;
	}

	@Override
	public User createSocialTag(String apiKey, String authKey, String europeanaObjectId, String tag)
			throws DatabaseException, SolrTypeException, SolrServerException {
		User user = getUser(apiKey, authKey);
		if (user != null) {
			userService.createSocialTag(user.getId(), europeanaObjectId, tag);
		}
		return null;
	}

	@Override
	public void removeSavedSearch(String apiKey, String authKey, Long savedSearchId) throws DatabaseException {
		User user = getUser(apiKey, authKey);
		if (user != null) {
			// TODO double check if item belongs to authorized user
			userService.removeSavedSearch(savedSearchId);
		}
	}

	@Override
	public void removeSavedItem(String apiKey, String authKey, Long savedItemId) throws DatabaseException {
		User user = getUser(apiKey, authKey);
		if (user != null) {
			// TODO double check if item belongs to authorized user
			userService.removeSavedItem(savedItemId);
		}
	}

	@Override
	public void removeSocialTag(String apiKey, String authKey, Long socialTagId) throws DatabaseException {
		User user = getUser(apiKey, authKey);
		if (user != null) {
			// TODO double check if item belongs to authorized user
			userService.removeSocialTag(socialTagId);
		}
	}

}
