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
package eu.europeana.corelib.solr.server;

import eu.europeana.corelib.definitions.solr.beans.FullBean;

/**
 * Basic MongoDB server implementation
 *
 * @author Yorgos.Mamakis@ kb.nl
 *
 */
public interface EdmMongoServer extends MongoServer{

    /**
     * A basic implementation of a MongoDB Server connection
     *
     * @param id The object id to retrieve from the database
     * @return A document from MongoDB - case where the user selects to retrieve
     * one specific object
     */
    FullBean getFullBean(String id);

    /**
     * Search using the rdf:about field of an EDM entity
     * @param <T> 
     *          The Class type of an EDM Entity
     * @param clazz
     *          The Class name of an EDM entity
     * @param about
     *          The unique identifier of an EDM Entity
     * @return 
     *          The EDM Mongo Entity
     */
    <T> T searchByAbout(Class<T> clazz, String about);
}
