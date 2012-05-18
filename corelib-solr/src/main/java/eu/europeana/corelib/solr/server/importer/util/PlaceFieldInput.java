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

import org.apache.solr.common.SolrInputDocument;

import eu.europeana.corelib.definitions.jibx.AltLabel;
import eu.europeana.corelib.definitions.jibx.IsPartOf;
import eu.europeana.corelib.definitions.jibx.Note;
import eu.europeana.corelib.definitions.jibx.PlaceType;
import eu.europeana.corelib.definitions.jibx.PrefLabel;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.solr.MongoServer;
import eu.europeana.corelib.solr.entity.PlaceImpl;
import eu.europeana.corelib.solr.server.EdmMongoServer;
import eu.europeana.corelib.solr.utils.MongoUtils;


/**
 * Constructor of a Place EDM Entity
 * 
 * @author Yorgos.Mamakis@ kb.nl
 */
public final class PlaceFieldInput {

	private PlaceFieldInput(){
		
	}
	/**
	 * Create a SolrInputDocument with the Place field values filled in
	 * 
	 * @param place
	 *            The JiBX Place Entity
	 * @param solrInputDocument
	 *            The SolrInputDocument to alter
	 * @return The SolrInputDocument with the place fields filled in
	 */
	public static SolrInputDocument createPlaceSolrFields(PlaceType place,
			SolrInputDocument solrInputDocument) {
		solrInputDocument.addField(EdmLabel.EDM_PLACE.toString(),
				place.getAbout());
		if (place.getAltLabelList() != null) {
			for (AltLabel altLabel : place.getAltLabelList()) {
				if (altLabel.getLang() != null) {
					solrInputDocument.addField(
							EdmLabel.PL_SKOS_ALT_LABEL.toString() + "."
									+ altLabel.getLang().getLang(),
							altLabel.getString());
				} else {
					solrInputDocument.addField(
							EdmLabel.PL_SKOS_ALT_LABEL.toString(),
							altLabel.getString());
				}
			}
		}
		if (place.getPrefLabelList() != null) {
			for (PrefLabel prefLabel : place.getPrefLabelList()) {
				if (prefLabel.getLang() != null) {
					solrInputDocument.addField(
							EdmLabel.PL_SKOS_PREF_LABEL.toString() + "."
									+ prefLabel.getLang().getLang(),
							prefLabel.getString());
				} else {
					solrInputDocument.addField(
							EdmLabel.PL_SKOS_PREF_LABEL.toString(),
							prefLabel.getString());
				}
			}
		}
		if (place.getIsPartOfList() != null) {
			for (IsPartOf isPartOf : place.getIsPartOfList()) {
				solrInputDocument.addField(
						EdmLabel.PL_DCTERMS_ISPART_OF.toString(),
						isPartOf.getString());
			}
		}
		if (place.getNoteList() != null) {
			for (Note note : place.getNoteList()) {
				solrInputDocument.addField(EdmLabel.PL_SKOS_NOTE.toString(),
						note.getString());
			}
		}
		if (place.getPosLong() != null && place.getPosLat() != null) {
			solrInputDocument.addField(EdmLabel.PL_POSITION.toString(), place
					.getPosLat().getPosLat()
					+ ","
					+ place.getPosLong().getPosLong());
		}
		return solrInputDocument;
	}

	/**
	 * Create or Update a MongoDB Place Entity
	 * 
	 * @param placeType
	 *            The JiBX Place Entity
	 * @param mongoServer
	 *            The MongoDB Server to save the Entity
	 * @return The MongoDB Place Entity
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static PlaceImpl createPlaceMongoFields(PlaceType placeType,
			MongoServer mongoServer) {

		// If place exists in mongo

		PlaceImpl place = (PlaceImpl) ((EdmMongoServer)mongoServer).searchByAbout(
				PlaceImpl.class, placeType.getAbout());

		// if it does not exist
		if (place == null) {
			place = createNewPlace(placeType);
			mongoServer.getDatastore().save(place);
		} else {
			place = updatePlace(place, placeType, mongoServer);
		}

		return place;
	}

	/**
	 * Update a Mongo Place Entity. In the update process everything is appended
	 * rather than deleted and reconstructed
	 * 
	 * @param place The Mongo Place Entity to update
	 * @param placeType The JiBX Entity from which the Mongo Place Entity will be updated
	 * @param mongoServer The server on which the Place will be updated
	 * @return The updated Mongo Place Entity
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	private static PlaceImpl updatePlace(PlaceImpl place, PlaceType placeType,
			MongoServer mongoServer)  {

		if (place.getNote() != null) {
			List<String> newNoteList = new ArrayList<String>();
			for (Note noteJibx : placeType.getNoteList()) {
				if (MongoUtils.contains(place.getNote(), noteJibx.getString())) {
					newNoteList.add(noteJibx.getString());
				}
			}
			for (String note : place.getNote()) {
				newNoteList.add(note);
			}
			MongoUtils.update(PlaceImpl.class, place.getAbout(), mongoServer,
					"note", newNoteList);

		}

		if (place.getAltLabel() != null) {
			Map<String, String> newAltLabelMap = place.getAltLabel();
			if (placeType.getAltLabelList() != null) {
				for (AltLabel altLabel : placeType.getAltLabelList()) {
					if (altLabel.getLang() != null) {
						if (!MongoUtils.contains(newAltLabelMap, altLabel
								.getLang().getLang(), altLabel.getString())) {
							newAltLabelMap.put(altLabel.getLang().getLang(),
									altLabel.getString());
						}
					} else {
						newAltLabelMap.put("def", altLabel.getString());
					}
				}
			}
			MongoUtils.update(PlaceImpl.class, place.getAbout(), mongoServer,
					"altLabel", newAltLabelMap);

		}

		if (place.getPrefLabel() != null) {
			Map<String, String> newPrefLabelMap = place.getPrefLabel();
			if (placeType.getPrefLabelList() != null) {
				for (PrefLabel prefLabel : placeType.getPrefLabelList()) {
					if (prefLabel.getLang() != null) {
						if (!MongoUtils.contains(newPrefLabelMap, prefLabel
								.getLang().getLang(), prefLabel.getString())) {
							newPrefLabelMap.put(prefLabel.getLang().getLang(),
									prefLabel.getString());
						}
					} else {
						newPrefLabelMap.put("def", prefLabel.getString());
					}
				}
				MongoUtils.update(PlaceImpl.class, place.getAbout(),
						mongoServer, "prefLabel", newPrefLabelMap);
			}
		}

		if (place.getIsPartOf() != null) {
			List<String> isPartOfList = new ArrayList<String>();
			if (placeType.getIsPartOfList() != null) {
				for (IsPartOf isPartOfJibx : placeType.getIsPartOfList()) {
					if (MongoUtils.contains(place.getIsPartOf(),
							isPartOfJibx.getResource())) {
						isPartOfList.add(isPartOfJibx.getResource());
					}
				}
			}
			for (String isPartOf : place.getIsPartOf()) {
				isPartOfList.add(isPartOf);
			}
			MongoUtils.update(PlaceImpl.class, place.getAbout(), mongoServer,
					"isPartOf", isPartOfList);
		}
		if (placeType.getPosLat() != null) {

			MongoUtils.update(PlaceImpl.class, place.getAbout(), mongoServer,
					"latitude", placeType.getPosLat().getPosLat());
		}

		if (placeType.getPosLong() != null) {

			MongoUtils.update(PlaceImpl.class, place.getAbout(), mongoServer,
					"longitude", placeType.getPosLong().getPosLong());
		}
		return (PlaceImpl) ((EdmMongoServer)mongoServer).searchByAbout(PlaceImpl.class,
				placeType.getAbout());
	}

	/**
	 * Create a new Place Mongo Entity from a JiBX palce
	 * 
	 * @param placeType
	 *            The JiBX Place Entity
	 * @return A new Mongo Place Entity
	 */
	private static PlaceImpl createNewPlace(PlaceType placeType) {
		PlaceImpl place = new PlaceImpl();
		place.setAbout(placeType.getAbout());

		if (placeType.getPosLat() != null) {
			place.setLatitude(placeType.getPosLat().getPosLat());
		}

		if (placeType.getPosLong() != null) {
			place.setLongitude(placeType.getPosLong().getPosLong());
		}

		if (placeType.getNoteList() != null) {
			List<String> noteList = new ArrayList<String>();
			for (Note note : placeType.getNoteList()) {
				noteList.add(note.getString());
			}
			place.setNote(noteList.toArray(new String[noteList.size()]));
		}

		if (placeType.getPrefLabelList() != null) {
			Map<String, String> prefLabelMongo = new HashMap<String, String>();
			for (PrefLabel prefLabelJibx : placeType.getPrefLabelList()) {
				if (prefLabelJibx.getLang() != null) {
					prefLabelMongo.put(prefLabelJibx.getLang().getLang(),
							prefLabelJibx.getString());
				} else {
					prefLabelMongo.put("def", prefLabelJibx.getString());
				}
			}
			place.setPrefLabel(prefLabelMongo);
		}

		if (placeType.getAltLabelList() != null) {
			Map<String, String> altLabelMongo = new HashMap<String, String>();
			for (AltLabel altLabelJibx : placeType.getAltLabelList()) {
				if (altLabelJibx.getLang() != null) {
					altLabelMongo.put(altLabelJibx.getLang().getLang(),
							altLabelJibx.getString());
				} else {
					altLabelMongo.put("def", altLabelJibx.getString());
				}
			}
			place.setAltLabel(altLabelMongo);
		}

		if (placeType.getIsPartOfList() != null) {
			List<String> isPartOfList = new ArrayList<String>();
			for (IsPartOf isPartOf : placeType.getIsPartOfList()) {
				isPartOfList.add(isPartOf.getString());
			}
			place.setIsPartOf(isPartOfList.toArray(new String[isPartOfList
					.size()]));
		}
		return place;
	}
}
