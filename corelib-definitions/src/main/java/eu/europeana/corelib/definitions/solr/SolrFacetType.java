package eu.europeana.corelib.definitions.solr;

/**
 * Check if needed
 *
 * @author Yorgos.Mamakis@ kb.nl
 */
public enum SolrFacetType {

    UGC("UGC"),
    LANGUAGE("LANGUAGE"),
    TYPE("TYPE"),
    YEAR("YEAR"),
    PROVIDER("PROVIDER"),
    DATA_PROVIDER("DATA_PROVIDER"),
    COUNTRY("COUNTRY"),
    RIGHTS("RIGHTS"),  // DEFAULT set stops here
    FACET_TAGS("facet_tags"), //SOLR eats 'm lowercase
    TEXT_FULLTEXT("is_fulltext"),
    THUMBNAIL("has_thumbnails"),
    MEDIA("has_media"),
    LANDINGPAGE("has_landingpage");

    private String facet;

    SolrFacetType(String facets) {
        this.facet = facets;
    }

    @Override
    public String toString() {
        return facet;
    }
}
