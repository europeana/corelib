package eu.europeana.corelib.web.service;

import eu.europeana.corelib.definitions.db.entity.relational.ApiKey;
import eu.europeana.corelib.definitions.db.entity.relational.Token;
import eu.europeana.corelib.definitions.db.entity.relational.User;
import eu.europeana.corelib.web.exception.EmailServiceException;

import java.util.Locale;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public interface EmailService {

    /**
     * Send an activation link to a new user account
     *
     * @param token   The token to send to the user
     * @param apiHost The api host url for the activation method in API
     */
    void sendActivationToken(Token token, String apiHost) throws EmailServiceException;

    /**
     * Send API keys to new api registration
     *
     * @param apiKey ApiKey object containing the new keys and email address
     */
    void sendApiKeys(ApiKey apiKey) throws EmailServiceException;

    /**
     *
     * @param token   The token to send to the user
     * @param apiHost The api host url for the activation method in API
     * @throws EmailServiceException
     */
    void sendNewPasswordToken(Token token, String apiHost, String salutation) throws EmailServiceException;

    /**
     *
     * @param user to whom the confirmation is sent
     * @throws EmailServiceException
     */
    void sendPasswordResetConfirmation(User user, String salutation) throws EmailServiceException;

    //TODO: remove because it's implemented in sedNewPasswordToken ?
    /**
     * Sends and email to user in case of forgotting password. It contains a link where the user can reset his password.
     *
     * @param user The user object
     * @param url  The URL of the password reset page
     */
    void sendForgotPassword(User user, String url) throws EmailServiceException;

    //TODO: remove because it's implemented in sedNewPasswordToken ?
    /**
     * Sends and email to user in case of forgotting password. It contains a link where the user can reset his password.
     *
     * @param email The user object
     * @param url   The URL of the password reset page
     */
    void sendForgotPassword(String email, String url) throws EmailServiceException;

    /**
     * Sends the user's feedback to the site admin, and sends an thanking email to the user
     *
     * @param email    The user's email address
     * @param feedback The user's feedback
     */
    void sendFeedback(String email, String feedback) throws EmailServiceException;

    /**
     * Sends exception to the site admin
     */
    void sendException(String subject, String body) throws EmailServiceException;

}
