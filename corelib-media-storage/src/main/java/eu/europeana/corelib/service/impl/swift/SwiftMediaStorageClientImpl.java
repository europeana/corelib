package eu.europeana.corelib.service.impl.swift;

import eu.europeana.corelib.domain.MediaFile;
import eu.europeana.corelib.service.MediaStorageClient;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jclouds.ContextBuilder;
import org.jclouds.io.Payload;
import org.jclouds.openstack.swift.v1.SwiftApi;
import org.jclouds.openstack.swift.v1.domain.SwiftObject;
import org.jclouds.openstack.swift.v1.features.ContainerApi;
import org.jclouds.openstack.swift.v1.features.ObjectApi;
import org.jclouds.openstack.swift.v1.options.PutOptions;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.jclouds.io.Payloads.newByteArrayPayload;

public class SwiftMediaStorageClientImpl implements MediaStorageClient {

    private final ObjectApi objectApi;

    public SwiftMediaStorageClientImpl(SwiftConfiguration config) {
        if (config == null) {
            throw new IllegalArgumentException("Config cannot be null");
        }

        final SwiftApi swiftApi = ContextBuilder.newBuilder("openstack-swift")
                .credentials(config.getIdentity(), config.getPassword())
                .endpoint(config.getAuthUrl())
                .buildApi(SwiftApi.class);

        final ContainerApi containerApi = swiftApi.getContainerApi(config.getRegionName());

        if (containerApi.get(config.getContainerName()) == null) {
            if (!containerApi.create(config.getContainerName())) {
                throw new RuntimeException("swift cannot create container: " + config.getContainerName());
            }
        }

        objectApi = swiftApi.getObjectApi(config.getRegionName(), config.getContainerName());

    }


    @Override
    public Boolean checkIfExists(String id) {
        return objectApi.getWithoutBody(id) != null;
    }

    @Override
    public MediaFile retrieve(String id, Boolean withContent) {

        final SwiftObject swiftObject = withContent ? objectApi.get(id) : objectApi.getWithoutBody(id);

        if (null == swiftObject) {
            return null;
        }

        final byte[] content;
        try {
            content = withContent ? IOUtils.toByteArray(swiftObject.getPayload().openStream()) : new byte[0];
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        final String contentMd5 = computeMd5(content);


        final String swiftObjectMd5 = swiftObject.getPayload().getContentMetadata().getContentMD5AsHashCode().toString();
        if (withContent && !StringUtils.equals(contentMd5, swiftObjectMd5)) {
            /*
             *  something wrong has happened to the data;
             *  security breach ?
             */
            throw new SecurityException("MD5 of content differs from expected");
        }

        return new MediaFile(id,
                null,
                swiftObject.getName(),
                null,
                contentMd5,
                null,
                null,
                content,
                null,
                swiftObject.getMetadata().get("content-type"),
                null,
                Integer.parseInt(swiftObject.getMetadata().get("size"))
        );
    }

    @Override
    public void createOrModify(MediaFile mediaFile) {
        delete(mediaFile.getId());

        final Payload payload = newByteArrayPayload(mediaFile.getContent());

        payload.getContentMetadata().setContentType(mediaFile.getContentType());
        payload.getContentMetadata().setContentLength(mediaFile.getLength());

        Map<String, String> metadata = new HashMap<>();

        metadata.put("Content-Type", mediaFile.getContentType());
        metadata.put("size", Integer.toString(mediaFile.getSize()));
        metadata = Collections.unmodifiableMap(metadata);

        objectApi.put(mediaFile.getId(), payload, PutOptions.Builder.metadata(metadata));
    }

    @Override
    public void delete(String id) {
        objectApi.delete(id);
    }

    private String computeMd5(final byte[] content) {
        try {
            final byte[] array = MessageDigest.getInstance("MD5").digest(content);
            StringBuilder sb = new StringBuilder();
            for (byte anArray : array) {
                sb.append(Integer.toHexString((anArray & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
