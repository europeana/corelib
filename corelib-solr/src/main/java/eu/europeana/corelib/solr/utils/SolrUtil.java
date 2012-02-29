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

import org.apache.commons.lang.StringUtils;

import eu.europeana.corelib.definitions.solr.DocType;

/**
 * Set of utils for SOLR queries
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public class SolrUtil {

	/**
	 * Checks if the Facet is TYPE that everything is uppercase and known DocType according to EDM
	 * 
	 * @param refinements
	 * @return
	 */
	public static boolean checkTypeFacet(String[] refinements) {
		for (String refinement : refinements) {
			if (StringUtils.startsWith(refinement, "TYPE:")) {
				if (!StringUtils.isAllUpperCase(StringUtils.split(refinement, ":")[1])
						|| StringUtils.split(refinement, ":").length != 2) {
					return false;
				} else {
					try {
						DocType.get(refinement);
					} catch (IllegalArgumentException e) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * Method that check if an object exists and return it
	 * @param clazz
	 * 			The class type of the object
	 * @param object
	 * 			The object to check if it exists
	 * @return the object
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public  static  <T> T exists (Class<T> clazz, T object) throws InstantiationException, IllegalAccessException{
		return ( object==null? clazz.newInstance():object );
	}
	
}
