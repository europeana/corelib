package eu.europeana.corelib.web.service.impl;

import eu.europeana.corelib.domain.MediaFile;
import eu.europeana.corelib.service.MediaStorageClient;
import eu.europeana.corelib.web.service.MediaStorageService;

import javax.annotation.Resource;

public class MediaStorageServiceImpl implements MediaStorageService {

    @Resource(name = "corelib_mediaStorageClient")
    private MediaStorageClient mediaStorageClient;

    @Override
    public Boolean checkIfExists(String id) {
        return mediaStorageClient.checkIfExists(id);
    }

    @Override
    public MediaFile retrieve(String id, Boolean withContent) {
        return mediaStorageClient.retrieve(id,withContent);
    }

    @Override
    public void createOrModify(MediaFile mediaFile) {
        mediaStorageClient.createOrModify(mediaFile);
    }

    @Override
    public void delete(String id) {
        mediaStorageClient.delete(id);
    }
}
