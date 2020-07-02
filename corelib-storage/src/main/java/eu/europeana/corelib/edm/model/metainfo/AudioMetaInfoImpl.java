package eu.europeana.corelib.edm.model.metainfo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import dev.morphia.annotations.Entity;


/**
 * A class which contains information about an AUDIO document
 */
@JsonInclude(Include.NON_EMPTY)
@Entity("AudioMetaInfo")
public class AudioMetaInfoImpl implements eu.europeana.corelib.definitions.edm.model.metainfo.AudioMetaInfo {

    /**
     * The number of samples of a sound that are taken per second to represent the event digitally.
     */
    private Integer sampleRate;

    /**
     * The number of bits that are conveyed or processed per unit of time.
     */
    private Integer bitRate;

    /**
     * The audio document duration in milliseconds.
     */
    private Long duration;

    /**
     * An Internet media type is a standard identifier used on the
     * Internet to indicate the type of data that a file contains.
     */
    private String mimeType;

    /**
     * A file format is a standard way that information is encoded for storage in a computer file.
     */
    private String fileFormat;

    /**
     * The size of the file in bytes.
     */
    private Long fileSize;

    /**
     * Either number of channels (1-7?) or mono/stereo.
     */
    private Integer channels;

    /**
     * The number of bits of information in each sample.
     */
    private Integer bitDepth;

    /**
     * The Codec the audio is encoded with.
     */
    private String codec;

    public AudioMetaInfoImpl() {
        this.sampleRate = null;
        this.bitRate = null;
        this.duration = null;
        this.mimeType = null;
        this.fileFormat = null;
        this.fileSize = null;
        this.channels = null;
        this.bitDepth = null;
        this.codec = null;
    }

    public AudioMetaInfoImpl(final Integer sampleRate, final Integer bitRate,
                             final Long duration, final String mimeType, final String fileFormat,
                             final Long fileSize, final Integer channels, final Integer bitDepth, final String codec) {
        this.sampleRate = sampleRate;
        this.bitRate = bitRate;
        this.duration = duration;
        this.mimeType = mimeType;
        this.fileFormat = fileFormat;
        this.fileSize = fileSize;
        this.channels = channels;
        this.bitDepth = bitDepth;
        this.codec = codec;
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

    public String getCodec() { return codec; }

    public void setSampleRate(Integer sampleRate) {
        this.sampleRate = sampleRate;
    }

    public void setBitRate(Integer bitRate) {
        this.bitRate = bitRate;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public void setChannels(Integer channels) {
        this.channels = channels;
    }

    public void setBitDepth(Integer bitDepth) {
        this.bitDepth = bitDepth;
    }

    public void setCodec(String codec) {
        this.codec = codec;
    }

}
