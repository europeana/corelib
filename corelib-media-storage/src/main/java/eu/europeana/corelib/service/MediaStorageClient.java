package eu.europeana.corelib.service;

import eu.europeana.corelib.domain.MediaFile;

public interface MediaStorageClient {

    /**
     * Checks if a file already exists in the database
     * @param id the if of the file, it's the MD5 of the URL
     * @return a boolean which indicates the presence of the file
     */
    public Boolean checkIfExists(String id);

    /**
     * Retrieves a file with or without the content.
     * If you want set withContent to false that means that you will get only the metadata of the file.
     * @param id the if of the file, it's the MD5 of the URL
     * @param withContent boolean which indicates the intent of the user (retrieve with or without the content)
     * @return an object which contains all the metainfos and optionally the actual content
     */
    public MediaFile retrieve(String id, Boolean withContent);

    /**
     * If the file does not exists in the DB it creates it, otherwise it will be updated.
     * @param mediaFile the new/modified MediaFile
     */
    public void createOrModify(MediaFile mediaFile);

    /**
     * Deletes a file with a given id.
     * @param id the if of the file, it's the MD5 of the URL
     */
    public void delete(String id);
}
