/*
 * Copyright 2007-2012 The Europeana Foundation
 *
 *  Licenced under the EUPL, Version 1.1 (the "Licence") and subsequent versions as approved
 *  by the European Commission;
 *  You may not use this work except in compliance with the Licence.
 * 
 *  You may obtain a copy of the Licence at:
 *  http://joinup.ec.europa.eu/software/page/eupl
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under
 *  the Licence is distributed on an "AS IS" basis, without warranties or conditions of
 *  any kind, either express or implied.
 *  See the Licence for the specific language governing permissions and limitations under
 *  the Licence.
 */
package eu.europeana.corelib.definitions.edm.entity;

import java.util.List;
import java.util.Map;

import eu.europeana.corelib.definitions.model.ColorSpace;
import eu.europeana.corelib.definitions.model.Orientation;
import eu.europeana.harvester.domain.SourceDocumentReferenceMetaInfo;

/**
 * EDM WebResource Fields implementation
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public interface WebResource extends AbstractEdmEntity {

	/**
	 * Retrieve the dc:rights fields of a WebResource
	 * 
	 * @return String array representing the dc:rights fields of a WebResource
	 */
	Map<String,List<String>> getWebResourceDcRights();

	/**
	 * Retrieve the edm:rights field of a WebResource
	 * 
	 * @return String representing the edm:rights fields of a WebResource
	 */
	Map<String,List<String>> getWebResourceEdmRights();

	/**
	 * Set the dc:rights fields of a WebResource
	 * 
	 * @param webResourceDcRights
	 *            String array representing the dc:rights fields of a
	 *            WebResource
	 */
	void setWebResourceDcRights(Map<String,List<String>> webResourceDcRights);

	/**
	 * Set the edm:rights field of a WebResource
	 * 
	 * @param webResourceEdmRights
	 *            String representing the edm:rights fields of a WebResource
	 */
	void setWebResourceEdmRights(Map<String,List<String>> webResourceEdmRights);

	/**
	 * sets the edm:isNextInSequence for the edm:WebResource
	 * @param isNextInSequence
	 */
	void setIsNextInSequence(String isNextInSequence);

	/**
	 * 
	 * @return the edm:isNextInSequence for the edm:WebResource
	 */
	String getIsNextInSequence();

	/**
	 * sets the dcterms:hasPart for the edm:WebResource
	 * @param dctermsHasPart
	 */
	void setDctermsHasPart(Map<String,List<String>> dctermsHasPart);

	/**
	 * 
	 * @return the dcterms:hasPart for the edm:WebResource
	 */
	Map<String,List<String>> getDctermsHasPart();

	/**
	 * sets the dcterms:isFormatOf for the edm:WebResource
	 * @param dctermsIsFormatOf
	 */
	void setDctermsIsFormatOf(Map<String,List<String>> dctermsIsFormatOf);

	/**
	 *  
	 * @return the dcterms:isFormatOf for the edm:WebResource
	 */
	Map<String,List<String>> getDctermsIsFormatOf();

	/**
	 * sets the dcterms:created for the edm:WebResource
	 * @param dctermsCreated
	 */
	void setDctermsCreated(Map<String,List<String>> dctermsCreated);

	/**
	 * 
	 * @return the dcterms:created for the edm:WebResource
	 */
	Map<String,List<String>> getDctermsCreated();

	/**
	 * 
	 * @return the dcterms:conformsTo for the edm:WebResource
	 */
	Map<String,List<String>> getDctermsConformsTo();

	/**
	 * sets the dcterms:conformsTo for the edm:WebResource
	 * @param dctermsConformsTo
	 */
	void setDctermsConformsTo(Map<String,List<String>> dctermsConformsTo);

	/**
	 * sets the dcterms:issued for the edm:WebResource
	 * @param dctermsIssued
	 */
	void setDctermsIssued(Map<String,List<String>> dctermsIssued);

	/**
	 * 
	 * @return the dcterms:issued for the edm:WebResource
	 */
	Map<String,List<String>> getDctermsIssued();

	/**
	 * 
	 * @return the dc:description for the edm:WebResource
	 */
	Map<String,List<String>> getDcDescription();

	/**
	 * sets the dc:description for the edm:WebResource
	 * @param dcDescription
	 */
	void setDcDescription(Map<String,List<String>> dcDescription);

	/**
	 * 
	 * @return the dc:format for the edm:WebResource
	 */
	Map<String,List<String>> getDcFormat();

	/**
	 * sets the dc:format for the edm:WebResource
	 * @param dcFormat
	 */
	void setDcFormat(Map<String,List<String>> dcFormat);

	/**
	 * 
	 * @return the dc:source for the edm:WebResource
	 */ 
	Map<String,List<String>> getDcSource();

	/**
	 * sets the dc:source for the edm:WebResource
	 * @param dcSource
	 */
	void setDcSource(Map<String,List<String>> dcSource);

	/**
	 * 
	 * @return the dcterms:extent for the edm:WebResource
	 */
	Map<String,List<String>> getDctermsExtent();

	/**
	 * sets the dcterms:extent for the edm:WebResource
	 * @param dctermsExtent
	 */
	void setDctermsExtent(Map<String,List<String>> dctermsExtent);

	/**
	 * dc:creator for edm:WebResource
	 * @return
	 */
	Map<String, List<String>> getDcCreator();
	/**
	 * dc:creator for edm:WebResource
	 */
	
	void setDcCreator(Map<String, List<String>> dcCreator);
	/**
	 * owl:sameAs for edm:WebResource
	 */
	void setOwlSameAs(String[] owlSameAs);
	/**
	 * owl:sameAs for edm:WebResource
	 * @return
	 */
	String[] getOwlSameAs();

	/**
	 * rdf:type for edm:WebResource
	 * @return
	 */
	String getRdfType();
	
	/**
	 * edm:codecName
	 * @return
	 */
	String getEdmCodecName();
	
	/**
	 * ebucore:hasMimeType
	 * @return
	 */
	String getEbucoreHasMimeType();
	
	/**
	 * ebucore:fileByteSize
	 * @return
	 */
	Long getEbucoreFileByteSize();
	
	/**
	 * ebucore:duration
	 * @return
	 */
	String getEbucoreDuration();
	
	/**
	 * ebucore:width
	 * @return
	 */
	Integer getEbucoreWidth();
	
	/**
	 * ebucore:height
	 * @return
	 */
	Integer getEbucoreHeight();
	
	/**
	 * edm:spatialResolution
	 * @return
	 */
	Integer getEdmSpatialResolution();
	
	
	/**
	 * ebucore:sampleSize
	 * @return
	 */
	Integer getEbucoreSampleSize();
	
	
	/**
	 * ebucore:sampleRate
	 * @return
	 */
	Integer getEbucoreSampleRate();
	
	/**
         * ebucore:frameRate
         */
        Double getEbucoreFrameRate();
	/**
	 * ebucore:bitRate
	 * @return
	 */
	Integer getEbucoreBitRate();
	
	/**
	 * edm:hasColorSpace
	 * @return
	 */
	ColorSpace getEdmHasColorSpace();
	
	
	/**
	 * edm:componentColor
	 * @return
	 */
	List<String> getEdmComponentColor();
	
	
	
	/**
	 * ebucore:orientation
	 * @return
	 */
	Orientation getEbucoreOrientation();
	
	void setSourceDocumentReferenceMetaInfo(SourceDocumentReferenceMetaInfo sourceDocumentReferenceMetaInfo);
	
	SourceDocumentReferenceMetaInfo getSourceDocumentReferenceMetaInfo();
}
