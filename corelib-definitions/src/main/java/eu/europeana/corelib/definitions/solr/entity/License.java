package eu.europeana.corelib.definitions.solr.entity;

import java.sql.Date;

public interface License extends AbstractEdmEntity{

	String getOdrlInheritFrom();
	
	void setOdrlInheritFrom(String odrlInheritFrom);
	
	Date getCcDeprecatedOn();
	
	void setCcDeprecatedOn(Date ccDeprecatedOn);
	
}
