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
