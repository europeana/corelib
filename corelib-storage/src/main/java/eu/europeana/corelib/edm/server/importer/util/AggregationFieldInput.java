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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import eu.europeana.corelib.definitions.jibx.AggregatedCHO;
import eu.europeana.corelib.definitions.jibx.HasView;
import eu.europeana.corelib.definitions.jibx.IsShownAt;
import eu.europeana.corelib.definitions.jibx.IsShownBy;
import eu.europeana.corelib.definitions.jibx.WebResourceType;
import eu.europeana.corelib.definitions.jibx._Object;
import eu.europeana.corelib.edm.utils.MongoUtils;
import eu.europeana.corelib.edm.utils.SolrUtils;
import eu.europeana.corelib.solr.entity.AggregationImpl;
import eu.europeana.corelib.solr.entity.WebResourceImpl;

/**
 * Constructor for an Aggregation
 *
 * @author Yorgos.Mamakis@ kb.nl
 *
 */
public final class AggregationFieldInput {

	public AggregationFieldInput() {

	}

	/**
	 * Create a web resource
	 *
	 * @param wResources
	 * @return
	 */
	public List<WebResourceImpl> createWebResources(
			List<WebResourceType> wResources) {
		List<WebResourceImpl> webResources = new ArrayList<WebResourceImpl>();

		for (WebResourceType wResourceType : wResources) {
			if (!containsWebResource(webResources, wResourceType.getAbout())) {
				WebResourceImpl webResource = new WebResourceImpl();
				webResource.setAbout(wResourceType.getAbout());

				Map<String, List<String>> desMap = MongoUtils
						.createResourceOrLiteralMapFromList(wResourceType
								.getDescriptionList());

				webResource.setDcDescription(desMap);

				Map<String, List<String>> forMap = MongoUtils
						.createResourceOrLiteralMapFromList(wResourceType
								.getFormatList());

				webResource.setDcFormat(forMap);

				Map<String, List<String>> sourceMap = MongoUtils
						.createResourceOrLiteralMapFromList(wResourceType
								.getSourceList());

				webResource.setDcSource(sourceMap);

				Map<String, List<String>> conformsToMap = MongoUtils
						.createResourceOrLiteralMapFromList(wResourceType
								.getConformsToList());

				webResource.setDctermsConformsTo(conformsToMap);

				Map<String, List<String>> createdMap = MongoUtils
						.createResourceOrLiteralMapFromList(wResourceType
								.getCreatedList());

				webResource.setDctermsCreated(createdMap);

				Map<String, List<String>> extentMap = MongoUtils
						.createResourceOrLiteralMapFromList(wResourceType
								.getExtentList());

				webResource.setDctermsExtent(extentMap);

				Map<String, List<String>> hasPartMap = MongoUtils
						.createResourceOrLiteralMapFromList(wResourceType
								.getHasPartList());

				webResource.setDctermsHasPart(hasPartMap);

				Map<String, List<String>> isFormatOfMap = MongoUtils
						.createResourceOrLiteralMapFromList(wResourceType
								.getIsFormatOfList());

				webResource.setDctermsIsFormatOf(isFormatOfMap);

				Map<String, List<String>> issuedMap = MongoUtils
						.createResourceOrLiteralMapFromList(wResourceType
								.getIssuedList());

				webResource.setDctermsIssued(issuedMap);
				Map<String, List<String>> rightMap = MongoUtils
						.createResourceOrLiteralMapFromList(wResourceType
								.getRightList());

				webResource.setWebResourceDcRights(rightMap);

				Map<String, List<String>> typeMap = MongoUtils
						.createResourceOrLiteralMapFromList(wResourceType.getTypeList());
				webResource.setDcType(typeMap);

				Map<String, List<String>> edmRightsMap = MongoUtils
						.createResourceMapFromString(wResourceType.getRights());

				webResource.setWebResourceEdmRights(edmRightsMap);

				if (wResourceType.getIsNextInSequence() != null) {
					webResource.setIsNextInSequence(wResourceType
							.getIsNextInSequence().getResource());
				}
				webResource.setOwlSameAs(SolrUtils
						.resourceListToArray(wResourceType.getSameAList()));

				webResource.setDcCreator(MongoUtils
						.createResourceOrLiteralMapFromList(wResourceType
								.getCreatorList()));

                if(wResourceType.getHasServiceList()!=null){
                    webResource.setSvcsHasService(SolrUtils.resourceListToArray(wResourceType.getHasServiceList()));
                }
				if(wResourceType.getIsReferencedByList()!=null){
					webResource.setDctermsIsReferencedBy(SolrUtils.resourceOrLiteralListToArray(wResourceType.getIsReferencedByList()));
				}
                if(wResourceType.getPreview()!=null){
                    webResource.setEdmPreview(wResourceType.getPreview().getResource());
                }
				webResources.add(webResource);
			}
		}
		return webResources;
	}

	private boolean containsWebResource(List<WebResourceImpl> webResources,
			String about) {
		for (WebResourceImpl wr : webResources) {
			if (StringUtils.equals(wr.getAbout(), about)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Create an Aggregation MongoDBEntity. The webresources are not inserted at
	 * this method. as the aggregation may be created before the web resources
	 * are encountered in the EDM parsing or after.
	 *
	 * @param aggregation
	 *            The JiBX Aggregation entity

	 * @return The MongoDB Aggregation entity
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public AggregationImpl createAggregationMongoFields(
			eu.europeana.corelib.definitions.jibx.Aggregation aggregation,
			List<WebResourceImpl> webResources) throws InstantiationException,
			IllegalAccessException {
		AggregationImpl mongoAggregation = new AggregationImpl();

		mongoAggregation.setAbout(aggregation.getAbout());
		Map<String, List<String>> dp = MongoUtils
				.createResourceOrLiteralMapFromString(aggregation
						.getDataProvider());
		mongoAggregation.setEdmDataProvider(dp);
        if(aggregation.getIntermediateProviderList()!=null) {
            Map<String, List<String>> providers = MongoUtils.
                    createResourceOrLiteralMapFromList(aggregation.getIntermediateProviderList());
            mongoAggregation.setEdmIntermediateProvider(providers);
        }
		String isShownAt = SolrUtils.exists(IsShownAt.class,
				(aggregation.getIsShownAt())).getResource();
		mongoAggregation.setEdmIsShownAt(isShownAt != null ? isShownAt.trim()
				: null);
		boolean containsIsShownAt = false;
		for(WebResourceImpl wr:webResources){
			if(StringUtils.equals(wr.getAbout(),isShownAt)){
				containsIsShownAt = true;
			}
		}
		if(!containsIsShownAt && isShownAt!=null){
			WebResourceImpl wr = new WebResourceImpl();
			wr.setAbout(isShownAt);
			webResources.add(wr);
		}
		String isShownBy = SolrUtils.exists(IsShownBy.class,
				(aggregation.getIsShownBy())).getResource();
		mongoAggregation.setEdmIsShownBy(isShownBy != null ? isShownBy.trim()
				: null);
		boolean containsIsShownBy = false;
		for(WebResourceImpl wr:webResources){
			if(StringUtils.equals(wr.getAbout(),isShownBy)){
				containsIsShownBy = true;
			}
		}
		if(!containsIsShownBy && isShownBy!=null){
			WebResourceImpl wr = new WebResourceImpl();
			wr.setAbout(isShownBy);
			webResources.add(wr);
		}
		String object = SolrUtils.exists(_Object.class,
				(aggregation.getObject())).getResource();
		mongoAggregation.setEdmObject(object != null ? object.trim() : null);
		boolean containsObject = false;
		for(WebResourceImpl wr:webResources){
			if(StringUtils.equals(wr.getAbout(),object)){
				containsObject = true;
			}
		}
		if(!containsObject&&object!=null){
			WebResourceImpl wr = new WebResourceImpl();
			wr.setAbout(object);
			webResources.add(wr);
		}
		Map<String, List<String>> prov = MongoUtils
				.createResourceOrLiteralMapFromString(aggregation.getProvider());
		mongoAggregation.setEdmProvider(prov);
		Map<String, List<String>> rights = MongoUtils
				.createResourceMapFromString(aggregation.getRights());
		mongoAggregation.setEdmRights(rights);

		if (aggregation.getUgc() != null) {
			mongoAggregation.setEdmUgc(aggregation.getUgc().getUgc().toString()
					.toLowerCase());
		} else {
			mongoAggregation.setEdmUgc(null);
		}

		String agCHO = SolrUtils.exists(AggregatedCHO.class,
				(aggregation.getAggregatedCHO())).getResource();
		mongoAggregation.setAggregatedCHO(agCHO);

		Map<String, List<String>> rights1 = MongoUtils
				.createResourceOrLiteralMapFromList(aggregation.getRightList());
		mongoAggregation.setDcRights(rights1);

		if (aggregation.getHasViewList() != null) {
			List<String> hasViewList = new ArrayList<String>();
			for (HasView hasView : aggregation.getHasViewList()) {
				hasViewList.add(hasView.getResource().trim());
				boolean containsHasView = false;
				for(WebResourceImpl wr:webResources){
					if(StringUtils.equals(wr.getAbout(),hasView.getResource().trim())){
						containsHasView = true;
					}
				}
				if(!containsHasView&&hasView.getResource().trim()!=null){
					WebResourceImpl wr = new WebResourceImpl();
					wr.setAbout(hasView.getResource().trim());
					webResources.add(wr);
				}
			}
			mongoAggregation.setHasView(hasViewList
					.toArray(new String[hasViewList.size()]));

		} else {
			mongoAggregation.setHasView(null);

		}
		if (webResources != null) {
			mongoAggregation.setWebResources(webResources);
		}

		return mongoAggregation;
	}
}
