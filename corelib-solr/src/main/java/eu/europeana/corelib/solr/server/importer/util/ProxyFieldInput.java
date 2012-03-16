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
package eu.europeana.corelib.solr.server.importer.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.common.SolrInputDocument;

import eu.europeana.corelib.definitions.jibx.Aggregation;
import eu.europeana.corelib.definitions.jibx.DCTermsType.Choice;
import eu.europeana.corelib.definitions.jibx.EdmType;
import eu.europeana.corelib.definitions.jibx.ProvidedCHOType;
import eu.europeana.corelib.definitions.jibx.ResourceType;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.definitions.solr.DocType;
import eu.europeana.corelib.solr.entity.ProxyImpl;
import eu.europeana.corelib.solr.server.MongoDBServer;
import eu.europeana.corelib.solr.utils.MongoUtils;
import eu.europeana.corelib.solr.utils.SolrUtils;

/**
 * Constructor for the Proxy Entity
 * 
 * @author Yorgos.Mamakis@ kb.nl
 */
public final class ProxyFieldInput {

	private ProxyFieldInput() {

	}

	/**
	 * Create a SolrInputDocument with the Proxy fields filled in
	 * 
	 * @param providedCHO
	 *            The JiBX ProvidedCHO Entity
	 * @param solrInputDocument
	 *            The SolrInputDocument to alter
	 * @return The altered SolrInputDocument with the Proxy fields filled in
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static SolrInputDocument createProxySolrFields(
			ProvidedCHOType providedCHO, SolrInputDocument solrInputDocument)
			throws InstantiationException, IllegalAccessException {
		solrInputDocument.addField(EdmLabel.ORE_PROXY.toString(),
				providedCHO.getAbout());
		solrInputDocument.addField(EdmLabel.EDM_TYPE.toString(), SolrUtils
				.exists(EdmType.class, (providedCHO.getType())).toString());
		solrInputDocument.addField(
				EdmLabel.EDM_CURRENT_LOCATION.toString(),
				SolrUtils.exists(ResourceType.class,
						(providedCHO.getCurrentLocation())).getResource());

		solrInputDocument.addField(EdmLabel.ORE_PROXY_FOR.toString(),
				SolrUtils.exists(String.class, providedCHO.getAbout()));

		// Retrieve the dcterms namespace fields
		List<eu.europeana.corelib.definitions.jibx.DCTermsType.Choice> dcTermsList = providedCHO
				.getChoiceList();
		if (dcTermsList != null) {
			for (eu.europeana.corelib.definitions.jibx.DCTermsType.Choice choice : dcTermsList) {
				if (choice.getAlternative() != null) {
					solrInputDocument.addField(EdmLabel.DCTERMS_ALTERNATIVE
							.toString(), choice.getAlternative().getString());
				}
				if (choice.getConformsTo() != null) {
					solrInputDocument
							.addField(
									EdmLabel.DCTERMS_CONFORMS_TO.toString(),
									choice.getConformsTo().getResource() != null ? choice
											.getConformsTo().getResource()
											: choice.getConformsTo()
													.getString());
				}
				if (choice.getCreated() != null) {
					solrInputDocument.addField(EdmLabel.DCTERMS_CREATED
							.toString(),
							choice.getCreated().getResource() != null ? choice
									.getCreated().getResource() : choice
									.getCreated().getString());
				}
				if (choice.getExtent() != null) {
					solrInputDocument.addField(EdmLabel.DCTERMS_EXTENT
							.toString(),
							choice.getExtent().getResource() != null ? choice
									.getExtent().getResource() : choice
									.getExtent().getString());
				}
				if (choice.getHasFormat() != null) {
					solrInputDocument
							.addField(
									EdmLabel.DCTERMS_HAS_FORMAT.toString(),
									choice.getHasFormat().getResource() != null ? choice
											.getHasFormat().getResource()
											: choice.getHasFormat().getString());
				}
				if (choice.getHasPart() != null) {
					solrInputDocument.addField(EdmLabel.DCTERMS_HAS_PART
							.toString(),
							choice.getHasPart().getResource() != null ? choice
									.getHasPart().getResource() : choice
									.getHasPart().getString());
				}
				if (choice.getHasVersion() != null) {
					solrInputDocument
							.addField(
									EdmLabel.DCTERMS_HAS_VERSION.toString(),
									choice.getHasVersion().getResource() != null ? choice
											.getHasVersion().getResource()
											: choice.getHasVersion()
													.getString());
				}
				if (choice.getIsFormatOf() != null) {
					solrInputDocument
							.addField(
									EdmLabel.DCTERMS_IS_FORMAT_OF.toString(),
									choice.getIsFormatOf().getResource() != null ? choice
											.getIsFormatOf().getResource()
											: choice.getIsFormatOf()
													.getString());
				}
				if (choice.getIsPartOf() != null) {
					solrInputDocument.addField(EdmLabel.DCTERMS_IS_PART_OF
							.toString(),
							choice.getIsPartOf().getResource() != null ? choice
									.getIsPartOf().getResource() : choice
									.getIsPartOf().getString());
				}
				if (choice.getIsReferencedBy() != null) {
					solrInputDocument
							.addField(EdmLabel.DCTERMS_IS_REFERENCED_BY
									.toString(), choice.getIsReferencedBy()
									.getResource() != null ? choice
									.getIsReferencedBy().getResource() : choice
									.getIsReferencedBy().getString());
				}
				if (choice.getIsReplacedBy() != null) {
					solrInputDocument
							.addField(EdmLabel.DCTERMS_IS_REPLACED_BY
									.toString(), choice.getIsReplacedBy()
									.getResource() != null ? choice
									.getIsReplacedBy().getResource() : choice
									.getIsReplacedBy().getString());
				}
				if (choice.getIsRequiredBy() != null) {
					solrInputDocument
							.addField(EdmLabel.DCTERMS_IS_REQUIRED_BY
									.toString(), choice.getIsRequiredBy()
									.getResource() != null ? choice
									.getIsRequiredBy().getResource() : choice
									.getIsRequiredBy().getString());

				}
				if (choice.getIssued() != null) {
					solrInputDocument.addField(EdmLabel.DCTERMS_ISSUED
							.toString(),
							choice.getIssued().getResource() != null ? choice
									.getIssued().getResource() : choice
									.getIssued().getString());
				}
				if (choice.getIsVersionOf() != null) {
					solrInputDocument
							.addField(
									EdmLabel.DCTERMS_IS_VERSION_OF.toString(),
									choice.getIsVersionOf().getResource() != null ? choice
											.getIsVersionOf().getResource()
											: choice.getIsVersionOf()
													.getString());
				}
				if (choice.getMedium() != null) {
					solrInputDocument.addField(EdmLabel.DCTERMS_MEDIUM
							.toString(),
							choice.getMedium().getResource() != null ? choice
									.getMedium().getResource() : choice
									.getMedium().getString());
				}
				if (choice.getProvenance() != null) {
					solrInputDocument
							.addField(
									EdmLabel.DCTERMS_PROVENANCE.toString(),
									choice.getProvenance().getResource() != null ? choice
											.getProvenance().getResource()
											: choice.getProvenance()
													.getString());
				}
				if (choice.getReferences() != null) {
					solrInputDocument
							.addField(
									EdmLabel.DCTERMS_REFERENCES.toString(),
									choice.getReferences().getResource() != null ? choice
											.getReferences().getResource()
											: choice.getReferences()
													.getString());
				}
				if (choice.getReplaces() != null) {
					solrInputDocument.addField(EdmLabel.DCTERMS_REPLACES
							.toString(),
							choice.getReplaces().getResource() != null ? choice
									.getReplaces().getResource() : choice
									.getReplaces().getString());
				}
				if (choice.getRequires() != null) {
					solrInputDocument.addField(EdmLabel.DCTERMS_REQUIRES
							.toString(),
							choice.getRequires().getResource() != null ? choice
									.getRequires().getResource() : choice
									.getRequires().getString());
				}
				if (choice.getSpatial() != null) {
					solrInputDocument.addField(EdmLabel.DCTERMS_SPATIAL
							.toString(),
							choice.getSpatial().getResource() != null ? choice
									.getSpatial().getResource() : choice
									.getSpatial().getString());
				}
				if (choice.getTableOfContents() != null) {
					solrInputDocument
							.addField(EdmLabel.DCTERMS_TABLE_OF_CONTENTS
									.toString(), choice.getTableOfContents()
									.getResource() != null ? choice
									.getTableOfContents().getResource()
									: choice.getTableOfContents().getString());
				}
				if (choice.getTemporal() != null) {
					solrInputDocument.addField(EdmLabel.DCTERMS_TEMPORAL
							.toString(),
							choice.getTemporal().getResource() != null ? choice
									.getTemporal().getResource() : choice
									.getTemporal().getString());
				}
			}
		}

		// Retrieve the dc namespace fields
		List<eu.europeana.corelib.definitions.jibx.DCType.Choice> dcList = providedCHO
				.getChoiceList1s();
		if (dcList != null) {
			for (eu.europeana.corelib.definitions.jibx.DCType.Choice choice : dcList) {
				if (choice.getContributor() != null) {
					solrInputDocument
							.addField(
									EdmLabel.DC_CONTRIBUTOR.toString(),
									choice.getContributor().getResource() != null ? choice
											.getContributor().getResource()
											: choice.getContributor()
													.getString());
				}
				if (choice.getCoverage() != null) {
					solrInputDocument.addField(EdmLabel.DC_COVERAGE.toString(),
							choice.getCoverage().getResource() != null ? choice
									.getCoverage().getResource() : choice
									.getCoverage().getString());
				}
				if (choice.getCreator() != null) {
					solrInputDocument.addField(EdmLabel.DC_CREATOR.toString(),
							choice.getCreator().getResource() != null ? choice
									.getCreator().getResource() : choice
									.getCreator().getString());
				}
				if (choice.getDate() != null) {
					solrInputDocument.addField(EdmLabel.DC_DATE.toString(),
							choice.getDate().getResource() != null ? choice
									.getDate().getResource() : choice.getDate()
									.getString());
				}
				if (choice.getDescription() != null) {
					solrInputDocument
							.addField(
									EdmLabel.DC_DESCRIPTION.toString(),
									choice.getDescription().getResource() != null ? choice
											.getDescription().getResource()
											: choice.getDescription()
													.getString());
				}
				if (choice.getFormat() != null) {
					solrInputDocument.addField(EdmLabel.DC_FORMAT.toString(),
							choice.getFormat().getResource() != null ? choice
									.getFormat().getResource() : choice
									.getFormat().getString());
				}
				if (choice.getIdentifier() != null) {
					solrInputDocument.addField(EdmLabel.DC_IDENTIFIER
							.toString(), choice.getIdentifier().getString());
				}
				if (choice.getLanguage() != null) {
					solrInputDocument.addField(EdmLabel.DC_LANGUAGE.toString(),
							choice.getLanguage().getString());
				}
				if (choice.getPublisher() != null) {
					solrInputDocument
							.addField(
									EdmLabel.DC_PUBLISHER.toString(),
									choice.getPublisher().getResource() != null ? choice
											.getPublisher().getResource()
											: choice.getPublisher().getString());
				}
				if (choice.getPublisher() != null) {
					solrInputDocument.addField(EdmLabel.DC_RELATION.toString(),
							choice.getRelation().getResource() != null ? choice
									.getRelation().getResource() : choice
									.getRelation().getString());
				}
				if (choice.getRights() != null) {
					solrInputDocument.addField(EdmLabel.PRX_DC_RIGHTS
							.toString(),
							choice.getRights().getResource() != null ? choice
									.getRights().getResource() : choice
									.getRights().getString());
				}
				if (choice.getSource() != null) {
					solrInputDocument.addField(EdmLabel.DC_SOURCE.toString(),
							choice.getSource().getResource() != null ? choice
									.getSource().getResource() : choice
									.getSource().getString());
				}
				if (choice.getSubject() != null) {
					solrInputDocument.addField(EdmLabel.DC_SUBJECT.toString(),
							choice.getSubject().getResource() != null ? choice
									.getSubject().getResource() : choice
									.getSubject().getString());
				}
				if (choice.getTitle() != null) {
					solrInputDocument.addField(EdmLabel.DC_TITLE.toString(),
							choice.getTitle().getString());
				}
				if (choice.getType() != null) {
					solrInputDocument.addField(EdmLabel.DC_TYPE.toString(),
							choice.getType().getResource() != null ? choice
									.getType().getResource() : choice.getType()
									.getString());
				}
			}
		}
		return solrInputDocument;
	}

	/**
	 * Construct the fields of a Proxy MongoDB Entity. The entity is
	 * instantiated when reading the ProvidedCHO
	 * 
	 * @param mongoProxy
	 *            The Proxy MongoDB Entity to save or update
	 * @param providedCHO
	 *            The ProvidedCHO JiBX Entity
	 * @param mongoServer
	 *            The MongoDB Server to save the entity to
	 * @return The MongoDB Proxy Entity
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static ProxyImpl createProxyMongoFields(ProxyImpl mongoProxy,
			ProvidedCHOType providedCHO, MongoDBServer mongoServer)
			throws InstantiationException, IllegalAccessException {

		mongoProxy.setAbout(providedCHO.getAbout());

		mongoProxy.setEdmCurrentLocation(SolrUtils.exists(ResourceType.class,
				(providedCHO.getCurrentLocation())).getResource());
		mongoProxy.setEdmType(DocType.get(SolrUtils.exists(EdmType.class,
				(providedCHO.getType())).toString()));

		mongoProxy.setProxyFor(SolrUtils.exists(String.class,
				providedCHO.getAbout()));

		List<String> alternatives = new ArrayList<String>();
		List<String> conformsTo = new ArrayList<String>();
		List<String> created = new ArrayList<String>();
		List<String> extent = new ArrayList<String>();
		List<String> hasFormat = new ArrayList<String>();
		List<String> hasPart = new ArrayList<String>();
		List<String> hasVersion = new ArrayList<String>();
		List<String> isFormatOf = new ArrayList<String>();
		List<String> isPartOf = new ArrayList<String>();
		List<String> isReferencedBy = new ArrayList<String>();
		List<String> isReplacedBy = new ArrayList<String>();
		List<String> isRequiredBy = new ArrayList<String>();
		List<String> issued = new ArrayList<String>();
		List<String> isVersionOf = new ArrayList<String>();
		List<String> medium = new ArrayList<String>();
		List<String> provenance = new ArrayList<String>();
		List<String> references = new ArrayList<String>();
		List<String> replaces = new ArrayList<String>();
		List<String> requires = new ArrayList<String>();
		List<String> spatial = new ArrayList<String>();
		List<String> tableOfContents = new ArrayList<String>();
		List<String> temporal = new ArrayList<String>();

		List<Choice> dcTermsList = providedCHO.getChoiceList();
		List<eu.europeana.corelib.definitions.jibx.DCType.Choice> dcList = providedCHO
				.getChoiceList1s();

		if (dcTermsList != null) {
			for (Choice dcTerm : dcTermsList) {

				if (dcTerm.getAlternative() != null) {
					alternatives.add(dcTerm.getAlternative().getString());
				}
				if (dcTerm.getConformsTo() != null) {
					conformsTo
							.add(dcTerm.getConformsTo().getResource() != null ? dcTerm
									.getConformsTo().getResource() : dcTerm
									.getConformsTo().getString());
				}
				if (dcTerm.getCreated() != null) {
					created.add(dcTerm.getCreated().getResource() != null ? dcTerm
							.getCreated().getResource() : dcTerm.getCreated()
							.getString());
				}
				if (dcTerm.getExtent() != null) {
					extent.add(dcTerm.getExtent().getResource() != null ? dcTerm
							.getExtent().getResource() : dcTerm.getExtent()
							.getString());
				}
				if (dcTerm.getHasFormat() != null) {
					hasFormat
							.add(dcTerm.getHasFormat().getResource() != null ? dcTerm
									.getHasFormat().getResource() : dcTerm
									.getHasFormat().getString());
				}
				if (dcTerm.getHasPart() != null) {
					hasPart.add(dcTerm.getHasPart().getResource() != null ? dcTerm
							.getHasPart().getResource() : dcTerm.getHasPart()
							.getString());
				}
				if (dcTerm.getHasVersion() != null) {
					hasVersion
							.add(dcTerm.getHasVersion().getResource() != null ? dcTerm
									.getHasVersion().getResource() : dcTerm
									.getHasVersion().getString());
				}
				if (dcTerm.getIsFormatOf() != null) {
					isFormatOf
							.add(dcTerm.getIsFormatOf().getResource() != null ? dcTerm
									.getIsFormatOf().getResource() : dcTerm
									.getIsFormatOf().getString());
				}
				if (dcTerm.getIsPartOf() != null) {
					isPartOf.add(dcTerm.getIsPartOf().getResource() != null ? dcTerm
							.getIsPartOf().getResource() : dcTerm.getIsPartOf()
							.getString());
				}
				if (dcTerm.getIsReferencedBy() != null) {
					isReferencedBy
							.add(dcTerm.getIsReferencedBy().getResource() != null ? dcTerm
									.getIsReferencedBy().getResource() : dcTerm
									.getIsReferencedBy().getString());
				}
				if (dcTerm.getIsReplacedBy() != null) {
					isReplacedBy
							.add(dcTerm.getIsReplacedBy().getResource() != null ? dcTerm
									.getIsReplacedBy().getResource() : dcTerm
									.getIsReplacedBy().getString());
				}
				if (dcTerm.getIsRequiredBy() != null) {
					isRequiredBy
							.add(dcTerm.getIsRequiredBy().getResource() != null ? dcTerm
									.getIsRequiredBy().getResource() : dcTerm
									.getIsRequiredBy().getString());
				}
				if (dcTerm.getIssued() != null) {
					issued.add(dcTerm.getIssued().getResource() != null ? dcTerm
							.getIssued().getResource() : dcTerm.getIssued()
							.getString());
				}
				if (dcTerm.getIsVersionOf() != null) {
					isVersionOf
							.add(dcTerm.getIsVersionOf().getResource() != null ? dcTerm
									.getIsVersionOf().getResource() : dcTerm
									.getIsVersionOf().getString());
				}
				if (dcTerm.getMedium() != null) {
					medium.add(dcTerm.getMedium().getResource() != null ? dcTerm
							.getMedium().getResource() : dcTerm.getMedium()
							.getString());
				}
				if (dcTerm.getProvenance() != null) {
					provenance
							.add(dcTerm.getProvenance().getResource() != null ? dcTerm
									.getProvenance().getResource() : dcTerm
									.getProvenance().getString());
				}
				if (dcTerm.getReferences() != null) {
					references
							.add(dcTerm.getReferences().getResource() != null ? dcTerm
									.getReferences().getResource() : dcTerm
									.getReferences().getString());
				}
				if (dcTerm.getReplaces() != null) {
					replaces.add(dcTerm.getReplaces().getResource() != null ? dcTerm
							.getReplaces().getResource() : dcTerm.getReplaces()
							.getString());
				}
				if (dcTerm.getRequires() != null) {
					requires.add(dcTerm.getRequires().getResource() != null ? dcTerm
							.getRequires().getResource() : dcTerm.getRequires()
							.getString());
				}
				if (dcTerm.getSpatial() != null) {
					spatial.add(dcTerm.getSpatial().getResource() != null ? dcTerm
							.getSpatial().getResource() : dcTerm.getSpatial()
							.getString());
				}
				if (dcTerm.getTableOfContents() != null) {
					tableOfContents.add(dcTerm.getTableOfContents()
							.getResource() != null ? dcTerm
							.getTableOfContents().getResource() : dcTerm
							.getTableOfContents().getString());
				}
				if (dcTerm.getTemporal() != null) {
					temporal.add(dcTerm.getTemporal().getResource() != null ? dcTerm
							.getTemporal().getResource() : dcTerm.getTemporal()
							.getString());
				}
			}
		}
		List<String> contributor = new ArrayList<String>();
		List<String> coverage = new ArrayList<String>();
		List<String> creator = new ArrayList<String>();
		List<String> date = new ArrayList<String>();
		List<String> description = new ArrayList<String>();
		List<String> format = new ArrayList<String>();
		List<String> identifier = new ArrayList<String>();
		List<String> language = new ArrayList<String>();
		List<String> publisher = new ArrayList<String>();
		List<String> relation = new ArrayList<String>();
		List<String> rights = new ArrayList<String>();
		List<String> source = new ArrayList<String>();
		List<String> subject = new ArrayList<String>();
		List<String> title = new ArrayList<String>();
		List<String> type = new ArrayList<String>();

		if (dcList != null) {
			for (eu.europeana.corelib.definitions.jibx.DCType.Choice dc : dcList) {
				if (dc.getContributor() != null) {
					contributor
							.add(dc.getContributor().getResource() != null ? dc
									.getContributor().getResource() : dc
									.getContributor().getString());
				}
				if (dc.getCoverage() != null) {
					coverage.add(dc.getCoverage().getResource() != null ? dc
							.getCoverage().getResource() : dc.getCoverage()
							.getString());
				}
				if (dc.getCreator() != null) {
					creator.add(dc.getCreator().getResource() != null ? dc
							.getCreator().getResource() : dc.getCreator()
							.getString());
				}
				if (dc.getDate() != null) {
					date.add(dc.getDate().getResource() != null ? dc.getDate()
							.getResource() : dc.getDate().getString());
				}
				if (dc.getDescription() != null) {
					description
							.add(dc.getDescription().getResource() != null ? dc
									.getDescription().getResource() : dc
									.getDescription().getString());
				}
				if (dc.getFormat() != null) {
					format.add(dc.getFormat().getResource() != null ? dc
							.getFormat().getResource() : dc.getFormat()
							.getString());
				}
				if (dc.getIdentifier() != null) {
					identifier.add(dc.getIdentifier().getString());
				}
				if (dc.getLanguage() != null) {
					language.add(dc.getLanguage().getString());
				}
				if (dc.getPublisher() != null) {
					publisher.add(dc.getPublisher().getResource() != null ? dc
							.getPublisher().getResource() : dc.getPublisher()
							.getString());
				}
				if (dc.getRelation() != null) {
					relation.add(dc.getRelation().getResource() != null ? dc
							.getRelation().getResource() : dc.getRelation()
							.getString());
				}
				if (dc.getRights() != null) {
					rights.add(dc.getRights().getResource() != null ? dc
							.getRights().getResource() : dc.getRights()
							.getString());
				}
				if (dc.getSource() != null) {
					source.add(dc.getSource().getResource() != null ? dc
							.getSource().getResource() : dc.getSource()
							.getString());
				}
				if (dc.getSubject() != null) {
					subject.add(dc.getSubject().getResource() != null ? dc
							.getSubject().getResource() : dc.getSubject()
							.getString());
				}
				if (dc.getTitle() != null) {
					title.add(dc.getTitle().getString());
				}
				if (dc.getType() != null) {
					type.add(dc.getType().getResource() != null ? dc.getType()
							.getResource() : dc.getType().getString());
				}
			}
		}
		mongoProxy.setDctermsAlternative(alternatives
				.toArray(new String[alternatives.size()]));
		mongoProxy.setDctermsConformsTo(conformsTo
				.toArray(new String[conformsTo.size()]));
		mongoProxy
				.setDctermsCreated(created.toArray(new String[created.size()]));
		mongoProxy.setDctermsExtent(extent.toArray(new String[extent.size()]));
		mongoProxy.setDctermsHasFormat(hasFormat.toArray(new String[hasFormat
				.size()]));
		mongoProxy
				.setDctermsHasPart(hasPart.toArray(new String[hasPart.size()]));
		mongoProxy.setDctermsHasVersion(hasVersion
				.toArray(new String[hasVersion.size()]));
		mongoProxy.setDctermsIsFormatOf(isFormatOf
				.toArray(new String[isFormatOf.size()]));
		mongoProxy.setDctermsIsPartOf(isPartOf.toArray(new String[isPartOf
				.size()]));
		mongoProxy.setDctermsIsReferencedBy(isReferencedBy
				.toArray(new String[isReferencedBy.size()]));
		mongoProxy.setDctermsIsReplacedBy(isReplacedBy
				.toArray(new String[isReplacedBy.size()]));
		mongoProxy.setDctermsIsRequiredBy(isRequiredBy
				.toArray(new String[isRequiredBy.size()]));
		mongoProxy.setDctermsIssued(issued.toArray(new String[issued.size()]));
		mongoProxy.setDctermsIsVersionOf(isVersionOf
				.toArray(new String[isVersionOf.size()]));
		mongoProxy.setDctermsMedium(medium.toArray(new String[medium.size()]));
		mongoProxy.setDctermsProvenance(provenance
				.toArray(new String[provenance.size()]));
		mongoProxy.setDctermsReferences(references
				.toArray(new String[references.size()]));
		mongoProxy.setDctermsReplaces(replaces.toArray(new String[replaces
				.size()]));
		mongoProxy.setDctermsRequires(requires.toArray(new String[requires
				.size()]));
		mongoProxy
				.setDctermsSpatial(spatial.toArray(new String[spatial.size()]));
		mongoProxy.setDctermsTemporal(temporal.toArray(new String[temporal
				.size()]));
		mongoProxy.setDctermsTOC(tableOfContents
				.toArray(new String[tableOfContents.size()]));

		mongoProxy.setDcContributor(contributor.toArray(new String[contributor
				.size()]));
		mongoProxy.setDcCoverage(coverage.toArray(new String[coverage.size()]));
		mongoProxy.setDcCreator(creator.toArray(new String[creator.size()]));
		mongoProxy.setDcDate(date.toArray(new String[date.size()]));
		mongoProxy.setDcDescription(description.toArray(new String[description
				.size()]));
		mongoProxy.setDcFormat(format.toArray(new String[format.size()]));
		mongoProxy.setDcIdentifier(identifier.toArray(new String[identifier
				.size()]));
		mongoProxy.setDcLanguage(language.toArray(new String[language.size()]));
		mongoProxy
				.setDcPublisher(publisher.toArray(new String[publisher.size()]));
		mongoProxy.setDcRelation(relation.toArray(new String[relation.size()]));
		mongoProxy.setDcRights(rights.toArray(new String[rights.size()]));
		mongoProxy.setDcSource(source.toArray(new String[source.size()]));
		mongoProxy.setDcSubject(subject.toArray(new String[subject.size()]));
		mongoProxy.setDcTitle(title.toArray(new String[title.size()]));
		mongoProxy.setDcType(type.toArray(new String[type.size()]));
		if (mongoServer.searchByAbout(ProxyImpl.class, mongoProxy.getAbout()) != null) {
			MongoUtils.updateProxy(mongoProxy, mongoServer);
		} else {
			mongoServer.getDatastore().save(mongoProxy);
		}
		return mongoProxy;
	}

	/**
	 * Set the ProxyIn field after the aggregations are created
	 * 
	 * @param proxy
	 *            The MongoDB proxy Entity
	 * @param aggregation
	 *            The JiBX Aggregation Entity
	 * @param mongoServer
	 *            The MongoDB Server to save the Entity
	 * @return The proxy with the proxyIn field
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */

	public static ProxyImpl addProxyForMongo(ProxyImpl proxy,
			Aggregation aggregation, MongoDBServer mongoServer)
			throws InstantiationException, IllegalAccessException {

		proxy.setProxyIn(SolrUtils.exists(String.class, aggregation.getAbout()));
		return proxy;
	}

	/**
	 * Set the ProxyIn field for a SolrInputDocument
	 * 
	 * @param aggregation
	 *            The JiBX Aggregation Entity
	 * @param solrInputDocument
	 *            The SolrInputDocument
	 * @return The SolrInputDocument with the ProxyIn field
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static SolrInputDocument addProxyForSolr(Aggregation aggregation,
			SolrInputDocument solrInputDocument) throws InstantiationException,
			IllegalAccessException {
		solrInputDocument.addField(EdmLabel.ORE_PROXY_IN.toString(),
				SolrUtils.exists(String.class, aggregation.getAbout()));
		return solrInputDocument;
	}

	public static void deleteProxyFromMongo(String about,
			MongoDBServer mongoServer) {
		MongoUtils.delete(ProxyImpl.class, about, mongoServer);
	}
}
