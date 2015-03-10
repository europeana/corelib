package eu.europeana.corelib.service.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import eu.europeana.corelib.domain.MediaFile;
import eu.europeana.corelib.domain.MetaDataFields;
import eu.europeana.corelib.domain.MongoMetaData;
import eu.europeana.corelib.service.MediaStorageClient;
import eu.europeana.corelib.server.impl.ThumbnailMongoServerImpl;
import org.joda.time.DateTime;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class MediaStorageClientImpl implements MediaStorageClient {

    @Resource
    ThumbnailMongoServerImpl thumbnailMongoServer;

    public MediaStorageClientImpl() {}

    @Override
    public Boolean checkIfExists(String id) {
        final BasicDBObject query = new BasicDBObject();
        query.put("_id", id);
        final GridFSDBFile file = thumbnailMongoServer.getGridFS().findOne(query);

        return file != null;
    }

    @Override
    public MediaFile retrieve(String id, Boolean withContent) {
        final BasicDBObject query = new BasicDBObject();
        query.put("_id", id);

        final GridFSDBFile file = thumbnailMongoServer.getGridFS().findOne(query);

        if(file == null) {
            return null;
        }

        final Long length = file.getLength();
        final String name = file.getFilename();
        final DateTime createdAt = new DateTime(file.getUploadDate());
        final String contentMd5 = file.getMD5();
        final String contentType = file.getContentType();

        final byte[] content;
        if(withContent) {
            content = toByteArray(file.getInputStream());
        } else {
            content = null;
        }

        final DBObject mongoMetaData = file.getMetaData();

        final String source = (String) mongoMetaData.get(String.valueOf(MetaDataFields.SOURCE));
        final List<String> aliases = (List<String>) mongoMetaData.get(String.valueOf(MetaDataFields.ALIASES));
        final String originalUrl = (String) mongoMetaData.get(String.valueOf(MetaDataFields.ORIGINAL_URL));
        final Integer versionNumber = (Integer) mongoMetaData.get(String.valueOf(MetaDataFields.VERSIONNUMBER));
        final Map<String, String> metaData = (Map<String, String>) mongoMetaData.get(String.valueOf(MetaDataFields.TECHNICAL_METADATA));

        Integer size = null;

        if (null == metaData) {
            return new MediaFile(source, name, aliases, contentMd5, originalUrl, createdAt,
                    content, versionNumber, contentType, metaData, size);
        }

        if(metaData.containsKey("size")) {
            size = Integer.parseInt(metaData.get("size"));
        }
        if(metaData.containsKey("height")) {
            size = Integer.parseInt(metaData.get("height"));
        }

        return new MediaFile(source, name, aliases, contentMd5, originalUrl, createdAt,
                content, versionNumber, contentType, metaData, size);
    }

    @Override
    public void createOrModify(MediaFile mediaFile) {
        final MongoMetaData metaData= new MongoMetaData();
        metaData.put(String.valueOf(MetaDataFields.SOURCE), mediaFile.getSource());
        metaData.put(String.valueOf(MetaDataFields.ORIGINAL_URL), mediaFile.getOriginalUrl());
        metaData.put(String.valueOf(MetaDataFields.VERSIONNUMBER), mediaFile.getVersionNumber());
        metaData.put(String.valueOf(MetaDataFields.TECHNICAL_METADATA), mediaFile.getMetaData());
        metaData.put(String.valueOf(MetaDataFields.ALIASES), mediaFile.getAliases());

        final GridFSInputFile file = thumbnailMongoServer.getGridFS().createFile(mediaFile.getContent());
        file.setFilename(mediaFile.getName());
        file.setContentType(mediaFile.getContentType());
        file.setId(mediaFile.getId());
        file.setMetaData(metaData);

        if(checkIfExists(mediaFile.getId())) {
            delete(mediaFile.getId());
        }

        file.save();
    }

    @Override
    public void delete(String id) {
        final BasicDBObject query = new BasicDBObject();
        query.put("_id", id);

        thumbnailMongoServer.getGridFS().remove(query);
    }

    /**
     * Converts an inputstream (which comes probably from a file) to a byte array
     * @param is inputstream
     * @return the content of the file in a byte array
     */
    private byte[] toByteArray(InputStream is) {
        final ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        final byte[] data = new byte[16384];

        try {
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return buffer.toByteArray();
    }
}
