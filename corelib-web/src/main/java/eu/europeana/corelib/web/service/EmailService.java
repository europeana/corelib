package eu.europeana.corelib.web.service;

import eu.europeana.corelib.definitions.db.entity.relational.ApiKey;
import eu.europeana.corelib.web.exception.EmailServiceException;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public interface EmailService {

    /**
     * Send API keys to new api registration
     *
     * @param apiKey ApiKey object containing the new keys and email address
     */
    void sendApiKeys(ApiKey apiKey) throws EmailServiceException;


    /**
     * Sends exception to the site admin
     */
    void sendException(String subject, String body) throws EmailServiceException;

}
