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
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.common.SolrInputDocument;

import eu.europeana.corelib.definitions.jibx.LiteralType;
import eu.europeana.corelib.definitions.jibx.RDF;
import eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType;
import eu.europeana.corelib.definitions.jibx.ResourceType;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.definitions.solr.DocType;
import eu.europeana.corelib.definitions.solr.beans.ApiBean;
import eu.europeana.corelib.definitions.solr.beans.BriefBean;
import eu.europeana.corelib.definitions.solr.beans.IdBean;
import eu.europeana.corelib.solr.bean.impl.ApiBeanImpl;
import eu.europeana.corelib.solr.bean.impl.BriefBeanImpl;
import eu.europeana.corelib.solr.bean.impl.IdBeanImpl;

/**
 * Set of utils for SOLR queries
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public final class SolrUtils {
	private static final Logger log = Logger.getLogger(SolrUtils.class
			.getName());

	private SolrUtils() {

	}

	/**
	 * Checks if the Facet is TYPE that everything is uppercase and known
	 * DocType according to EDM
	 * 
	 * @param refinements
	 * @return
	 */
	public static boolean checkTypeFacet(String[] refinements) {
		if (refinements != null) {
			for (String refinement : refinements) {
				if (StringUtils.contains(refinement, "TYPE:")
						&& !StringUtils.contains(refinement, " OR ")) {
					try {
						DocType.get(StringUtils.substringAfter(refinement,
								"TYPE:"));
					} catch (IllegalArgumentException e) {
						log.severe(e.getMessage());
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * Method that check if an object exists and return it
	 * 
	 * @param clazz
	 *            The class type of the object
	 * @param object
	 *            The object to check if it exists
	 * @return the object
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static <T> T exists(Class<T> clazz, T object)
			throws InstantiationException, IllegalAccessException {
		return (object == null ? clazz.newInstance() : object);
	}

	/**
	 * Method that adds a ResourceOrLiteralType object in a solr field
	 * 
	 * @param solrInputDocument
	 *            The solr document to add the field to
	 * @param label
	 *            The label of the field
	 * @param type
	 *            The ResourceOrLiteralType object
	 */
	public static void addResourceOrLiteralType(
			SolrInputDocument solrInputDocument, EdmLabel label,
			ResourceOrLiteralType type) {
		if (type != null) {
			if (type.getString() != null) {
				solrInputDocument.addField(label.toString(), type.getString());
			}
			if (type.getResource() != null) {
				solrInputDocument
						.addField(label.toString(), type.getResource());
			}
		}
	}

	/**
	 * Returns an array of strings based on values from a ResourceOrLiteralType
	 * object. Since it is perfectly valid to have both an rdf:resource and a
	 * value on a field This method will return both in a String array
	 * 
	 * @param type
	 *            The ResourceOrLiteralType object
	 * @return An array of strings with the values of the object
	 */
	public static String[] resourceOrLiteralToArray(ResourceOrLiteralType type) {
		if (type != null) {
			List<String> lst = new ArrayList<String>();
			if (type.getResource() != null) {
				lst.add(type.getResource());
			}
			if (type.getString() != null) {
				lst.add(type.getString());
			}
			return lst.toArray(new String[lst.size()]);
		}
		return new String[] {};
	}

	/**
	 * Returns an array of strings based on values from a ResourceOrLiteralType
	 * list. Since it is perfectly valid to have both an rdf:resource and a
	 * value on a field This method will return both in a String array
	 * 
	 * @param list
	 *            The ResourceOrLiteralType list
	 * @return An array of strings with the values of the list
	 */
	public static String[] resourceOrLiteralListToArray(
			List<? extends ResourceOrLiteralType> list) {
		if (list != null) {
			String[] arr = new String[list.size()];
			int i = 0;
			for (ResourceOrLiteralType obj : list) {
				arr[i] = obj.getResource() != null ? obj.getResource() : obj
						.getString();
				i++;
			}
			return arr;
		}
		return new String[] {};
	}

	/**
	 * Returns an array of strings based on values from a LiteralType
	 * list. 
	 * 
	 * @param list
	 *            The LiteralType list
	 * @return An array of strings with the values of the list
	 */
	public static String[] literalListToArray(List<? extends LiteralType> list) {
		if (list != null) {
			String[] arr = new String[list.size()];
			int i = 0;
			for (LiteralType obj : list) {
				arr[i] = obj.getString();
				i++;
			}
			return arr;
		}
		return new String[] {};
	}

	/**
	 * Returns an array of strings based on values from a ResourceType
	 * list. 
	 * 
	 * @param list
	 *            The ResourceType list
	 * @return An array of strings with the values of the list
	 */
	public static String[] resourceListToArray(List<? extends ResourceType> list) {
		if (list != null) {
			String[] arr = new String[list.size()];
			int i = 0;
			for (ResourceType obj : list) {
				arr[i] = obj.getResource();
				i++;
			}
			return arr;
		}
		return new String[] {};
	}

	/**
	 * 
	 * @param obj The LiteralType object
	 * @return a string from a LiteralType object
	 */
	public static String getLiteralString(LiteralType obj) {
		if (obj != null) {
			return obj.getString() != null ? obj.getString() : null;
		}
		return null;
	}

	/**
	 * 
	 * @param obj The ResourceOrLiteralType object
	 * @return a string from a ResourceOrLiteralType object
	 */
	public static String getResourceOrLiteralString(ResourceOrLiteralType obj) {
		if (obj != null) {
			return obj.getResource() != null ? obj.getResource() : obj
					.getString();
		}
		return null;
	}

	/**
	 * 
	 * @param obj The ResourceType object
	 * @return a string from a ResourceType object
	 */
	public static String getResourceString(ResourceType obj) {
		if (obj != null) {
			return obj.getResource() != null ? obj.getResource() : null;
		}
		return null;
	}

	/**
	 * Get the implementation class of one of the Solr Beans
	 * @param interfaze The interfaze to check
	 * @return The corresponding implementation class
	 */
	public static Class<? extends IdBeanImpl> getImplementationClass(
			Class<? extends IdBean> interfaze) {
		if (interfaze != null) {
			if (interfaze == ApiBean.class) {
				return ApiBeanImpl.class;
			}
			if (interfaze == BriefBean.class) {
				return BriefBeanImpl.class;
			}
			if (interfaze == IdBean.class) {
				return IdBeanImpl.class;
			}
		}
		return null;
	}

	/**
	 * Adds a field to a document from a LiteralType object
	 * @param solrInputDocument The document to add a document to
	 * @param obj The object to add
	 * @param label The label of the field to add the object to
	 * @return
	 */
	public static <T extends LiteralType> SolrInputDocument addFieldFromLiteral(
			SolrInputDocument solrInputDocument, T obj, EdmLabel label) {
		if (obj != null) {
			if (obj.getString() != null) {
				if (obj.getLang() != null) {
					solrInputDocument.addField(label.toString() + "."
							+ obj.getLang().getLang(), obj.getString());
				} else {
					solrInputDocument.addField(label.toString(),
							obj.getString());
				}
			}
		}
		return solrInputDocument;
	}

	/**
	 * Adds a field to a document from a ResourceOrLiteralType object
	 * @param solrInputDocument The document to add a document to
	 * @param obj The object to add
	 * @param label The label of the field to add the object to
	 * @return
	 */
	public static <T extends ResourceOrLiteralType> SolrInputDocument addFieldFromResourceOrLiteral(
			SolrInputDocument solrInputDocument, T obj, EdmLabel label) {
		if (obj != null) {
			if (obj.getResource() != null) {
				solrInputDocument.addField(label.toString(), obj.getResource());
			}
			if (obj.getString() != null) {
				if (obj.getLang() != null) {
					solrInputDocument.addField(label.toString() + "."
							+ obj.getLang().getLang(), obj.getString());
				} else {
					solrInputDocument.addField(label.toString(),
							obj.getString());
				}
			}
		}

		return solrInputDocument;
	}
	
	public static String getPreviewUrl(RDF rdf){
		String previewUrl = null;
		if(rdf.getAggregationList().get(0).getIsShownBy()!=null&&StringUtils.isNotEmpty(rdf.getAggregationList().get(0).getIsShownBy().getResource())){
			previewUrl = rdf.getAggregationList().get(0).getIsShownBy().getResource();
		}
		if(rdf.getAggregationList().get(0).getObject()!=null&& StringUtils.isNotEmpty(rdf.getAggregationList().get(0).getObject().getResource())){
			previewUrl = rdf.getAggregationList().get(0).getObject().getResource();
		}
		return previewUrl;
	}

	/**
	 * Translates ESE fielded queries to EDM fielded queries
	 * @param query
	 * @return
	 */
	public static String translateQuery(String query) {
		if (!query.contains(":")) {
			return query;
		}

		// handling field + "*:*"
		if (query.equals("title:*:*")) {return "title:*";}
		if (query.equals("who:*:*")) {return "who:*";}
		if (query.equals("what:*:*")) {return "what:*";}
		if (query.equals("where:*:*")) {return "where:*";}
		if (query.equals("when:*:*")) {return "when:*";}

		// handling API1 fields
		for (FieldMapping field : FieldMapping.values()) {
			if (query.contains(field.getEseField() + ":")) {
				query = query.replace(field.getEseField() + ":", field.getEdmField() + ":");
			}
		}

		return query;
	}
}
