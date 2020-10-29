package eu.europeana.corelib.edm.utils;

import eu.europeana.corelib.definitions.edm.entity.WebResource;
import eu.europeana.metis.schema.jibx.*;
import eu.europeana.corelib.definitions.model.Orientation;
import eu.europeana.corelib.solr.entity.AggregationImpl;
import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for converting WebResource objects to JIBX WebResourceType
 * @author Patrick Ehlert
 * Created on 25-04-2018
 */
public class EdmWebResourceUtils {

    private static final  String LONGDATATYPE = "http://www.w3.org/2001/XMLSchema#long";
    private static final String HEXBINARYDATATYPE = "http://www.w3.org/2001/XMLSchema#hexBinary";
    private static final String STRINGDATATYPE = "http://www.w3.org/2001/XMLSchema#string";
    private static final String INTEGERDATATYPE = "http://www.w3.org/2001/XMLSchema#integer";
    private static final String NONNEGATIVEINTEGERDATATYPE = "http://www.w3.org/2001/XMLSchema#nonNegativeInteger";
    private static final String DOUBLEDATATYPE = "http://www.w3.org/2001/XMLSchema#double";

    private EdmWebResourceUtils() {
        //empty constructor to prevent initialization
    }

    /**
     * Add all webresources in the provided aggregation, to the provide RDF object
     * @param rdf
     * @param aggr
     * @param preserveIdentifiers
     */
    public static void createWebResources(RDF rdf, AggregationImpl aggr,
        boolean preserveIdentifiers) {
        List<WebResourceType> webResources = new ArrayList<>();
        for (WebResource wr : aggr.getWebResources()) {
            WebResourceType wResource = new WebResourceType();
            wResource.setAbout(wr.getAbout());
            EdmUtils.addAsList(wResource, ConformsTo.class, wr.getDctermsConformsTo());
            EdmUtils.addAsList(wResource, Created.class, wr.getDctermsCreated());
            EdmUtils.addAsList(wResource, Creator.class, wr.getDcCreator());
            EdmUtils.addAsList(wResource, Description.class, wr.getDcDescription());
            EdmUtils.addAsList(wResource, Extent.class, wr.getDctermsExtent());
            EdmUtils.addAsList(wResource, Format.class, wr.getDcFormat());
            EdmUtils.addAsList(wResource, HasPart.class, wr.getDctermsHasPart());
            EdmUtils.addAsList(wResource, IsFormatOf.class, wr.getDctermsIsFormatOf());
            EdmUtils.addAsList(wResource, IsPartOf.class, wr.getDctermsIsPartOf());
            EdmUtils.addAsObject(wResource, IsNextInSequence.class, wr.getIsNextInSequence(), preserveIdentifiers);
            EdmUtils.addAsList(wResource, Issued.class, wr.getDctermsIssued());
            EdmUtils.addAsList(wResource, Rights.class, wr.getWebResourceDcRights());
            EdmUtils.addAsList(wResource, Type.class, wr.getDcType());
            EdmUtils.addAsObject(wResource, Rights1.class, wr.getWebResourceEdmRights());
            EdmUtils.addAsList(wResource, Source.class, wr.getDcSource());
            EdmUtils.addAsList(wResource, SameAs.class, wr.getOwlSameAs());
            EdmUtils.addAsObject(wResource, Type1.class, wr.getRdfType(), preserveIdentifiers);

            setRdfType(wr, wResource);
            setCodecName(wr, wResource);
            setHasMimeType(wr, wResource);
            setFileByteSize(wr, wResource);
            setDuration(wr, wResource);
            setWidth(wr, wResource);
            setHeight(wr, wResource);
            setSpatialResolution(wr, wResource);
            setAudioChannelNumber(wr, wResource);
            setSampleRate(wr, wResource);
            setSampleSize(wr, wResource);
            setBitRate(wr, wResource);
            setFrameRate(wr, wResource);
            setColorSpace(wr, wResource);
            setOrientation(wr, wResource);
            setComponentColorList(wr, wResource);
            setHasServiceList(wr, wResource);

            EdmUtils.addAsObject(wResource,Preview.class,wr.getEdmPreview(), preserveIdentifiers);
            //addAsList(wResource, IsReferencedBy.class, wr.getDctermsIsReferencedBy());

            setIsReferencedBy(wr, wResource);
            webResources.add(wResource);
        }

        rdf.setWebResourceList(webResources);
    }

    private static void setRdfType(WebResource wr, WebResourceType wResource) {
        if (wr.getRdfType() != null) {
            Type1 type = new Type1();
            type.setResource(wr.getRdfType());
            wResource.setType(type);
        }
    }

    private static void setIsReferencedBy(WebResource wr, WebResourceType wResource) {
        if (wr.getDctermsIsReferencedBy() != null) {
            List<IsReferencedBy> hsList = new ArrayList<>();
            for (String isRef : wr.getDctermsIsReferencedBy()) {
                IsReferencedBy hs = new IsReferencedBy();
                if (EdmUtils.isUri(isRef)) {
                    ResourceOrLiteralType.Resource res = new ResourceOrLiteralType.Resource();
                    res.setResource(isRef);
                    hs.setResource(res);
                    hs.setString("");
                } else {
                    hs.setString(isRef);
                    hs.setResource(null);
                }
                hs.setLang(null);
                hsList.add(hs);

            }
            wResource.setIsReferencedByList(hsList);
        }
    }

    private static void setHasServiceList(WebResource wr, WebResourceType wResource) {
        if(wr.getSvcsHasService()!=null){
            List<HasService> hsList = new ArrayList<>();
            for(String hasService:wr.getSvcsHasService()){
                HasService hs = new HasService();
                hs.setResource(hasService);
                hsList.add(hs);

            }
            wResource.setHasServiceList(hsList);
        }
    }

    private static void setComponentColorList(WebResource wr, WebResourceType wResource) {
        if (wr.getEdmComponentColor() != null && !wr.getEdmComponentColor().isEmpty()) {
            List<HexBinaryType> componentColors = new ArrayList<>();
            for (String componentColor : wr.getEdmComponentColor()) {
                HexBinaryType type = new HexBinaryType();
                type.setString(componentColor);
                type.setDatatype(HEXBINARYDATATYPE);
                componentColors.add(type);
            }
            wResource.setComponentColorList(componentColors);
        }
    }

    private static void setOrientation(WebResource wr, WebResourceType wResource) {
        if (wr.getEbucoreOrientation() != null) {
            OrientationType orientation = new OrientationType();

            if (StringUtils.equals(wr.getEbucoreOrientation(), Orientation.getValue(Orientation.LANDSCAPE))) {
                orientation.setString("landscape");
            } else {
                orientation.setString("portrait");
            }
            orientation.setDatatype(STRINGDATATYPE);
            wResource.setOrientation(orientation);
        }
    }

    private static void setColorSpace(WebResource wr, WebResourceType wResource) {
        String colorSpace = wr.getEdmHasColorSpace();
        if (colorSpace != null) {
            ColorSpaceType csType = ColorSpaceType.convert(colorSpace);
            HasColorSpace hasColorSpace = new HasColorSpace();
            hasColorSpace.setHasColorSpace(csType);
            wResource.setHasColorSpace(hasColorSpace);
        }
    }

    private static void setFrameRate(WebResource wr, WebResourceType wResource) {
        if (wr.getEbucoreFrameRate() != null) {
            DoubleType frameRate = new DoubleType();
            frameRate.setDouble(wr.getEbucoreFrameRate());
            frameRate.setDatatype(DOUBLEDATATYPE);
            wResource.setFrameRate(frameRate);
        }
    }

    private static void setBitRate(WebResource wr, WebResourceType wResource) {
        if (wr.getEbucoreBitRate() != null) {
            BitRate bitRate = new BitRate();
            bitRate.setInteger(new BigInteger(Integer.toString(wr.getEbucoreBitRate())));
            bitRate.setDatatype(NONNEGATIVEINTEGERDATATYPE);
            wResource.setBitRate(bitRate);
        }
    }

    private static void setSampleSize(WebResource wr, WebResourceType wResource) {
        if (wr.getEbucoreSampleSize() != null) {
            SampleSize sampleSize = new SampleSize();
            sampleSize.setLong(wr.getEbucoreSampleSize());
            sampleSize.setDatatype(INTEGERDATATYPE);
            wResource.setSampleSize(sampleSize);
        }
    }

    private static void setSampleRate(WebResource wr, WebResourceType wResource) {
        if (wr.getEbucoreSampleRate() != null) {
            SampleRate sampleRate = new SampleRate();
            sampleRate.setLong(wr.getEbucoreSampleRate());
            sampleRate.setDatatype(INTEGERDATATYPE);
            wResource.setSampleRate(sampleRate);
        }
    }

    private static void setAudioChannelNumber(WebResource wr, WebResourceType wResource) {
        if (wr.getEbucoreAudioChannelNumber() != null) {
            AudioChannelNumber acn = new AudioChannelNumber();
            acn.setInteger(BigInteger.valueOf(wr.getEbucoreAudioChannelNumber()));
            acn.setDatatype(NONNEGATIVEINTEGERDATATYPE);
            wResource.setAudioChannelNumber(acn);
        }
    }

    private static void setSpatialResolution(WebResource wr, WebResourceType wResource) {
        if (wr.getEdmSpatialResolution() != null) {
            SpatialResolution resolution = new SpatialResolution();
            resolution.setDatatype(NONNEGATIVEINTEGERDATATYPE);
            resolution.setInteger(new BigInteger(Integer.toString(wr.getEdmSpatialResolution())));
            wResource.setSpatialResolution(resolution);
        }
    }

    private static void setHeight(WebResource wr, WebResourceType wResource) {
        if (wr.getEbucoreHeight() != null) {
            Height height = new Height();
            height.setLong(wr.getEbucoreHeight());
            height.setDatatype(INTEGERDATATYPE);
            wResource.setHeight(height);
        }
    }

    private static void setWidth(WebResource wr, WebResourceType wResource) {
        if (wr.getEbucoreWidth() != null) {
            Width width = new Width();
            width.setLong(wr.getEbucoreWidth());
            width.setDatatype(INTEGERDATATYPE);
            wResource.setWidth(width);
        }
    }

    private static void setDuration(WebResource wr, WebResourceType wResource) {
        if (wr.getEbucoreDuration() != null) {
            Duration duration = new Duration();
            duration.setDuration(wr.getEbucoreDuration());
            wResource.setDuration(duration);
        }
    }

    private static void setFileByteSize(WebResource wr, WebResourceType wResource) {
        if (wr.getEbucoreFileByteSize() != null) {
            LongType fileByteSize = new LongType();
            fileByteSize.setLong(wr.getEbucoreFileByteSize());
            fileByteSize.setDatatype(LONGDATATYPE);
            wResource.setFileByteSize(fileByteSize);
        }
    }

    private static void setHasMimeType(WebResource wr, WebResourceType wResource) {
        if (wr.getEbucoreHasMimeType() != null) {
            HasMimeType hasMimeType = new HasMimeType();
            hasMimeType.setHasMimeType(wr.getEbucoreHasMimeType());
            wResource.setHasMimeType(hasMimeType);
        }
    }

    private static void setCodecName(WebResource wr, WebResourceType wResource) {
        if (wr.getEdmCodecName() != null) {
            CodecName codecName = new CodecName();
            codecName.setCodecName(wr.getEdmCodecName());
            wResource.setCodecName(codecName);
        }
    }
}
