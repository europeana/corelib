package eu.europeana.corelib.edm.utils.construct;

import eu.europeana.corelib.edm.exceptions.MongoUpdateException;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import eu.europeana.corelib.storage.MongoServer;
import eu.europeana.corelib.edm.utils.MongoUtils;
import eu.europeana.corelib.solr.entity.AgentImpl;

public class AgentUpdater implements Updater<AgentImpl> {

	public AgentImpl update(AgentImpl agent, AgentImpl newAgent,
			MongoServer mongoServer) throws MongoUpdateException {
		Query<AgentImpl> updateQuery = mongoServer.getDatastore()
				.createQuery(AgentImpl.class).field("about")
				.equal(agent.getAbout());
		UpdateOperations<AgentImpl> ops = mongoServer.getDatastore()
				.createUpdateOperations(AgentImpl.class);
		boolean update = false;
		update = MongoUtils.updateMap(agent, newAgent, "begin", ops) || update;
		update = MongoUtils.updateMap(agent, newAgent, "dcDate", ops) || update;
		update = MongoUtils.updateMap(agent, newAgent, "dcIdentifier", ops)
				|| update;
		update = MongoUtils.updateMap(agent, newAgent,
				"rdaGr2BiographicalInformation", ops) || update;
		update = MongoUtils
				.updateMap(agent, newAgent, "rdaGr2DateOfBirth", ops) || update;
		update = MongoUtils
				.updateMap(agent, newAgent, "rdaGr2DateOfDeath", ops) || update;
		update = MongoUtils.updateMap(agent, newAgent, "rdaGr2PlaceOfBirth",
				ops) || update;
		update = MongoUtils.updateMap(agent, newAgent, "rdaGr2PlaceOfDeath",
				ops) || update;
		update = MongoUtils.updateMap(agent, newAgent,
				"rdaGr2BiographicalInformation", ops) || update;
		update = MongoUtils.updateMap(agent, newAgent,
				"rdaGr2DateOfEstablishment", ops) || update;
		update = MongoUtils.updateMap(agent, newAgent,
				"rdaGr2DateOfTermination", ops) || update;
		update = MongoUtils.updateMap(agent, newAgent, "rdaGr2Gender", ops)
				|| update;
		update = MongoUtils.updateMap(agent, newAgent,
				"rdaGr2ProfessionOrOccupation", ops) || update;
		update = MongoUtils.updateMap(agent, newAgent,
				"rdaGr2BiographicalInformation", ops) || update;
		update = MongoUtils.updateMap(agent, newAgent, "edmHasMet", ops)
				|| update;
		update = MongoUtils.updateMap(agent, newAgent, "edmIsRelatedTo", ops)
				|| update;
		update = MongoUtils.updateMap(agent, newAgent, "foafName", ops)
				|| update;
		update = MongoUtils.updateArray(agent, newAgent, "owlSameAs", ops)
				|| update;
		update = MongoUtils.updateMap(agent, newAgent, "end", ops) || update;
		update = MongoUtils.updateMap(agent, newAgent, "note", ops) || update;
		update = MongoUtils.updateMap(agent, newAgent, "altLabel", ops)
				|| update;
		update = MongoUtils.updateMap(agent, newAgent, "prefLabel", ops)
				|| update;
		if (update) {
			mongoServer.getDatastore().update(updateQuery, ops);
		}
		return agent;
	}
}
