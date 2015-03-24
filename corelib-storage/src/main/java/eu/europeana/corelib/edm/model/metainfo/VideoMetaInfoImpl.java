package eu.europeana.corelib.edm.model.metainfo;

import com.google.code.morphia.annotations.Entity;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

/**
 * A class which contains information about a VIDEO document
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
@Entity("VideoMetaInfo")
public class VideoMetaInfoImpl implements Serializable, eu.europeana.corelib.definitions.edm.model.metainfo.VideoMetaInfo {

    /**
     * The width of frames in pixels.
     */
    private final Integer width;

    /**
     * The height of frames in pixels.
     */
    private final Integer height;

    /**
     * The video document duration in milliseconds.
     */
    private final Long duration;

    /**
     * An Internet media type is a standard identifier used on the
     * Internet to indicate the type of data that a file contains.
     */
    private final String mimeType;

    /**
     *  also known as frame frequency and frames per second (FPS), is the frequency (rate)
     *  at which an imaging device produces unique consecutive images called frames.
     */
    private final Double frameRate;

    /**
     * The size of the file in bytes.
     */
    private final Long fileSize;

    /**
     * The Codec the video is encoded with.
     */
    private final String codec;

    /**
     * Width x Height
     */
    private final String resolution;

    /**
     * Number of bits that are transmitted over a set length of time.
     */
    private final Integer bitRate;

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

    public String getResolution() {
        return resolution;
    }

    public Integer getBitRate() {
        return bitRate;
    }
}
