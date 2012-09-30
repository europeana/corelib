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

import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;

import eu.europeana.corelib.definitions.solr.entity.WebResource;

/**
 * @see eu.europeana.corelib.definitions.solr.entity.corelid.definitions.model.WebResource
 * 
 * @author Yorgos.Mamakis@ kb.nl
 */
@JsonSerialize(include = Inclusion.NON_EMPTY)
@Entity("WebResource")
public class WebResourceImpl implements WebResource {
	@Id
	protected ObjectId id = new ObjectId();
	protected Map<String,List<String>> webResourceDcRights;
	protected Map<String,List<String>> webResourceEdmRights;

	@Indexed(unique=false)
	protected String about;
	protected Map<String,List<String>> dcDescription;
	protected Map<String,List<String>> dcFormat;
	protected Map<String,List<String>> dcSource;
	protected Map<String,List<String>> dctermsExtent;
	protected Map<String,List<String>> dctermsIssued;
	protected Map<String,List<String>> dctermsConformsTo;
	protected Map<String,List<String>> dctermsCreated;
	protected Map<String,List<String>> dctermsIsFormatOf;
	protected Map<String,List<String>> dctermsHasPart;
	protected String isNextInSequence;

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
	public Map<String,List<String>> getDcDescription() {
		return dcDescription;
	}

	@Override
	public void setDcDescription(Map<String,List<String>> dcDescription) {
		this.dcDescription = dcDescription;
	}

	@Override
	public Map<String,List<String>> getDcFormat() {
		return dcFormat;
	}

	@Override
	public void setDcFormat(Map<String,List<String>> dcFormat) {
		this.dcFormat = dcFormat;
	}

	@Override
	public Map<String,List<String>> getDcSource() {
		return dcSource;
	}

	@Override
	public void setDcSource(Map<String,List<String>> dcSource) {
		this.dcSource = dcSource;
	}

	@Override
	public Map<String,List<String>> getDctermsExtent() {
		return dctermsExtent;
	}

	@Override
	public void setDctermsExtent(Map<String,List<String>> dctermsExtent) {
		this.dctermsExtent = dctermsExtent;
	}

	@Override
	public Map<String,List<String>> getDctermsIssued() {
		return dctermsIssued;
	}

	@Override
	public void setDctermsIssued(Map<String,List<String>> dctermsIssued) {
		this.dctermsIssued = dctermsIssued;
	}

	@Override
	public Map<String,List<String>> getDctermsConformsTo() {
		return dctermsConformsTo;
	}

	@Override
	public void setDctermsConformsTo(Map<String,List<String>> dctermsConformsTo) {
		this.dctermsConformsTo = dctermsConformsTo;
	}

	@Override
	public Map<String,List<String>> getDctermsCreated() {
		return dctermsCreated;
	}

	@Override
	public void setDctermsCreated(Map<String,List<String>> dctermsCreated) {
		this.dctermsCreated = dctermsCreated;
	}

	@Override
	public Map<String,List<String>> getDctermsIsFormatOf() {
		return dctermsIsFormatOf;
	}

	@Override
	public void setDctermsIsFormatOf(Map<String,List<String>> dctermsIsFormatOf) {
		this.dctermsIsFormatOf = dctermsIsFormatOf;
	}

	@Override
	public Map<String,List<String>> getDctermsHasPart() {
		return dctermsHasPart;
	}

	@Override
	public void setDctermsHasPart(Map<String,List<String>> dctermsHasPart) {
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
	public void setWebResourceDcRights(Map<String,List<String>> webResourceDcRights) {
		this.webResourceDcRights = webResourceDcRights;
	}

	@Override
	public void setWebResourceEdmRights(Map<String,List<String>> webResourceEdmRights) {
		this.webResourceEdmRights = webResourceEdmRights;
	}

	@Override
	public Map<String,List<String>> getWebResourceDcRights() {
		return this.webResourceDcRights;
	}

	@Override
	public Map<String,List<String>> getWebResourceEdmRights() {
		return this.webResourceEdmRights;
	}

	@Override
	public ObjectId getId() {
		return this.id;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null){
			return false;
		}
		if (o.getClass() == this.getClass()){
			return this.getAbout().equals(((WebResourceImpl) o).getAbout());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.about.hashCode();
	}
}
