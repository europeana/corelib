package eu.europeana.corelib.service.impl;

import eu.europeana.corelib.domain.MediaFile;
import eu.europeana.corelib.service.MediaStorageClient;
import eu.europeana.domain.ObjectMetadata;
import eu.europeana.domain.StorageObject;
import eu.europeana.features.ObjectStorageClient;
import eu.europeana.features.S3ObjectStorageClient;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.jclouds.io.Payload;
import org.joda.time.DateTime;


import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static org.jclouds.io.Payloads.newByteArrayPayload;

public class EuropeanaObjectStorageClientImpl implements MediaStorageClient {

    private static final Logger LOG = Logger.getLogger(EuropeanaObjectStorageClientImpl.class);

    @Resource(name = "corelib_web_S3ObjectStorageClient")
    ObjectStorageClient objectApi;

    public EuropeanaObjectStorageClientImpl(S3ObjectStorageClient s3ObjectStorageClient) {
        this.objectApi = s3ObjectStorageClient;
    }

    /**
     * @see eu.europeana.corelib.service.MediaStorageClient#checkIfExists(String)
     */
    @Override
    public Boolean checkIfExists(String id) {
        return objectApi.isAvailable(id);
    }

    /**
     * @see eu.europeana.corelib.service.MediaStorageClient#retrieve(String, Boolean)
     */
    @Override
    public MediaFile retrieve(String id, Boolean withContent) {
        final Optional<StorageObject> storageObject = withContent ? objectApi.get(id) : objectApi.getWithoutBody(id);

        if (!storageObject.isPresent()) {
            return null;
        }
        final StorageObject storageObjectValue = storageObject.get();
        final byte[] content;
        if (withContent) {
            try (InputStream in = storageObjectValue.getPayload().openStream()) {
                content = IOUtils.toByteArray(in);
            } catch (IOException e) {
                throw new RuntimeException("Error reading payload data", e);
            }
        } else {
            content = null;
        }

        return new MediaFile(id,
                null,
                storageObjectValue.getName(),
                null,
                storageObjectValue.getMetadata().getETag(),
                null,
                new DateTime(storageObjectValue.getMetadata().getLastModified().getTime()),
                content,
                null,
                storageObjectValue.getMetadata().getContentType(),
                null,
                (int) storageObjectValue.getMetadata().getContentLength()
        );
    }

    /**
     * @see eu.europeana.corelib.service.MediaStorageClient#retrieveContent(String)
     */
    @Override
    public byte[] retrieveContent(String id) {
        return objectApi.getContent(id);
    }

    /**
     * @see eu.europeana.corelib.service.MediaStorageClient#retrieveMetaData(String)
     */
    @Override
    public ObjectMetadata retrieveMetaData(String id) {
        return objectApi.getMetaData(id);
    }

    /**
     * @see eu.europeana.corelib.service.MediaStorageClient#createOrModify(MediaFile)
     */
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

    /**
     * @see eu.europeana.corelib.service.MediaStorageClient#delete(String)
     */
    @Override
    public void delete(String id) {
        objectApi.delete(id);
    }

}
