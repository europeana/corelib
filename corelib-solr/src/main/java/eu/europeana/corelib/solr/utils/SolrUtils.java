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

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.common.SolrInputDocument;

import eu.europeana.corelib.definitions.jibx.RDF;
import eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.definitions.solr.DocType;
import eu.europeana.corelib.definitions.solr.beans.ApiBean;
import eu.europeana.corelib.definitions.solr.beans.BriefBean;
import eu.europeana.corelib.definitions.solr.beans.IdBean;
import eu.europeana.corelib.dereference.impl.Dereferencer;
import eu.europeana.corelib.dereference.impl.EuropeanaField;
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
	private static final Logger log = Logger.getLogger(SolrUtils.class.getName());
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
				if (StringUtils.contains(refinement, "TYPE:")) {
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

	public static void addResourceOrLiteralType(SolrInputDocument destination,
			EdmLabel label, ResourceOrLiteralType type, RDF rdf)
			throws MalformedURLException, IOException {
		List<List<String>> value = getValueOfResourceOrLiteralType(type, label,
				rdf);
		if (value != null) {
			SolrInputDocument solrInputDocument = (SolrInputDocument) destination;
			for (List<String> values : value) {
				if (StringUtils.equals(values.get(0), "original")) {
					solrInputDocument.addField(label.toString(), values.get(1));
				} else {
					solrInputDocument.addField(values.get(0), values.get(1));
				}
			}
		}
	}

	public static void addResourceOrLiteralType(List<String> destination,
			EdmLabel label, ResourceOrLiteralType type,
			Map<String, List<String>> mapLists, RDF rdf)
			throws MalformedURLException, IOException {

		List<List<String>> value = getValueOfResourceOrLiteralType(type, label,
				rdf);
		if (value != null) {

			for (List<String> values : value) {
				if (StringUtils.equals(values.get(0), "original")) {
					destination.add(values.get(1));
				} else {
					List<String> fieldValues = mapLists.get(values.get(0)
							.toString());
					if (!fieldValues.contains(values.get(1))) {
						fieldValues.add(values.get(1));
					}
				}
			}
		}
	}

	public static List<List<String>> getValueOfResourceOrLiteralType(
			ResourceOrLiteralType type, EdmLabel label, RDF rdf)
			throws MalformedURLException, IOException {
		List<List<String>> value = new ArrayList<List<String>>();
		if (type != null) {
			if (EuropeanaField.contains(label.toString())) {
				if (StringUtils.isNotEmpty(type.getResource())) {
					value = Dereferencer.normalize(type.getResource());
				} else if (StringUtils.isNotEmpty(type.getString())) {
					value = Dereferencer.normalize(type.getString());
				}
			} else {
				List<String> temp = new ArrayList<String>();
				temp.add("original");
				if (StringUtils.isNotEmpty(type.getResource())) {
					temp.add(type.getResource());
				} else if (StringUtils.isNotEmpty(type.getString())) {
					temp.add(type.getString());
				}
				value.add(temp);
			}

		}
		for (List<String> entry : value) {

			// TODO: cannot be implemented unless the fields are there
		}
		return value;
	}

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
}
