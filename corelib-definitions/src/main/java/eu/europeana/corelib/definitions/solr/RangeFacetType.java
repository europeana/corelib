/*
 * Copyright 2007-2018 The Europeana Foundation
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
