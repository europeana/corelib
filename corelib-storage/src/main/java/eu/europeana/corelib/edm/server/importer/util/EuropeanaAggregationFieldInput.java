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
package eu.europeana.corelib.edm.server.importer.util;

import java.util.List;
import java.util.Map;
import eu.europeana.corelib.definitions.jibx.AggregatedCHO;
import eu.europeana.corelib.definitions.jibx.IsShownBy;
import eu.europeana.corelib.edm.utils.MongoUtils;
import eu.europeana.corelib.edm.utils.SolrUtils;
import eu.europeana.corelib.solr.entity.EuropeanaAggregationImpl;

/**
 * Constructor of a Europeana Aggregation
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public final class EuropeanaAggregationFieldInput {

	public EuropeanaAggregationFieldInput() {

	}

        /**
	 * Create a EuropeanaAggregation to save in MongoDB storage
	 * 
	 * @param aggregation
	 *            The RDF EuropeanaAggregation representation
	 * @param mongoServer
	 *            The MongoServer to use to save the EuropeanaAggregation
	 * @return the EuropeanaAggregation created
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public EuropeanaAggregationImpl createAggregationMongoFields(
			eu.europeana.corelib.definitions.jibx.EuropeanaAggregationType aggregation,
			 String previewUrl)
			throws InstantiationException, IllegalAccessException {
		EuropeanaAggregationImpl mongoAggregation = new EuropeanaAggregationImpl();
		
		mongoAggregation.setAbout(aggregation.getAbout());

		Map<String, List<String>> creator = MongoUtils
				.createResourceOrLiteralMapFromString(aggregation.getCreator());
		
			mongoAggregation.setDcCreator(creator);
	

		Map<String, List<String>> country = MongoUtils
				.createLiteralMapFromString(aggregation.getCountry()
						.getCountry().xmlValue().toLowerCase());
			mongoAggregation.setEdmCountry(country);
		String isShownBy = SolrUtils.exists(IsShownBy.class,
				aggregation.getIsShownBy()).getResource();
			mongoAggregation.setEdmIsShownBy(isShownBy);
		
		Map<String, List<String>> language = MongoUtils
				.createLiteralMapFromString(aggregation.getLanguage()
						.getLanguage().xmlValue().toLowerCase());

		mongoAggregation.setEdmLanguage(language);

		String agCHO = SolrUtils.exists(AggregatedCHO.class,
				aggregation.getAggregatedCHO()).getResource();
		mongoAggregation.setAggregatedCHO(agCHO);
		mongoAggregation.setEdmLandingPageFromAggregatedCHO();

		Map<String, List<String>> edmRights = MongoUtils
				.createResourceMapFromString(aggregation.getRights());
			mongoAggregation.setEdmRights(edmRights);
		String[] aggregates = SolrUtils.resourceListToArray(aggregation
				.getAggregateList());
			mongoAggregation.setAggregates(aggregates);
		String[] hasViewList = SolrUtils.resourceListToArray(aggregation
				.getHasViewList());
		mongoAggregation.setEdmHasView(hasViewList);
		// TODO: This is the future scenario
		// String preview = SolrUtils.exists(Preview.class,
		// aggregation.getPreview()).getResource();
		// if (preview != null) {
		// mongoAggregation.setEdmPreview(preview);
		// ops.set("edmPreview", preview);
		// } else {
		// mongoAggregation.setEdmPreview(EUROPEANA_URI + agCHO
		// + "&size=BRIEF_DOC");
		// ops.set("edmPreview", EUROPEANA_URI + agCHO + "&size=BRIEF_DOC");
		// }
//		if (previewUrl != null) {
//			String preview = EuropeanaUrlServiceImpl
//					.getBeanInstance().getThumbnailUrl(previewUrl, type);
//			mongoAggregation.setEdmPreview(preview);
//			ops.set("edmPreview", preview);
//		}
		// TODO: Currently the europeana aggregation does not generate any
		// WebResource, do we want it?
		return mongoAggregation;
	}
}
