package eu.europeana.corelib.edm.model.metainfo;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.morphia.annotations.Embedded;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Embedded(useDiscriminator = false)
public class ThreeDMetaInfoImpl implements eu.europeana.corelib.definitions.edm.model.metainfo.ThreeDMetaInfo{

    /**
     * An Internet media type is a standard identifier used on the
     * Internet to indicate the type of data that a file contains.
     */
    private String mimeType;

    /**
     * The size of the file in bytes.
     */
    private Long fileSize;

    public ThreeDMetaInfoImpl(String mimeType, Long fileSize) {
        this.mimeType = mimeType;
        this.fileSize = fileSize;
    }

    public String getMimeType() {
        return null;
    }


    public Long getFileSize() {
        return null;
    }
}
