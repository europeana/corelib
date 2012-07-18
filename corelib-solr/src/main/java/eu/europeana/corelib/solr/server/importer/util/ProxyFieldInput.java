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

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.common.SolrInputDocument;

import eu.europeana.corelib.definitions.jibx.Aggregation;
import eu.europeana.corelib.definitions.jibx.EdmType;
import eu.europeana.corelib.definitions.jibx.ProvidedCHOType;
import eu.europeana.corelib.definitions.jibx.RDF;
import eu.europeana.corelib.definitions.jibx.ResourceType;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.definitions.solr.DocType;
import eu.europeana.corelib.solr.MongoServer;
import eu.europeana.corelib.solr.entity.ProxyImpl;
import eu.europeana.corelib.solr.server.EdmMongoServer;
import eu.europeana.corelib.solr.utils.MongoUtils;
import eu.europeana.corelib.solr.utils.SolrUtils;
import eu.europeana.corelib.utils.StringArrayUtils;

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
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	public static SolrInputDocument createProxySolrFields(
			ProvidedCHOType providedCHO, SolrInputDocument solrInputDocument, RDF rdf, boolean shouldDereference)
			throws InstantiationException, IllegalAccessException,
			MalformedURLException, IOException {
		solrInputDocument.addField(EdmLabel.PROVIDER_ORE_PROXY.toString(),
				providedCHO.getAbout());
		solrInputDocument.addField(EdmLabel.PROVIDER_EDM_TYPE.toString(), SolrUtils
				.exists(EdmType.class, (providedCHO.getType())).toString());
		solrInputDocument.addField(
				EdmLabel.PROVIDER_EDM_CURRENT_LOCATION_LAT.toString(),
				SolrUtils.exists(ResourceType.class,
						(providedCHO.getCurrentLocation())).getResource());
		solrInputDocument.addField(
				EdmLabel.PROVIDER_EDM_CURRENT_LOCATION_LONG.toString(),
				SolrUtils.exists(ResourceType.class,
						(providedCHO.getCurrentLocation())).getResource());
		solrInputDocument.addField(EdmLabel.PROVIDER_EDM_IS_NEXT_IN_SEQUENCE.toString(),  SolrUtils
				.exists(ResourceType.class, (providedCHO.getIsNextInSequence())).getResource());
		solrInputDocument.addField(EdmLabel.PROVIDER_ORE_PROXY_FOR.toString(),
				SolrUtils.exists(String.class, providedCHO.getAbout()));

		// Retrieve the dcterms and dc namespace fields
		List<eu.europeana.corelib.definitions.jibx.EuropeanaType.Choice> europeanaTypeList = providedCHO
				.getChoiceList();
		if (europeanaTypeList != null) {
			for (eu.europeana.corelib.definitions.jibx.EuropeanaType.Choice choice : europeanaTypeList) {
				if (choice.getAlternative() != null) {
					solrInputDocument.addField(EdmLabel.PROVIDER_DCTERMS_ALTERNATIVE
							.toString(), choice.getAlternative().getString());
				}
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROVIDER_DCTERMS_CONFORMS_TO, choice.getConformsTo(), rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROVIDER_DCTERMS_CREATED, choice.getCreated(), rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROVIDER_DCTERMS_EXTENT, choice.getExtent(), rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROVIDER_DCTERMS_HAS_FORMAT, choice.getHasFormat(), rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROVIDER_DCTERMS_HAS_PART, choice.getHasPart(), rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROVIDER_DCTERMS_HAS_VERSION, choice.getHasVersion(), rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROVIDER_DCTERMS_IS_FORMAT_OF, choice.getIsFormatOf(), rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROVIDER_DCTERMS_IS_PART_OF, choice.getIsPartOf(), rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROVIDER_DCTERMS_IS_REFERENCED_BY,
						choice.getIsReferencedBy(), rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROVIDER_DCTERMS_IS_REPLACED_BY,
						choice.getIsReplacedBy(), rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROVIDER_DCTERMS_IS_REQUIRED_BY,
						choice.getIsRequiredBy(), rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROVIDER_DCTERMS_ISSUED, choice.getIssued(), rdf, shouldDereference);
				SolrUtils
						.addResourceOrLiteralType(solrInputDocument,
								EdmLabel.PROVIDER_DCTERMS_IS_VERSION_OF,
								choice.getIsVersionOf(), rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROVIDER_DCTERMS_MEDIUM, choice.getMedium(), rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROVIDER_DCTERMS_PROVENANCE, choice.getProvenance(), rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROVIDER_DCTERMS_REFERENCES, choice.getReferences(), rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROVIDER_DCTERMS_REPLACES, choice.getReplaces(), rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROVIDER_DCTERMS_REQUIRES, choice.getRequires(), rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROVIDER_DCTERMS_SPATIAL, choice.getSpatial(), rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROVIDER_DCTERMS_TABLE_OF_CONTENTS,
						choice.getTableOfContents(), rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROVIDER_DCTERMS_TEMPORAL, choice.getTemporal(), rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROVIDER_DC_CONTRIBUTOR, choice.getContributor(), rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROVIDER_DC_COVERAGE, choice.getCoverage(), rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROVIDER_DC_CREATOR, choice.getCreator(), rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROVIDER_DC_DATE, choice.getDate(), rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROVIDER_DC_DESCRIPTION, choice.getDescription(), rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROVIDER_DC_FORMAT, choice.getFormat(), rdf, shouldDereference);
				if (choice.getIdentifier() != null) {
					solrInputDocument.addField(EdmLabel.PROVIDER_DC_IDENTIFIER
							.toString(), choice.getIdentifier().getString());
				}
				if (choice.getLanguage() != null) {
					solrInputDocument.addField(EdmLabel.PROVIDER_DC_LANGUAGE.toString(),
							choice.getLanguage().getString());
				}
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROVIDER_DC_PUBLISHER, choice.getPublisher(), rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROVIDER_DC_RELATION, choice.getRelation(),rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROVIDER_DC_RIGHTS, choice.getRights(), rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROVIDER_DC_SOURCE, choice.getSource(), rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROVIDER_DC_SUBJECT, choice.getSubject(), rdf, shouldDereference);
				if (choice.getTitle() != null) {
					solrInputDocument.addField(EdmLabel.PROVIDER_DC_TITLE.toString(),
							choice.getTitle().getString());
				}
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROVIDER_DC_TYPE, choice.getType(), rdf, shouldDereference);
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
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	public static ProxyImpl createProxyMongoFields(ProxyImpl mongoProxy,
			ProvidedCHOType providedCHO, MongoServer mongoServer, RDF rdf, boolean shouldDereference)
			throws InstantiationException, IllegalAccessException, MalformedURLException, IOException {

		mongoProxy.setAbout(providedCHO.getAbout());

		mongoProxy.setEdmCurrentLocation(SolrUtils.exists(ResourceType.class,
				(providedCHO.getCurrentLocation())).getResource());
		mongoProxy.setEdmIsNextInSequence(SolrUtils.exists(ResourceType.class,
				(providedCHO.getIsNextInSequence())).getResource());
		mongoProxy.setEdmType(DocType.get(SolrUtils.exists(EdmType.class,
				(providedCHO.getType())).toString()));

		mongoProxy.setProxyFor(SolrUtils.exists(String.class,
				providedCHO.getAbout()));
		Map <String,List<String>> mapLists = new HashMap<String, List<String>>();
		List<String> alternatives = new ArrayList<String>();
		mapLists.put(EdmLabel.PROVIDER_DCTERMS_ALTERNATIVE.toString(), alternatives);
		List<String> conformsTo = new ArrayList<String>();
		mapLists.put(EdmLabel.PROVIDER_DCTERMS_CONFORMS_TO.toString(), conformsTo);
		List<String> created = new ArrayList<String>();
		mapLists.put(EdmLabel.PROVIDER_DCTERMS_CREATED.toString(), created);
		List<String> extent = new ArrayList<String>();
		mapLists.put(EdmLabel.PROVIDER_DCTERMS_EXTENT.toString(), extent);
		List<String> hasFormat = new ArrayList<String>();
		mapLists.put(EdmLabel.PROVIDER_DCTERMS_HAS_FORMAT.toString(), hasFormat);
		List<String> hasPart = new ArrayList<String>();
		mapLists.put(EdmLabel.PROVIDER_DCTERMS_HAS_PART.toString(), hasPart);
		List<String> hasVersion = new ArrayList<String>();
		mapLists.put(EdmLabel.PROVIDER_DCTERMS_HAS_VERSION.toString(), hasVersion);
		List<String> isFormatOf = new ArrayList<String>();
		mapLists.put(EdmLabel.PROVIDER_DCTERMS_IS_FORMAT_OF.toString(), isFormatOf);
		List<String> isPartOf = new ArrayList<String>();
		mapLists.put(EdmLabel.PROVIDER_DCTERMS_IS_PART_OF.toString(), isPartOf);
		List<String> isReferencedBy = new ArrayList<String>();
		mapLists.put(EdmLabel.PROVIDER_DCTERMS_IS_REFERENCED_BY.toString(), isReferencedBy);
		List<String> isReplacedBy = new ArrayList<String>();
		mapLists.put(EdmLabel.PROVIDER_DCTERMS_IS_REPLACED_BY.toString(), isReplacedBy);
		List<String> isRequiredBy = new ArrayList<String>();
		mapLists.put(EdmLabel.PROVIDER_DCTERMS_IS_REQUIRED_BY.toString(), isRequiredBy);
		List<String> issued = new ArrayList<String>();
		mapLists.put(EdmLabel.PROVIDER_DCTERMS_ISSUED.toString(), issued);
		List<String> isVersionOf = new ArrayList<String>();
		mapLists.put(EdmLabel.PROVIDER_DCTERMS_IS_VERSION_OF.toString(), isVersionOf);
		List<String> medium = new ArrayList<String>();
		mapLists.put(EdmLabel.PROVIDER_DCTERMS_MEDIUM.toString(), medium);
		List<String> provenance = new ArrayList<String>();
		mapLists.put(EdmLabel.PROVIDER_DCTERMS_PROVENANCE.toString(), provenance);
		List<String> references = new ArrayList<String>();
		mapLists.put(EdmLabel.PROVIDER_DCTERMS_REFERENCES.toString(), references);
		List<String> replaces = new ArrayList<String>();
		mapLists.put(EdmLabel.PROVIDER_DCTERMS_REPLACES.toString(), replaces);
		List<String> requires = new ArrayList<String>();
		mapLists.put(EdmLabel.PROVIDER_DCTERMS_REQUIRES.toString(), requires);
		List<String> spatial = new ArrayList<String>();
		mapLists.put(EdmLabel.PROVIDER_DCTERMS_SPATIAL.toString(), spatial);
		List<String> tableOfContents = new ArrayList<String>();
		mapLists.put(EdmLabel.PROVIDER_DCTERMS_TABLE_OF_CONTENTS.toString(), tableOfContents);
		List<String> temporal = new ArrayList<String>();
		mapLists.put(EdmLabel.PROVIDER_DCTERMS_TEMPORAL.toString(), temporal);
		List<String> contributor = new ArrayList<String>();
		mapLists.put(EdmLabel.PROVIDER_DC_CONTRIBUTOR.toString(), contributor);
		List<String> coverage = new ArrayList<String>();
		mapLists.put(EdmLabel.PROVIDER_DC_COVERAGE.toString(), coverage);
		List<String> creator = new ArrayList<String>();
		mapLists.put(EdmLabel.PROVIDER_DC_CREATOR.toString(), creator);
		List<String> date = new ArrayList<String>();
		mapLists.put(EdmLabel.PROVIDER_DC_DATE.toString(), date);
		List<String> description = new ArrayList<String>();
		mapLists.put(EdmLabel.PROVIDER_DC_DESCRIPTION.toString(), description);
		List<String> format = new ArrayList<String>();
		mapLists.put(EdmLabel.PROVIDER_DC_FORMAT.toString(), format);
		List<String> identifier = new ArrayList<String>();
		mapLists.put(EdmLabel.PROVIDER_DC_IDENTIFIER.toString(), identifier);
		List<String> language = new ArrayList<String>();
		mapLists.put(EdmLabel.PROVIDER_DC_LANGUAGE.toString(), language);
		List<String> publisher = new ArrayList<String>();
		mapLists.put(EdmLabel.PROVIDER_DC_PUBLISHER.toString(), publisher);
		List<String> relation = new ArrayList<String>();
		mapLists.put(EdmLabel.PROVIDER_DC_RELATION.toString(), relation);

		List<String> rights = new ArrayList<String>();
		mapLists.put(EdmLabel.PROVIDER_DC_RIGHTS.toString(), rights);
		List<String> source = new ArrayList<String>();
		mapLists.put(EdmLabel.PROVIDER_DC_SOURCE.toString(), source);
		List<String> subject = new ArrayList<String>();
		mapLists.put(EdmLabel.PROVIDER_DC_SUBJECT.toString(), subject);
		List<String> title = new ArrayList<String>();
		mapLists.put(EdmLabel.PROVIDER_DC_TITLE.toString(), title);
		List<String> type = new ArrayList<String>();
		mapLists.put(EdmLabel.PROVIDER_DC_TYPE.toString(), type);

		
		List<eu.europeana.corelib.definitions.jibx.EuropeanaType.Choice> europeanaTypeList = providedCHO.getChoiceList();
		

		if (europeanaTypeList != null) {
			for (eu.europeana.corelib.definitions.jibx.EuropeanaType.Choice europeanaType : europeanaTypeList) {

				if (europeanaType.getAlternative() != null) {
					alternatives.add(europeanaType.getAlternative().getString());
				}
				SolrUtils.addResourceOrLiteralType(conformsTo, EdmLabel.PROVIDER_DCTERMS_CONFORMS_TO, europeanaType.getConformsTo(),mapLists,rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(created,EdmLabel.PROVIDER_DCTERMS_CREATED, europeanaType.getCreated(),mapLists, rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(extent, EdmLabel.PROVIDER_DCTERMS_EXTENT,europeanaType.getExtent(),mapLists,rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(hasFormat, EdmLabel.PROVIDER_DCTERMS_HAS_FORMAT,europeanaType.getHasFormat(),mapLists, rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(hasPart,EdmLabel.PROVIDER_DCTERMS_HAS_PART, europeanaType.getHasPart(),mapLists, rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(hasVersion,EdmLabel.PROVIDER_DCTERMS_HAS_VERSION, europeanaType.getHasVersion(),mapLists, rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(isFormatOf, EdmLabel.PROVIDER_DCTERMS_IS_FORMAT_OF,europeanaType.getIsFormatOf(),mapLists, rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(isPartOf, EdmLabel.PROVIDER_DCTERMS_IS_PART_OF,europeanaType.getIsPartOf(),mapLists, rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(isReferencedBy, EdmLabel.PROVIDER_DCTERMS_IS_REFERENCED_BY,europeanaType.getIsReferencedBy(),mapLists, rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(isReplacedBy, EdmLabel.PROVIDER_DCTERMS_IS_REPLACED_BY,europeanaType.getIsReplacedBy(),mapLists, rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(isRequiredBy, EdmLabel.PROVIDER_DCTERMS_IS_REQUIRED_BY,europeanaType.getIsRequiredBy(),mapLists, rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(issued, EdmLabel.PROVIDER_DCTERMS_ISSUED,europeanaType.getIssued(),mapLists, rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(isVersionOf, EdmLabel.PROVIDER_DCTERMS_IS_VERSION_OF,europeanaType.getIsVersionOf(),mapLists, rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(medium, EdmLabel.PROVIDER_DCTERMS_MEDIUM,europeanaType.getMedium(), mapLists, rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(provenance, EdmLabel.PROVIDER_DCTERMS_PROVENANCE,europeanaType.getProvenance(), mapLists, rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(references, EdmLabel.PROVIDER_DCTERMS_REFERENCES,europeanaType.getReferences(), mapLists, rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(replaces,EdmLabel.PROVIDER_DCTERMS_REPLACES, europeanaType.getReplaces(), mapLists, rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(requires, EdmLabel.PROVIDER_DCTERMS_REQUIRES,europeanaType.getRequires(), mapLists, rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(spatial, EdmLabel.PROVIDER_DCTERMS_SPATIAL,europeanaType.getSpatial(), mapLists, rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(tableOfContents, EdmLabel.PROVIDER_DCTERMS_TABLE_OF_CONTENTS,europeanaType.getTableOfContents(), mapLists, rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(temporal,EdmLabel.PROVIDER_DCTERMS_TEMPORAL, europeanaType.getTemporal(), mapLists, rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(contributor, EdmLabel.PROVIDER_DC_CONTRIBUTOR,europeanaType.getContributor(), mapLists, rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(coverage, EdmLabel.PROVIDER_DC_COVERAGE,europeanaType.getCoverage(), mapLists, rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(creator, EdmLabel.PROVIDER_DC_CREATOR,europeanaType.getCreator(), mapLists, rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(date,EdmLabel.PROVIDER_DC_DATE, europeanaType.getDate(), mapLists, rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(description,EdmLabel.PROVIDER_DC_DESCRIPTION,europeanaType.getDescription(), mapLists, rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(format, EdmLabel.PROVIDER_DC_FORMAT,europeanaType.getFormat(), mapLists, rdf, shouldDereference);
				if (europeanaType.getIdentifier() != null) {
					identifier.add(europeanaType.getIdentifier().getString());
				}
				if (europeanaType.getLanguage() != null) {
					language.add(europeanaType.getLanguage().getString());
				}
				SolrUtils.addResourceOrLiteralType(publisher, EdmLabel.PROVIDER_DC_PUBLISHER,europeanaType.getPublisher(), mapLists, rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(relation, EdmLabel.PROVIDER_DC_RELATION,europeanaType.getRelation(), mapLists, rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(rights, EdmLabel.PROVIDER_DC_RIGHTS,europeanaType.getRights(), mapLists, rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(source, EdmLabel.PROVIDER_DC_SOURCE,europeanaType.getSource(),mapLists, rdf, shouldDereference);
				SolrUtils.addResourceOrLiteralType(subject, EdmLabel.PROVIDER_DC_SUBJECT,europeanaType.getSubject(),mapLists, rdf, shouldDereference);
				if (europeanaType.getTitle() != null) {
					title.add(europeanaType.getTitle().getString());
				}
				SolrUtils.addResourceOrLiteralType(type, EdmLabel.PROVIDER_DC_TYPE,europeanaType.getType(),mapLists, rdf, shouldDereference);
			}
		}
	
		mongoProxy.setDctermsAlternative(StringArrayUtils.toArray(alternatives));
		mongoProxy.setDctermsConformsTo(StringArrayUtils.toArray(conformsTo));
		mongoProxy.setDctermsCreated(StringArrayUtils.toArray(created));
		mongoProxy.setDctermsExtent(StringArrayUtils.toArray(extent));
		mongoProxy.setDctermsHasFormat(StringArrayUtils.toArray(hasFormat));
		mongoProxy.setDctermsHasPart(StringArrayUtils.toArray(hasPart));
		mongoProxy.setDctermsHasVersion(StringArrayUtils.toArray(hasVersion));
		mongoProxy.setDctermsIsFormatOf(StringArrayUtils.toArray(isFormatOf));
		mongoProxy.setDctermsIsPartOf(StringArrayUtils.toArray(isPartOf));
		mongoProxy.setDctermsIsReferencedBy(StringArrayUtils.toArray(isReferencedBy));
		mongoProxy.setDctermsIsReplacedBy(StringArrayUtils.toArray(isReplacedBy));
		mongoProxy.setDctermsIsRequiredBy(StringArrayUtils.toArray(isRequiredBy));
		mongoProxy.setDctermsIssued(StringArrayUtils.toArray(issued));
		mongoProxy.setDctermsIsVersionOf(StringArrayUtils.toArray(isVersionOf));
		mongoProxy.setDctermsMedium(StringArrayUtils.toArray(medium));
		mongoProxy.setDctermsProvenance(StringArrayUtils.toArray(provenance));
		mongoProxy.setDctermsReferences(StringArrayUtils.toArray(references));
		mongoProxy.setDctermsReplaces(StringArrayUtils.toArray(replaces));
		mongoProxy.setDctermsRequires(StringArrayUtils.toArray(requires));
		mongoProxy.setDctermsSpatial(StringArrayUtils.toArray(spatial));
		mongoProxy.setDctermsTemporal(StringArrayUtils.toArray(temporal));
		mongoProxy.setDctermsTOC(StringArrayUtils.toArray(tableOfContents));
		mongoProxy.setDcContributor(StringArrayUtils.toArray(contributor));
		mongoProxy.setDcCoverage(StringArrayUtils.toArray(coverage));
		mongoProxy.setDcCreator(StringArrayUtils.toArray(creator));
		mongoProxy.setDcDate(StringArrayUtils.toArray(date));
		mongoProxy.setDcDescription(StringArrayUtils.toArray(description));
		mongoProxy.setDcFormat(StringArrayUtils.toArray(format));
		mongoProxy.setDcIdentifier(StringArrayUtils.toArray(identifier));
		mongoProxy.setDcLanguage(StringArrayUtils.toArray(language));
		mongoProxy.setDcPublisher(StringArrayUtils.toArray(publisher));
		mongoProxy.setDcRelation(StringArrayUtils.toArray(relation));
		mongoProxy.setDcRights(StringArrayUtils.toArray(rights));
		mongoProxy.setDcSource(StringArrayUtils.toArray(source));
		mongoProxy.setDcSubject(StringArrayUtils.toArray(subject));
		mongoProxy.setDcTitle(StringArrayUtils.toArray(title));
		mongoProxy.setDcType(StringArrayUtils.toArray(type));
		if (((EdmMongoServer)mongoServer).searchByAbout(ProxyImpl.class, mongoProxy.getAbout()) != null) {
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
			Aggregation aggregation, MongoServer mongoServer)
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
		solrInputDocument.addField(EdmLabel.PROVIDER_ORE_PROXY_IN.toString(),
				SolrUtils.exists(String.class, aggregation.getAbout()));
		return solrInputDocument;
	}

	public static void deleteProxyFromMongo(String about,
			EdmMongoServer mongoServer) {
		MongoUtils.delete(ProxyImpl.class, about, mongoServer);
	}
}
