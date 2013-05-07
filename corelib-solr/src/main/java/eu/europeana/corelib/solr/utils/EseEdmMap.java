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

import eu.europeana.corelib.definitions.solr.beans.FullBean;
/**
 * Enumeration mapping information from ESE to EDM
 * @author Yorgos.Mamakis@ kb.nl
 *
 */
public enum EseEdmMap {

	EUROPEANA_ISSHOWNBY("europeana_isShownBy") {
		@Override
		public String getEdmValue(FullBean bean) {
			return bean.getAggregations().get(0).getEdmIsShownBy();
		}
	},
	EUROPEANA_ISSHOWNAT("europeana_isShownAt") {
		@Override
		public String getEdmValue(FullBean bean) {
			return bean.getAggregations().get(0).getEdmIsShownAt();
		}
	},
	EUROPEANA_OBJECT("europeana_object") {
		@Override
		public String getEdmValue(FullBean bean) {
			return bean.getAggregations().get(0).getEdmObject();
		}
	},
	DC_IDENTIFIER("dc_identifier") {
		@Override
		public	String getEdmValue(FullBean bean) {
			return bean.getProxies().get(0).getDcIdentifier().values().iterator().next().get(0);
		}
	}
	;
	private String field;
	private EseEdmMap(String field) {
		this.field = field;
	}
	/**
	 * Return the field value from a bean
	 * @param bean
	 * @return
	 */
	
	public String getField (){
		return this.field;
	}
	
	public static EseEdmMap getEseEdmMap(String field){
		for(EseEdmMap map: EseEdmMap.values())
		if(StringUtils.equals(map.field, field)){
			return map;
		}
		throw new RuntimeException("Field not found :" + field);
	}
	public abstract String getEdmValue(FullBean bean);

}
