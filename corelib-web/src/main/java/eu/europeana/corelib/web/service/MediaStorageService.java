package eu.europeana.corelib.web.service;

import eu.europeana.corelib.web.model.MediaFile;
import eu.europeana.domain.ObjectMetadata;
import eu.europeana.domain.StorageObject;
import org.jclouds.io.Payload;

import java.io.IOException;


public interface MediaStorageService {

    /**
     * Checks if a particular file with the provided id is present in the media storage
     * @param id the id of the file
     * @return true if the file is present, false if it is not
     */
    Boolean checkIfExists(String id);

    /**
     * Retrieves a file from media storage with or without the content.
     * @param id the id of the file
     * @param withContent boolean which indicates whether the actual contents should be retrieved, or
     *                    only the metadata of the file
     * @return an object which contains all the metadata of the file and optionally the actual content
     */
    MediaFile retrieveAsMediaFile(String id, boolean withContent);

    /**
     * Retrieves a file from media storage with or without the content.
     * Note that this method is a bit faster than retrieveAsMediaFile, but does require you to process a payload to
     * access the actual file content (@see convertPayloadToByteArray)
     * @param id the id of the file
     * @param withContent boolean which indicates whether the actual contents should be retrieved, or
     *                    only the metadata of the file
     * @return an object which contains all the metadata of the file and optionally the actual content
     */
    StorageObject retrieveAsStorageObject(String id, boolean withContent);

    /**
     * Helper class to convert payload to an array of bytes
     * @param payload
     * @return array of bytes representing the payload object
     */
    byte[] convertPayloadToByteArray(Payload payload) throws IOException;

    /**
     * Retrieves only the content of a media file (so no metadata)
     * @param id the id of the file
     * @return an array of bytes representing only the media content of the file
     */
    byte[] retrieveContent(String id);

    /**
     * Retrieves only the metadata of a media file
     * @param id the id of the file
     * @return ObjectMetadata object
     */
    ObjectMetadata retrieveMetaData(String id);

    /**
     * If the file does not exists in the DB it creates it, otherwise it will be updated.
     * @param file the new/modified MediaFile
     * @deprecated September 2018, no longer needed/required
     */
    @Deprecated
    void createOrModify(MediaFile file);

    /**
     * Deletes a file with a given id.
     * @param id the id of the file
     * @deprecated September 2018, no longer needed/required
     */
    @Deprecated
    void delete(String id);
}
