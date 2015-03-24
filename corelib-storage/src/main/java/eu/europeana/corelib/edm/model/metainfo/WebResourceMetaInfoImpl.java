package eu.europeana.corelib.edm.model.metainfo;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Property;
import eu.europeana.corelib.definitions.edm.model.metainfo.AudioMetaInfo;
import eu.europeana.corelib.definitions.edm.model.metainfo.ImageMetaInfo;
import eu.europeana.corelib.definitions.edm.model.metainfo.TextMetaInfo;
import eu.europeana.corelib.definitions.edm.model.metainfo.VideoMetaInfo;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * An object which wraps all types of metainfo. It will have always maximum one field which is not null.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
@Entity("WebResourceMetaInfo")
public class WebResourceMetaInfoImpl implements eu.europeana.corelib.definitions.edm.model.metainfo.WebResourceMetaInfo {
    @Id
    @Property("id")
    private final String id;

    /**
     * A class which contains information about an IMAGE document
     */
    private final ImageMetaInfo imageMetaInfo;

    /**
     * A class which contains information about an AUDIO document
     */
    private final AudioMetaInfo audioMetaInfo;

    /**
     * A class which contains information about a VIDEO document
     */
    private final VideoMetaInfo videoMetaInfo;

    /**
     * A class which contains information about a TEXT document
     */
    private final TextMetaInfo textMetaInfo;

    public WebResourceMetaInfoImpl() {
        this.id = null;
        this.imageMetaInfo = null;
        this.audioMetaInfo = null;
        this.videoMetaInfo = null;
        this.textMetaInfo = null;
    }

    public WebResourceMetaInfoImpl(final String recordID, final ImageMetaInfoImpl imageMetaInfo,
                                   final AudioMetaInfo audioMetaInfo, final VideoMetaInfo videoMetaInfo, TextMetaInfo textMetaInfo) {
        this.id = recordID;
        this.imageMetaInfo = imageMetaInfo;
        this.audioMetaInfo = audioMetaInfo;
        this.videoMetaInfo = videoMetaInfo;
        this.textMetaInfo = textMetaInfo;
    }

    public String getId() {
        return id;
    }

    public ImageMetaInfo getImageMetaInfo() {
        return imageMetaInfo;
    }

    public AudioMetaInfo getAudioMetaInfo() {
        return audioMetaInfo;
    }

    public VideoMetaInfo getVideoMetaInfo() {
        return videoMetaInfo;
    }

    public TextMetaInfo getTextMetaInfo() {
        return textMetaInfo;
    }
}
