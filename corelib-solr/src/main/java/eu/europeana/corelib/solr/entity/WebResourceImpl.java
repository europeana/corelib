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

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Indexed;

import eu.europeana.corelib.definitions.solr.entity.WebResource;

/**
 * @see eu.europeana.corelib.definitions.solr.entity.corelid.definitions.model.WebResource
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
@Embedded
public class WebResourceImpl implements WebResource {

	private ObjectId id;
	private String[] webResourceDcRights;
	private String webResourceEdmRights;
	@Indexed(unique=false)
	private String about;
	private String[] dcDescription;
	private String[] dcFormat;
	private String[] dcSource;
	private String[] dctermsExtent;
	private String[] dctermsIssued;
	private String[] dctermsConformsTo;
	private String[] dctermsCreated;
	private String[] dctermsIsFormatOf;
	private String[] dctermsHasPart;
	private String isNextInSequence;
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
	public String[] getDcDescription() {
		return dcDescription;
	}

	@Override
	public void setDcDescription(String[] dcDescription) {
		this.dcDescription = dcDescription;
	}

	@Override
	public String[] getDcFormat() {
		return dcFormat;
	}
	@Override
	public void setDcFormat(String[] dcFormat) {
		this.dcFormat = dcFormat;
	}
	@Override
	public String[] getDcSource() {
		return dcSource;
	}
	@Override
	public void setDcSource(String[] dcSource) {
		this.dcSource = dcSource;
	}
	@Override
	public String[] getDctermsExtent() {
		return dctermsExtent;
	}
	@Override
	public void setDctermsExtent(String[] dctermsExtent) {
		this.dctermsExtent = dctermsExtent;
	}
	@Override
	public String[] getDctermsIssued() {
		return dctermsIssued;
	}
	@Override
	public void setDctermsIssued(String[] dctermsIssued) {
		this.dctermsIssued = dctermsIssued;
	}
	@Override
	public String[] getDctermsConformsTo() {
		return dctermsConformsTo;
	}
	@Override
	public void setDctermsConformsTo(String[] dctermsConformsTo) {
		this.dctermsConformsTo = dctermsConformsTo;
	}
	@Override
	public String[] getDctermsCreated() {
		return dctermsCreated;
	}
	@Override
	public void setDctermsCreated(String[] dctermsCreated) {
		this.dctermsCreated = dctermsCreated;
	}
	@Override
	public String[] getDctermsIsFormatOf() {
		return dctermsIsFormatOf;
	}
	@Override
	public void setDctermsIsFormatOf(String[] dctermsIsFormatOf) {
		this.dctermsIsFormatOf = dctermsIsFormatOf;
	}
	@Override
	public String[] getDctermsHasPart() {
		return dctermsHasPart;
	}
	@Override
	public void setDctermsHasPart(String[] dctermsHasPart) {
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
	public void setWebResourceDcRights(String[] webResourceDcRights) {
		this.webResourceDcRights = webResourceDcRights.clone();
	}

	@Override
	public void setWebResourceEdmRights(String webResourceEdmRights) {
		this.webResourceEdmRights = webResourceEdmRights;
	}

	@Override
	public String[] getWebResourceDcRights() {
		return (this.webResourceDcRights != null ? this.webResourceDcRights
				.clone() : null);
	}

	@Override
	public String getWebResourceEdmRights() {
		return this.webResourceEdmRights;
	}

	@Override
	public ObjectId getId() {
		return this.id;
	}

	@Override
	public boolean equals(Object o) {
		if(o==null){
			return false;
		}
		if(o.getClass() == this.getClass()){
			return this.getAbout().equals(((WebResourceImpl) o).getAbout());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.about.hashCode();
	}
}
