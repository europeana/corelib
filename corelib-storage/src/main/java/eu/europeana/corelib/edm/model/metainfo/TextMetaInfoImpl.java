package eu.europeana.corelib.edm.model.metainfo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.mongodb.morphia.annotations.Entity;

@JsonInclude(Include.NON_EMPTY)
@Entity("TextMetaInfo")
public class TextMetaInfoImpl implements eu.europeana.corelib.definitions.edm.model.metainfo.TextMetaInfo {

    /**
     * An Internet media type is a standard identifier used on the
     * Internet to indicate the type of data that a file contains.
     */
    private String mimeType;

    /**
     * The size of the file in bytes.
     */
    private Long fileSize;

    /**
     * The number of lines or resolution height e.g. 1080, 720, etc.
     * */
    private Integer resolution;

    /**
     * Shows if the file contains only images (returns false) or any text (returns true)
     */
    private Boolean isSearchable;

    /**
     * Contains the rdf:resource attribute value of the rdf:type
     */
    private String rdfType;

    public TextMetaInfoImpl(final String mimeType, final Long fileSize,
                            final Integer resolution, final Boolean isSearchable, final String rdfType) {
        this.mimeType = mimeType;
        this.fileSize = fileSize;
        this.resolution = resolution;
        this.isSearchable = isSearchable;
        this.rdfType = rdfType;
    }

    public TextMetaInfoImpl() {
        this.mimeType = null;
        this.fileSize = null;
        this.resolution = null;
        this.isSearchable = null;
    }

    public String getMimeType() {
        return mimeType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public Integer getResolution() {
        return resolution;
    }

    public Boolean getIsSearchable() {
        return isSearchable;
    }

    public String getRdfType() {
        return rdfType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public void setResolution(Integer resolution) {
        this.resolution = resolution;
    }

    public void setIsSearchable(Boolean isSearchable) {
        this.isSearchable = isSearchable;
    }

    public void setRdfType(String rdfType) {
        this.rdfType = rdfType;
    }
}
