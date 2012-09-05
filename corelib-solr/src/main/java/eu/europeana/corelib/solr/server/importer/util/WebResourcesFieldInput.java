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

import org.apache.solr.common.SolrInputDocument;

import eu.europeana.corelib.definitions.jibx.ConformsTo;
import eu.europeana.corelib.definitions.jibx.Created;
import eu.europeana.corelib.definitions.jibx.Description;
import eu.europeana.corelib.definitions.jibx.Extent;
import eu.europeana.corelib.definitions.jibx.Format;
import eu.europeana.corelib.definitions.jibx.HasPart;
import eu.europeana.corelib.definitions.jibx.IsFormatOf;
import eu.europeana.corelib.definitions.jibx.Issued;
import eu.europeana.corelib.definitions.jibx.Rights;
import eu.europeana.corelib.definitions.jibx.Source;
import eu.europeana.corelib.definitions.jibx.WebResourceType;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.solr.MongoServer;
import eu.europeana.corelib.solr.entity.WebResourceImpl;
import eu.europeana.corelib.solr.server.EdmMongoServer;
import eu.europeana.corelib.solr.utils.MongoUtils;
import eu.europeana.corelib.solr.utils.SolrUtils;

/**
 * Class Creating a MongoDB Web Resource
 * 
 * @author Yorgos.Mamakis@ kb.nl
 */
public final class WebResourcesFieldInput {

	private WebResourcesFieldInput() {

	}

	/**
	 * Create a SolrInputDocument with the web resource fields filled in
	 * 
	 * @param webResource
	 *            The JiBX object representing a WebResource
	 * @param solrInputDocument
	 *            The SolrInputDocument to alter
	 * @return The altered SolrInputDocument withe the web resource fields
	 *         filled in
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static SolrInputDocument createWebResourceSolrFields(
			WebResourceType webResource, SolrInputDocument solrInputDocument)
			throws InstantiationException, IllegalAccessException {
		solrInputDocument.addField(EdmLabel.EDM_WEB_RESOURCE.toString(),
				webResource.getAbout());
		solrInputDocument = SolrUtils
				.addFieldFromResourceOrLiteral(solrInputDocument, webResource.getRights(),
						EdmLabel.WR_EDM_RIGHTS);
		if (webResource.getRightList() != null) {
			for (Rights dcRights : webResource.getRightList()) {
				solrInputDocument = SolrUtils
						.addFieldFromResourceOrLiteral(solrInputDocument, dcRights,
								EdmLabel.WR_DC_RIGHTS);
			}
		}

		if (webResource.getIsNextInSequence() != null) {
			solrInputDocument.addField(
					EdmLabel.WR_EDM_IS_NEXT_IN_SEQUENCE.toString(),
					webResource.getIsNextInSequence().getResource());
		}

		if (webResource.getDescriptionList() != null) {
			for (Description description : webResource.getDescriptionList()) {
				solrInputDocument = SolrUtils
						.addFieldFromResourceOrLiteral(solrInputDocument, description,
								EdmLabel.WR_DC_DESCRIPTION);
			}
		}

		if (webResource.getFormatList() != null) {
			for (Format format : webResource.getFormatList()) {
				solrInputDocument = SolrUtils
						.addFieldFromResourceOrLiteral(solrInputDocument, format,
								EdmLabel.WR_DC_FORMAT);
			}
		}
		if (webResource.getSourceList() != null) {
			for (Source source : webResource.getSourceList()) {
				solrInputDocument = SolrUtils
						.addFieldFromResourceOrLiteral(solrInputDocument, source,
								EdmLabel.WR_DC_SOURCE);
			}
		}
		if (webResource.getConformsToList() != null) {
			for (ConformsTo conformsTo : webResource.getConformsToList()) {
				solrInputDocument = SolrUtils
						.addFieldFromResourceOrLiteral(solrInputDocument, conformsTo,
								EdmLabel.WR_DCTERMS_CONFORMSTO);
			}
		}
		if (webResource.getCreatedList() != null) {
			for (Created created : webResource.getCreatedList()) {
				solrInputDocument = SolrUtils
						.addFieldFromResourceOrLiteral(solrInputDocument, created,
								EdmLabel.WR_DCTERMS_CREATED);
			}
		}
		if (webResource.getExtentList() != null) {
			for (Extent extent : webResource.getExtentList()) {
				solrInputDocument = SolrUtils
						.addFieldFromResourceOrLiteral(solrInputDocument, extent,
								EdmLabel.WR_DCTERMS_EXTENT);
			}
		}
		if (webResource.getHasPartList() != null) {
			for (HasPart hasPart : webResource.getHasPartList()) {
				solrInputDocument = SolrUtils
						.addFieldFromResourceOrLiteral(solrInputDocument, hasPart,
								EdmLabel.WR_DCTERMS_HAS_PART);
			}
		}
		if (webResource.getIsFormatOfList() != null) {
			for (IsFormatOf isFormatOf : webResource.getIsFormatOfList()) {
				solrInputDocument = SolrUtils
						.addFieldFromResourceOrLiteral(solrInputDocument, isFormatOf,
								EdmLabel.WR_DCTERMS_ISFORMATOF);
			}
		}
		if (webResource.getIssuedList() != null) {
			for (Issued issued : webResource.getIssuedList()) {
				solrInputDocument = SolrUtils
						.addFieldFromResourceOrLiteral(solrInputDocument, issued,
								EdmLabel.WR_DCTERMS_ISSUED);
			}
		}
		return solrInputDocument;
	}

	/**
	 * Create a MongoDB Webresource Entity
	 * 
	 * @param webResource
	 *            The JiBX Entity representing a Web resource
	 * @param mongoServer
	 *            The MongoDB Server to store the WebResource Entity
	 * @return The WebResource MongoEntity
	 */
	public static WebResourceImpl createWebResourceMongoField(
			WebResourceType webResource, MongoServer mongoServer) {
		WebResourceImpl mongoWebResource = new WebResourceImpl();
		//mongoWebResource.setId(new ObjectId());
		mongoWebResource.setAbout(webResource.getAbout());
		mongoWebResource.setWebResourceEdmRights(MongoUtils.createResourceOrLiteralMapFromString(webResource.getRights()));
		mongoWebResource.setWebResourceDcRights(MongoUtils.createResourceOrLiteralMapFromList(webResource.getRightList()));
		mongoWebResource.setDcDescription(MongoUtils.createResourceOrLiteralMapFromList(webResource
								.getDescriptionList()));
		mongoWebResource.setDcFormat(MongoUtils.createResourceOrLiteralMapFromList(webResource.getFormatList()));
		mongoWebResource.setDcSource(MongoUtils.createResourceOrLiteralMapFromList(webResource.getSourceList()));
		mongoWebResource.setDctermsConformsTo(MongoUtils.createResourceOrLiteralMapFromList(webResource.getConformsToList()));
		mongoWebResource.setDctermsCreated(MongoUtils.createResourceOrLiteralMapFromList(webResource.getCreatedList()));
		mongoWebResource.setDctermsExtent(MongoUtils.createResourceOrLiteralMapFromList(webResource.getExtentList()));
		mongoWebResource.setDctermsHasPart(MongoUtils.createResourceOrLiteralMapFromList(webResource.getHasPartList()));
		mongoWebResource.setDctermsIsFormatOf(MongoUtils.createResourceOrLiteralMapFromList(webResource.getIsFormatOfList()));
		mongoWebResource.setDctermsIssued(MongoUtils.createResourceOrLiteralMapFromList(webResource.getIssuedList()));
		if(webResource.getIsNextInSequence()!=null){
		mongoWebResource.setIsNextInSequence(webResource.getIsNextInSequence()
				.getResource());
		}
		return mongoWebResource;
	}

	public static void deleteWebResourceFromMongo(String about,
			EdmMongoServer mongoServer) {
		MongoUtils.delete(WebResourceImpl.class, about, mongoServer);
	}
}
