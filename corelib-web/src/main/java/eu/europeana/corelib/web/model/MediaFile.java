package eu.europeana.corelib.web.model;

import eu.europeana.domain.ObjectMetadata;
import org.joda.time.DateTime;

/**
 * Wrapping class around a media object retrieved from object storage
 * @deprecated 15 August 2019, all thumbnail files are now served by the thumbnail application itself
 */
@Deprecated
public class MediaFile {

    private final String id;

    private final String originalUrl;

    private final byte[] content;

    private final ObjectMetadata metaData;

    public MediaFile(String id, String originalUrl, byte[] content) {
        this(id, originalUrl, content, null);
    }

    public MediaFile(String id, String originalUrl, byte[] content, ObjectMetadata metadata) {
        this.id = id;
        this.originalUrl = originalUrl;
        this.content = content;
        this.metaData = metadata;
    }

    /**
     * @return The md5 of the original url plus hyphen and size (This is how a thumbnail file is stored in S3)
     */
    public String getId() {
        return id;
    }

    /**
     * @return the original url of where the file was stored
     */
    public String getOriginalUrl() {
        return originalUrl;
    }

    /**
     * @return actual content as byte array
     */
    public byte[] getContent() {
        return content;
    }

    /**
     * @return Length of the content in number of bytes
     */
    public int getContentLength() {
        if (content == null) {
            return 0;
        }
        return content.length;
    }

    /**
     * @return date when the file was last modified (if available)
     */
    public DateTime getLastModified() {
        if (metaData == null) {
            return null;
        }
        return new DateTime(metaData.getLastModified().getTime());
    }

    /**
     * @return eTag of the content (if available)
     */
    public String getETag() {
        if (metaData == null) {
            return null;
        }
        return metaData.getETag();
    }

}
