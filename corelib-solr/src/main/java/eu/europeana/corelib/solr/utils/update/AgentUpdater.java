package eu.europeana.corelib.solr.utils.update;

import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;

import eu.europeana.corelib.solr.MongoServer;
import eu.europeana.corelib.solr.entity.AgentImpl;
import eu.europeana.corelib.solr.utils.MongoUtils;

public class AgentUpdater implements Updater<AgentImpl> {

	public void update(AgentImpl agent, AgentImpl newAgent,
			MongoServer mongoServer) {
		Query<AgentImpl> updateQuery = mongoServer.getDatastore()
				.createQuery(AgentImpl.class).field("about")
				.equal(agent.getAbout());
		UpdateOperations<AgentImpl> ops = mongoServer.getDatastore()
				.createUpdateOperations(AgentImpl.class);
		boolean update = false;
		if (newAgent.getBegin() != null) {
			if (agent.getBegin() == null
					|| !MongoUtils.mapEquals(newAgent.getBegin(),
							agent.getBegin())) {
				ops.set("begin", newAgent.getBegin());
				update = true;
			}
		} else {
			if (agent.getBegin() != null) {
				ops.unset("begin");
				update = true;
			}
		}

		if (newAgent.getDcDate() != null) {
			if (agent.getDcDate() == null
					|| !MongoUtils.mapEquals(newAgent.getDcDate(),
							agent.getDcDate())) {
				ops.set("dcDate", newAgent.getDcDate());
				update = true;
			}
		} else {
			if (agent.getDcDate() != null) {
				ops.unset("dcDate");
				update = true;
			}
		}

		if (newAgent.getDcIdentifier() != null) {
			if (agent.getDcIdentifier() == null
					|| !MongoUtils.mapEquals(newAgent.getDcIdentifier(),
							agent.getDcIdentifier())) {
				ops.set("dcIdentifier", newAgent.getDcIdentifier());
				update = update | true;
			}
		} else {
			if (agent.getDcIdentifier() != null) {
				ops.unset("dcIdentifier");
				update = true;
			}
		}

		if (newAgent.getRdaGr2BiographicalInformation() != null) {
			if (agent.getRdaGr2BiographicalInformation() == null
					|| !MongoUtils.mapEquals(
							newAgent.getRdaGr2BiographicalInformation(),
							agent.getRdaGr2BiographicalInformation())) {
				ops.set("rdaGr2BiographicalInformation",
						newAgent.getRdaGr2BiographicalInformation());
				update = true;
			}
		} else {
			if (agent.getRdaGr2BiographicalInformation() != null) {
				ops.unset("rdaGr2BiographicalInformation");
				update = true;
			}
		}

		if (newAgent.getRdaGr2DateOfBirth() != null) {
				if (agent.getRdaGr2DateOfBirth() == null
						|| !MongoUtils.mapEquals(newAgent.getRdaGr2DateOfBirth(),
								agent.getRdaGr2DateOfBirth())) {

					ops.set("rdaGr2DateOfBirth", newAgent.getRdaGr2DateOfBirth());
					update = true;
				}
		} else {
			if (agent.getRdaGr2DateOfBirth() != null) {
				ops.unset("rdaGr2DateOfBirth");
				update = true;
			}
		}

		if (newAgent.getRdaGr2DateOfDeath() != null) {
				if (agent.getRdaGr2DateOfDeath() == null
						|| !MongoUtils.mapEquals(newAgent.getRdaGr2DateOfDeath(),
								agent.getRdaGr2DateOfDeath())) {
					ops.set("rdaGr2DateOfDeath", newAgent.getRdaGr2DateOfDeath());
					update = true;
				}
		} else {
			if (agent.getRdaGr2DateOfDeath() != null) {
				ops.unset("rdaGr2DateOfDeath");
				update = true;
			}
		}

		if (newAgent.getRdaGr2DateOfEstablishment() != null) {
				if (agent.getRdaGr2DateOfEstablishment() == null
						|| !MongoUtils.mapEquals(newAgent.getRdaGr2DateOfEstablishment(),
								agent.getRdaGr2DateOfEstablishment())) {
					ops.set("rdaGr2DateOfEstablishment", newAgent.getRdaGr2DateOfEstablishment());
					update = true;
				}
		} else {
			if (agent.getRdaGr2DateOfEstablishment() != null) {
				ops.unset("rdaGr2DateOfEstablishment");
				update = true;
			}
		}

		if (newAgent.getRdaGr2DateOfTermination() != null) {
				if (agent.getRdaGr2DateOfTermination() == null
						|| !MongoUtils.mapEquals(newAgent.getRdaGr2DateOfTermination(),
								agent.getRdaGr2DateOfTermination())) {
					ops.set("rdaGr2DateOfTermination", newAgent.getRdaGr2DateOfTermination());
					update = update | true;
				}
		} else {
			if (agent.getRdaGr2DateOfTermination() != null) {
				ops.unset("rdaGr2DateOfTermination");
				update = true;
			}
		}

		if (newAgent.getRdaGr2Gender() != null) {
				if (agent.getRdaGr2Gender() == null
						|| !MongoUtils.mapEquals(newAgent.getRdaGr2Gender(),
								agent.getRdaGr2Gender())) {
					ops.set("rdaGr2Gender", newAgent.getRdaGr2Gender());
					update = true;
			}
		} else {
			if (agent.getRdaGr2Gender() != null) {
				ops.unset("rdaGr2Gender");
				update = true;
			}
		}

		if (newAgent.getRdaGr2ProfessionOrOccupation()!= null) {
				if (agent.getRdaGr2ProfessionOrOccupation() == null
						|| !MongoUtils.mapEquals(newAgent.getRdaGr2ProfessionOrOccupation(),
								agent.getRdaGr2ProfessionOrOccupation())) {
					ops.set("rdaGr2ProfessionOrOccupation",
							newAgent.getRdaGr2ProfessionOrOccupation());
					update = true;
				}
		} else {
			if (agent.getRdaGr2ProfessionOrOccupation() != null) {
				ops.unset("rdaGr2ProfessionOrOccupation");
				update = true;
			}
		}

		if (newAgent.getEdmHasMet() != null) {
				if (agent.getEdmHasMet() == null
						|| !MongoUtils.mapEquals(newAgent.getEdmHasMet(), agent.getEdmHasMet())) {
					ops.set("edmHasMet", newAgent.getEdmHasMet());
					update = true;
				}
		} else {
			if (agent.getEdmHasMet() != null) {
				ops.unset("edmHasMet");
				update = true;
			}
		}

		if (newAgent.getEdmIsRelatedTo()!= null) {
				if (agent.getEdmIsRelatedTo() == null
						|| !MongoUtils.mapEquals(newAgent.getEdmIsRelatedTo(),
								agent.getEdmIsRelatedTo())) {
					ops.set("edmIsRelatedTo", newAgent.getEdmIsRelatedTo());
					update = true;
			}
		} else {
			if (agent.getEdmIsRelatedTo() != null) {
				ops.unset("edmIsRelatedTo");
				update = true;
			}
		}

		if (newAgent.getFoafName() != null) {
				if (agent.getFoafName() == null
						|| !MongoUtils.mapEquals(newAgent.getFoafName(), agent.getFoafName())) {
					ops.set("foafName", newAgent.getFoafName());
					update = true;
				}

		} else {
			if (agent.getFoafName() != null) {
				ops.unset("foafName");
				update = true;
			}
		}

		if (newAgent.getOwlSameAs()!= null) {
			
				if (agent.getOwlSameAs() == null
						|| !MongoUtils
								.arrayEquals(newAgent.getOwlSameAs(), agent.getOwlSameAs())) {
					ops.set("owlSameAs", newAgent.getOwlSameAs());
					update = true;
				}
		} else {
			if (agent.getOwlSameAs() != null) {
				ops.unset("owlSameAs");
				update = true;
			}
		}

		if (newAgent.getEnd() != null) {
				if (agent.getEnd() == null
						|| !MongoUtils.mapEquals(newAgent.getEnd(), agent.getEnd())) {
					ops.set("end", newAgent.getEnd());
					update = true;
				}
		} else {
			if (agent.getEnd() != null) {
				ops.unset("end");
				update = true;
			}
		}

		if (newAgent.getNote() != null) {
				if (agent.getNote() == null
						|| !MongoUtils.mapEquals(newAgent.getNote(), agent.getNote())) {
					ops.set("note", newAgent.getNote());
					update = true;
			}
		} else {
			if (agent.getNote() != null) {
				ops.unset("note");
				update = true;
			}
		}

		if (newAgent.getAltLabel() != null) {
				if (agent.getAltLabel() == null
						|| !MongoUtils.mapEquals(newAgent.getAltLabel(), agent.getAltLabel())) {
					ops.set("altLabel", newAgent.getAltLabel());
					update = true;
				}
		} else {
			if (agent.getAltLabel() != null) {
				ops.unset("altLabel");
				update = true;
			}
		}

		if (newAgent.getPrefLabel() != null) {
				if (agent.getPrefLabel() == null
						|| !MongoUtils.mapEquals(newAgent.getPrefLabel(),
								agent.getPrefLabel())) {
					ops.set("prefLabel", newAgent.getPrefLabel());
					update = true;
				}
		} else {
			if (agent.getPrefLabel() != null) {
				ops.unset("predfLabel");
				update = true;
			}
		}
		if (update) {
			mongoServer.getDatastore().update(updateQuery, ops);
		}
	}
}
