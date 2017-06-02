package eu.europeana.corelib.web.service;

import eu.europeana.corelib.domain.MediaFile;


public interface MediaStorageService {

    /**
     * @see eu.europeana.corelib.service.MediaStorageClient#checkIfExists(String)
     */
    Boolean checkIfExists(String id);

    /**
     * @see eu.europeana.corelib.service.MediaStorageClient#retrieve(String, Boolean)
     */
    MediaFile retrieve(String id, Boolean withContent);

    /**
     * @see eu.europeana.corelib.service.MediaStorageClient#retrieveContent(String)
     */
    byte[] retrieveContent(String id);

    /**
     * @see eu.europeana.corelib.service.MediaStorageClient#createOrModify(MediaFile)
     */
    void createOrModify(MediaFile file);

    /**
     * @see eu.europeana.corelib.service.MediaStorageClient#delete(String)
     */
    void delete(String id);
}
