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

package eu.europeana.corelib.db.service;

import org.apache.solr.client.solrj.SolrServerException;

import eu.europeana.corelib.db.exception.DatabaseException;
import eu.europeana.corelib.db.service.abstracts.AbstractService;
import eu.europeana.corelib.definitions.db.entity.User;
import eu.europeana.corelib.solr.exceptions.SolrTypeException;

/**
 * Service with User related actions.
 * 
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * 
 * @see eu.europeana.corelib.db.entity.relational.UserImpl
 */
public interface UserService extends AbstractService<User> {

	/**
	 * Creates a new User, based on a existing token, and given params
	 * 
	 * @param token
	 *            A Token string, existing in the database
	 * @param username
	 *            The username for this user profile
	 * @param password
	 *            The login password (case sensitive)
	 * @return Created user entity.
	 * @throws DatabaseException
	 *             When the Token is invalid
	 */
	User create(String token, String username, String password) throws DatabaseException;

	/**
	 * Returns a User if there is a valid email provided.
	 * 
	 * @param email
	 *            Email address of user, not case sensitive
	 * @return A user with given email adres, null if not found.
	 */
	User findByEmail(String email);

	/**
	 * Returns a User if there is a valid api key provided.
	 * 
	 * @param apiKey
	 *            API key of user, case sensitive
	 * @return A user with given API key, null if not found.
	 */
	User findByApiKey(String apiKey);

	/**
	 * Returns a User if there is a valid email and password provided.
	 * 
	 * @param email
	 *            Email address of user, not case sensitive
	 * @param password
	 *            User's Password, case sensitive
	 * @return A user if both params are valid, otherwise null
	 */
	User authenticateUser(String email, String password);
	
	/**
	 * Changes the existing password of an existing password
	 * 
	 * @param userId
	 *            The id of the excising user
	 * @param oldPassword
	 *            User's current password, case sensitive
	 * @param newPassword
	 *            User's new password, case sensitive
	 * @return A user if both params are valid, otherwise null
	 * @exception DatabaseException
	 *                Thrown when no valid user or passwords are provided
	 */
	User changePassword(Long userId, String oldPassword, String newPassword) throws DatabaseException;

	/**
	 * Creates and add a SavedSearch to an existing User
	 * 
	 * @param userId
	 *            The id of the excising user to add the new SavedSearch to
	 * @param query
	 *            query contains the query as shown in searchbox
	 * @param queryString
	 *            contains the complete query string including facets
	 * @return The User including the new saved search
	 * @exception DatabaseException
	 *                Thrown when no valid user or query(string) is provided
	 */
	User createSavedSearch(Long userId, String query, String queryString) throws DatabaseException;

	/**
	 * Creates and add a SavedItem to an existing User
	 * 
	 * @param userId
	 *            The id of the excising user to add the new SavedSearch to
	 * @param objectId
	 *            EuropeanaObjectId
	 * @return The User including the new saved item
	 * @exception DatabaseException
	 *                Thrown when no valid user or object id is provided
	 * @throws SolrServerException 
	 * @throws SolrTypeException 
	 */
	User createSavedItem(Long userId, String europeanaObjectId) throws DatabaseException;

	/**
	 * Creates and add a SocialTag to an existing user
	 * 
	 * @param userId
	 *            The id of the excising user to add the new SavedSearch to
	 * @param objectId
	 *            EuropeanaObjectId
	 * @param tag
	 * @return The User including the new social tag
	 * @exception DatabaseException
	 *                Thrown when no valid user, object id or tag is provided
	 * @throws SolrServerException 
	 * @throws SolrTypeException 
	 */
	User createSocialTag(Long userId, String europeanaObjectId, String tag) throws DatabaseException;
	
	/**
	 * Removes a SavedSearch from database and User.
	 * 
	 * @param savedSearchId
	 *            The primary key of the saved search to remove
	 * @throws DatabaseException 
	 */
	void removeSavedSearch(Long savedSearchId) throws DatabaseException;

	/**
	 * Removes a SavedItem from database and User.
	 * 
	 * @param savedItemId
	 *            The primary key of the saved item to remove
	 * @throws DatabaseException 
	 */
	void removeSavedItem(Long savedItemId) throws DatabaseException;

	/**
	 * Removes a SocialTag from database and User.
	 * 
	 * @param socialTagId
	 *            The primary key of the social tag to remove
	 * @throws DatabaseException 
	 */
	void removeSocialTag(Long socialTagId) throws DatabaseException;

}
