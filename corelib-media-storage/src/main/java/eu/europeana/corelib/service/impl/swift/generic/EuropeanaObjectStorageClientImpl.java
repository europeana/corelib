package eu.europeana.corelib.service.impl.swift.generic;

import eu.europeana.corelib.domain.MediaFile;
import eu.europeana.corelib.service.MediaStorageClient;
import eu.europeana.domain.ObjectMetadata;
import eu.europeana.domain.StorageObject;
import eu.europeana.features.ObjectStorageClient;
import eu.europeana.features.S3ObjectStorageClient;
import org.apache.commons.io.IOUtils;
import org.jclouds.io.Payload;


import javax.annotation.Resource;
import java.io.IOException;
import java.util.Optional;

import static org.jclouds.io.Payloads.newByteArrayPayload;

public class EuropeanaObjectStorageClientImpl implements MediaStorageClient {
    @Resource(name = "corelib_web_S3ObjectStorageClient")
    ObjectStorageClient objectApi;

    public EuropeanaObjectStorageClientImpl(S3ObjectStorageClient s3ObjectStorageClient) {
        this.objectApi = s3ObjectStorageClient;
    }

    @Override
    public Boolean checkIfExists(String id) {
        return objectApi.getWithoutBody(id).isPresent();
    }

    @Override
    public MediaFile retrieve(String id, Boolean withContent) {
        final Optional<StorageObject> storageObject = withContent ? objectApi.get(id) : objectApi.getWithoutBody(id);


        if (!storageObject.isPresent()) {
            return null;
        }
        final StorageObject storageObjectValue = storageObject.get();
        final byte[] content;
        try {
            content = withContent ? IOUtils.toByteArray(storageObjectValue.getPayload().openStream()) : new byte[0];
        } catch (IOException e) {
            // e.printStackTrace();
            throw new RuntimeException(e);
        }

        return new MediaFile(id,
                null,
                storageObjectValue.getName(),
                null,
                storageObjectValue.getMetadata().getETag(),
                null,
                null,
                content,
                null,
                storageObjectValue.getMetadata().getContentType(),
                null,
                (int) storageObjectValue.getMetadata().getContentLength()
        );
    }

    @Override
    public void createOrModify(MediaFile mediaFile) {
        delete(mediaFile.getId());

        final Payload payload = newByteArrayPayload(mediaFile.getContent());

        payload.getContentMetadata().setContentType(mediaFile.getContentType());
        payload.getContentMetadata().setContentLength(mediaFile.getLength());

        ObjectMetadata metadata = new ObjectMetadata();

        metadata.setContentType(mediaFile.getContentType());
        metadata.setContentLength(mediaFile.getSize().longValue());


        objectApi.put(mediaFile.getId(), payload);
    }

    @Override
    public void delete(String id) {
        objectApi.delete(id);
    }

}
