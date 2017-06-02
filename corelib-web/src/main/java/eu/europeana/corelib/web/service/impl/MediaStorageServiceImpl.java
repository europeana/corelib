package eu.europeana.corelib.web.service.impl;

import eu.europeana.corelib.domain.MediaFile;
import eu.europeana.corelib.service.MediaStorageClient;
import eu.europeana.corelib.web.service.MediaStorageService;

import javax.annotation.Resource;

public class MediaStorageServiceImpl implements MediaStorageService {

    @Resource(name = "corelib_mediaStorageClient")
    private MediaStorageClient mediaStorageClient;

    /**
     * @see eu.europeana.corelib.service.MediaStorageClient#checkIfExists(String)
     */
    @Override
    public Boolean checkIfExists(String id) {
        return mediaStorageClient.checkIfExists(id);
    }

    /**
     * @see eu.europeana.corelib.service.MediaStorageClient#retrieve(String, Boolean)
     */
    @Override
    public MediaFile retrieve(String id, Boolean withContent) {
        return mediaStorageClient.retrieve(id,withContent);
    }

    /**
     * @see eu.europeana.corelib.service.MediaStorageClient#retrieveContent(String)
     */
    @Override
    public byte[] retrieveContent(String id) {return mediaStorageClient.retrieveContent(id); }

    /**
     * @see eu.europeana.corelib.service.MediaStorageClient#createOrModify(MediaFile)
     */
    @Override
    public void createOrModify(MediaFile mediaFile) {
        mediaStorageClient.createOrModify(mediaFile);
    }

    /**
     * @see eu.europeana.corelib.service.MediaStorageClient#delete(String)
     */
    @Override
    public void delete(String id) {
        mediaStorageClient.delete(id);
    }
}
