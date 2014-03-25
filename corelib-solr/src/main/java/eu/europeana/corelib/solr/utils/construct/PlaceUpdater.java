package eu.europeana.corelib.solr.utils.construct;

import java.util.ArrayList;
import java.util.List;

import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;

import eu.europeana.corelib.solr.MongoServer;
import eu.europeana.corelib.solr.entity.PlaceImpl;
import eu.europeana.corelib.solr.utils.MongoUtils;
import eu.europeana.corelib.utils.StringArrayUtils;

public class PlaceUpdater implements Updater<PlaceImpl> {

	public PlaceImpl update(PlaceImpl place, PlaceImpl newPlace,
			MongoServer mongoServer) {
		Query<PlaceImpl> query = mongoServer.getDatastore()
				.createQuery(PlaceImpl.class).field("about")
				.equal(place.getAbout());
		UpdateOperations<PlaceImpl> ops = mongoServer.getDatastore()
				.createUpdateOperations(PlaceImpl.class);
		boolean update = false;
		if (newPlace.getNote() != null) {
				if (place.getNote() == null
						|| !MongoUtils.mapEquals(newPlace.getNote(), place.getNote())) {
					ops.set("note", newPlace.getNote());
					place.setNote(newPlace.getNote());
					update = true;
				}
		} else {
			if (place.getNote() != null) {
				ops.unset("note");
				place.setNote(null);
				update = true;
			}
		}

		if (newPlace.getAltLabel() != null) {
				if (place.getAltLabel() == null
						|| !MongoUtils.mapEquals(newPlace.getAltLabel(), place.getAltLabel())) {
					ops.set("altLabel", newPlace.getAltLabel());
					place.setAltLabel(newPlace.getAltLabel());
					update = true;
				}

		} else {
			if (place.getAltLabel() != null) {
				ops.unset("altLabel");
				place.setAltLabel(null);
				update = true;
			}
		}

		if (newPlace.getPrefLabel() != null) {
				if (place.getPrefLabel() == null
						|| !MongoUtils.mapEquals(newPlace.getPrefLabel(),
								place.getPrefLabel())) {
					ops.set("prefLabel", newPlace.getPrefLabel());
					place.setPrefLabel(newPlace.getPrefLabel());
					update = true;
			}

		} else {
			if (place.getPrefLabel() != null) {
				ops.unset("prefLabel");
				place.setPrefLabel(null);
				update = true;
			}
		}

		if (newPlace.getIsPartOf() != null) {
				if (place.getIsPartOf() == null
						|| !MongoUtils.mapEquals(newPlace.getIsPartOf(), place.getIsPartOf())) {
					ops.set("isPartOf", newPlace.getIsPartOf());
					place.setIsPartOf(newPlace.getIsPartOf());
					update = true;
				}
		} else {
			if (place.getIsPartOf() != null) {
				ops.unset("isPartOf");
				place.setIsPartOf(null);
				update = true;
			}
		}

		if (newPlace.getDcTermsHasPart() != null) {
				if (place.getDcTermsHasPart() == null
						|| !MongoUtils.mapEquals(newPlace.getDcTermsHasPart() ,
								place.getDcTermsHasPart())) {
					ops.set("dcTermsHasPart", newPlace.getDcTermsHasPart());
					place.setDcTermsHasPart(newPlace.getDcTermsHasPart());
					update = true;
				}
		} else {
			if(place.getDcTermsHasPart()!=null){
				ops.unset("dcTermsHasPart");
				place.setDcTermsHasPart(null);
				update =true;
			}
		}
		if (newPlace.getOwlSameAs() != null) {
			List<String> owlSameAs = new ArrayList<String>();

				for (String sameAsJibx : newPlace.getOwlSameAs()) {
					if (!MongoUtils.contains(place.getOwlSameAs(),
							sameAsJibx)) {
						owlSameAs.add(sameAsJibx);
					}
				}

			if (place.getOwlSameAs() != null) {
				for (String owlSameAsItem : place.getOwlSameAs()) {
					owlSameAs.add(owlSameAsItem);
				}
			ops.set("owlSameAs", StringArrayUtils.toArray(owlSameAs));
			place.setOwlSameAs(newPlace.getOwlSameAs());
			update = true;
			}
		} else {
			if(place.getOwlSameAs()!=null){
				ops.unset("owlSameAs");
				place.setOwlSameAs(null);
				update = true;
			}
		}

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
