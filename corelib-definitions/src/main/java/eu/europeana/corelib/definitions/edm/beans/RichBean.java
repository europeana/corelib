package eu.europeana.corelib.definitions.edm.beans;

import java.util.List;
import java.util.Map;

public interface RichBean extends ApiBean {

	/**
	 * 
	 * @return edm:isShownBy
	 */
	String[] getEdmIsShownBy();

	/**
	 * 
	 * @return edm:isShownBy
	 */
	String[] getDcDescription();

	/**
	 * 
	 * @return edm:landingPage
	 */
	String[] getEdmLandingPage();
        
        /**
         * Language aware dc:description
         * @return 
         */
        Map<String,List<String>> getDcDescriptionLangAware();
        
        /**
         * Language aware dc:type
         * @return 
         */
        Map<String, List<String>> getDcTypeLangAware();
        
        /**
         * Language aware dc:subject
         * @return 
         */
        Map<String, List<String>> getDcSubjectLangAware();
}
