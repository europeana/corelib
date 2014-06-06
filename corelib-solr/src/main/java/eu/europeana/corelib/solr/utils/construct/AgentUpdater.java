package eu.europeana.corelib.solr.utils.construct;

import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;

import eu.europeana.corelib.solr.MongoServer;
import eu.europeana.corelib.solr.entity.AgentImpl;
import eu.europeana.corelib.solr.utils.MongoUtils;
import java.lang.reflect.InvocationTargetException;

public class AgentUpdater implements Updater<AgentImpl> {

	public AgentImpl update(AgentImpl agent, AgentImpl newAgent,
			MongoServer mongoServer) throws NoSuchMethodException, IllegalAccessException,InvocationTargetException{
		Query<AgentImpl> updateQuery = mongoServer.getDatastore()
				.createQuery(AgentImpl.class).field("about")
				.equal(agent.getAbout());
		UpdateOperations<AgentImpl> ops = mongoServer.getDatastore()
				.createUpdateOperations(AgentImpl.class);
		boolean update = false;
		update = MongoUtils.updateMap(agent, newAgent, "begin", ops) || update;
                update = MongoUtils.updateMap(agent,newAgent,"dcDate",ops) ||update;
                update = MongoUtils.updateMap(agent,newAgent,"dcIdentifier",ops)||update;
		update = MongoUtils.updateMap(agent,newAgent,"rdaGr2BiographicalInformation",ops)||update;
                update = MongoUtils.updateMap(agent,newAgent,"rdaGr2DateOfBirth",ops)||update;
                update = MongoUtils.updateMap(agent,newAgent,"rdaGr2DateOfDeath",ops)||update;
                update = MongoUtils.updateMap(agent,newAgent,"rdaGr2BiographicalInformation",ops)||update;
                update = MongoUtils.updateMap(agent,newAgent,"rdaGr2DateOfEstablishment",ops)||update;
                update = MongoUtils.updateMap(agent,newAgent,"rdaGr2DateOfTermination",ops)||update;
                update = MongoUtils.updateMap(agent,newAgent,"rdaGr2Gender",ops)||update;
		update = MongoUtils.updateMap(agent,newAgent,"rdaGr2ProfessionOrOccupation",ops)||update;
                update = MongoUtils.updateMap(agent,newAgent,"rdaGr2BiographicalInformation",ops)||update;
		update = MongoUtils.updateMap(agent,newAgent,"edmHasMet",ops)||update;
                update = MongoUtils.updateMap(agent,newAgent,"edmisRelatedTo",ops)||update;
                update = MongoUtils.updateMap(agent,newAgent,"foafName",ops)||update;
                update = MongoUtils.updateArray(agent,newAgent,"owlSameAs",ops)||update;
                update = MongoUtils.updateMap(agent,newAgent,"end",ops)||update;
                update = MongoUtils.updateMap(agent,newAgent,"note",ops)||update;
                update = MongoUtils.updateMap(agent,newAgent,"altLabel",ops)||update;
                update = MongoUtils.updateMap(agent,newAgent,"prefLabel",ops)||update;
                
//		if (newAgent.getRdaGr2PlaceOfBirth() != null) {
//			if (agent.getRdaGr2PlaceOfBirth() == null
//					|| !MongoUtils.mapEquals(newAgent.getRdaGr2PlaceOfBirth(),
//							agent.getRdaGr2PlaceOfBirth())) {
//
//				ops.set("rdaGr2PlaceOfBirth", newAgent.getRdaGr2PlaceOfBirth());
//				agent.setRdaGr2PlaceOfBirth(newAgent.getRdaGr2PlaceOfBirth());
//				update = true;
//			}
//	} else {
//		if (agent.getRdaGr2PlaceOfBirth() != null) {
//			ops.unset("rdaGr2PlaceOfBirth");
//			agent.setRdaGr2PlaceOfBirth(null);
//			update = true;
//		}
//	}
//
//	if (newAgent.getRdaGr2PlaceOfDeath() != null) {
//			if (agent.getRdaGr2PlaceOfDeath() == null
//					|| !MongoUtils.mapEquals(newAgent.getRdaGr2PlaceOfDeath(),
//							agent.getRdaGr2PlaceOfDeath())) {
//				ops.set("rdaGr2PlaceOfDeath", newAgent.getRdaGr2PlaceOfDeath());
//				agent.setRdaGr2PlaceOfDeath(newAgent.getRdaGr2PlaceOfDeath());
//				update = true;
//			}
//	} else {
//		if (agent.getRdaGr2PlaceOfDeath() != null) {
//			ops.unset("rdaGr2PlaceOfDeath");
//			agent.setRdaGr2PlaceOfDeath(null);
//			update = true;
//		}
//	}
                
		if (update) {
			mongoServer.getDatastore().update(updateQuery, ops);
		}
		return agent;
	}
}
