package eu.europeana.corelib.edm.utils.construct;

import eu.europeana.corelib.edm.exceptions.MongoUpdateException;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import eu.europeana.corelib.storage.MongoServer;
import eu.europeana.corelib.edm.utils.MongoUtils;
import eu.europeana.corelib.solr.entity.PlaceImpl;

public class PlaceUpdater implements Updater<PlaceImpl> {

	public PlaceImpl update(PlaceImpl place, PlaceImpl newPlace,
			MongoServer mongoServer) throws MongoUpdateException {
		Query<PlaceImpl> query = mongoServer.getDatastore()
				.createQuery(PlaceImpl.class).field("about")
				.equal(place.getAbout());
		UpdateOperations<PlaceImpl> ops = mongoServer.getDatastore()
				.createUpdateOperations(PlaceImpl.class);
		boolean update = false;
                update = MongoUtils.updateMap(place, newPlace, "note", ops)||update;
		update = MongoUtils.updateMap(place, newPlace, "altLabel", ops)||update;
                update = MongoUtils.updateMap(place, newPlace, "prefLabel", ops)||update;
		update = MongoUtils.updateMap(place, newPlace, "isPartOf", ops)||update;
                update = MongoUtils.updateMap(place, newPlace, "dcTermsHasPart", ops)||update;
                update = MongoUtils.updateArray(place, newPlace, "owlSameAs", ops)||update;
		if (newPlace.getLatitude() != null) {
			
			if (place.getLatitude() == null || newPlace.getLatitude() != place.getLatitude()) {
				ops.set("latitude", newPlace.getLatitude());
				place.setLatitude(newPlace.getLatitude());
				update = true;
			}
		} else {
			if(place.getLatitude()!=null){
				ops.unset("latitude");
				place.setLatitude(newPlace.getLatitude());
				update=true;
			}
		}

		if (newPlace.getLongitude() != null) {
			if (place.getLongitude() == null || newPlace.getLongitude() != place.getLongitude()) {
				ops.set("longitude", newPlace.getLongitude());
				place.setLongitude(newPlace.getLongitude());
				update = true;
			}
		} else {
			if(place.getLongitude()!=null){
				ops.unset("longitude");
				place.setLongitude(null);
				update=true;
			}
		}

		if (newPlace.getAltitude() != null) {
			if (place.getAltitude() == null || newPlace.getAltitude()  != place.getAltitude()) {
				ops.set("altitude", newPlace.getAltitude() );
				place.setAltitude(newPlace.getAltitude());
				update = true;
			}
		} else {
			if (place.getAltitude()!=null){
				ops.unset("altitude");
				place.setAltitude(newPlace.getAltitude());
				update=true;
			}
		}
		if (update) {
			mongoServer.getDatastore().update(query, ops);
		}
		
		return place;
	}
}
