package eu.europeana.corelib.solr.utils;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;

import eu.europeana.corelib.definitions.solr.beans.FullBean;
import eu.europeana.corelib.solr.bean.impl.FullBeanImpl;
import eu.europeana.corelib.solr.entity.AggregationImpl;
import eu.europeana.corelib.solr.entity.ProxyImpl;
import eu.europeana.corelib.solr.server.EdmMongoServer;

public final class MongoUtils {

	private MongoUtils() {
		// Constructor must be private
	}

	public static boolean contains(String[] str1, String str2) {
		for (String str : str1) {
			if (StringUtils.equals(str, str2)) {
				return true;
			}
		}
		return false;
	}

	public static boolean contains(Map<String, String> map, String key,
			String val) {
		if (map.keySet().contains(key)) {
			if (StringUtils.equals(map.get(key).toString(), val)) {
				return true;
			}
		}
		return false;
	}

	public static void delete(Class<?> clazz, String about,
			EdmMongoServer mongoServer) {
		mongoServer.getDatastore().delete(
				mongoServer.getDatastore().createQuery(clazz)
						.filter("about", about));
	}

	/**
	 * Delete a generic Mongo Entity from the MongoDB Server
	 * 
	 * @param clazz
	 *            The class type of the Mongo Entity
	 * @param about
	 *            The rdf:about field of the Mongo entity
	 * @param mongoServer
	 *            The MongoDBServer to connect to
	 * @param field
	 *            The field to update
	 * @param value
	 *            The value to update
	 */
	@SuppressWarnings("unchecked")
	public static <T> void update(Class<T> clazz, String about,
			EdmMongoServer mongoServer, String field, Object value) {
		if (value != null) {
			Query<T> updateQuery = mongoServer.getDatastore()
					.createQuery(clazz).field("about").equal(about);
			UpdateOperations<T> ops = null;

			/*
			 * If the value is an ArrayList it must be converted to String Array
			 * unless it refers to Web Resources
			 */
			if (value instanceof List
					&& !(StringUtils.equals("webResources", field))) {
				ops = mongoServer
						.getDatastore()
						.createUpdateOperations(clazz)
						.set(field,
								((List<String>) value)
										.toArray(new String[((List<String>) value)
												.size()]));
			} else {
				ops = mongoServer.getDatastore().createUpdateOperations(clazz)
						.set(field, value);

			}
			mongoServer.getDatastore().update(updateQuery, ops);
		}
	}

	// TODO: beautify this
	public static <T, V> void updateEntity(Class<T> clazz, String about,
			EdmMongoServer mongoServer, String field, Object value) {
		if (value != null) {
			Query<T> updateQuery = mongoServer.getDatastore()
					.createQuery(clazz).field("about").equal(about);
			UpdateOperations<T> ops = mongoServer.getDatastore()
					.createUpdateOperations(clazz).set(field, value);

			mongoServer.getDatastore().update(updateQuery, ops);
		}
	}

	public static void updateAggregation(AggregationImpl mongoAggregation,
			EdmMongoServer mongoServer) {
		update(AggregationImpl.class, mongoAggregation.getAbout(), mongoServer,
				"aggregatedCHO", mongoAggregation.getAggregatedCHO());

		update(AggregationImpl.class, mongoAggregation.getAbout(), mongoServer,
				"dcRights", mongoAggregation.getDcRights());

		update(AggregationImpl.class, mongoAggregation.getAbout(), mongoServer,
				"edmDataProvider", mongoAggregation.getEdmDataProvider());

		update(AggregationImpl.class, mongoAggregation.getAbout(), mongoServer,
				"hasView", mongoAggregation.getHasView());

		update(AggregationImpl.class, mongoAggregation.getAbout(), mongoServer,
				"edmIsShownAt", mongoAggregation.getEdmIsShownAt());

		update(AggregationImpl.class, mongoAggregation.getAbout(), mongoServer,
				"edmIsShownBy", mongoAggregation.getEdmIsShownBy());

		update(AggregationImpl.class, mongoAggregation.getAbout(), mongoServer,
				"edmObject", mongoAggregation.getEdmObject());

		update(AggregationImpl.class, mongoAggregation.getAbout(), mongoServer,
				"edmProvider", mongoAggregation.getEdmProvider());

		update(AggregationImpl.class, mongoAggregation.getAbout(), mongoServer,
				"edmPreviewNoDistribute",
				mongoAggregation.getEdmPreviewNoDistribute());

		update(AggregationImpl.class, mongoAggregation.getAbout(), mongoServer,
				"edmRights", mongoAggregation.getEdmRights());

		update(AggregationImpl.class, mongoAggregation.getAbout(), mongoServer,
				"edmUgc", mongoAggregation.getEdmUgc());

	}

	public static void updateProxy(ProxyImpl proxy, EdmMongoServer mongoServer) {

		update(ProxyImpl.class, proxy.getAbout(), mongoServer, "dcContributor",
				proxy.getDcContributor());

		update(ProxyImpl.class, proxy.getAbout(), mongoServer, "dcCoverage",
				proxy.getDcCoverage());
		update(ProxyImpl.class, proxy.getAbout(), mongoServer, "dcCreator",
				proxy.getDcCreator());
		update(ProxyImpl.class, proxy.getAbout(), mongoServer, "dcDate",
				proxy.getDcDate());
		update(ProxyImpl.class, proxy.getAbout(), mongoServer, "dcDescription",
				proxy.getDcDescription());
		update(ProxyImpl.class, proxy.getAbout(), mongoServer, "dcFormat",
				proxy.getDcFormat());
		update(ProxyImpl.class, proxy.getAbout(), mongoServer, "dcIdentifier",
				proxy.getDcIdentifier());
		update(ProxyImpl.class, proxy.getAbout(), mongoServer, "dcLanguage",
				proxy.getDcLanguage());
		update(ProxyImpl.class, proxy.getAbout(), mongoServer, "dcPublisher",
				proxy.getDcPublisher());
		update(ProxyImpl.class, proxy.getAbout(), mongoServer, "dcRelation",
				proxy.getDcRelation());
		update(ProxyImpl.class, proxy.getAbout(), mongoServer, "dcRights",
				proxy.getDcRights());
		update(ProxyImpl.class, proxy.getAbout(), mongoServer, "dcSource",
				proxy.getDcSource());
		update(ProxyImpl.class, proxy.getAbout(), mongoServer, "dcSubject",
				proxy.getDcSubject());
		update(ProxyImpl.class, proxy.getAbout(), mongoServer, "dcTitle",
				proxy.getDcTitle());
		update(ProxyImpl.class, proxy.getAbout(), mongoServer, "dcType",
				proxy.getDcType());
		update(ProxyImpl.class, proxy.getAbout(), mongoServer,
				"dctermsAlternative", proxy.getDctermsAlternative());
		update(ProxyImpl.class, proxy.getAbout(), mongoServer,
				"dctermsConformsTo", proxy.getDctermsConformsTo());
		update(ProxyImpl.class, proxy.getAbout(), mongoServer,
				"dctermsCreated", proxy.getDctermsCreated());
		update(ProxyImpl.class, proxy.getAbout(), mongoServer, "dctermsExtent",
				proxy.getDctermsExtent());
		update(ProxyImpl.class, proxy.getAbout(), mongoServer,
				"dctermsHasFormat", proxy.getDctermsHasFormat());
		update(ProxyImpl.class, proxy.getAbout(), mongoServer,
				"dctermsHasPart", proxy.getDctermsHasPart());
		update(ProxyImpl.class, proxy.getAbout(), mongoServer,
				"dctermsHasVersion", proxy.getDctermsHasVersion());
		update(ProxyImpl.class, proxy.getAbout(), mongoServer,
				"dctermsIsFormatOf", proxy.getDctermsIsFormatOf());
		update(ProxyImpl.class, proxy.getAbout(), mongoServer,
				"dctermsIsPartOf", proxy.getDctermsIsPartOf());
		update(ProxyImpl.class, proxy.getAbout(), mongoServer,
				"dctermsIsReferencedBy", proxy.getDctermsIsReferencedBy());
		update(ProxyImpl.class, proxy.getAbout(), mongoServer,
				"dctermsIsReplacedBy", proxy.getDctermsIsReplacedBy());
		update(ProxyImpl.class, proxy.getAbout(), mongoServer,
				"dctermsIsRequiredBy", proxy.getDctermsIsRequiredBy());
		update(ProxyImpl.class, proxy.getAbout(), mongoServer, "dctermsIssued",
				proxy.getDctermsIssued());
		update(ProxyImpl.class, proxy.getAbout(), mongoServer,
				"dctermsIsVersionOf", proxy.getDctermsIsVersionOf());
		update(ProxyImpl.class, proxy.getAbout(), mongoServer, "dctermsMedium",
				proxy.getDctermsMedium());
		update(ProxyImpl.class, proxy.getAbout(), mongoServer,
				"dctermsProvenance", proxy.getDctermsProvenance());
		update(ProxyImpl.class, proxy.getAbout(), mongoServer,
				"dctermsReferences", proxy.getDctermsReferences());
		update(ProxyImpl.class, proxy.getAbout(), mongoServer,
				"dctermsReplaces", proxy.getDctermsReplaces());
		update(ProxyImpl.class, proxy.getAbout(), mongoServer,
				"dctermsRequires", proxy.getDctermsRequires());
		update(ProxyImpl.class, proxy.getAbout(), mongoServer,
				"dctermsSpatial", proxy.getDctermsSpatial());
		update(ProxyImpl.class, proxy.getAbout(), mongoServer, "dctermsTOC",
				proxy.getDctermsTOC());
		update(ProxyImpl.class, proxy.getAbout(), mongoServer,
				"dctermsTemporal", proxy.getDctermsTemporal());
		update(ProxyImpl.class, proxy.getAbout(), mongoServer, "edmType",
				proxy.getEdmType());
		update(ProxyImpl.class, proxy.getAbout(), mongoServer,
				"edmCurrentLocation", proxy.getEdmCurrentLocation());
		update(ProxyImpl.class, proxy.getAbout(), mongoServer, "edmRights",
				proxy.getEdmRights());
		update(ProxyImpl.class, proxy.getAbout(), mongoServer, "proxyIn",
				proxy.getProxyIn());
		update(ProxyImpl.class, proxy.getAbout(), mongoServer, "proxyFor",
				proxy.getProxyFor());
	}

	public static void updateFullBean(FullBean fullBean,
			EdmMongoServer mongoDBServer) {

		updateEntity(FullBeanImpl.class, fullBean.getAbout(), mongoDBServer,
				"aggregations", fullBean.getAggregations());
		updateEntity(FullBeanImpl.class, fullBean.getAbout(), mongoDBServer,
				"agents", fullBean.getAgents());
		updateEntity(FullBeanImpl.class, fullBean.getAbout(), mongoDBServer,
				"concepts", fullBean.getConcepts());
		updateEntity(FullBeanImpl.class, fullBean.getAbout(), mongoDBServer,
				"timespans", fullBean.getTimespans());
		updateEntity(FullBeanImpl.class, fullBean.getAbout(), mongoDBServer,
				"providedCHOs", fullBean.getProvidedCHOs());
		updateEntity(FullBeanImpl.class, fullBean.getAbout(), mongoDBServer,
				"places", fullBean.getPlaces());
	}

}
