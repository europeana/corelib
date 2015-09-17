package eu.europeana.corelib.domain;

import org.joda.time.DateTime;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 The public language
 The domain model exposed by the media client through it's public interface.
 */
public class MediaFile {

    /**
     *  The md5 of the original url
     */
    private final String id;

    /**
     *  The process that created it.
     */
    private final String source;

    /**
     * Length of the byte array, which is the actual content of a file.
     */
    private final Long length;

    /**
     * File name.
     */
    private final String name;

    /**
     * Aliases from the metadata.
     */
    private final List<String> aliases;

    /**
     * The observed MD5 during transfer.
     */
    private final String contentMd5;

    /**
     * The source url of the file.
     */
    private final String originalUrl;

    /**
     * Time of saving the file to the DB.
     */
    private final DateTime createdAt;

    /**
     * The content of the file in a byte array format.
     */
    private final byte[] content;

    /**
     * The versionNumber of the file.
     */
    private final Integer versionNumber;

    /**
     * The content type.
     */
    private final String contentType;

    /**
     * The thumbnails metadata: height, width, ...
     */
    private final Map<String,String> metaData;

    /**
     * The size of the thumbnail
     */
    private final Integer size;

    public MediaFile(String source, String name, List<String> aliases, String contentMd5,
                     String originalUrl, DateTime createdAt, byte[] content, Integer versionNumber, String contentType,
                     Map<String, String> metaData, int size) {
        final MessageDigest messageDigest;
        String temp;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update((originalUrl + size).getBytes());
            final byte[] resultByte = messageDigest.digest();
            StringBuffer sb = new StringBuffer();
            for (byte aResultByte : resultByte) {
                sb.append(Integer.toString((aResultByte & 0xff) + 0x100, 16).substring(1));
            }
            temp = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            temp = originalUrl;
        }

        this.id = temp;
        this.source = source;
        this.length = (long) content.length;
        this.name = name;
        this.aliases = aliases;
        this.contentMd5 = contentMd5;
        this.originalUrl = originalUrl;
        this.createdAt = createdAt;
        this.content = content;
        this.versionNumber = versionNumber;
        this.contentType = contentType;
        this.metaData = (null == metaData) ? new HashMap<String, String>() : metaData;
        this.size = size;
    }

    public MediaFile(String id, String source, String name, List<String> aliases, String contentMd5,
                     String originalUrl, DateTime createdAt, byte[] content, Integer versionNumber, String contentType,
                     Map<String, String> metaData, int size) {
        this.id = id;
        this.source = source;
        this.length = (long) content.length;
        this.name = name;
        this.aliases = aliases;
        this.contentMd5 = contentMd5;
        this.originalUrl = originalUrl;
        this.createdAt = createdAt;
        this.content = content;
        this.versionNumber = versionNumber;
        this.contentType = contentType;
        this.metaData = metaData;
        this.size = size;
    }

    public String getId() {
        return id;
    }

    public String getSource() {
        return source;
    }

    public Long getLength() {
        return length;
    }

    public String getName() {
        return name;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public String getContentMd5() {
        return contentMd5;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public byte[] getContent() {
        return content;
    }

    public Integer getVersionNumber() {
        return versionNumber;
    }

    public String getContentType() {
        return contentType;
    }

    public Map<String, String> getMetaData() {
        return metaData;
    }

    public Integer getSize() {
        return size;
    }

}
