package eu.europeana.corelib.service;

import eu.europeana.corelib.domain.MediaFile;
import eu.europeana.domain.ObjectMetadata;

public interface MediaStorageClient {

    /**
     * Checks if a file already exists in the database
     * @param id the id of the file (the MD5 of the URL)RL
     * @return a boolean which indicates the presence of the file
     */
    Boolean checkIfExists(String id);

    /**
     * Retrieves a file with or without the content.
     * If you want set withContent to false that means that you will get only the metadata of the file.
     * @param id the id of the file (the MD5 of the URL)
     * @param withContent boolean which indicates the intent of the user (retrieve with or without the content)
     * @return an object which contains all the metainfos and optionally the actual content
     */
    MediaFile retrieve(String id, Boolean withContent);

    /**
     * Retrieves only the content of a media file
     * @param id the id of the file (the MD5 of the URL)
     * @return a array of bytes representing only the media content
     */
    byte[] retrieveContent(String id);

    /**
     * Retrieves only the metadata of a media file
     * @param id the id of the file (the MD5 of the URL)
     * @return ObjectMetadata object
     */
    ObjectMetadata retrieveMetaData(String id);

    /**
     * If the file does not exists in the DB it creates it, otherwise it will be updated.
     * @param mediaFile the new/modified MediaFile
     */
    void createOrModify(MediaFile mediaFile);

    /**
     * Deletes a file with a given id.
     * @param id the if of the file, it's the MD5 of the URL
     */
    void delete(String id);
}
