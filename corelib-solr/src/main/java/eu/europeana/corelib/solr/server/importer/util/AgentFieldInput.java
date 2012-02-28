package eu.europeana.corelib.solr.server.importer.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.common.SolrInputDocument;

import com.google.code.morphia.mapping.MappingException;

import eu.europeana.corelib.definitions.jibx.AgentType;
import eu.europeana.corelib.definitions.jibx.AltLabel;
import eu.europeana.corelib.definitions.jibx.Note;
import eu.europeana.corelib.definitions.jibx.PrefLabel;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.solr.entity.AgentImpl;
import eu.europeana.corelib.solr.server.MongoDBServer;

//TODO: Normalizer
public class AgentFieldInput {

	public static SolrInputDocument createAgentSolrFields(AgentType agentType,
			SolrInputDocument solrInputDocument) {
		solrInputDocument.addField(EdmLabel.EDM_AGENT.toString(),
				agentType.getAbout());
		if (agentType.getAltLabelList()!=null){
		for (AltLabel altLabel : agentType.getAltLabelList()) {
			try{
			solrInputDocument.addField(EdmLabel.AG_SKOS_ALT_LABEL.toString()
					+ "." + altLabel.getLang().getLang(), altLabel.getString());
			}
			catch (NullPointerException e){
				solrInputDocument.addField(EdmLabel.AG_SKOS_ALT_LABEL.toString(),
						altLabel.getString());
			}
		}
		}
		if(agentType.getPrefLabelList()!=null){
		for (PrefLabel prefLabel : agentType.getPrefLabelList()) {
			try{
			solrInputDocument.addField(EdmLabel.AG_SKOS_PREF_LABEL.toString()
					+ "." + prefLabel.getLang().getLang(),
					prefLabel.getString());
			}
			catch(NullPointerException e){
				solrInputDocument.addField(EdmLabel.AG_SKOS_PREF_LABEL.toString(),
						prefLabel.getString());
			}
		}
		}
		
		if(agentType.getNoteList()!=null){
		for (Note note : agentType.getNoteList()) {
			solrInputDocument.addField(EdmLabel.AG_SKOS_NOTE.toString(),
					note.getString());
		}
		}
		if(agentType.getBegins()!=null){
		for (String begin : agentType.getBegins()) {
			solrInputDocument.addField(EdmLabel.AG_EDM_BEGIN.toString(), begin);
		}
		}
		if(agentType.getEnds()!=null){
		for (String end : agentType.getEnds()) {
			solrInputDocument.addField(EdmLabel.AG_EDM_END.toString(), end);
		}
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
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws MappingException 
	 */
	public static AgentImpl createAgentMongoEntity(AgentType agentType,
			MongoDBServer mongoServer) throws MappingException, InstantiationException, IllegalAccessException {
		AgentImpl agent = new AgentImpl();
		try {
			agent= (AgentImpl) mongoServer.searchByAbout(AgentImpl.class,agentType
					.getAbout());
			agent.getAbout();
		}
		// if it does not exist
		catch (NullPointerException npe) {
			// Agent MongoDB Entity
			agent = new AgentImpl();

			agent.setAbout(agentType.getAbout());
			
			if(agentType.getNoteList()!=null){
			List<String> noteList = new ArrayList<String>();
			for(Note note: agentType.getNoteList()){
				noteList.add(note.getString());
			}
			agent.setNote(noteList.toArray(new String[noteList.size()]));
			}
			
			if(agentType.getPrefLabelList()!=null){
			Map<String, String> prefLabelMongo = new HashMap<String, String>();
			for (PrefLabel prefLabelJibx : agentType.getPrefLabelList()) {
				try{
				prefLabelMongo.put(prefLabelJibx.getLang().getLang(),prefLabelJibx.getString());
				}
				catch(NullPointerException e){
					prefLabelMongo.put("def",prefLabelJibx.getString());
				}
			}
			agent.setPrefLabel(prefLabelMongo);
			}
			
			if(agentType.getAltLabelList()!=null){
			Map<String, String> altLabelMongo = new HashMap<String, String>();
			for (AltLabel altLabelJibx : agentType.getAltLabelList()) {
				try{
				altLabelMongo.put(altLabelJibx.getLang().getLang(),
						altLabelJibx.getString());
				}
				catch(NullPointerException e){
					altLabelMongo.put("def",
							altLabelJibx.getString());
				}
			}
			agent.setAltLabel(altLabelMongo);
			}
			
			if(agentType.getBegins().size()>0){
			agent.setBegin(agentType.getBegins().get(0));
			}
			if(agentType.getEnds().size()>0){
			agent.setEnd(agentType.getEnds().get(0));
			}
			mongoServer.getDatastore().save(agent);
		
		}
		return agent;
	}
	
	
}
