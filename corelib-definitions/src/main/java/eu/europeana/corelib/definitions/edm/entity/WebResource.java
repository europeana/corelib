package eu.europeana.corelib.definitions.edm.entity;

import java.util.List;
import java.util.Map;


/**
 * EDM WebResource Fields implementation
 *
 * @author Yorgos.Mamakis@ kb.nl
 */
public interface WebResource extends AbstractEdmEntity {
  /**
   * Retrieve the dc:rights fields of a WebResource
   *
   * @return String array representing the dc:rights fields of a WebResource
   */
  Map<String, List<String>> getWebResourceDcRights();

  /**
   * Retrieve the edm:rights field of a WebResource
   *
   * @return String representing the edm:rights fields of a WebResource
   */
  Map<String, List<String>> getWebResourceEdmRights();

  /**
   * Set the dc:rights fields of a WebResource
   *
   * @param webResourceDcRights String array representing the dc:rights fields of a WebResource
   */
  void setWebResourceDcRights(Map<String, List<String>> webResourceDcRights);

  /**
   * Set the edm:rights field of a WebResource
   *
   * @param webResourceEdmRights String representing the edm:rights fields of a WebResource
   */
  void setWebResourceEdmRights(Map<String, List<String>> webResourceEdmRights);

  /**
   * sets the edm:isNextInSequence for the edm:WebResource
   */
  void setIsNextInSequence(String isNextInSequence);

  /**
   * @return the edm:isNextInSequence for the edm:WebResource
   */
  String getIsNextInSequence();

  /**
   * sets the dcterms:hasPart for the edm:WebResource
   */
  void setDctermsHasPart(Map<String, List<String>> dctermsHasPart);

  /**
   * @return the dcterms:hasPart for the edm:WebResource
   */
  Map<String, List<String>> getDctermsHasPart();

  /**
   * sets the dcterms:isFormatOf for the edm:WebResource
   */
  void setDctermsIsFormatOf(Map<String, List<String>> dctermsIsFormatOf);

  /**
   * @return the dcterms:isFormatOf for the edm:WebResource
   */
  Map<String, List<String>> getDctermsIsFormatOf();

  /**
   * sets the dcterms:isPartOf for the edm:WebResource
   */
  void setDctermsIsPartOf(Map<String, List<String>> dctermsIsPartOf);

  /**
   * @return the dcterms:isPartOf for the edm:WebResource
   */
  Map<String, List<String>> getDctermsIsPartOf();



  /**
   * sets the dcterms:created for the edm:WebResource
   */
  void setDctermsCreated(Map<String, List<String>> dctermsCreated);

  /**
   * @return the dcterms:created for the edm:WebResource
   */
  Map<String, List<String>> getDctermsCreated();

  /**
   * @return the dcterms:conformsTo for the edm:WebResource
   */
  Map<String, List<String>> getDctermsConformsTo();

  /**
   * sets the dcterms:conformsTo for the edm:WebResource
   */
  void setDctermsConformsTo(Map<String, List<String>> dctermsConformsTo);

  /**
   * sets the dcterms:issued for the edm:WebResource
   */
  void setDctermsIssued(Map<String, List<String>> dctermsIssued);

  /**
   * @return the dcterms:issued for the edm:WebResource
   */
  Map<String, List<String>> getDctermsIssued();

  /**
   * @return the dc:description for the edm:WebResource
   */
  Map<String, List<String>> getDcDescription();

  /**
   * sets the dc:description for the edm:WebResource
   */
  void setDcDescription(Map<String, List<String>> dcDescription);

  /**
   * @return the dc:format for the edm:WebResource
   */
  Map<String, List<String>> getDcFormat();

  /**
   * sets the dc:format for the edm:WebResource
   */
  void setDcFormat(Map<String, List<String>> dcFormat);

  /**
   * @return the dc:source for the edm:WebResource
   */
  Map<String, List<String>> getDcSource();

  /**
   * sets the dc:source for the edm:WebResource
   */
  void setDcSource(Map<String, List<String>> dcSource);

  /**
   * @return the dcterms:extent for the edm:WebResource
   */
  Map<String, List<String>> getDctermsExtent();

  /**
   * sets the dcterms:extent for the edm:WebResource
   */
  void setDctermsExtent(Map<String, List<String>> dctermsExtent);

  /**
   * dc:creator for edm:WebResource
   */
  Map<String, List<String>> getDcCreator();

  /**
   * dc:creator for edm:WebResource
   */

  void setDcCreator(Map<String, List<String>> dcCreator);

  /**
   * Retrieve the dc:type fields of a WebResource
   *
   * @return Map representing the dc:type fields of a WebResource
   */
  Map<String, List<String>> getDcType();

  /**
   * Set the dc:type fields of a WebResource
   *
   * @param dcType Map representing the dc:type fields of a WebResource
   */
  void setDcType(Map<String, List<String>> dcType);

  /**
   * owl:sameAs for edm:WebResource
   */
  void setOwlSameAs(String[] owlSameAs);

  /**
   * owl:sameAs for edm:WebResource
   */
  String[] getOwlSameAs();

  /**
   * rdf:type for edm:WebResource
   */
  String getRdfType();

  /**
   * edm:codecName
   */
  String getEdmCodecName();


  /**
   * File format is not used in EDM, so no need to serialize!
   */
  String getFileFormat();


  /**
   * ebucore:hasMimeType
   * @return The main MIME types as defined by IANA: e.g. audio, video, text, application, or a container MIME type.
   */
  String getEbucoreHasMimeType();

  /**
   * ebucore:fileByteSize
   * @return The size of a Media Resource file expressed in bytes.
   */
  Long getEbucoreFileByteSize();

  /**
   * ebucore:duration
   * @return The duration of a track or a signal expressed in ms.
   */
  String getEbucoreDuration();

/**
 * ebucore:audioChannelNumber
 * @return The total number of audio channels contained in the Media Resource (non-negative integer)
 */
  Integer getEbucoreAudioChannelNumber();

  /**
   * ebucore:width
   * @return The width of e.g. an image or video frame typically expressed as a number of pixels.
   */
  Integer getEbucoreWidth();

  /**
   * ebucore:height
   * @return The height of e.g. an image or video frame typically expressed as a number of pixels.
   */
  Integer getEbucoreHeight();

  /**
   * edm:spatialResolution
   */
  Integer getEdmSpatialResolution();

  /**
   * ebucore:sampleSize
   * @return The size of an audio sample in bits. Also called bit depth.
   */
  Integer getEbucoreSampleSize();

  /**
   * ebucore:sampleRate
   * @return The frequency at which an audio is sampled per second. Also called sampling rate.
   */
  Integer getEbucoreSampleRate();

  /**
   * ebucore:frameRate
   * @return The frame rate of the video signal in frames per second.
   */
  Double getEbucoreFrameRate();

  /**
   * ebucore:bitRate
   * @return The bitrate at which the Media Resource can be played in bits per second (non-negative integer)
   */
  Integer getEbucoreBitRate();

  /**
   * edm:hasColorSpace
   */
  String getEdmHasColorSpace();

  /**
   * edm:componentColor
   */
  List<String> getEdmComponentColor();

  /**
   * ebucore:orientation
   * @return The orientation of a document or an image (portrait or landscape)
   */
  String getEbucoreOrientation();

  /**
   * edm:preview
   */
  String getEdmPreview();

  /**
   * edm:preview
   */
  void setEdmPreview(String edmPreview);

  /**
   * svcs:has_service
   */
  String[] getSvcsHasService();

  /**
   * svcs:has_service
   */
  void setSvcsHasService(String[] svcsHasService);

  String[] getDctermsIsReferencedBy();

  void setDctermsIsReferencedBy(String[] dctermsIsReferencedBy);
}
