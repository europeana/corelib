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
public enum Facet {

    UGC("UGC"),
    LANGUAGE("LANGUAGE"),
    TYPE("TYPE"),
    YEAR("YEAR"),
    PROVIDER("PROVIDER"),
    DATA_PROVIDER("DATA_PROVIDER"),
    COUNTRY("COUNTRY"),
    RIGHTS("RIGHTS"),
    FACET_TAGS("facet_tags"),
    IS_FULLTEXT("is_fulltext"),
    //HAS_THUMBNAILS("has_thumbnails"),
    HAS_MEDIA("has_media");

    private String facet;

    Facet(String facets) {
        this.facet = facets;
    }

    @Override
    public String toString() {
        return facet;
    }
}
