package eu.europeana.corelib.definitions.edm.beans;

import java.util.List;
import java.util.Map;

/**
 *  The RichBean contains the fields exposed by the SOLR engine for presenting each item in the search results when
 *  using the 'rich' profile
 */
public interface RichBean extends ApiBean {

    /**
     * @return edm:landingPage
     */
    String[] getEdmLandingPage();

    /**
     * Language aware dc:type
     */
    Map<String, List<String>> getDcTypeLangAware();

    /**
     * Language aware dc:subject
     */
    Map<String, List<String>> getDcSubjectLangAware();

    /**
     * @return foaf_organization (organizations)
     */
    String[] getOrganizations();

    // temporary added for debugging purposes (see EA-1395)
    List<Map<String, String>> getFulltext();

    // temporary added for debugging purposes (see EA-1395)
    Map<String, List<String>> getFulltextLangAware();
}
