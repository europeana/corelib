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
import eu.europeana.corelib.definitions.jibx.Rights1;
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
		solrInputDocument
				.addField(
						EdmLabel.WR_EDM_RIGHTS.toString(),
						SolrUtils.exists(Rights1.class,
								(webResource.getRights())).getResource());
		if (webResource.getRightList() != null) {
			for (Rights dcRights : webResource.getRightList()) {
				solrInputDocument.addField(EdmLabel.WR_DC_RIGHTS.toString(),
						dcRights.getResource());
			}
		}

		if (webResource.getIsNextInSequence() != null) {
			solrInputDocument.addField(
					EdmLabel.WR_DCTERMS_IS_NEXT_IN_SEQUENCE.toString(),
					webResource.getIsNextInSequence().getResource());
		}

		if (webResource.getDescriptionList() != null) {
			for (Description description : webResource.getDescriptionList()) {
				if (description.getResource() != null) {
					solrInputDocument.addField(
							EdmLabel.WR_DC_DESCRIPTION.toString(),
							description.getResource());
				}
				if (description.getString() != null) {
					solrInputDocument.addField(
							EdmLabel.WR_DC_DESCRIPTION.toString(),
							description.getString());
				}
			}
		}

		if (webResource.getFormatList() != null) {
			for (Format format : webResource.getFormatList()) {
				if (format.getResource() != null) {
					solrInputDocument.addField(
							EdmLabel.WR_DC_FORMAT.toString(),
							format.getResource());
				}
				if (format.getString() != null) {
					solrInputDocument.addField(
							EdmLabel.WR_DC_FORMAT.toString(),
							format.getString());
				}
			}
		}
		if (webResource.getSourceList() != null) {
			for (Source source : webResource.getSourceList()) {
				if (source.getResource() != null) {
					solrInputDocument.addField(
							EdmLabel.WR_DC_SOURCE.toString(),
							source.getResource());
				}
				if (source.getString() != null) {
					solrInputDocument.addField(
							EdmLabel.WR_DC_SOURCE.toString(),
							source.getString());
				}
			}
		}
		if (webResource.getConformsToList() != null) {
			for (ConformsTo conformsTo : webResource.getConformsToList()) {
				if (conformsTo.getResource() != null) {
					solrInputDocument.addField(
							EdmLabel.WR_DCTERMS_CONFORMSTO.toString(),
							conformsTo.getResource());
				}
				if (conformsTo.getString() != null) {
					solrInputDocument.addField(
							EdmLabel.WR_DCTERMS_CONFORMSTO.toString(),
							conformsTo.getString());
				}
			}
		}
		if (webResource.getCreatedList() != null) {
			for (Created created : webResource.getCreatedList()) {
				if (created.getResource() != null) {
					solrInputDocument.addField(
							EdmLabel.WR_DCTERMS_CREATED.toString(),
							created.getResource());
				}
				if (created.getString() != null) {
					solrInputDocument.addField(
							EdmLabel.WR_DCTERMS_CREATED.toString(),
							created.getString());
				}
			}
		}
		if (webResource.getExtentList() != null) {
			for (Extent extent : webResource.getExtentList()) {
				if (extent.getResource() != null) {
					solrInputDocument.addField(
							EdmLabel.WR_DCTERMS_EXTENT.toString(),
							extent.getResource());
				}
				if (extent.getString() != null) {
					solrInputDocument.addField(
							EdmLabel.WR_DCTERMS_EXTENT.toString(),
							extent.getString());
				}
			}
		}
		if (webResource.getHasPartList() != null) {
			for (HasPart hasPart : webResource.getHasPartList()) {
				if (hasPart.getResource() != null) {
					solrInputDocument.addField(
							EdmLabel.WR_DCTERMS_HAS_PART.toString(),
							hasPart.getResource());
				}
				if (hasPart.getString() != null) {
					solrInputDocument.addField(
							EdmLabel.WR_DCTERMS_HAS_PART.toString(),
							hasPart.getString());
				}
			}
		}
		if (webResource.getIsFormatOfList() != null) {
			for (IsFormatOf isFormatOf : webResource.getIsFormatOfList()) {
				if (isFormatOf.getResource() != null) {
					solrInputDocument.addField(
							EdmLabel.WR_DCTERMS_ISFORMATOF.toString(),
							isFormatOf.getResource());
				}
				if (isFormatOf.getString() != null) {
					solrInputDocument.addField(
							EdmLabel.WR_DCTERMS_ISFORMATOF.toString(),
							isFormatOf.getString());
				}
			}
		}
		if (webResource.getIssuedList() != null) {
			for (Issued issued : webResource.getIssuedList()) {
				if (issued.getResource() != null) {
					solrInputDocument.addField(
							EdmLabel.WR_DCTERMS_ISSUED.toString(),
							issued.getResource());
				}
				if (issued.getString() != null) {
					solrInputDocument.addField(
							EdmLabel.WR_DCTERMS_ISSUED.toString(),
							issued.getString());
				}
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
		mongoWebResource.setAbout(webResource.getAbout());
		mongoWebResource.setWebResourceEdmRights(webResource.getRights()
				.getResource());
		mongoWebResource.setWebResourceDcRights(SolrUtils
				.resourceOrLiteralListToArray(webResource.getRightList()));
		mongoWebResource
				.setDcDescription(SolrUtils
						.resourceOrLiteralListToArray(webResource
								.getDescriptionList()));
		mongoWebResource.setDcFormat(SolrUtils
				.resourceOrLiteralListToArray(webResource.getFormatList()));
		mongoWebResource.setDcSource(SolrUtils
				.resourceOrLiteralListToArray(webResource.getSourceList()));
		mongoWebResource.setDctermsConformsTo(SolrUtils
				.resourceOrLiteralListToArray(webResource.getConformsToList()));
		mongoWebResource.setDctermsCreated(SolrUtils
				.resourceOrLiteralListToArray(webResource.getCreatedList()));
		mongoWebResource.setDctermsExtent(SolrUtils
				.resourceOrLiteralListToArray(webResource.getExtentList()));
		mongoWebResource.setDctermsHasPart(SolrUtils
				.resourceOrLiteralListToArray(webResource.getHasPartList()));
		mongoWebResource.setDctermsIsFormatOf(SolrUtils
				.resourceOrLiteralListToArray(webResource.getIsFormatOfList()));
		mongoWebResource.setDctermsIssued(SolrUtils
				.resourceOrLiteralListToArray(webResource.getIssuedList()));
		mongoWebResource.setIsNextInSequence(webResource.getIsNextInSequence()
				.getResource());
		return mongoWebResource;
	}

	public static void deleteWebResourceFromMongo(String about,
			EdmMongoServer mongoServer) {
		MongoUtils.delete(WebResourceImpl.class, about, mongoServer);
	}
}
