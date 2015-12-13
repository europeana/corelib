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

import org.apache.commons.lang.StringUtils;

import eu.europeana.corelib.definitions.edm.beans.FullBean;
import eu.europeana.corelib.edm.exceptions.EdmFieldNotFoundException;
import eu.europeana.corelib.edm.exceptions.EdmValueNotFoundException;

/**
 * Enumeration mapping information from ESE to EDM
 * 
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public enum EseEdmMap {

	EUROPEANA_ISSHOWNBY("europeana_isShownBy") {
		@Override
		public String getEdmValue(FullBean bean) {
			try {
				return bean.getAggregations().get(0).getEdmIsShownBy();
			} catch (ArrayIndexOutOfBoundsException e) {
				throw new EdmValueNotFoundException("edm:isShownBy",
						bean.getAbout());
			}
		}
	},
	EUROPEANA_ISSHOWNAT("europeana_isShownAt") {
		@Override
		public String getEdmValue(FullBean bean) {
			try {
				return bean.getAggregations().get(0).getEdmIsShownAt();
			} catch (ArrayIndexOutOfBoundsException e) {
				throw new EdmValueNotFoundException("edm:isShownAt",
						bean.getAbout());
			}
		}
	},
	EUROPEANA_OBJECT("europeana_object") {
		@Override
		public String getEdmValue(FullBean bean) {
			try {
				return bean.getAggregations().get(0).getEdmObject();
			} catch (ArrayIndexOutOfBoundsException e) {
				throw new EdmValueNotFoundException("edm:object",
						bean.getAbout());
			}
		}
	},
	DC_IDENTIFIER("dc_identifier") {
		@Override
		public String getEdmValue(FullBean bean) {
			try {
				return bean.getProxies().get(0).getDcIdentifier().values()
						.iterator().next().get(0);
			} catch (ArrayIndexOutOfBoundsException e) {
				throw new EdmValueNotFoundException("dc:identifier",
						bean.getAbout());
			}
		}
	},
	EUROPEANA_FAKE("europeana_fake") {
		@Override
		public String getEdmValue(FullBean bean) {
			try {
				return bean.getAggregations().get(0).getAbout();
			} catch (ArrayIndexOutOfBoundsException e) {
				throw new EdmValueNotFoundException(
						"ore:Aggregation@rdf:about", bean.getAbout());
			}
		}
	},
	EUROPEANA_HASVIEW("edm_hasView") {
		@Override
		public String getEdmValue(FullBean bean) {
			try {
				return bean.getEuropeanaAggregation().getEdmHasView()[0];
			} catch (ArrayIndexOutOfBoundsException e) {
				throw new EdmValueNotFoundException("edm:hasView",
						bean.getAbout());
			}
		}
	},
	// For 1914-1918
	ID_EUROPEANAURI("oai_europeana19141918_id_europeanaURI") {
		@Override
		public String getEdmValue(FullBean bean) {
			try {
				return bean.getAggregations().get(0).getHasView()[0];
			} catch (NullPointerException e) {
				throw e;
			}
		}
	}
	,
	EUROPEANA_IDENTIFIER("europeana_identifier"){

		@Override
		public String getEdmValue(FullBean bean)
				throws EdmValueNotFoundException, NullPointerException {
			try{
				return bean.getProvidedCHOs().get(0).getOwlSameAs()[0];
			} catch (NullPointerException e){
				throw e;
			}
		}
		
	},
	HEADER_IDENTIFIER("header_identifier"){

		@Override
		public String getEdmValue(FullBean bean)
				throws EdmValueNotFoundException, NullPointerException {
			try{
				return bean.getProvidedCHOs().get(0).getOwlSameAs()[0];
			} catch (NullPointerException e){
				throw e;
			}
		}
		
	}
	;
	private String field;

	EseEdmMap(String field) {
		this.field = field;
	}

	/**
	 * Return the field value from a bean
	 * 
	 * @param bean
	 * @return
	 */

	public String getField() {
		return this.field;
	}

	public static EseEdmMap getEseEdmMap(String field, String recordId) {
		for (EseEdmMap map : EseEdmMap.values())
			if (StringUtils.equals(map.field, field)) {
				return map;
			}
		throw new EdmFieldNotFoundException(field, recordId);
	}

	public abstract String getEdmValue(FullBean bean)
			throws EdmValueNotFoundException, NullPointerException;

}
