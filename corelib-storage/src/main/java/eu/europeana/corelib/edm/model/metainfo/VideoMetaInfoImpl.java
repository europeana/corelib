package eu.europeana.corelib.edm.model.metainfo;

import org.mongodb.morphia.annotations.Entity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.io.Serializable;

/**
 * A class which contains information about a VIDEO document
 */
@JsonInclude(Include.NON_EMPTY)
@Entity("VideoMetaInfo")
public class VideoMetaInfoImpl implements eu.europeana.corelib.definitions.edm.model.metainfo.VideoMetaInfo {

    /**
     * The width of frames in pixels.
     */
    private Integer width;

    /**
     * The height of frames in pixels.
     */
    private Integer height;

    /**
     * The video document duration in milliseconds.
     */
    private Long duration;

    /**
     * An Internet media type is a standard identifier used on the
     * Internet to indicate the type of data that a file contains.
     */
    private String mimeType;

    /**
     *  also known as frame frequency and frames per second (FPS), is the frequency (rate)
     *  at which an imaging device produces unique consecutive images called frames.
     */
    private Double frameRate;

    /**
     * The size of the file in bytes.
     */
    private Long fileSize;

    /**
     * The Codec the video is encoded with.
     */
    private String codec;

    /**
     * Width x Height.
     * Note that we do not publish this f since we already have width and height
     */
    private String resolution;

    /**
     * Number of bits that are transmitted over a set length of time.
     */
    private Integer bitRate;

    public VideoMetaInfoImpl() {
        this.width = null;
        this.height = null;
        this.duration = null;
        this.mimeType = null;
        this.frameRate = null;
        this.fileSize = null;
        this.codec = null;
        this.resolution = null;
        this.bitRate = null;
    }

    public VideoMetaInfoImpl(final Integer width, final Integer height, final Long duration, final String mimeType,
                             final Double frameRate, final Long fileSize, final String codec,
                             final String resolution, Integer bitRate) {
        this.width = width;
        this.height = height;
        this.duration = duration;
        this.mimeType = mimeType;
        this.frameRate = frameRate;
        this.fileSize = fileSize;
        this.codec = codec;
        this.resolution = resolution;
        this.bitRate = bitRate;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public Long getDuration() {
        return duration;
    }

    public String getMimeType() {
        return mimeType;
    }

    public Double getFrameRate() {
        return frameRate;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public String getCodec() {
        return codec;
    }

    /**
     * String containing width and height.
     * At the moment it is not used (not published) because we already have width and height.
     * @return
     */
    public String getResolution() {
        return resolution;
    }

    public Integer getBitRate() {
        return bitRate;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public void setFrameRate(Double frameRate) {
        this.frameRate = frameRate;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public void setCodec(String codec) {
        this.codec = codec;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public void setBitRate(Integer bitRate) {
        this.bitRate = bitRate;
    }
}
