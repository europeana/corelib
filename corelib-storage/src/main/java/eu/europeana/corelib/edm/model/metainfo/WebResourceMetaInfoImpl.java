package eu.europeana.corelib.edm.model.metainfo;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * An object which wraps all types of metainfo. It will have always maximum one info field which is not null.
 */
@JsonInclude(Include.NON_EMPTY)
@Entity("WebResourceMetaInfo")
public class WebResourceMetaInfoImpl implements eu.europeana.corelib.definitions.edm.model.metainfo.WebResourceMetaInfo {
    @Id
    @Property("id")
    private final String id;

    /**
     * A class which contains information about an IMAGE document
     */
    private ImageMetaInfoImpl imageMetaInfo;

    /**
     * A class which contains information about an AUDIO document
     */
    private AudioMetaInfoImpl audioMetaInfo;

    /**
     * A class which contains information about a VIDEO document
     */
    private VideoMetaInfoImpl videoMetaInfo;

    /**
     * A class which contains information about a TEXT document
     */
    private TextMetaInfoImpl textMetaInfo;

    public WebResourceMetaInfoImpl() {
        this.id = null;
        this.imageMetaInfo = null;
        this.audioMetaInfo = null;
        this.videoMetaInfo = null;
        this.textMetaInfo = null;
    }

    public WebResourceMetaInfoImpl(final String recordID, final ImageMetaInfoImpl imageMetaInfo,
                                   final AudioMetaInfoImpl audioMetaInfo, final VideoMetaInfoImpl videoMetaInfo, TextMetaInfoImpl textMetaInfo) {
        this.id = recordID;
        this.imageMetaInfo = imageMetaInfo;
        this.audioMetaInfo = audioMetaInfo;
        this.videoMetaInfo = videoMetaInfo;
        this.textMetaInfo = textMetaInfo;
    }

    public String getId() {
        return id;
    }

    public ImageMetaInfoImpl getImageMetaInfo() {
        return imageMetaInfo;
    }

    public AudioMetaInfoImpl getAudioMetaInfo() {
        return audioMetaInfo;
    }

    public VideoMetaInfoImpl getVideoMetaInfo() {
        return videoMetaInfo;
    }

    public TextMetaInfoImpl getTextMetaInfo() {
        return textMetaInfo;
    }

    public void setImageMetaInfo(ImageMetaInfoImpl imageMetaInfo) {
        this.imageMetaInfo = imageMetaInfo;
    }

    public void setAudioMetaInfo(AudioMetaInfoImpl audioMetaInfo) {
        this.audioMetaInfo = audioMetaInfo;
    }

    public void setVideoMetaInfo(VideoMetaInfoImpl videoMetaInfo) {
        this.videoMetaInfo = videoMetaInfo;
    }

    public void setTextMetaInfo(TextMetaInfoImpl textMetaInfo) {
        this.textMetaInfo = textMetaInfo;
    }
}
