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

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.common.SolrInputDocument;

import eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType;
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
	 * Checks if the Facet is TYPE that everything is uppercase and known DocType according to EDM
	 * 
	 * @param refinements
	 * @return
	 */
	public static boolean checkTypeFacet(String[] refinements) {
		if (refinements != null) {
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
	public static <T> T exists(Class<T> clazz, T object) throws InstantiationException, IllegalAccessException {
		return (object == null ? clazz.newInstance() : object);
	}

	public static void addResourceOrLiteralType(SolrInputDocument destination, EdmLabel label,
			ResourceOrLiteralType type) {
		String value = getValueOfResourceOrLiteralType(type);
		if (value != null) {
			SolrInputDocument solrInputDocument = (SolrInputDocument) destination;
			solrInputDocument.addField(label.toString(), value);
		}
	}

	public static void addResourceOrLiteralType(List<String> destination, ResourceOrLiteralType type) {
		String value = getValueOfResourceOrLiteralType(type);
		if (value != null) {
			destination.add(value);
		}
	}

	public static String getValueOfResourceOrLiteralType(ResourceOrLiteralType type) {
		String value = null;
		if (type != null) {
			value = StringUtils.isNotEmpty(type.getResource()) ? type.getResource() : type.getString();
		}
		return value;
	}

	public static Class<? extends IdBeanImpl> getImplementationClass(Class<? extends IdBean> interfaze) {
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
