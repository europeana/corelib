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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.common.SolrInputDocument;

import com.google.code.morphia.mapping.MappingException;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import com.google.code.morphia.query.UpdateResults;

import eu.europeana.corelib.definitions.jibx.AgentType;
import eu.europeana.corelib.definitions.jibx.AltLabel;
import eu.europeana.corelib.definitions.jibx.Note;
import eu.europeana.corelib.definitions.jibx.PrefLabel;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.solr.entity.AgentImpl;
import eu.europeana.corelib.solr.server.MongoDBServer;

/**
 * Constructor of Agent Fields. TODO:update
 *
 * @author Yorgos.Mamakis@ kb.nl
 *
 */
// TODO: Normalizer
public class AgentFieldInput {

    /**
     * Fill in a SolrInputDocument with Agent specific fields
     *
     * @param agentType A JiBX entity that represents an Agent
     * @param solrInputDocument The SolrInputDocument to alter
     * @return The SolrInputDocument with Agent specific values
     */
    public static SolrInputDocument createAgentSolrFields(AgentType agentType,
            SolrInputDocument solrInputDocument) {
        solrInputDocument.addField(EdmLabel.EDM_AGENT.toString(),
                agentType.getAbout());
        if (agentType.getAltLabelList() != null) {
            for (AltLabel altLabel : agentType.getAltLabelList()) {
                if(altLabel.getLang()!=null) {
                    solrInputDocument.addField(
                            EdmLabel.AG_SKOS_ALT_LABEL.toString() + "."
                            + altLabel.getLang().getLang(),
                            altLabel.getString());
                } else {
                    solrInputDocument.addField(
                            EdmLabel.AG_SKOS_ALT_LABEL.toString(),
                            altLabel.getString());
                }
            }
        }
        if (agentType.getPrefLabelList() != null) {
            for (PrefLabel prefLabel : agentType.getPrefLabelList()) {
                if(prefLabel.getLang()!=null) {
                    solrInputDocument.addField(
                            EdmLabel.AG_SKOS_PREF_LABEL.toString() + "."
                            + prefLabel.getLang().getLang(),
                            prefLabel.getString());
                } else {
                    solrInputDocument.addField(
                            EdmLabel.AG_SKOS_PREF_LABEL.toString(),
                            prefLabel.getString());
                }
            }
        }

        if (agentType.getNoteList() != null) {
            for (Note note : agentType.getNoteList()) {
                solrInputDocument.addField(EdmLabel.AG_SKOS_NOTE.toString(),
                        note.getString());
            }
        }
        if (agentType.getBegins() != null) {
            for (String begin : agentType.getBegins()) {
                solrInputDocument.addField(EdmLabel.AG_EDM_BEGIN.toString(),
                        begin);
            }
        }
        if (agentType.getEnds() != null) {
            for (String end : agentType.getEnds()) {
                solrInputDocument.addField(EdmLabel.AG_EDM_END.toString(), end);
            }
        }
        return solrInputDocument;
    }

    /**
     * Create a Mongo Entity of type Agent from the JiBX AgentType object
     *
     * Mapping from the JibXBinding Fields to the MongoDB Entity Fields The
     * fields mapped are the rdf:about (String -> String) skos:note(List<Note>
     * -> String[]) skos:prefLabel(List<PrefLabel> -> HashMap<String,String>
     * (lang,description)) skos:altLabel(List<AltLabel> ->
     * HashMap<String,String> (lang,description)) edm:begin (String -> Date)
     * edm:end (String -> Date)
     *
     * @param agentType - JiBX representation of an Agent EDM entity
     * @param mongoServer - The mongoServer to save the entity
     * @return A list with the agent created (EDM allows more than one Agent
     * entities per ProvidedCHO)
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws MappingException
     */
    public static AgentImpl createAgentMongoEntity(AgentType agentType,
            MongoDBServer mongoServer) throws MappingException,
            InstantiationException, IllegalAccessException {

       AgentImpl agent = (AgentImpl) mongoServer.searchByAbout(AgentImpl.class,
                agentType.getAbout());

        // if it does not exist

        if (agent == null) {
        	agent = createNewAgent(agentType);
            mongoServer.getDatastore().save(agent);
        } else {
        	agent = updateMongoAgent(agent,agentType,mongoServer);
        }
        return agent;
    }
    
    //Make it look nicer TODO: the same for SOLR
    private static AgentImpl updateMongoAgent(AgentImpl agent, AgentType agentType,
			MongoDBServer mongoServer) {
    	if (agent.getBegin()!=null && !StringUtils.equals(agentType.getBegins().get(0), agent.getBegin())){
    		
    		Query<AgentImpl> updateQuery = mongoServer.getDatastore().createQuery(AgentImpl.class).field("about").equal(agent.getAbout());
    		UpdateOperations<AgentImpl> ops = mongoServer.getDatastore().createUpdateOperations(AgentImpl.class).set("begin", agentType.getBegins().get(0));
    		mongoServer.getDatastore().update(updateQuery, ops);
    		
    	}
    	if (agent.getEnd()!=null && !StringUtils.equals(agentType.getEnds().get(0), agent.getEnd())){
    		Query<AgentImpl> updateQuery = mongoServer.getDatastore().createQuery(AgentImpl.class).field("about").equal(agent.getAbout());
    		UpdateOperations<AgentImpl> ops = mongoServer.getDatastore().createUpdateOperations(AgentImpl.class).set("end", agentType.getEnds().get(0));
    		mongoServer.getDatastore().update(updateQuery, ops);
    		
    	}
    	
    	
    	
		return (AgentImpl) mongoServer.searchByAbout(AgentImpl.class,
                agentType.getAbout());
	}

	private static AgentImpl createNewAgent(AgentType agentType){
    	AgentImpl agent = new AgentImpl();

         agent.setAbout(agentType.getAbout());

         if (agentType.getNoteList() != null) {
             List<String> noteList = new ArrayList<String>();
             for (Note note : agentType.getNoteList()) {
                 noteList.add(note.getString());
             }
             agent.setNote(noteList.toArray(new String[noteList.size()]));
         }

         if (agentType.getPrefLabelList() != null) {
             Map<String, String> prefLabelMongo = new HashMap<String, String>();
             for (PrefLabel prefLabelJibx : agentType.getPrefLabelList()) {
                 if (prefLabelJibx.getLang() != null) {
                     prefLabelMongo.put(prefLabelJibx.getLang().getLang(),
                             prefLabelJibx.getString());
                 } else {
                     prefLabelMongo.put("def", prefLabelJibx.getString());
                 }
             }
             agent.setPrefLabel(prefLabelMongo);
         }

         if (agentType.getAltLabelList() != null) {
             Map<String, String> altLabelMongo = new HashMap<String, String>();
             for (AltLabel altLabelJibx : agentType.getAltLabelList()) {
                 if (altLabelJibx.getLang() != null) {
                     altLabelMongo.put(altLabelJibx.getLang().getLang(),
                             altLabelJibx.getString());
                 } else {
                     altLabelMongo.put("def", altLabelJibx.getString());
                 }
             }
             agent.setAltLabel(altLabelMongo);
         }

         if (agentType.getBegins().size() > 0) {
             agent.setBegin(agentType.getBegins().get(0));
         }
         if (agentType.getEnds().size() > 0) {
             agent.setEnd(agentType.getEnds().get(0));
         }
         return agent;
    }
}
