package eu.europeana.corelib.solr.model.metainfo;

import java.io.Serializable;

/**
 * A class which contains information about an AUDIO document
 */
public class AudioMetaInfo implements Serializable {

    /**
     * The number of samples of a sound that are taken per second to represent the event digitally.
     */
    private final Integer sampleRate;

    /**
     * The number of bits that are conveyed or processed per unit of time.
     */
    private final Integer bitRate;

    /**
     * The audio document duration in milliseconds.
     */
    private final Long duration;

    /**
     * An Internet media type is a standard identifier used on the
     * Internet to indicate the type of data that a file contains.
     */
    private final String mimeType;

    /**
     * A file format is a standard way that information is encoded for storage in a computer file.
     */
    private final String fileFormat;

    /**
     * The size of the file in bytes.
     */
    private final Long fileSize;

    /**
     * Either number of channels (1-7?) or mono/stereo.
     */
    private final Integer channels;

    /**
     * The number of bits of information in each sample.
     */
    private final Integer bitDepth;

    public AudioMetaInfo() {
        this.sampleRate = null;
        this.bitRate = null;
        this.duration = null;
        this.mimeType = null;
        this.fileFormat = null;
        this.fileSize = null;
        this.channels = null;
        this.bitDepth = null;
    }

    public AudioMetaInfo(final Integer sampleRate, final Integer bitRate,
                         final Long duration, final String mimeType, final String fileFormat,
                         final Long fileSize, final Integer channels, final Integer bitDepth) {
        this.sampleRate = sampleRate;
        this.bitRate = bitRate;
        this.duration = duration;
        this.mimeType = mimeType;
        this.fileFormat = fileFormat;
        this.fileSize = fileSize;
        this.channels = channels;
        this.bitDepth = bitDepth;
    }

    public Integer getSampleRate() {
        return sampleRate;
    }

    public Integer getBitRate() {
        return bitRate;
    }

    public Long getDuration() {
        return duration;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public Integer getChannels() {
        return channels;
    }

    public Integer getBitDepth() {
        return bitDepth;
    }
}
