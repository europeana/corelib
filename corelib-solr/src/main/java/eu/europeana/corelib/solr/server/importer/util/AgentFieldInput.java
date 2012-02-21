package eu.europeana.corelib.solr.server.importer.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.common.SolrInputDocument;

import com.google.code.morphia.converters.DateConverter;

import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.definitions.solr.entity.Agent;
import eu.europeana.corelib.solr.entity.AgentImpl;
import eu.europeana.corelib.solr.server.MongoDBServer;
import eu.europeana.corelib.definitions.jibx.AgentType;
import eu.europeana.corelib.definitions.jibx.AltLabel;
import eu.europeana.corelib.definitions.jibx.Note;
import eu.europeana.corelib.definitions.jibx.PrefLabel;

//TODO: Normalizer
public class AgentFieldInput {

	public static SolrInputDocument createAgentSolrFields(AgentType agentType,
			SolrInputDocument solrInputDocument) {
		solrInputDocument.addField(EdmLabel.EDM_AGENT.toString(),
				agentType.getAbout());
		for (AltLabel altLabel : agentType.getAltLabelList()) {
			solrInputDocument.addField(EdmLabel.AG_SKOS_ALT_LABEL.toString()
					+ "." + altLabel.getLang().getLang(), altLabel.getString());
		}
		for (PrefLabel prefLabel : agentType.getPrefLabelList()) {
			solrInputDocument.addField(EdmLabel.AG_SKOS_PREF_LABEL.toString()
					+ "." + prefLabel.getLang().getLang(),
					prefLabel.getString());
		}
		for (Note note : agentType.getNoteList()) {
			solrInputDocument.addField(EdmLabel.AG_SKOS_NOTE.toString(),
					note.getString());
		}
		for (String begin : agentType.getBegins()) {
			solrInputDocument.addField(EdmLabel.AG_EDM_BEGIN.toString(), begin);
		}
		for (String end : agentType.getEnds()) {
			solrInputDocument.addField(EdmLabel.AG_EDM_END.toString(), end);
		}
		return solrInputDocument;
	}

	/**
	 * Create a Mongo Entity of type Agent from the JiBX AgentType object
	 * 
	 * Mapping from the JibXBinding Fields to the MongoDB Entity Fields 
	 * The fields mapped are the 
	 * rdf:about (String -> String) 
	 * skos:note(List<Note> -> String[]) 
	 * skos:prefLabel(List<PrefLabel> -> HashMap<String,String> (lang,description)) 
	 * skos:altLabel(List<AltLabel> -> HashMap<String,String> (lang,description))
	 * edm:begin (String -> Date)
	 * edm:end (String -> Date)
	 * 
	 * @param agentType
	 *            - JiBX representation of an Agent EDM entity
	 * @param mongoServer
	 *            - The mongoServer to save the entity
	 * @return A list with the agent created (EDM allows more than one Agent entities per ProvidedCHO)
	 */
	public static List<Agent> createAgentMongoEntity(AgentType agentType,
			MongoDBServer mongoServer) {
		List<Agent> agentList = new ArrayList<Agent>();
		// if the Agent already exists
		try {
			agentList.add((AgentImpl) mongoServer.searchByAbout(agentType
					.getAbout()));
		}
		// if it does not exist
		catch (NullPointerException npe) {
			// Agent MongoDB Entity
			Agent agent = new AgentImpl();

			agent.setAbout(agentType.getAbout());
			
			List<String> noteList = new ArrayList<String>();
			for(Note note: agentType.getNoteList()){
				noteList.add(note.getString());
			}
			agent.setNote(noteList.toArray(new String[noteList.size()]));

			Map<String, String> prefLabelMongo = new HashMap<String, String>();
			for (PrefLabel prefLabelJibx : agentType.getPrefLabelList()) {
				prefLabelMongo.put(prefLabelJibx.getLang().getLang(),prefLabelJibx.getString());
			}
			agent.setPrefLabel(prefLabelMongo);

			Map<String, String> altLabelMongo = new HashMap<String, String>();
			for (AltLabel altLabelJibx : agentType.getAltLabelList()) {
				altLabelMongo.put(altLabelJibx.getLang().getLang(),
						altLabelJibx.getString());
			}
			agent.setAltLabel(altLabelMongo);

			DateConverter dc = new DateConverter();

			agent.setBegin((Date) dc.encode(agentType.getBegins().get(0)));

			agent.setEnd((Date) dc.encode(agentType.getEnds().get(0)));

			mongoServer.getDatastore().save(agent);
			agentList.add(agent);
		}
		return agentList;
	}

}
