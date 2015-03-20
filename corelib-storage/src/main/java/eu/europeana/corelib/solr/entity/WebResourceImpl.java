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
package eu.europeana.corelib.solr.entity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Transient;

import eu.europeana.corelib.definitions.edm.entity.WebResource;
import eu.europeana.corelib.definitions.model.ColorSpace;
import eu.europeana.corelib.definitions.model.Orientation;
import eu.europeana.harvester.domain.SourceDocumentReferenceMetaInfo;

/**
 * @see eu.europeana.corelib.definitions.solr.entity.corelid.definitions.model.WebResource
 * 
 * @author Yorgos.Mamakis@ kb.nl
 */
@JsonSerialize(include = Inclusion.NON_EMPTY)
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
	private Map<String, List<String>> dctermsHasPart;
	private Map<String, List<String>> dcCreator;
	private String isNextInSequence;
	private String[] owlSameAs;
	@Transient
	@JsonIgnore
	private SourceDocumentReferenceMetaInfo sourceDocumentReferenceMetaInfo;

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
	public void setWebResourceDcRights(
			Map<String, List<String>> webResourceDcRights) {
		this.webResourceDcRights = webResourceDcRights;
	}

	@Override
	public void setWebResourceEdmRights(
			Map<String, List<String>> webResourceEdmRights) {
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
	
		return null;
	}

	@Override
	public String getEdmCodecName() {
		if (sourceDocumentReferenceMetaInfo != null
				&& sourceDocumentReferenceMetaInfo.getVideoMetaInfo() != null
				&& sourceDocumentReferenceMetaInfo.getVideoMetaInfo()
						.getCodec() != null) {
			return sourceDocumentReferenceMetaInfo.getVideoMetaInfo()
					.getCodec();
		}
		return null;
	}

	@Override
	public String getEbucoreHasMimeType() {
		if (sourceDocumentReferenceMetaInfo != null
				&& sourceDocumentReferenceMetaInfo.getAudioMetaInfo() != null
				&& sourceDocumentReferenceMetaInfo.getAudioMetaInfo()
						.getMimeType() != null) {
			return sourceDocumentReferenceMetaInfo.getAudioMetaInfo()
					.getMimeType();
		}
		if (sourceDocumentReferenceMetaInfo != null
				&& sourceDocumentReferenceMetaInfo.getVideoMetaInfo() != null
				&& sourceDocumentReferenceMetaInfo.getVideoMetaInfo()
						.getMimeType() != null) {
			return sourceDocumentReferenceMetaInfo.getVideoMetaInfo()
					.getMimeType();
		}
		if (sourceDocumentReferenceMetaInfo != null
				&& sourceDocumentReferenceMetaInfo.getImageMetaInfo() != null
				&& sourceDocumentReferenceMetaInfo.getImageMetaInfo()
						.getMimeType() != null) {
			return sourceDocumentReferenceMetaInfo.getImageMetaInfo()
					.getMimeType();
		}
		return null;
	}

	@Override
	public Long getEbucoreFileByteSize() {
		if (sourceDocumentReferenceMetaInfo != null
				&& sourceDocumentReferenceMetaInfo.getAudioMetaInfo() != null
				&& sourceDocumentReferenceMetaInfo.getAudioMetaInfo()
						.getFileSize() != null) {
			return sourceDocumentReferenceMetaInfo.getAudioMetaInfo()
					.getFileSize();
		}
		if (sourceDocumentReferenceMetaInfo != null
				&& sourceDocumentReferenceMetaInfo.getVideoMetaInfo() != null
				&& sourceDocumentReferenceMetaInfo.getVideoMetaInfo()
						.getFileSize() != null) {
			return sourceDocumentReferenceMetaInfo.getVideoMetaInfo()
					.getFileSize();
		}
		if (sourceDocumentReferenceMetaInfo != null
				&& sourceDocumentReferenceMetaInfo.getImageMetaInfo() != null
				&& sourceDocumentReferenceMetaInfo.getImageMetaInfo()
						.getFileSize() != null) {
			return sourceDocumentReferenceMetaInfo.getImageMetaInfo()
					.getFileSize();
		}
		return null;
	}

	@Override
	public String getEbucoreDuration() {
		if (sourceDocumentReferenceMetaInfo != null
				&& sourceDocumentReferenceMetaInfo.getAudioMetaInfo() != null
				&& sourceDocumentReferenceMetaInfo.getAudioMetaInfo()
						.getDuration() != null) {
			return Long.toString(sourceDocumentReferenceMetaInfo
					.getAudioMetaInfo().getDuration());
		}
		if (sourceDocumentReferenceMetaInfo != null
				&& sourceDocumentReferenceMetaInfo.getVideoMetaInfo() != null
				&& sourceDocumentReferenceMetaInfo.getVideoMetaInfo()
						.getDuration() != null) {
			return Long.toString(sourceDocumentReferenceMetaInfo
					.getVideoMetaInfo().getDuration());
		}

		return null;
	}

	@Override
	public Integer getEbucoreWidth() {
		if (sourceDocumentReferenceMetaInfo != null
				&& sourceDocumentReferenceMetaInfo.getVideoMetaInfo() != null
				&& sourceDocumentReferenceMetaInfo.getVideoMetaInfo()
						.getWidth() != null) {
			return sourceDocumentReferenceMetaInfo.getVideoMetaInfo()
					.getWidth();
		}
		if (sourceDocumentReferenceMetaInfo != null
				&& sourceDocumentReferenceMetaInfo.getImageMetaInfo() != null
				&& sourceDocumentReferenceMetaInfo.getImageMetaInfo()
						.getWidth() != null) {
			return sourceDocumentReferenceMetaInfo.getImageMetaInfo()
					.getWidth();
		}
		return null;
	}

	@Override
	public Integer getEbucoreHeight() {
		if (sourceDocumentReferenceMetaInfo != null
				&& sourceDocumentReferenceMetaInfo.getVideoMetaInfo() != null
				&& sourceDocumentReferenceMetaInfo.getVideoMetaInfo()
						.getHeight() != null) {
			return sourceDocumentReferenceMetaInfo.getVideoMetaInfo()
					.getHeight();
		}
		if (sourceDocumentReferenceMetaInfo != null
				&& sourceDocumentReferenceMetaInfo.getImageMetaInfo() != null
				&& sourceDocumentReferenceMetaInfo.getImageMetaInfo()
						.getHeight() != null) {
			return sourceDocumentReferenceMetaInfo.getImageMetaInfo()
					.getHeight();
		}
		return null;
	}

	@Override
	public Integer getEdmSpatialResolution() {
		if (sourceDocumentReferenceMetaInfo != null
				&& sourceDocumentReferenceMetaInfo.getVideoMetaInfo() != null
				&& sourceDocumentReferenceMetaInfo.getVideoMetaInfo()
						.getResolution() != null) {
			return Integer.parseInt(sourceDocumentReferenceMetaInfo.getVideoMetaInfo()
					.getResolution());
		}
		return null;
	}

	@Override
	public Integer getEbucoreSampleSize() {
		
		return null;
	}

	@Override
	public Integer getEbucoreSampleRate() {
		if (sourceDocumentReferenceMetaInfo != null
				&& sourceDocumentReferenceMetaInfo.getAudioMetaInfo() != null
				&& sourceDocumentReferenceMetaInfo.getAudioMetaInfo()
						.getSampleRate() != null) {
			return sourceDocumentReferenceMetaInfo.getAudioMetaInfo()
					.getSampleRate();
		}
		return null;
	}

	@Override
	public Integer getEbucoreBitRate() {
		if (sourceDocumentReferenceMetaInfo != null
				&& sourceDocumentReferenceMetaInfo.getAudioMetaInfo() != null
				&& sourceDocumentReferenceMetaInfo.getAudioMetaInfo()
						.getBitRate() != null) {
			return sourceDocumentReferenceMetaInfo.getAudioMetaInfo()
					.getBitRate();
		}
		return null;
	}

	@Override
	public ColorSpace getEdmHasColorSpace() {
		if (sourceDocumentReferenceMetaInfo != null
				&& sourceDocumentReferenceMetaInfo.getImageMetaInfo() != null
				&& sourceDocumentReferenceMetaInfo.getImageMetaInfo()
						.getColorSpace() != null) {
			if (sourceDocumentReferenceMetaInfo.getImageMetaInfo()
					.getColorSpace().equalsIgnoreCase("grayscale")) {
				return ColorSpace.GRAYSCALE;
			} else {
				return ColorSpace.SRGB;
			}
		}
		return null;
	}

	@Override
	public List<String> getEdmComponentColor() {
		if (sourceDocumentReferenceMetaInfo != null
				&& sourceDocumentReferenceMetaInfo.getImageMetaInfo() != null
				&& sourceDocumentReferenceMetaInfo.getImageMetaInfo()
						.getColorPalette() != null) {
			return Arrays.asList(sourceDocumentReferenceMetaInfo.getImageMetaInfo()
						.getColorPalette());
		}
		return null;
	}

	@Override
	public Orientation getEbucoreOrientation() {
		if (sourceDocumentReferenceMetaInfo != null
				&& sourceDocumentReferenceMetaInfo.getVideoMetaInfo() != null
				&& sourceDocumentReferenceMetaInfo.getVideoMetaInfo()
						.getWidth() != null&& sourceDocumentReferenceMetaInfo.getVideoMetaInfo()
						.getHeight() != null) {
			if (sourceDocumentReferenceMetaInfo.getVideoMetaInfo()
						.getHeight()>=sourceDocumentReferenceMetaInfo.getVideoMetaInfo()
						.getWidth()){
				return Orientation.PORTRAIT;
			} else {
				return Orientation.LANDSCAPE;
			}
		}
		if (sourceDocumentReferenceMetaInfo != null
				&& sourceDocumentReferenceMetaInfo.getImageMetaInfo() != null
				&& sourceDocumentReferenceMetaInfo.getImageMetaInfo()
						.getWidth() != null&& sourceDocumentReferenceMetaInfo.getImageMetaInfo()
						.getHeight() != null) {
			if (sourceDocumentReferenceMetaInfo.getImageMetaInfo()
						.getHeight()>=sourceDocumentReferenceMetaInfo.getImageMetaInfo()
						.getWidth()){
				return Orientation.PORTRAIT;
			} else {
				return Orientation.LANDSCAPE;
			}
		}
		return null;
	}

	@Override
	public void setSourceDocumentReferenceMetaInfo(
			SourceDocumentReferenceMetaInfo sourceDocumentReferenceMetaInfo) {
		this.sourceDocumentReferenceMetaInfo = sourceDocumentReferenceMetaInfo;

	}

	@Override
	public SourceDocumentReferenceMetaInfo getSourceDocumentReferenceMetaInfo() {
		return this.sourceDocumentReferenceMetaInfo;
	}

    @Override
    public Double getEbucoreFrameRate() {
        if (sourceDocumentReferenceMetaInfo != null
				&& sourceDocumentReferenceMetaInfo.getVideoMetaInfo() != null
				&& sourceDocumentReferenceMetaInfo.getVideoMetaInfo()
						.getFrameRate() != null) {
			return sourceDocumentReferenceMetaInfo.getVideoMetaInfo()
						.getFrameRate();
		}
        return null;
    }
}
