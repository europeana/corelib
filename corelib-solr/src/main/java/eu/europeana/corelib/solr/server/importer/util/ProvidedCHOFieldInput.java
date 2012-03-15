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
package eu.europeana.corelib.solr.server.importer.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.common.SolrInputDocument;

import eu.europeana.corelib.definitions.jibx.ProvidedCHOType;
import eu.europeana.corelib.definitions.jibx.ResourceType;
import eu.europeana.corelib.definitions.jibx.SameAs;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.solr.entity.ProvidedCHOImpl;
import eu.europeana.corelib.solr.server.MongoDBServer;
import eu.europeana.corelib.solr.utils.MongoUtils;
import eu.europeana.corelib.solr.utils.SolrUtils;

/**
 * Class constructing a SOLR document and MongoDB representation of a
 * ProvidedCHO
 *
 *
 * @author Yorgos.Mamakis@ kb.nl
 *
 */
public final class ProvidedCHOFieldInput {

	
	private ProvidedCHOFieldInput(){
		
	}
    /**
     * Method filling in a SolrInputDocument with the fields of a providedCHO
     *
     * @param providedCHO The ProvidedCHO representation from the JiBX bindings
     * @param solrInputDocument The SolrInputDocument whose fields the class
     * fills in
     * @return A SolrInputDocument with filled in ProvidedCHO fields
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static SolrInputDocument createProvidedCHOFields(
            ProvidedCHOType providedCHO, SolrInputDocument solrInputDocument)
            throws InstantiationException, IllegalAccessException {
        solrInputDocument.addField(EdmLabel.EUROPEANA_ID.toString(),
                providedCHO.getAbout());
        if (providedCHO.getSameAList() != null) {
            for (SameAs sameAs : providedCHO.getSameAList()) {
                solrInputDocument.addField(EdmLabel.OWL_SAMEAS.toString(),
                        sameAs.getResource());
            }
        }
        solrInputDocument.addField(
                EdmLabel.EDM_IS_NEXT_IN_SEQUENCE.toString(),
                SolrUtils.exists(ResourceType.class,
                providedCHO.getIsNextInSequence()).getResource());

        return solrInputDocument;
    }

    /**
     *
     * Method Creating a MongoDB Entity of a ProvidedCHO
     *
     * @param providedCHO The ProvidedCHO representation from the JiBX bindings
     * @param mongoServer The MongoDB Server object to save the ProvidedCHO
     * @return The MongoDB ProvidedCHO Entity
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static ProvidedCHOImpl createProvidedCHOMongoFields(
            ProvidedCHOType providedCHO, MongoDBServer mongoServer)
            throws InstantiationException, IllegalAccessException {
        ProvidedCHOImpl mongoProvidedCHO = mongoServer.searchByAbout(
                ProvidedCHOImpl.class, providedCHO.getAbout());
        //If the ProvidedCHO does not exist create it
        if (mongoProvidedCHO == null) {
            mongoProvidedCHO = new ProvidedCHOImpl();
            mongoProvidedCHO.setAbout(providedCHO.getAbout());
            mongoProvidedCHO.setEdmIsNextInSequence(SolrUtils.exists(
                    ResourceType.class, providedCHO.getIsNextInSequence()).getResource());
            List<String> owlSameAsList = new ArrayList<String>();
            if (providedCHO.getSameAList() != null) {
                for (SameAs sameAs : providedCHO.getSameAList()) {
                    owlSameAsList.add(sameAs.getResource());
                }
                mongoProvidedCHO.setOwlSameAs(owlSameAsList.toArray(new String[owlSameAsList.size()]));
            }
            mongoServer.getDatastore().save(mongoProvidedCHO);
        } else {
            //update the ProvidedCHO
            //Start by updating the isNextInSequence fields
            if (!StringUtils.equals(mongoProvidedCHO.getEdmIsNextInSequence(),
                    SolrUtils.exists(ResourceType.class, providedCHO.getIsNextInSequence()).getResource())) {
                mongoProvidedCHO.setEdmIsNextInSequence(SolrUtils.exists(
                        ResourceType.class, providedCHO.getIsNextInSequence()).getResource());
                MongoUtils.update(ProvidedCHOImpl.class, mongoProvidedCHO.getAbout(), mongoServer,
  						"edmIsNextInSequence", providedCHO.getIsNextInSequence().getResource());
              
            }
            //then the sameAs list
            List<String> owlSameAsList = null;
            if (providedCHO.getSameAList() != null) {
                owlSameAsList = new ArrayList<String>();
                for (SameAs sameAs : providedCHO.getSameAList()) {
                    owlSameAsList.add(sameAs.getResource());
                }
                MongoUtils.update(ProvidedCHOImpl.class, mongoProvidedCHO.getAbout(), mongoServer,
  						"owlSameAs", owlSameAsList);
            }

        }
        return mongoProvidedCHO;
    }
    
    public static void deleteProvideCHOFromMongo(String about, MongoDBServer mongoServer){
    	MongoUtils.delete(ProvidedCHOImpl.class,about,mongoServer);
    }
    
}
