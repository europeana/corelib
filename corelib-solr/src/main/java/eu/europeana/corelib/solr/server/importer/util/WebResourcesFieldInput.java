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

import eu.europeana.corelib.definitions.jibx.Rights;
import eu.europeana.corelib.definitions.jibx.Rights1;
import eu.europeana.corelib.definitions.jibx.WebResourceType;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.solr.entity.WebResourceImpl;
import eu.europeana.corelib.solr.server.MongoDBServer;
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
		solrInputDocument.addField(EdmLabel.WR_EDM_RIGHTS.toString(), SolrUtils
				.exists(Rights.class, (webResource.getRights())).getResource());
		if (webResource.getRightList() != null) {
			for (Rights1 dcRights : webResource.getRightList()) {
				solrInputDocument.addField(EdmLabel.WR_DC_RIGHTS.toString(),
						dcRights.getString());
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
			WebResourceType webResource, MongoDBServer mongoServer) {
		WebResourceImpl mongoWebResource = new WebResourceImpl();
		mongoWebResource.setAbout(webResource.getAbout());
		if (webResource.getRights() != null) {
			mongoWebResource.setWebResourceEdmRights(webResource.getRights()
					.getResource());
		}

		List<String> dcRightsList = new ArrayList<String>();
		if (webResource.getRightList() != null) {
			for (Rights1 dcRights : webResource.getRightList()) {
				dcRightsList.add(dcRights.getResource());
			}
		}
		mongoWebResource.setWebResourceDcRights(dcRightsList
				.toArray(new String[dcRightsList.size()]));
		return mongoWebResource;
	}

	public static void deleteWebResourceFromMongo(String about,
			MongoDBServer mongoServer) {
		MongoUtils.delete(WebResourceImpl.class, about, mongoServer);
	}
}
