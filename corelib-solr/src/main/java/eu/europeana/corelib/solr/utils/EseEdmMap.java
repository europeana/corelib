package eu.europeana.corelib.solr.utils;

import eu.europeana.corelib.definitions.solr.beans.FullBean;

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
			return bean.getEdmObject()[0];
		}
	},
	DC_IDENTIFIER("dc_identifier") {
		@Override
		public	String getEdmValue(FullBean bean) {
			return bean.getDcIdentifier()[0];
		}
	}
	;
	
	private EseEdmMap(String field) {
		// TODO Auto-generated constructor stub
	}

	public abstract String getEdmValue(FullBean bean);

}
