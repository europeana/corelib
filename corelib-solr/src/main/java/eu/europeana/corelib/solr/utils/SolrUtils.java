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

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.util.ClientUtils;
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

	private SolrUtils() {

	}

	/**
	 * Checks if there is no TYPE facet with an invalid type according to EDM
	 *
	 * 
	 * @param refinements
	 * @return
	 *   Returns true if there is no TYPE facet or each type facet has valid value
	 */
	public static boolean checkTypeFacet(String[] refinements) {
		if (refinements != null) {
			for (String refinement : refinements) {
				if (StringUtils.contains(refinement, "TYPE:")
						&& !StringUtils.contains(refinement, " OR ")) {
					if (DocType.safeValueOf(StringUtils.substringAfter(
							refinement, "TYPE:")) == null) {
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
				lst.add(type.getResource().getResource());
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
			List<String> lst = new ArrayList<String>();
			
			for (ResourceOrLiteralType obj : list) {
				if(obj.getResource()!=null){
					lst.add(obj.getResource().getResource());
				}
				if(obj.getString()!=null){
					lst.add(obj.getString());
				}
			}
			String[] arr = lst.toArray(new String[lst.size()]);
			return arr;
		}
		return new String[] {};
	}

	/**
	 * Returns an array of strings based on values from a LiteralType list.
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
	 * Returns an array of strings based on values from a ResourceType list.
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
	 * @param obj
	 *            The LiteralType object
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
	 * @param obj
	 *            The ResourceOrLiteralType object
	 * @return a string from a ResourceOrLiteralType object
	 */
	public static String getResourceOrLiteralString(ResourceOrLiteralType obj) {
		if (obj != null) {
			return obj.getResource() != null ? obj.getResource().getResource()
					: obj.getString();
		}
		return null;
	}

	/**
	 * 
	 * @param obj
	 *            The ResourceType object
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
	 * 
	 * @param interfaze
	 *            The interfaze to check
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
	 * 
	 * @param solrInputDocument
	 *            The document to add a document to
	 * @param obj
	 *            The object to add
	 * @param label
	 *            The label of the field to add the object to
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
	 * Adds a field from an enumeration.
	 * 
	 * @param solrInputDocument
	 * @param obj
	 * @param label
	 * @return
	 */
	public static SolrInputDocument addFieldFromEnum(
			SolrInputDocument solrInputDocument, String obj, EdmLabel label) {
		if (obj != null) {
			solrInputDocument.addField(label.toString(),
					StringUtils.lowerCase(obj));
		}
		return solrInputDocument;
	}

	/**
	 * Adds a field to a document from a ResourceOrLiteralType object
	 * 
	 * @param solrInputDocument
	 *            The document to add a document to
	 * @param obj
	 *            The object to add
	 * @param label
	 *            The label of the field to add the object to
	 * @return
	 */
	public static <T extends ResourceOrLiteralType> SolrInputDocument addFieldFromResourceOrLiteral(
			SolrInputDocument solrInputDocument, T obj, EdmLabel label) {
		if (obj != null) {
			if (obj.getResource() != null) {
				solrInputDocument.addField(label.toString(), obj.getResource()
						.getResource());
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

	/**
	 * Get the edm:preview url from an EDM object
	 * 
	 * @param rdf
	 * @return
	 */
	public static String getPreviewUrl(RDF rdf) {
		if (rdf.getAggregationList().get(0).getObject() != null
				&& StringUtils.isNotEmpty(rdf.getAggregationList().get(0)
						.getObject().getResource())) {
			return rdf.getAggregationList().get(0).getObject().getResource();
		}
		if (rdf.getAggregationList().get(0).getIsShownBy() != null
				&& StringUtils.isNotEmpty(rdf.getAggregationList().get(0)
						.getIsShownBy().getResource())) {
			return rdf.getAggregationList().get(0).getIsShownBy().getResource();
		}

		return null;
	}

	/**
	 * Translates ESE fielded queries to EDM fielded queries
	 * 
	 * @param query
	 * @return
	 */
	public static String translateQuery(String query) {
		if (!query.contains(":")) {
			return query;
		}

		// handling field + "*:*"
		if (query.equals("title:*:*")) {
			return "title:*";
		}
		if (query.equals("who:*:*")) {
			return "who:*";
		}
		if (query.equals("what:*:*")) {
			return "what:*";
		}
		if (query.equals("where:*:*")) {
			return "where:*";
		}
		if (query.equals("when:*:*")) {
			return "when:*";
		}

		// handling API1 fields
		for (FieldMapping field : FieldMapping.values()) {
			if (query.contains(field.getEseField() + ":")) {
				query = query.replaceAll("\\b" + field.getEseField() + ":",
						field.getEdmField() + ":");
			}
		}

		return query;
	}

	public static String escapeQuery(String query) {
		return ClientUtils.escapeQueryChars(query).replace("\\ ", " ")
				.replace("\\-", "-");
	}
	
	public static String escapeFacet(String field, String query) {
		if (StringUtils.isNotBlank(field) && StringUtils.isNotBlank(query)) {
			query = escapeQuery(StringUtils.trim(query));
			StringBuilder sb = new StringBuilder(StringUtils.trim(field));
			sb.append(":\"");
			sb.append(query);
			sb.append("\"");
			return sb.toString();
		}
		return null;
	}
}
