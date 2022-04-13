
package eu.europeana.corelib.definitions.jibx;

import java.util.ArrayList;
import java.util.List;

/** 
 * Base class for WebResource implementations
 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:ns1="http://purl.org/dc/elements/1.1/" xmlns:ns2="http://purl.org/dc/terms/" xmlns:ns3="http://www.ebu.ch/metadata/ontologies/ebucore/ebucore#" xmlns:ns4="http://www.w3.org/2002/07/owl#" xmlns:ns5="http://rdfs.org/sioc/services#" xmlns:ns6="http://www.europeana.eu/schemas/edm/" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="WebResourceType">
 *   &lt;xs:complexContent>
 *     &lt;xs:extension base="ns:AboutType">
 *       &lt;xs:sequence>
 *         &lt;xs:element ref="ns1:creator" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns1:description" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns1:format" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns1:rights" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns1:source" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns1:type" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns2:conformsTo" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns2:created" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns2:extent" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns2:hasPart" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns2:isFormatOf" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns2:isPartOf" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns2:issued" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns6:isNextInSequence" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns6:rights" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns4:sameAs" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns:type" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns6:codecName" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns3:hasMimeType" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns3:fileByteSize" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns3:duration" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns3:width" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns3:height" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns6:spatialResolution" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns3:sampleSize" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns3:sampleRate" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns3:bitRate" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns3:frameRate" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns6:hasColorSpace" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns6:componentColor" minOccurs="0" maxOccurs="6"/>
 *         &lt;xs:element ref="ns3:orientation" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns3:audioChannelNumber" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns2:isReferencedBy" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns6:preview" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns5:has_service" minOccurs="0" maxOccurs="unbounded"/>
 *       &lt;/xs:sequence>
 *     &lt;/xs:extension>
 *   &lt;/xs:complexContent>
 * &lt;/xs:complexType>
 * </pre>
 */
public class WebResourceType extends AboutType
{
    private List<Creator> creatorList = new ArrayList<Creator>();
    private List<Description> descriptionList = new ArrayList<Description>();
    private List<Format> formatList = new ArrayList<Format>();
    private List<Rights> rightList = new ArrayList<Rights>();
    private List<Source> sourceList = new ArrayList<Source>();
    private List<Type> typeList = new ArrayList<Type>();
    private List<ConformsTo> conformsToList = new ArrayList<ConformsTo>();
    private List<Created> createdList = new ArrayList<Created>();
    private List<Extent> extentList = new ArrayList<Extent>();
    private List<HasPart> hasPartList = new ArrayList<HasPart>();
    private List<IsFormatOf> isFormatOfList = new ArrayList<IsFormatOf>();
    private List<IsPartOf> isPartOfList = new ArrayList<IsPartOf>();
    private List<Issued> issuedList = new ArrayList<Issued>();
    private IsNextInSequence isNextInSequence;
    private Rights1 rights;
    private List<SameAs> sameAList = new ArrayList<SameAs>();
    private Type1 type;
    private CodecName codecName;
    private HasMimeType hasMimeType;
    private LongType fileByteSize;
    private Duration duration;
    private Width width;
    private Height height;
    private SpatialResolution spatialResolution;
    private SampleSize sampleSize;
    private SampleRate sampleRate;
    private BitRate bitRate;
    private DoubleType frameRate;
    private HasColorSpace hasColorSpace;
    private List<HexBinaryType> componentColorList = new ArrayList<HexBinaryType>();
    private OrientationType orientation;
    private AudioChannelNumber audioChannelNumber;
    private List<IsReferencedBy> isReferencedByList = new ArrayList<IsReferencedBy>();
    private Preview preview;
    private List<HasService> hasServiceList = new ArrayList<HasService>();

    /** 
     * Get the list of 'creator' element items.
     * 
     * @return list
     */
    public List<Creator> getCreatorList() {
        return creatorList;
    }

    /** 
     * Set the list of 'creator' element items.
     * 
     * @param list
     */
    public void setCreatorList(List<Creator> list) {
        creatorList = list;
    }

    /** 
     * Get the list of 'description' element items.
     * 
     * @return list
     */
    public List<Description> getDescriptionList() {
        return descriptionList;
    }

    /** 
     * Set the list of 'description' element items.
     * 
     * @param list
     */
    public void setDescriptionList(List<Description> list) {
        descriptionList = list;
    }

    /** 
     * Get the list of 'format' element items.
     * 
     * @return list
     */
    public List<Format> getFormatList() {
        return formatList;
    }

    /** 
     * Set the list of 'format' element items.
     * 
     * @param list
     */
    public void setFormatList(List<Format> list) {
        formatList = list;
    }

    /** 
     * Get the list of 'rights' element items.
     * 
     * @return list
     */
    public List<Rights> getRightList() {
        return rightList;
    }

    /** 
     * Set the list of 'rights' element items.
     * 
     * @param list
     */
    public void setRightList(List<Rights> list) {
        rightList = list;
    }

    /** 
     * Get the list of 'source' element items.
     * 
     * @return list
     */
    public List<Source> getSourceList() {
        return sourceList;
    }

    /** 
     * Set the list of 'source' element items.
     * 
     * @param list
     */
    public void setSourceList(List<Source> list) {
        sourceList = list;
    }

    /** 
     * Get the list of 'type' element items.
     * 
     * @return list
     */
    public List<Type> getTypeList() {
        return typeList;
    }

    /** 
     * Set the list of 'type' element items.
     * 
     * @param list
     */
    public void setTypeList(List<Type> list) {
        typeList = list;
    }

    /** 
     * Get the list of 'conformsTo' element items.
     * 
     * @return list
     */
    public List<ConformsTo> getConformsToList() {
        return conformsToList;
    }

    /** 
     * Set the list of 'conformsTo' element items.
     * 
     * @param list
     */
    public void setConformsToList(List<ConformsTo> list) {
        conformsToList = list;
    }

    /** 
     * Get the list of 'created' element items.
     * 
     * @return list
     */
    public List<Created> getCreatedList() {
        return createdList;
    }

    /** 
     * Set the list of 'created' element items.
     * 
     * @param list
     */
    public void setCreatedList(List<Created> list) {
        createdList = list;
    }

    /** 
     * Get the list of 'extent' element items.
     * 
     * @return list
     */
    public List<Extent> getExtentList() {
        return extentList;
    }

    /** 
     * Set the list of 'extent' element items.
     * 
     * @param list
     */
    public void setExtentList(List<Extent> list) {
        extentList = list;
    }

    /** 
     * Get the list of 'hasPart' element items.
     * 
     * @return list
     */
    public List<HasPart> getHasPartList() {
        return hasPartList;
    }

    /** 
     * Set the list of 'hasPart' element items.
     * 
     * @param list
     */
    public void setHasPartList(List<HasPart> list) {
        hasPartList = list;
    }

    /** 
     * Get the list of 'isFormatOf' element items.
     * 
     * @return list
     */
    public List<IsFormatOf> getIsFormatOfList() {
        return isFormatOfList;
    }

    /** 
     * Set the list of 'isFormatOf' element items.
     * 
     * @param list
     */
    public void setIsFormatOfList(List<IsFormatOf> list) {
        isFormatOfList = list;
    }

    /** 
     * Get the list of 'isPartOf' element items.
     * 
     * @return list
     */
    public List<IsPartOf> getIsPartOfList() {
        return isPartOfList;
    }

    /** 
     * Set the list of 'isPartOf' element items.
     * 
     * @param list
     */
    public void setIsPartOfList(List<IsPartOf> list) {
        isPartOfList = list;
    }

    /** 
     * Get the list of 'issued' element items.
     * 
     * @return list
     */
    public List<Issued> getIssuedList() {
        return issuedList;
    }

    /** 
     * Set the list of 'issued' element items.
     * 
     * @param list
     */
    public void setIssuedList(List<Issued> list) {
        issuedList = list;
    }

    /** 
     * Get the 'isNextInSequence' element value.
     * 
     * @return value
     */
    public IsNextInSequence getIsNextInSequence() {
        return isNextInSequence;
    }

    /** 
     * Set the 'isNextInSequence' element value.
     * 
     * @param isNextInSequence
     */
    public void setIsNextInSequence(IsNextInSequence isNextInSequence) {
        this.isNextInSequence = isNextInSequence;
    }

    /** 
     * Get the 'rights' element value.
     * 
     * @return value
     */
    public Rights1 getRights() {
        return rights;
    }

    /** 
     * Set the 'rights' element value.
     * 
     * @param rights
     */
    public void setRights(Rights1 rights) {
        this.rights = rights;
    }

    /** 
     * Get the list of 'sameAs' element items.
     * 
     * @return list
     */
    public List<SameAs> getSameAList() {
        return sameAList;
    }

    /** 
     * Set the list of 'sameAs' element items.
     * 
     * @param list
     */
    public void setSameAList(List<SameAs> list) {
        sameAList = list;
    }

    /** 
     * Get the 'type' element value.
     * 
     * @return value
     */
    public Type1 getType() {
        return type;
    }

    /** 
     * Set the 'type' element value.
     * 
     * @param type
     */
    public void setType(Type1 type) {
        this.type = type;
    }

    /** 
     * Get the 'codecName' element value.
     * 
     * @return value
     */
    public CodecName getCodecName() {
        return codecName;
    }

    /** 
     * Set the 'codecName' element value.
     * 
     * @param codecName
     */
    public void setCodecName(CodecName codecName) {
        this.codecName = codecName;
    }

    /** 
     * Get the 'hasMimeType' element value.
     * 
     * @return value
     */
    public HasMimeType getHasMimeType() {
        return hasMimeType;
    }

    /** 
     * Set the 'hasMimeType' element value.
     * 
     * @param hasMimeType
     */
    public void setHasMimeType(HasMimeType hasMimeType) {
        this.hasMimeType = hasMimeType;
    }

    /** 
     * Get the 'fileByteSize' element value.
     * 
     * @return value
     */
    public LongType getFileByteSize() {
        return fileByteSize;
    }

    /** 
     * Set the 'fileByteSize' element value.
     * 
     * @param fileByteSize
     */
    public void setFileByteSize(LongType fileByteSize) {
        this.fileByteSize = fileByteSize;
    }

    /** 
     * Get the 'duration' element value.
     * 
     * @return value
     */
    public Duration getDuration() {
        return duration;
    }

    /** 
     * Set the 'duration' element value.
     * 
     * @param duration
     */
    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    /** 
     * Get the 'width' element value.
     * 
     * @return value
     */
    public Width getWidth() {
        return width;
    }

    /** 
     * Set the 'width' element value.
     * 
     * @param width
     */
    public void setWidth(Width width) {
        this.width = width;
    }

    /** 
     * Get the 'height' element value.
     * 
     * @return value
     */
    public Height getHeight() {
        return height;
    }

    /** 
     * Set the 'height' element value.
     * 
     * @param height
     */
    public void setHeight(Height height) {
        this.height = height;
    }

    /** 
     * Get the 'spatialResolution' element value.
     * 
     * @return value
     */
    public SpatialResolution getSpatialResolution() {
        return spatialResolution;
    }

    /** 
     * Set the 'spatialResolution' element value.
     * 
     * @param spatialResolution
     */
    public void setSpatialResolution(SpatialResolution spatialResolution) {
        this.spatialResolution = spatialResolution;
    }

    /** 
     * Get the 'sampleSize' element value.
     * 
     * @return value
     */
    public SampleSize getSampleSize() {
        return sampleSize;
    }

    /** 
     * Set the 'sampleSize' element value.
     * 
     * @param sampleSize
     */
    public void setSampleSize(SampleSize sampleSize) {
        this.sampleSize = sampleSize;
    }

    /** 
     * Get the 'sampleRate' element value.
     * 
     * @return value
     */
    public SampleRate getSampleRate() {
        return sampleRate;
    }

    /** 
     * Set the 'sampleRate' element value.
     * 
     * @param sampleRate
     */
    public void setSampleRate(SampleRate sampleRate) {
        this.sampleRate = sampleRate;
    }

    /** 
     * Get the 'bitRate' element value.
     * 
     * @return value
     */
    public BitRate getBitRate() {
        return bitRate;
    }

    /** 
     * Set the 'bitRate' element value.
     * 
     * @param bitRate
     */
    public void setBitRate(BitRate bitRate) {
        this.bitRate = bitRate;
    }

    /** 
     * Get the 'frameRate' element value.
     * 
     * @return value
     */
    public DoubleType getFrameRate() {
        return frameRate;
    }

    /** 
     * Set the 'frameRate' element value.
     * 
     * @param frameRate
     */
    public void setFrameRate(DoubleType frameRate) {
        this.frameRate = frameRate;
    }

    /** 
     * Get the 'hasColorSpace' element value.
     * 
     * @return value
     */
    public HasColorSpace getHasColorSpace() {
        return hasColorSpace;
    }

    /** 
     * Set the 'hasColorSpace' element value.
     * 
     * @param hasColorSpace
     */
    public void setHasColorSpace(HasColorSpace hasColorSpace) {
        this.hasColorSpace = hasColorSpace;
    }

    /** 
     * Get the list of 'componentColor' element items.
     * 
     * @return list
     */
    public List<HexBinaryType> getComponentColorList() {
        return componentColorList;
    }

    /** 
     * Set the list of 'componentColor' element items.
     * 
     * @param list
     */
    public void setComponentColorList(List<HexBinaryType> list) {
        componentColorList = list;
    }

    /** 
     * Get the 'orientation' element value.
     * 
     * @return value
     */
    public OrientationType getOrientation() {
        return orientation;
    }

    /** 
     * Set the 'orientation' element value.
     * 
     * @param orientation
     */
    public void setOrientation(OrientationType orientation) {
        this.orientation = orientation;
    }

    /** 
     * Get the 'audioChannelNumber' element value.
     * 
     * @return value
     */
    public AudioChannelNumber getAudioChannelNumber() {
        return audioChannelNumber;
    }

    /** 
     * Set the 'audioChannelNumber' element value.
     * 
     * @param audioChannelNumber
     */
    public void setAudioChannelNumber(AudioChannelNumber audioChannelNumber) {
        this.audioChannelNumber = audioChannelNumber;
    }

    /** 
     * Get the list of 'isReferencedBy' element items.
     * 
     * @return list
     */
    public List<IsReferencedBy> getIsReferencedByList() {
        return isReferencedByList;
    }

    /** 
     * Set the list of 'isReferencedBy' element items.
     * 
     * @param list
     */
    public void setIsReferencedByList(List<IsReferencedBy> list) {
        isReferencedByList = list;
    }

    /** 
     * Get the 'preview' element value.
     * 
     * @return value
     */
    public Preview getPreview() {
        return preview;
    }

    /** 
     * Set the 'preview' element value.
     * 
     * @param preview
     */
    public void setPreview(Preview preview) {
        this.preview = preview;
    }

    /** 
     * Get the list of 'has_service' element items.
     * 
     * @return list
     */
    public List<HasService> getHasServiceList() {
        return hasServiceList;
    }

    /** 
     * Set the list of 'has_service' element items.
     * 
     * @param list
     */
    public void setHasServiceList(List<HasService> list) {
        hasServiceList = list;
    }
}
