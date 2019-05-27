package eu.europeana.corelib.definitions.edm.entity;

import java.util.Date;

/**
 * 
 * @author ymamakis
 *
 */
public interface License extends AbstractEdmEntity{

	String getOdrlInheritFrom();
	
	void setOdrlInheritFrom(String odrlInheritFrom);
	
	Date getCcDeprecatedOn();
	
	void setCcDeprecatedOn(Date ccDeprecatedOn);
	
}
