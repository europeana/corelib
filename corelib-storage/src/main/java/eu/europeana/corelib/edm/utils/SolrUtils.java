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
package eu.europeana.corelib.edm.utils;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import eu.europeana.corelib.definitions.jibx.RDF;
import eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType;
import eu.europeana.corelib.definitions.jibx.ResourceType;

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
				if (obj.getResource() != null) {
					lst.add(obj.getResource().getResource());
				}
				if (obj.getString() != null && StringUtils.isNotEmpty(obj.getString())) {
					lst.add(obj.getString());
				}
			}
			String[] arr = lst.toArray(new String[lst.size()]);
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
}
