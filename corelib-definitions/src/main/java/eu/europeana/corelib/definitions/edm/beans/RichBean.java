package eu.europeana.corelib.definitions.edm.beans;

import java.util.List;
import java.util.Map;

public interface RichBean extends ApiBean {

    /**
     * @return edm:isShownBy
     */
    String[] getEdmIsShownBy();

    /**
     * @return edm:isShownBy
     */
    String[] getDcDescription();

    /**
     * @return edm:landingPage
     */
    String[] getEdmLandingPage();

    /**
     * Language aware dc:description
     */
    Map<String, List<String>> getDcDescriptionLangAware();

    /**
     * Language aware dc:type
     */
    Map<String, List<String>> getDcTypeLangAware();

    /**
     * Language aware dc:subject
     */
    Map<String, List<String>> getDcSubjectLangAware();
}
