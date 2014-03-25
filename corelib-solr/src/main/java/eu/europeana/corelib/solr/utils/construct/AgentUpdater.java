package eu.europeana.corelib.solr.utils.construct;

import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;

import eu.europeana.corelib.solr.MongoServer;
import eu.europeana.corelib.solr.entity.AgentImpl;
import eu.europeana.corelib.solr.utils.MongoUtils;

public class AgentUpdater implements Updater<AgentImpl> {

	public AgentImpl update(AgentImpl agent, AgentImpl newAgent,
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
				agent.setBegin(newAgent.getBegin());
				update = true;
			}
		} else {
			if (agent.getBegin() != null) {
				ops.unset("begin");
				agent.setBegin(null);
				update = true;
			}
		}

		if (newAgent.getDcDate() != null) {
			if (agent.getDcDate() == null
					|| !MongoUtils.mapEquals(newAgent.getDcDate(),
							agent.getDcDate())) {
				ops.set("dcDate", newAgent.getDcDate());
				agent.setDcDate(newAgent.getDcDate());
				update = true;
			}
		} else {
			if (agent.getDcDate() != null) {
				ops.unset("dcDate");
				agent.setDcDate(null);
				update = true;
			}
		}

		if (newAgent.getDcIdentifier() != null) {
			if (agent.getDcIdentifier() == null
					|| !MongoUtils.mapEquals(newAgent.getDcIdentifier(),
							agent.getDcIdentifier())) {
				ops.set("dcIdentifier", newAgent.getDcIdentifier());
				agent.setDcIdentifier(newAgent.getDcIdentifier());
				update = update | true;
			}
		} else {
			if (agent.getDcIdentifier() != null) {
				ops.unset("dcIdentifier");
				agent.setDcIdentifier(null);
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
				agent.setRdaGr2BiographicalInformation(newAgent.getRdaGr2BiographicalInformation());
				update = true;
			}
		} else {
			if (agent.getRdaGr2BiographicalInformation() != null) {
				ops.unset("rdaGr2BiographicalInformation");
				agent.setRdaGr2BiographicalInformation(null);
				update = true;
			}
		}

		if (newAgent.getRdaGr2DateOfBirth() != null) {
				if (agent.getRdaGr2DateOfBirth() == null
						|| !MongoUtils.mapEquals(newAgent.getRdaGr2DateOfBirth(),
								agent.getRdaGr2DateOfBirth())) {

					ops.set("rdaGr2DateOfBirth", newAgent.getRdaGr2DateOfBirth());
					agent.setRdaGr2DateOfBirth(newAgent.getRdaGr2DateOfBirth());
					update = true;
				}
		} else {
			if (agent.getRdaGr2DateOfBirth() != null) {
				ops.unset("rdaGr2DateOfBirth");
				agent.setRdaGr2DateOfBirth(null);
				update = true;
			}
		}

		if (newAgent.getRdaGr2DateOfDeath() != null) {
				if (agent.getRdaGr2DateOfDeath() == null
						|| !MongoUtils.mapEquals(newAgent.getRdaGr2DateOfDeath(),
								agent.getRdaGr2DateOfDeath())) {
					ops.set("rdaGr2DateOfDeath", newAgent.getRdaGr2DateOfDeath());
					agent.setRdaGr2DateOfDeath(newAgent.getRdaGr2DateOfDeath());
					update = true;
				}
		} else {
			if (agent.getRdaGr2DateOfDeath() != null) {
				ops.unset("rdaGr2DateOfDeath");
				agent.setRdaGr2DateOfDeath(null);
				update = true;
			}
		}

		if (newAgent.getRdaGr2DateOfEstablishment() != null) {
				if (agent.getRdaGr2DateOfEstablishment() == null
						|| !MongoUtils.mapEquals(newAgent.getRdaGr2DateOfEstablishment(),
								agent.getRdaGr2DateOfEstablishment())) {
					ops.set("rdaGr2DateOfEstablishment", newAgent.getRdaGr2DateOfEstablishment());
					agent.setRdaGr2DateOfEstablishment(newAgent.getRdaGr2DateOfEstablishment());
					update = true;
				}
		} else {
			if (agent.getRdaGr2DateOfEstablishment() != null) {
				ops.unset("rdaGr2DateOfEstablishment");
				agent.setRdaGr2DateOfEstablishment(null);
				update = true;
			}
		}

		if (newAgent.getRdaGr2DateOfTermination() != null) {
				if (agent.getRdaGr2DateOfTermination() == null
						|| !MongoUtils.mapEquals(newAgent.getRdaGr2DateOfTermination(),
								agent.getRdaGr2DateOfTermination())) {
					ops.set("rdaGr2DateOfTermination", newAgent.getRdaGr2DateOfTermination());
					agent.setRdaGr2DateOfTermination(newAgent.getRdaGr2DateOfTermination());
					update = update | true;
				}
		} else {
			if (agent.getRdaGr2DateOfTermination() != null) {
				ops.unset("rdaGr2DateOfTermination");
				agent.setRdaGr2DateOfTermination(null);
				update = true;
			}
		}

		if (newAgent.getRdaGr2Gender() != null) {
				if (agent.getRdaGr2Gender() == null
						|| !MongoUtils.mapEquals(newAgent.getRdaGr2Gender(),
								agent.getRdaGr2Gender())) {
					ops.set("rdaGr2Gender", newAgent.getRdaGr2Gender());
					agent.setRdaGr2Gender(newAgent.getRdaGr2Gender());
					update = true;
			}
		} else {
			if (agent.getRdaGr2Gender() != null) {
				ops.unset("rdaGr2Gender");
				agent.setRdaGr2Gender(null);
				update = true;
			}
		}

		if (newAgent.getRdaGr2ProfessionOrOccupation()!= null) {
				if (agent.getRdaGr2ProfessionOrOccupation() == null
						|| !MongoUtils.mapEquals(newAgent.getRdaGr2ProfessionOrOccupation(),
								agent.getRdaGr2ProfessionOrOccupation())) {
					ops.set("rdaGr2ProfessionOrOccupation",
							newAgent.getRdaGr2ProfessionOrOccupation());
					agent.setRdaGr2ProfessionOrOccupation(newAgent.getRdaGr2ProfessionOrOccupation());
					update = true;
				}
		} else {
			if (agent.getRdaGr2ProfessionOrOccupation() != null) {
				ops.unset("rdaGr2ProfessionOrOccupation");
				agent.setRdaGr2ProfessionOrOccupation(null);
				update = true;
			}
		}

		if (newAgent.getEdmHasMet() != null) {
				if (agent.getEdmHasMet() == null
						|| !MongoUtils.mapEquals(newAgent.getEdmHasMet(), agent.getEdmHasMet())) {
					ops.set("edmHasMet", newAgent.getEdmHasMet());
					agent.setEdmHasMet(newAgent.getEdmHasMet());
					update = true;
				}
		} else {
			if (agent.getEdmHasMet() != null) {
				ops.unset("edmHasMet");
				agent.setEdmHasMet(null);
				update = true;
			}
		}

		if (newAgent.getEdmIsRelatedTo()!= null) {
				if (agent.getEdmIsRelatedTo() == null
						|| !MongoUtils.mapEquals(newAgent.getEdmIsRelatedTo(),
								agent.getEdmIsRelatedTo())) {
					ops.set("edmIsRelatedTo", newAgent.getEdmIsRelatedTo());
					agent.setEdmIsRelatedTo(newAgent.getEdmIsRelatedTo());
					update = true;
			}
		} else {
			if (agent.getEdmIsRelatedTo() != null) {
				ops.unset("edmIsRelatedTo");
				agent.setEdmIsRelatedTo(null);
				update = true;
			}
		}

		if (newAgent.getFoafName() != null) {
				if (agent.getFoafName() == null
						|| !MongoUtils.mapEquals(newAgent.getFoafName(), agent.getFoafName())) {
					ops.set("foafName", newAgent.getFoafName());
					agent.setFoafName(newAgent.getFoafName());
					update = true;
				}

		} else {
			if (agent.getFoafName() != null) {
				ops.unset("foafName");
				agent.setFoafName(null);
				update = true;
			}
		}

		if (newAgent.getOwlSameAs()!= null) {
			
				if (agent.getOwlSameAs() == null
						|| !MongoUtils
								.arrayEquals(newAgent.getOwlSameAs(), agent.getOwlSameAs())) {
					ops.set("owlSameAs", newAgent.getOwlSameAs());
					agent.setOwlSameAs(newAgent.getOwlSameAs());
					update = true;
				}
		} else {
			if (agent.getOwlSameAs() != null) {
				ops.unset("owlSameAs");
				agent.setOwlSameAs(null);
				update = true;
			}
		}

		if (newAgent.getEnd() != null) {
				if (agent.getEnd() == null
						|| !MongoUtils.mapEquals(newAgent.getEnd(), agent.getEnd())) {
					ops.set("end", newAgent.getEnd());
					agent.setEnd(newAgent.getEnd());
					update = true;
				}
		} else {
			if (agent.getEnd() != null) {
				ops.unset("end");
				agent.setEnd(null);
				update = true;
			}
		}

		if (newAgent.getNote() != null) {
				if (agent.getNote() == null
						|| !MongoUtils.mapEquals(newAgent.getNote(), agent.getNote())) {
					ops.set("note", newAgent.getNote());
					agent.setNote(newAgent.getNote());
					update = true;
			}
		} else {
			if (agent.getNote() != null) {
				ops.unset("note");
				agent.setNote(null);
				update = true;
			}
		}

		if (newAgent.getAltLabel() != null) {
				if (agent.getAltLabel() == null
						|| !MongoUtils.mapEquals(newAgent.getAltLabel(), agent.getAltLabel())) {
					ops.set("altLabel", newAgent.getAltLabel());
					agent.setAltLabel(newAgent.getAltLabel());
					update = true;
				}
		} else {
			if (agent.getAltLabel() != null) {
				ops.unset("altLabel");
				agent.setAltLabel(null);
				update = true;
			}
		}

		if (newAgent.getPrefLabel() != null) {
				if (agent.getPrefLabel() == null
						|| !MongoUtils.mapEquals(newAgent.getPrefLabel(),
								agent.getPrefLabel())) {
					ops.set("prefLabel", newAgent.getPrefLabel());
					agent.setPrefLabel(newAgent.getPrefLabel());
					update = true;
				}
		} else {
			if (agent.getPrefLabel() != null) {
				ops.unset("prefLabel");
				agent.setPrefLabel(null);
				update = true;
			}
		}
		if (update) {
			mongoServer.getDatastore().update(updateQuery, ops);
		}
		return agent;
	}
}
