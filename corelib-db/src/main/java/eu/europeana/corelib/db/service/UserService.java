package eu.europeana.corelib.db.service;

import eu.europeana.corelib.db.entity.relational.custom.TagCloudItem;
import eu.europeana.corelib.db.exception.DatabaseException;
import eu.europeana.corelib.db.service.abstracts.AbstractService;
import eu.europeana.corelib.definitions.db.entity.relational.SavedItem;
import eu.europeana.corelib.definitions.db.entity.relational.SocialTag;
import eu.europeana.corelib.definitions.db.entity.relational.Token;
import eu.europeana.corelib.definitions.db.entity.relational.User;
import eu.europeana.corelib.neo4j.exception.Neo4JException;
import eu.europeana.corelib.web.exception.EmailServiceException;

import java.util.List;

/**
 * Service with User related actions.
 *
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * @see eu.europeana.corelib.db.entity.relational.UserImpl
 * @deprecated January 2018 OAuth and MyEuropeana are no longer being used*
 */
@Deprecated
public interface UserService extends AbstractService<User> {

    /**
     * Creates a new User, based on a existing token, and given params
     */
    User create(String email, String username, String password, String company, String country, String firstName,
                String lastName, String website, String address, String phone, String fieldOfWork, String redirect,
                String activationUrl) throws DatabaseException, EmailServiceException;

    /**
     * updates an existing User
     */
    User update(User user, String email, String username, String password, String company, String country, String firstName,
                String lastName, String website, String address, String phone, String fieldOfWork)
            throws DatabaseException;

    /**
     * deletes an existing User
     */
    void delete(User user) throws DatabaseException;

    /**
     * Activate a user account
     *
     * @param email email address identifying the account
     * @param token token emailed to user
     * @return The activated user account
     */
    Token activate(String email, String token) throws DatabaseException;

    /**
     *
     * @param email email address identifying the account
     * @param redirect  token emailed to user
     * @return user with given email adres, null if not found.
     * @throws DatabaseException
     * @throws EmailServiceException
     */
    User sendResetPasswordToken(String email, String redirect, String activationUrl) throws DatabaseException, EmailServiceException;

    /**
     *
     * @param email       The email address associated which the token
     * @param tokenString The token string
     * @return the redirect URL associated with the token
     * @throws DatabaseException
     */
    String getRedirectFromToken(String email, String tokenString) throws DatabaseException;

    /**
     *
     * @param email    The email address identifying the user
     * @param password the newly submitted password
     * @return user identified by the supplied email adres, null if not found or if password reset failed
     * @throws DatabaseException
     */
    User resetPassword(String email, String tokenString, String password) throws DatabaseException, EmailServiceException;

    /**
     * Returns a User if there is a valid email provided.
     *
     * @param email Email address of user, not case sensitive
     * @return A user with given email adres, null if not found.
     */
    User findByEmail(String email);

    /**
     * Returns a User if there is a valid user name provided.
     *
     * @param userName Name of user, case sensitive
     * @return A user with given user name, null if not found.
     */
    User findByName(String userName);

    /**
     * Returns a User if there is a valid email and password provided.
     *
     * @param email    Email address of user, not case sensitive
     * @param password User's Password, case sensitive
     * @return A user if both params are valid, otherwise null
     */
    User authenticateUser(String email, String password);

    //TODO - remove because this is implemented by sendResetPasswordToken and following
    /**
     * Changes the existing password of an existing password
     *
     * @param userId      The id of the excising user
     * @param oldPassword User's current password, case sensitive
     * @param newPassword User's new password, case sensitive
     * @return A user if both params are valid, otherwise null
     * @throws DatabaseException Thrown when no valid user or passwords are provided
     */
    User changePassword(Long userId, String oldPassword, String newPassword) throws DatabaseException;

    /**
     * Creates and add a SavedSearch to an existing User
     *
     * @param userId      The id of the excising user to add the new SavedSearch to
     * @param query       query contains the query as shown in searchbox
     * @param queryString contains the complete query string including facets
     * @return The User including the new saved search
     * @throws DatabaseException Thrown when no valid user or query(string) is provided
     */
    User createSavedSearch(Long userId, String query, String queryString) throws DatabaseException;

    /**
     * Updates an existing SavedSearch from an existing User
     *
     * @param userId            The id of the excising user to add the new SavedSearch to
     * @param savedSearchId     The id of the saved search made bij the user
     * @param query       query contains the query as shown in searchbox
     * @param queryString contains the complete query string including facets
     * @return The User including the new saved search
     * @throws DatabaseException Thrown when no valid user or savedSearch or query(string) is provided
     */
    User updateSavedSearch(Long userId,Long savedSearchId, String query, String queryString) throws DatabaseException;

    /**
     * Creates and add a SavedItem to an existing User
     *
     * @param userId            The id of the excising user to add the new SavedSearch to
     * @param europeanaObjectId EuropeanaObjectId
     * @return The User including the new saved item
     * @throws DatabaseException Thrown when no valid user or object id is provided
     */
    User createSavedItem(Long userId, String europeanaObjectId) throws DatabaseException, Neo4JException;

    /**
     * Creates and add a SocialTag to an existing user
     *
     * @param userId            The id of the excising user to add the new SavedSearch to
     * @param europeanaObjectId EuropeanaObjectId
     * @param tag
     * @return The User including the new social tag
     * @throws DatabaseException Thrown when no valid user, object id or tag is provided
     */
    User createSocialTag(Long userId, String europeanaObjectId, String tag) throws DatabaseException, Neo4JException;

    /**
     * Removes a SavedSearch from database and User.
     *
     * @param userId        The id of the existing user
     * @param savedSearchId The primary key of the saved search to remove
     * @throws DatabaseException
     */
    void removeSavedSearch(Long userId, Long savedSearchId) throws DatabaseException;

    /**
     * Removes a SavedItem from database and User.
     *
     * @param userId      The id of the existing user
     * @param savedItemId The primary key of the saved item to remove
     * @throws DatabaseException
     */
    void removeSavedItem(Long userId, Long savedItemId) throws DatabaseException;

    /**
     * Removes a SavedItem from database by objectid
     *
     * @param userId   The id of the existing user
     * @param objectId EuropeanaObjectId
     * @throws DatabaseException
     */
    void removeSavedItem(Long userId, String objectId) throws DatabaseException;

    /**
     * Removes a SocialTag from database and User.
     *
     * @param socialTagId The primary key of the social tag to remove
     * @throws DatabaseException
     */
    void removeSocialTag(Long userId, Long socialTagId) throws DatabaseException;

    /**
     * Remove SocialTag(s) matching the objectId and/or tag
     *
     * @param userId   The id of the existing user
     * @param objectId EuropeanaObjectId
     * @param tag      Tag to remove
     * @throws DatabaseException
     */
    void removeSocialTag(Long userId, String objectId, String tag) throws DatabaseException;

    /**
     * Returns a distinct list of tags (combination of tag as a string and a count)
     *
     * @param userId Existing id of user account
     */
    List<TagCloudItem> createSocialTagCloud(Long userId) throws DatabaseException;

    /**
     * Returns a list of SocialTag objects filtered by User and Tag
     *
     * @param userId Existing id of user account
     * @param tag    Tag string to filter on (case-insensitive)
     * @throws DatabaseException
     */
    List<SocialTag> findSocialTagsByTag(Long userId, String tag) throws DatabaseException;

    /**
     * Returns a list of SocialTag objects filtered by User and EuropeanaId
     *
     * @param userId      Existing id of user account
     * @param europeanaId EuropeanaObjectId
     * @throws DatabaseException
     */
    List<SocialTag> findSocialTagsByEuropeanaId(Long userId, String europeanaId) throws DatabaseException;

    /**
     * @param userId      Existing id of user account
     * @param europeanaId EuropeanaObjectId
     * @throws DatabaseException
     */
    SavedItem findSavedItemByEuropeanaId(Long userId, String europeanaId) throws DatabaseException;

    /**
     * Set the language portal to the user profile
     *
     * @param userId       Existing id of user account
     * @param languageCode 2 character language code, empty string or null will clear the value in profile
     * @return Updated user profile
     * @throws DatabaseException
     */
    User updateUserLanguagePortal(Long userId, String languageCode) throws DatabaseException;

    /**
     * Set the item default translation setting to the user profile
     *
     * @param userId       Existing id of user account
     * @param languageCode 2 character language code, empty string or null will clear the value in profile
     * @return Updated user profile
     * @throws DatabaseException
     */
    User updateUserLanguageItem(Long userId, String languageCode) throws DatabaseException;

    /**
     * Set the preferred search query translations to the user profile
     *
     * @param userId        Existing id of user account
     * @param languageCodes Two options: only 1 string with codes, comma separated or a string array of codes
     *                      2 character language code, empty string or null will clear the value in profile
     * @return Updated user profile
     * @throws DatabaseException
     */
    User updateUserLanguageSearch(Long userId, String... languageCodes) throws DatabaseException;

    /**
     * Set a flag for enabling query translations
     *
     * @param userId                Existing id of user account
     * @param languageSearchApplied Whether query translations are enabled or not
     * @return Updated user profile
     * @throws DatabaseException
     */
    User updateUserLanguageSearchApplied(Long userId, Boolean languageSearchApplied) throws DatabaseException;

}
