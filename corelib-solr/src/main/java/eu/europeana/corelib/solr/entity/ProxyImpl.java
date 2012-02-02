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

import java.util.Date;

import eu.europeana.corelib.definitions.solr.DocType;
import eu.europeana.corelib.definitions.solr.entity.Proxy;

import com.google.code.morphia.annotations.*;
import org.bson.types.*;
/**
 * @see eu.europeana.corelib.definitions.solr.entity.model.Proxy
 * @author yorgos.mamakis@kb.nl
 *
 */

@Entity("Proxy")
public class ProxyImpl implements Proxy {

	@Id ObjectId proxyId;
	private String[] owlSameAs;
	private String[] dcContributor;
	private String[] dcCoverage;
	private String[] dcCreator;
	private Date[] dcDate;
	private String[] dcDescription;
	private String[] dcFormat;
	private String[] dcIdentifier;
	private String[] dcLanguage;
	private String[] dcPublisher;
	private String[] dcRelation;
	private String[] dcRights;
	private String[] dcSource;
	private String[] dcSubject;
	private String[] dcTitle;
	private String[] dcType;
	private String[] dctermsAlternative;
	private String[] dctermsConformsTo;
	private Date[] dctermsCreated;
	private String[] dctermsExtent;
	private String[] dctermsHasFormat;
	private String[] dctermsHasPart;
	private String[] dctermsHasVersion;
	private String[] dctermsIsFormatOf;
	private String[] dctermsIsPartOf;
	private String[] dctermsIsReferencedBy;
	private String[] dctermsIsReplacedBy;
	private String[] dctermsIsRequiredBy;
	private String[] dctermsIssued;
	private String[] dctermsIsVersionOf;
	private String[] dctermsMedium;
	private String[] dctermsProvenance;
	private String[] dctermsReferences;
	private String[] dctermsReplaces;
	private String[] dctermsRequires;
	private String[] dctermsSpatial;
	private String[] dctermsTOC;
	private String[] dctermsTemporal;
	private DocType edmType;
	private String edmCurrentLocation;
	private String edmIsNextInSequence;
	
	
	@Override
	public String[] getOwlSameAs() {
		return this.owlSameAs;
	}

	@Override
	public String[] getDcContributor() {
		return this.dcContributor;
	}

	@Override
	public String[] getDcCoverage() {
		return this.dcCoverage;
	}

	@Override
	public String[] getDcCreator() {
		return this.dcCreator;
	}

	@Override
	public Date[] getDcDate() {
		return this.dcDate;
	}

	@Override
	public String[] getDcDescription() {
		return this.dcDescription;
	}

	@Override
	public String[] getDcFormat() {
		return this.dcFormat;
	}

	@Override
	public String[] getDcIdentifier() {
		return this.dcIdentifier;
	}

	@Override
	public String[] getDcLanguage() {
		return this.dcLanguage;
	}

	@Override
	public String[] getDcPublisher() {
		return this.dcPublisher;
	}

	@Override
	public String[] getDcRelation() {
		return this.dcRelation;
	}

	@Override
	public String[] getDcRights() {
		return this.dcRights;
	}

	@Override
	public String[] getDcSource() {
		return this.dcSource;
	}

	@Override
	public String[] getDcSubject() {
		return this.dcSubject;
	}

	@Override
	public String[] getDcTitle() {
		return this.dcTitle;
	}

	@Override
	public String[] getDcType() {
		return this.dcType;
	}

	@Override
	public String[] getDctermsAlternative() {
		return this.dctermsAlternative;
	}

	@Override
	public String[] getDctermsConformsTo() {
		return this.dctermsConformsTo;
	}

	@Override
	public Date[] getDctermsCreated() {
		return this.dctermsCreated;
	}

	@Override
	public String[] getDctermsExtent() {
		return this.dctermsExtent;
	}

	@Override
	public String[] getDctermsHasFormat() {
		return this.dctermsHasFormat;
	}

	@Override
	public String[] getDctermsHasPart() {
		return this.dctermsHasPart;
	}

	@Override
	public String[] getDctermsHasVersion() {
		return this.dctermsHasVersion;
	}

	@Override
	public String[] getDctermsIsFormatOf() {
		return this.dctermsIsFormatOf;
	}

	@Override
	public String[] getDctermsIsPartOf() {
		return this.dctermsIsPartOf;
	}

	@Override
	public String[] getDctermsIsReferencedBy() {
		return this.dctermsIsReferencedBy;
	}

	@Override
	public String[] getDctermsIsReplacedBy() {
		return this.dctermsIsReplacedBy;
	}

	@Override
	public String[] getDctermsIsRequiredBy() {
		return this.dctermsIsRequiredBy;
	}

	@Override
	public String[] getDctermsIssued() {
		return this.dctermsIssued;
	}

	@Override
	public String[] getDctermsIsVersionOf() {
		return this.dctermsIsVersionOf;
	}

	@Override
	public String[] getDctermsMedium() {
		return this.dctermsMedium;
	}

	@Override
	public String[] getDctermsProvenance() {
		return this.dctermsProvenance;
	}

	@Override
	public String[] getDctermsReferences() {
		return this.dctermsReferences;
	}

	@Override
	public String[] getDctermsReplaces() {
		return this.dctermsReplaces;
	}

	@Override
	public String[] getDctermsRequires() {
		return this.dctermsRequires;
	}

	@Override
	public String[] getDctermsSpatial() {
		return this.dctermsSpatial;
	}

	@Override
	public String[] getDctermsTOC() {
		return this.dctermsTOC;
	}

	@Override
	public String[] getDctermsTemporal() {
		return this.dctermsTemporal;
	}

	@Override
	public DocType getEdmType() {
		return this.edmType;
	}

	@Override
	public String getEdmCurrentLocation() {
		return this.edmCurrentLocation;
	}

	@Override
	public ObjectId getProxyId() {
		return this.proxyId;
	}

	@Override
	public String getEdmIsNextInSequence() {
		return this.edmIsNextInSequence;
	}

}
