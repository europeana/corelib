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
package eu.europeana.corelib.solr.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;

import eu.europeana.corelib.definitions.jibx.LiteralType;
import eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType;
import eu.europeana.corelib.definitions.solr.entity.EuropeanaAggregation;
import eu.europeana.corelib.solr.MongoServer;
import eu.europeana.corelib.solr.entity.AggregationImpl;
import eu.europeana.corelib.solr.entity.EuropeanaAggregationImpl;
import eu.europeana.corelib.solr.entity.ProxyImpl;
import eu.europeana.corelib.solr.server.EdmMongoServer;

/**
 * Class with util methods for Mongo objects
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public final class MongoUtils {

	private MongoUtils() {
		// Constructor must be private
	}

	/**
	 * Checks if a string is contained in a string array
	 * 
	 * @param str1
	 *            A string array
	 * @param str2
	 *            The string to be checked
	 * @return true if it is contained false otherwise
	 */
	public static boolean contains(String[] str1, String str2) {
		for (String str : str1) {
			if (StringUtils.equals(str, str2)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Method that checks whether a specific key contains a specific value in a
	 * Map
	 * 
	 * @param map
	 *            The map to search on
	 * @param key
	 *            The key field
	 * @param val
	 *            The value
	 * @return true if the specified key has the value, false otherwise
	 */
	public static boolean contains(Map<String, List<String>> map, String key,
			String val) {
		if (map.keySet().contains(key)) {
			if (StringUtils.equals(map.get(key).toString(), val)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Method that deletes a class from the Mongo storage
	 * 
	 * @param clazz
	 *            The class to delete
	 * @param about
	 *            The about field
	 * @param mongoServer
	 *            The mongo server to use
	 */
	public static void delete(Class<?> clazz, String about,
			EdmMongoServer mongoServer) {
		mongoServer.getDatastore().delete(
				mongoServer.getDatastore().createQuery(clazz)
						.filter("about", about));
	}

	/**
	 * Method that updates an entity in MongoDB storage
	 * 
	 * @param clazz
	 *            The class type of the object
	 * @param about
	 *            The search field
	 * @param mongoServer
	 *            The server to be used
	 * @param field
	 *            The field to update
	 * @param value
	 *            The value
	 */
	@SuppressWarnings("unchecked")
	public static <T> void update(Class<T> clazz, String about,
			MongoServer mongoServer, String field, Object value) {
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
			}

			else {
				ops = mongoServer.getDatastore().createUpdateOperations(clazz)
						.set(field, value);

			}
			mongoServer.getDatastore().update(updateQuery, ops);
		}
	}

	/**
	 * Method that converts a LiteralType.class object to a multilingual map of
	 * strings
	 * 
	 * @param obj
	 *            The LiteralType object
	 * @return A Map of strings. The keys are the languages and the values are
	 *         lists of strings for the corresponding language. If the object is
	 *         null, the method returns null. In case a language is missing the
	 *         def notation is used as key
	 */
	public static <T extends LiteralType> Map<String, List<String>> createLiteralMapFromString(
			T obj) {
		Map<String, List<String>> retMap = new HashMap<String, List<String>>();
		if (obj != null) {
			if (obj.getLang() != null
					&& StringUtils.isNotBlank(obj.getLang().getLang())) {
				List<String> val = new ArrayList<String>();
				val.add(obj.getString());
				retMap.put(obj.getLang().getLang(), val);
			} else {
				List<String> val = new ArrayList<String>();
				val.add(obj.getString());
				retMap.put("def", val);
			}
			return retMap;
		}

		return null;
	}

	/**
	 * Method that converts a ResourceOrLiteralType.class object to a
	 * multilingual map of strings
	 * 
	 * @param obj
	 *            The ResourceOrLiteralType object
	 * @return A Map of strings. The keys are the languages and the values are
	 *         lists of strings for the corresponding language. If the object is
	 *         null, the method returns null. In case a language is missing the
	 *         def notation is used as key
	 */
	public static <T extends ResourceOrLiteralType> Map<String, List<String>> createResourceOrLiteralMapFromString(
			T obj) {
		Map<String, List<String>> retMap = new HashMap<String, List<String>>();
		if (obj != null) {
			if (obj.getLang() != null
					&& StringUtils.isNotEmpty(obj.getLang().getLang())) {
				if (obj.getString() != null) {
					List<String> val = new ArrayList<String>();
					val.add(obj.getString());
					retMap.put(obj.getLang().getLang(), val);
				}
				if (obj.getResource() != null) {
					List<String> val = retMap.get(obj.getLang().getLang()) != null ? retMap
							.get(obj.getLang().getLang())
							: new ArrayList<String>();
					val.add(obj.getResource());
					retMap.put(obj.getLang().getLang(), val);
				}
			} else {
				if (obj.getString() != null) {
					List<String> val = retMap.get("def") != null ? retMap
							.get("def")
							: new ArrayList<String>();
					val.add(obj.getString());
					retMap.put("def", val);
				}
				if (obj.getResource() != null) {
					List<String> val = retMap.get("def") != null ? retMap
							.get("def")
							: new ArrayList<String>();
					val.add(obj.getResource());
					retMap.put("def", val);
				}
			}
			return retMap;
		}

		return null;
	}

	/**
	 * Method that converts a LiteralType.class list to a multilingual map of
	 * strings
	 * 
	 * @param list
	 *            The LiteralType list
	 * @return A Map of strings. The keys are the languages and the values are
	 *         lists of strings for the corresponding language. If the object is
	 *         null, the method returns null. In case a language is missing the
	 *         def notation is used as key
	 */
	public static <T extends LiteralType> Map<String, List<String>> createLiteralMapFromList(
			List<T> list) {
		if (list != null) {
			Map<String, List<String>> retMap = new HashMap<String, List<String>>();
			for (T obj : list) {
				if (obj.getLang() != null
						&& StringUtils.isNotBlank(obj.getLang().getLang())) {
					String lang = obj.getLang().getLang();
					List<String> val = retMap.get(lang);
					if (val == null) {
						val = new ArrayList<String>();
					}
					val.add(obj.getString());
					retMap.put(lang, val);
				} else {
					List<String> val = retMap.get("def");
					if (val == null) {
						val = new ArrayList<String>();
					}
					val.add(obj.getString());
					retMap.put("def", val);
				}
			}
			return retMap;
		}
		return null;
	}

	/**
	 * Method that converts a ResourceOrLiteralType.class list to a multilingual
	 * map of strings
	 * 
	 * @param list
	 *            The ResourceOrLiteralType list
	 * @return A Map of strings. The keys are the languages and the values are
	 *         lists of strings for the corresponding language. If the object is
	 *         null, the method returns null. In case a language is missing the
	 *         def notation is used as key
	 */
	public static <T extends ResourceOrLiteralType> Map<String, List<String>> createResourceOrLiteralMapFromList(
			List<T> list) {
		if (list != null) {
			Map<String, List<String>> retMap = new HashMap<String, List<String>>();
			for (T obj : list) {
				if (obj.getString() != null) {
					if (obj.getLang() != null
							&& StringUtils.isNotBlank(obj.getLang().getLang())) {
						List<String> val = retMap.get((obj.getLang().getLang()));
						if (val==null){
							 val = new ArrayList<String>();

						}
						val.add(obj.getString());
						retMap.put(obj.getLang().getLang(), val);
					} else {
						List<String> val = new ArrayList<String>();
						val.add(obj.getString());
						retMap.put("def", val);
					}
				}
				if (obj.getResource() != null) {
					if (obj.getLang() != null) {
						String lang = obj.getLang().getLang();
						if (retMap.containsKey(lang)) {
							List<String> val = retMap.get(lang);
							val.add(obj.getResource());
							retMap.put(lang, val);
						} else {
							List<String> val = new ArrayList<String>();
							val.add(obj.getResource());
							retMap.put(lang, val);

						}
					} else {
						List<String> val = new ArrayList<String>();
						val.add(obj.getResource());
						retMap.put("def", val);
					}
				}
			}
			return retMap;
		}
		return null;
	}

	/**
	 * Method that updates an Aggregation in Mongo
	 * 
	 * @param mongoAggregation
	 *            The Aggregation to update
	 * @param mongoServer
	 *            The server to be used
	 */
	public static void updateAggregation(AggregationImpl mongoAggregation,
			MongoServer mongoServer) {
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

	/**
	 * Method that updates a Europeana Aggregation in Mongo
	 * 
	 * @param mongoAggregation
	 *            The Europeana aggregation to update
	 * @param mongoServer
	 *            The server to be used
	 */
	public static void updateEuropeanaAggregation(
			EuropeanaAggregation mongoAggregation, MongoServer mongoServer) {
		update(EuropeanaAggregationImpl.class, mongoAggregation.getAbout(),
				mongoServer, "aggregatedCHO",
				mongoAggregation.getAggregatedCHO());

		update(EuropeanaAggregationImpl.class, mongoAggregation.getAbout(),
				mongoServer, "aggregates", mongoAggregation.getAggregates());

		update(EuropeanaAggregationImpl.class, mongoAggregation.getAbout(),
				mongoServer, "dcCreator", mongoAggregation.getDcCreator());

		update(EuropeanaAggregationImpl.class, mongoAggregation.getAbout(),
				mongoServer, "edmHasView", mongoAggregation.getEdmHasView());

		update(EuropeanaAggregationImpl.class, mongoAggregation.getAbout(),
				mongoServer, "edmCountry", mongoAggregation.getEdmCountry());

		update(EuropeanaAggregationImpl.class, mongoAggregation.getAbout(),
				mongoServer, "edmIsShownBy", mongoAggregation.getEdmIsShownBy());

		update(EuropeanaAggregationImpl.class, mongoAggregation.getAbout(),
				mongoServer, "edmLandingPage",
				mongoAggregation.getEdmLandingPage());

		update(EuropeanaAggregationImpl.class, mongoAggregation.getAbout(),
				mongoServer, "edmLanguage", mongoAggregation.getEdmLanguage());

		update(EuropeanaAggregationImpl.class, mongoAggregation.getAbout(),
				mongoServer, "edmRights", mongoAggregation.getEdmRights());

	}

	/**
	 * Method that updates a Proxy in Mongo
	 * 
	 * @param proxy
	 *            The proxy to update
	 * @param mongoServer
	 *            The server to be used
	 */
	public static ProxyImpl updateProxy(ProxyImpl proxy, MongoServer mongoServer) {

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
		return proxy;
	}

}
