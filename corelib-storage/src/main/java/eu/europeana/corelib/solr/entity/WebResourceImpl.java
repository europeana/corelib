package eu.europeana.corelib.solr.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import eu.europeana.corelib.definitions.edm.entity.Aggregation;
import eu.europeana.corelib.definitions.edm.entity.WebResource;
import eu.europeana.corelib.definitions.edm.model.metainfo.WebResourceMetaInfo;
import eu.europeana.corelib.definitions.jibx.ColorSpaceType;
import eu.europeana.corelib.definitions.model.Orientation;
import eu.europeana.corelib.edm.model.metainfo.WebResourceMetaInfoImpl;
import eu.europeana.corelib.solr.derived.AttributionSnippet;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.bson.types.ObjectId;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Transient;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

/**
 * @author Yorgos.Mamakis@ kb.nl
 * @see eu.europeana.corelib.definitions.edm.entity.WebResource
 */
@JsonSerialize(include = Inclusion.NON_EMPTY)
@JsonInclude(NON_EMPTY)
@Entity("WebResource")
public class WebResourceImpl implements WebResource {

    @Id
    private ObjectId id = new ObjectId();
    private Map<String, List<String>> webResourceDcRights;
    private Map<String, List<String>> webResourceEdmRights;

    @Indexed(unique = false)
    private String about;

    private Map<String, List<String>> dcDescription;
    private Map<String, List<String>> dcFormat;
    private Map<String, List<String>> dcSource;
    private Map<String, List<String>> dctermsExtent;
    private Map<String, List<String>> dctermsIssued;
    private Map<String, List<String>> dctermsConformsTo;
    private Map<String, List<String>> dctermsCreated;
    private Map<String, List<String>> dctermsIsFormatOf;
    private Map<String, List<String>> dctermsIsPartOf;
    private Map<String, List<String>> dctermsHasPart;
    private Map<String, List<String>> dcCreator;
    private Map<String, List<String>> dcType;
    private String isNextInSequence;
    private String[] owlSameAs;
    private AttributionSnippet attributionSnippet;
    private String textAttributionSnippet;
    private String htmlAttributionSnippet;
    private String[] svcsHasService;
    private String[] dctermsIsReferencedBy;
    private String edmPreview;

    @Transient
    // Jackson JsonIgnore annotation is required for proper serialization by Search & Record API
    @JsonIgnore @com.fasterxml.jackson.annotation.JsonIgnore
    private AggregationImpl parentAggregation;

    @Transient
    // Jackson JsonIgnore annotation is required for proper serialization by Search & Record API
    @JsonIgnore @com.fasterxml.jackson.annotation.JsonIgnore
    private WebResourceMetaInfoImpl webResourceMetaInfo;

    @Override
    public String getAbout() {
        return this.about;
    }

    @Override
    public void setAbout(String about) {
        this.about = about;
    }

    @Override
    public void setId(ObjectId id) {
        this.id = id;
    }

    @Override
    public Map<String, List<String>> getDcDescription() {
        return dcDescription;
    }

    @Override
    public void setDcDescription(Map<String, List<String>> dcDescription) {
        this.dcDescription = dcDescription;
    }

    @Override
    public Map<String, List<String>> getDcFormat() {
        return dcFormat;
    }

    @Override
    public void setDcFormat(Map<String, List<String>> dcFormat) {
        this.dcFormat = dcFormat;
    }

    @Override
    public Map<String, List<String>> getDcSource() {
        return dcSource;
    }

    @Override
    public void setDcSource(Map<String, List<String>> dcSource) {
        this.dcSource = dcSource;
    }

    @Override
    public Map<String, List<String>> getDctermsExtent() {
        return dctermsExtent;
    }

    @Override
    public void setDctermsExtent(Map<String, List<String>> dctermsExtent) {
        this.dctermsExtent = dctermsExtent;
    }

    @Override
    public Map<String, List<String>> getDctermsIssued() {
        return dctermsIssued;
    }

    @Override
    public void setDctermsIssued(Map<String, List<String>> dctermsIssued) {
        this.dctermsIssued = dctermsIssued;
    }

    @Override
    public Map<String, List<String>> getDctermsConformsTo() {
        return dctermsConformsTo;
    }

    @Override
    public void setDctermsConformsTo(Map<String, List<String>> dctermsConformsTo) {
        this.dctermsConformsTo = dctermsConformsTo;
    }

    @Override
    public Map<String, List<String>> getDctermsCreated() {
        return dctermsCreated;
    }


    @Override
    public void setDctermsCreated(Map<String, List<String>> dctermsCreated) {
        this.dctermsCreated = dctermsCreated;
    }

    @Override
    public Map<String, List<String>> getDctermsIsFormatOf() {
        return dctermsIsFormatOf;
    }

    @Override
    public void setDctermsIsFormatOf(Map<String, List<String>> dctermsIsFormatOf) {
        this.dctermsIsFormatOf = dctermsIsFormatOf;
    }

    @Override
    public Map<String, List<String>> getDctermsIsPartOf() {
        return dctermsIsPartOf;
    }

    @Override
    public void setDctermsIsPartOf(Map<String, List<String>> dctermsIsPartOf) {
        this.dctermsIsPartOf = dctermsIsPartOf;
    }

    @Override
    public Map<String, List<String>> getDctermsHasPart() {
        return dctermsHasPart;
    }

    @Override
    public void setDctermsHasPart(Map<String, List<String>> dctermsHasPart) {
        this.dctermsHasPart = dctermsHasPart;
    }

    @Override
    public String getIsNextInSequence() {
        return isNextInSequence;
    }

    @Override
    public void setIsNextInSequence(String isNextInSequence) {
        this.isNextInSequence = isNextInSequence;
    }

    @Override
    public void setWebResourceDcRights(Map<String, List<String>> webResourceDcRights) {
        this.webResourceDcRights = webResourceDcRights;
    }

    @Override
    public void setWebResourceEdmRights(Map<String, List<String>> webResourceEdmRights) {
        this.webResourceEdmRights = webResourceEdmRights;
    }

    @Override
    public Map<String, List<String>> getWebResourceDcRights() {
        return this.webResourceDcRights;
    }

    @Override
    public Map<String, List<String>> getWebResourceEdmRights() {
        return this.webResourceEdmRights;
    }

    @Override
    public ObjectId getId() {
        return this.id;
    }

    @Override
    public Map<String, List<String>> getDcCreator() {
        return this.dcCreator;
    }

    @Override
    public void setDcCreator(Map<String, List<String>> dcCreator) {
        this.dcCreator = dcCreator;
    }

    @Override
    public Map<String, List<String>> getDcType() {
        return this.dcType;
    }

    @Override
    public void setDcType(Map<String, List<String>> dcType) {
        this.dcType = dcType;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o.getClass() == this.getClass()) {
            return this.getAbout().equals(((WebResourceImpl) o).getAbout());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.about.hashCode();
    }

    @Override
    public void setOwlSameAs(String[] owlSameAs) {
        this.owlSameAs = owlSameAs;
    }

    @Override
    public String[] getOwlSameAs() {
        return this.owlSameAs;
    }

    @Override
    public String getRdfType() {
        if (webResourceMetaInfo != null && webResourceMetaInfo.getTextMetaInfo() != null
                && webResourceMetaInfo.getTextMetaInfo().getIsSearchable() != null
                && webResourceMetaInfo.getTextMetaInfo().getIsSearchable()) {
            // TODO set proper value once we defined edm:FullTextResource
            //return "edm:FullTextResource";
        }
        return null;
    }

    @Override
    public String getEdmCodecName() {
        if (webResourceMetaInfo != null && webResourceMetaInfo.getVideoMetaInfo() != null) {
            return webResourceMetaInfo.getVideoMetaInfo().getCodec();
        }
        return null;
    }

    /**
     * Note that we only expose this for internal usage. FileFormat is not part of EDM and should not be serialized!
     * @return
     */
    @Override
    @JsonIgnore @com.fasterxml.jackson.annotation.JsonIgnore
    public String getFileFormat() {
        if (webResourceMetaInfo != null) {
            if (webResourceMetaInfo.getImageMetaInfo() != null) {
                return webResourceMetaInfo.getImageMetaInfo().getFileFormat();
            }
            if (webResourceMetaInfo.getAudioMetaInfo() != null) {
                return webResourceMetaInfo.getAudioMetaInfo().getFileFormat();
            }
        }
        return null;
    }

    @Override
    public String getEbucoreHasMimeType() {
        if (webResourceMetaInfo != null) {
            if (webResourceMetaInfo.getAudioMetaInfo() != null) {
                return webResourceMetaInfo.getAudioMetaInfo().getMimeType();
            }
            if (webResourceMetaInfo.getVideoMetaInfo() != null) {
                return webResourceMetaInfo.getVideoMetaInfo().getMimeType();
            }
            if (webResourceMetaInfo.getImageMetaInfo() != null) {
                return webResourceMetaInfo.getImageMetaInfo().getMimeType();
            }
            if (webResourceMetaInfo.getTextMetaInfo() != null) {
                return webResourceMetaInfo.getTextMetaInfo().getMimeType();
            }
        }
        return null;
    }

    @Override
    public Long getEbucoreFileByteSize() {
        if (webResourceMetaInfo != null) {
            if (webResourceMetaInfo.getAudioMetaInfo() != null) {
                return webResourceMetaInfo.getAudioMetaInfo().getFileSize();
            }
            if (webResourceMetaInfo.getVideoMetaInfo() != null) {
                return webResourceMetaInfo.getVideoMetaInfo().getFileSize();
            }
            if (webResourceMetaInfo.getImageMetaInfo() != null) {
                return webResourceMetaInfo.getImageMetaInfo().getFileSize();
            }
            if (webResourceMetaInfo.getTextMetaInfo() != null) {
                return webResourceMetaInfo.getTextMetaInfo().getFileSize();
            }
        }
        return null;
    }

    @Override
    public Integer getEbucoreAudioChannelNumber() {
        if (webResourceMetaInfo != null && webResourceMetaInfo.getAudioMetaInfo() != null) {
            return webResourceMetaInfo.getAudioMetaInfo().getChannels();
        }
        return null;
    }

    @Override
    public String getEbucoreDuration() {
        if (webResourceMetaInfo != null) {
            if (webResourceMetaInfo.getAudioMetaInfo() != null) {
                return Long.toString(webResourceMetaInfo.getAudioMetaInfo().getDuration());
            }
            if (webResourceMetaInfo.getVideoMetaInfo() != null) {
                return Long.toString(webResourceMetaInfo.getVideoMetaInfo().getDuration());
            }
        }

        return null;
    }

    @Override
    public Integer getEbucoreWidth() {
        if (webResourceMetaInfo != null) {
            if (webResourceMetaInfo.getVideoMetaInfo() != null) {
                return webResourceMetaInfo.getVideoMetaInfo().getWidth();
            }
            if (webResourceMetaInfo.getImageMetaInfo() != null) {
                return webResourceMetaInfo.getImageMetaInfo().getWidth();
            }
        }
        return null;
    }

    @Override
    public Integer getEbucoreHeight() {
        if (webResourceMetaInfo != null) {
            if (webResourceMetaInfo.getVideoMetaInfo() != null) {
                return webResourceMetaInfo.getVideoMetaInfo().getHeight();
            }
            if (webResourceMetaInfo.getImageMetaInfo() != null) {
                return webResourceMetaInfo.getImageMetaInfo().getHeight();
            }
        }
        return null;
    }

    @Override
    public Integer getEdmSpatialResolution() {
        if (webResourceMetaInfo != null && webResourceMetaInfo.getTextMetaInfo() != null) {
            return webResourceMetaInfo.getTextMetaInfo().getResolution();
        }
        return null;
    }

    @Override
    public Integer getEbucoreSampleSize() {
        if (webResourceMetaInfo != null && webResourceMetaInfo.getAudioMetaInfo() != null) {
            return webResourceMetaInfo.getAudioMetaInfo().getBitDepth();
        }
        return null;
    }

    @Override
    public Integer getEbucoreSampleRate() {
        if (webResourceMetaInfo != null && webResourceMetaInfo.getAudioMetaInfo() != null) {
            return webResourceMetaInfo.getAudioMetaInfo().getSampleRate();
        }
        return null;
    }

    @Override
    public Integer getEbucoreBitRate() {
        if (webResourceMetaInfo != null) {
            if (webResourceMetaInfo.getAudioMetaInfo() != null) {
                return webResourceMetaInfo.getAudioMetaInfo().getBitRate();
            }
            if (webResourceMetaInfo.getVideoMetaInfo() != null) {
                return webResourceMetaInfo.getVideoMetaInfo().getBitRate();
            }
        }
        return null;
    }

    @Override
    public String getEdmHasColorSpace() {
        if (webResourceMetaInfo != null && webResourceMetaInfo.getImageMetaInfo() != null) {
            final String colorSpace = webResourceMetaInfo.getImageMetaInfo().getColorSpace();
            if (StringUtils.isNotEmpty(colorSpace)) {
                // we check if it's a known value so the json response will be consistent with .rdf and jsonld (see also
                // EdmWebResourceUtils.setColorSpace())
                ColorSpaceType csType = ColorSpaceType.convert(colorSpace);
                if (csType == null) {
                    LogManager.getLogger(WebResourceImpl.class).warn("Unknown color space '{}' for WebResourceMetaInfo {}",
                            colorSpace, webResourceMetaInfo.getId());
                    return null;
                }
                return csType.xmlValue();
            }
        }
        return null;
    }

    @Override
    public List<String> getEdmComponentColor() {
        if (webResourceMetaInfo != null && webResourceMetaInfo.getImageMetaInfo() != null
                && webResourceMetaInfo.getImageMetaInfo().getColorPalette() != null) {
            return Arrays.asList(webResourceMetaInfo.getImageMetaInfo().getColorPalette());
        }
        return null;
    }

    @Override
    public String getEbucoreOrientation() {
        if (webResourceMetaInfo != null) {
            if (webResourceMetaInfo.getVideoMetaInfo() != null && webResourceMetaInfo.getVideoMetaInfo().getWidth() != null
                    && webResourceMetaInfo.getVideoMetaInfo().getHeight() != null) {
                if (webResourceMetaInfo.getVideoMetaInfo().getHeight() >= webResourceMetaInfo.getVideoMetaInfo().getWidth()) {
                    return Orientation.getValue(Orientation.PORTRAIT);
                } else {
                    return Orientation.getValue(Orientation.LANDSCAPE);
                }
            }
            if (webResourceMetaInfo.getImageMetaInfo() != null && webResourceMetaInfo.getImageMetaInfo().getWidth() != null
                    && webResourceMetaInfo.getImageMetaInfo().getHeight() != null) {
                if (webResourceMetaInfo.getImageMetaInfo().getHeight() >= webResourceMetaInfo.getImageMetaInfo().getWidth()) {
                    return Orientation.getValue(Orientation.PORTRAIT);
                } else {
                    return Orientation.getValue(Orientation.LANDSCAPE);
                }
            }
        }
        return null;
    }

    @Override
    public Double getEbucoreFrameRate() {
        if (webResourceMetaInfo != null && webResourceMetaInfo.getVideoMetaInfo() != null) {
            return webResourceMetaInfo.getVideoMetaInfo().getFrameRate();
        }
        return null;
    }

    @Override
    public String getEdmPreview() {
        return edmPreview;
    }

    @Override
    public void setEdmPreview(String edmPreview) {
        this.edmPreview = edmPreview;
    }

    @Override
    public String[] getSvcsHasService() {
        return svcsHasService;
    }

    @Override
    public void setSvcsHasService(String[] svcsHasService) {
        this.svcsHasService = svcsHasService;
    }

    @Override
    public String[] getDctermsIsReferencedBy() {
        return dctermsIsReferencedBy;
    }

    @Override
    public void setDctermsIsReferencedBy(String[] dctermsIsReferencedBy) {
        this.dctermsIsReferencedBy = dctermsIsReferencedBy;
    }

    public void setWebResourceMetaInfo(WebResourceMetaInfoImpl wrMetaInfo) {
        this.webResourceMetaInfo = wrMetaInfo;
    }

    @JsonIgnore @com.fasterxml.jackson.annotation.JsonIgnore
    public WebResourceMetaInfo getWebResourceMetaInfo() {
        return webResourceMetaInfo;
    }

    public void initAttributionSnippet() {
        attributionSnippet = new AttributionSnippet(this);
    }

    public String getTextAttributionSnippet() {
        return attributionSnippet.getTextSnippet();
    }

    public String getHtmlAttributionSnippet() {
        return attributionSnippet.getHtmlSnippet();
    }

    /**
     * Sets encapsulating aggregation of this webresource (not made available through the interface
     * bean); used in the Attributionsnippet alone
     */
    public void setParentAggregation(AggregationImpl parentAggregation) {
        this.parentAggregation = parentAggregation;
    }

    /**
     * Encapsulating aggregation of this webresource (not made available through the interface bean);
     * used in the Attributionsnippet alone
     *
     * @return parentAggregation
     */
    public Aggregation getParentAggregation() {
        return this.parentAggregation;
    }
}
