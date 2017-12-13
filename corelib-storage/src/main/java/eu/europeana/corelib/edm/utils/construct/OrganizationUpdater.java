package eu.europeana.corelib.edm.utils.construct;

import eu.europeana.corelib.edm.exceptions.MongoUpdateException;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import eu.europeana.corelib.storage.MongoServer;
import eu.europeana.corelib.definitions.edm.entity.Organization;
import eu.europeana.corelib.edm.utils.MongoUtils;
import eu.europeana.corelib.solr.entity.OrganizationImpl;
//TODO: NOT TO BE USED
public class OrganizationUpdater implements Updater<Organization> {
	public Organization update(Organization mongoEntity, Organization newEntity,
			MongoServer mongoServer) throws MongoUpdateException {
		Query<OrganizationImpl> updateQuery = mongoServer.getDatastore()
				.createQuery(OrganizationImpl.class).field("about")
				.equal(mongoEntity.getAbout());
		UpdateOperations<OrganizationImpl> ops = mongoServer.getDatastore()
				.createUpdateOperations(OrganizationImpl.class);
		boolean update = false;
		
		update = MongoUtils.updateMap(mongoEntity, newEntity, "begin", ops) || update;
		update = MongoUtils.updateMap(mongoEntity, newEntity, "dcDate", ops) || update;
		update = MongoUtils.updateMap(mongoEntity, newEntity, "dcIdentifier", ops)
				|| update;
		update = MongoUtils.updateMap(mongoEntity, newEntity,
				"rdaGr2BiographicalInformation", ops) || update;
		update = MongoUtils
				.updateMap(mongoEntity, newEntity, "rdaGr2DateOfBirth", ops) || update;
		update = MongoUtils
				.updateMap(mongoEntity, newEntity, "rdaGr2DateOfDeath", ops) || update;
		update = MongoUtils.updateMap(mongoEntity, newEntity, "rdaGr2PlaceOfBirth",
				ops) || update;
		update = MongoUtils.updateMap(mongoEntity, newEntity, "rdaGr2PlaceOfDeath",
				ops) || update;
		update = MongoUtils.updateMap(mongoEntity, newEntity,
				"rdaGr2BiographicalInformation", ops) || update;
		update = MongoUtils.updateMap(mongoEntity, newEntity,
				"rdaGr2DateOfEstablishment", ops) || update;
		update = MongoUtils.updateMap(mongoEntity, newEntity,
				"rdaGr2DateOfTermination", ops) || update;
		update = MongoUtils.updateMap(mongoEntity, newEntity, "rdaGr2Gender", ops)
				|| update;
		update = MongoUtils.updateMap(mongoEntity, newEntity,
				"rdaGr2ProfessionOrOccupation", ops) || update;
		update = MongoUtils.updateMap(mongoEntity, newEntity,
				"rdaGr2BiographicalInformation", ops) || update;
		update = MongoUtils.updateMap(mongoEntity, newEntity, "edmHasMet", ops)
				|| update;
		update = MongoUtils.updateMap(mongoEntity, newEntity, "edmIsRelatedTo", ops)
				|| update;
		update = MongoUtils.updateMap(mongoEntity, newEntity, "foafName", ops)
				|| update;
		update = MongoUtils.updateArray(mongoEntity, newEntity, "owlSameAs", ops)
				|| update;
		update = MongoUtils.updateMap(mongoEntity, newEntity, "end", ops) || update;
		update = MongoUtils.updateMap(mongoEntity, newEntity, "note", ops) || update;
		update = MongoUtils.updateMap(mongoEntity, newEntity, "altLabel", ops)
				|| update;
		update = MongoUtils.updateMap(mongoEntity, newEntity, "prefLabel", ops)
				|| update;
		update = MongoUtils.updateMapRef(mongoEntity, newEntity, "edmOrganizationScope", ops) || update;
		update = MongoUtils.updateMapRef(mongoEntity, newEntity, "edmOrganizationDomain", ops) || update;
		update = MongoUtils.updateMapRef(mongoEntity, newEntity, "edmOrganizationSector", ops) || update;
		update = MongoUtils.updateMapRef(mongoEntity, newEntity, "edmGeographicLevel", ops) || update;
		update = MongoUtils.updateMap(mongoEntity, newEntity, "edmAcronym", ops) || update;
		update = MongoUtils.updateString(mongoEntity, newEntity, "edmCountry", ops) || update;
		update = MongoUtils.updateString(mongoEntity, newEntity, "foafHomepage", ops) || update;
		update = MongoUtils.updateMap(mongoEntity, newEntity, "edmEuropeanaRole", ops) || update;
		if (update) {
			mongoServer.getDatastore().update(updateQuery, ops);
		}
		return mongoEntity;
	}
}
