package eu.europeana.corelib.definitions.solr;

/**
 * Created by luthien on 22/11/2018.
 */
public enum RangeFacetType {

    FILTER_TAGS("filter_tags"),
    FACET_TAGS("facet_tags"),
    NIF_CONFIDENCE("nif_confidence"),
    EUROPEANA_COMPLETENESS("europeana_completeness"),
    VERSION("version"),
    TIMESTAMP("timestamp"),
    TIMESTAMP_CREATED("timestamp_created"),
    TIMESTAMP_UPDATED("timestamp_update"),
    ISSUED("issued"),
    WR_CC_DEPRECATED_ON("wr_cc_deprecated_on"),
    PA_CC_DEPRECATED_ON("provider_aggregation_cc_deprecated_on");


    private String facet;

    RangeFacetType(String facets) {
        this.facet = facets;
    }

    @Override
    public String toString() {
        return facet;
    }
}
