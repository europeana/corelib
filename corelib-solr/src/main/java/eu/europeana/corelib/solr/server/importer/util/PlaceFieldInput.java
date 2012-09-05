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
import java.util.Map.Entry;

import org.apache.solr.common.SolrInputDocument;

import eu.europeana.corelib.definitions.jibx.AltLabel;
import eu.europeana.corelib.definitions.jibx.HasPart;
import eu.europeana.corelib.definitions.jibx.IsPartOf;
import eu.europeana.corelib.definitions.jibx.Note;
import eu.europeana.corelib.definitions.jibx.PlaceType;
import eu.europeana.corelib.definitions.jibx.PrefLabel;
import eu.europeana.corelib.definitions.jibx.SameAs;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.solr.MongoServer;
import eu.europeana.corelib.solr.entity.PlaceImpl;
import eu.europeana.corelib.solr.server.EdmMongoServer;
import eu.europeana.corelib.solr.utils.MongoUtils;
import eu.europeana.corelib.solr.utils.SolrUtils;
import eu.europeana.corelib.utils.StringArrayUtils;

/**
 * Constructor of a Place EDM Entity
 * 
 * @author Yorgos.Mamakis@ kb.nl
 */
public final class PlaceFieldInput {

	private PlaceFieldInput() {

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
				solrInputDocument = SolrUtils
						.addFieldFromLiteral(solrInputDocument, altLabel,
								EdmLabel.PL_SKOS_ALT_LABEL);
			}
		}
		if (place.getPrefLabelList() != null) {
			for (PrefLabel prefLabel : place.getPrefLabelList()) {
				solrInputDocument = SolrUtils
						.addFieldFromLiteral(solrInputDocument, prefLabel,
								EdmLabel.PL_SKOS_PREF_LABEL);
			}
		}
		if (place.getIsPartOfList() != null) {
			for (IsPartOf isPartOf : place.getIsPartOfList()) {
				solrInputDocument = SolrUtils
						.addFieldFromResourceOrLiteral(solrInputDocument, isPartOf,
								EdmLabel.PL_DCTERMS_ISPART_OF);
			}
		}
		if (place.getNoteList() != null) {
			for (Note note : place.getNoteList()) {
				solrInputDocument = SolrUtils
						.addFieldFromLiteral(solrInputDocument, note,
								EdmLabel.PL_SKOS_NOTE);
			}
		}
		if (place.getLong() != null && place.getLat() != null) {
			solrInputDocument.addField(EdmLabel.PL_WGS84_POS_LAT_LONG.toString(), place
					.getLat().getString() + "," + place.getLong().getString());
			solrInputDocument.addField(EdmLabel.PL_WGS84_POS_LAT.toString(), place
					.getLat().getString());
			solrInputDocument.addField(EdmLabel.PL_WGS84_POS_LONG.toString(), place
					.getLong().getString());
		}
		
		if(place.getAlt()!=null){
			solrInputDocument = SolrUtils
					.addFieldFromLiteral(solrInputDocument, place.getAlt(),
							EdmLabel.PL_WGS84_POS_ALT);
		}
		if (place.getHasPartList() != null) {
			for (HasPart hasPart : place.getHasPartList()) {
				solrInputDocument = SolrUtils
						.addFieldFromResourceOrLiteral(solrInputDocument, hasPart,
								EdmLabel.PL_DCTERMS_HASPART);
			}
		}

		if (place.getSameAList() != null) {
			for (SameAs sameAs : place.getSameAList()) {
				solrInputDocument.addField(EdmLabel.PL_OWL_SAMEAS.toString(),
						sameAs.getResource());
			}
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

		PlaceImpl place = (PlaceImpl) ((EdmMongoServer) mongoServer)
				.searchByAbout(PlaceImpl.class, placeType.getAbout());

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
	 * @param place
	 *            The Mongo Place Entity to update
	 * @param placeType
	 *            The JiBX Entity from which the Mongo Place Entity will be
	 *            updated
	 * @param mongoServer
	 *            The server on which the Place will be updated
	 * @return The updated Mongo Place Entity
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	private static PlaceImpl updatePlace(PlaceImpl place, PlaceType placeType,
			MongoServer mongoServer) {
		if (place.getNote() != null) {
		
			MongoUtils.update(PlaceImpl.class, place.getAbout(), mongoServer,
					"note", MongoUtils.createLiteralMapFromList(placeType.getNoteList()));

		}

		if (place.getAltLabel() != null) {
			
			MongoUtils.update(PlaceImpl.class, place.getAbout(), mongoServer,
					"altLabel", MongoUtils.createLiteralMapFromList(placeType.getAltLabelList()));

		}

		if (place.getPrefLabel() != null) {
			
				MongoUtils.update(PlaceImpl.class, place.getAbout(),
						mongoServer, "prefLabel", MongoUtils.createLiteralMapFromList(placeType.getPrefLabelList()));
			
		}

		if (place.getIsPartOf() != null) {
			
			MongoUtils.update(PlaceImpl.class, place.getAbout(), mongoServer,
					"isPartOf", MongoUtils.createResourceOrLiteralMapFromList(placeType.getIsPartOfList()));
		}
		
		if (place.getDcTermsHasPart() != null) {
			
			MongoUtils.update(PlaceImpl.class, place.getAbout(), mongoServer,
					"dcTermsHasPart", MongoUtils.createResourceOrLiteralMapFromList(placeType.getHasPartList()));
		}
		
		if (place.getOwlSameAs() != null) {
			List<String> owlSameAs = new ArrayList<String>();
			if (placeType.getSameAList() != null) {
				for (SameAs sameAsJibx : placeType.getSameAList()) {
					if (!MongoUtils.contains(place.getOwlSameAs(),
							sameAsJibx.getResource())) {
						owlSameAs.add(sameAsJibx.getResource());
					}
				}
			}
			for (String owlSameAsItem : place.getOwlSameAs()) {
				owlSameAs.add(owlSameAsItem);
			}
			MongoUtils.update(PlaceImpl.class, place.getAbout(), mongoServer,
					"owlSameAs", StringArrayUtils.toArray(owlSameAs));
		}
		
		
		if (placeType.getLat() != null) {

			MongoUtils.update(PlaceImpl.class, place.getAbout(), mongoServer,
					"latitude", convertToFloat(MongoUtils.createLiteralMapFromString(placeType.getLat())));
		}

		if (placeType.getLong() != null) {

			MongoUtils.update(PlaceImpl.class, place.getAbout(), mongoServer,
					"longitude", convertToFloat(MongoUtils.createLiteralMapFromString(placeType.getLong())));
		}
		
		if (placeType.getAlt() != null) {

			MongoUtils.update(PlaceImpl.class, place.getAbout(), mongoServer,
					"altitude", convertToFloat(MongoUtils.createLiteralMapFromString(placeType.getAlt())));
		}
		return (PlaceImpl) ((EdmMongoServer) mongoServer).searchByAbout(
				PlaceImpl.class, placeType.getAbout());
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
		//place.setId(new ObjectId());
		place.setAbout(placeType.getAbout());

		place.setLatitude(Float.parseFloat(placeType.getLat().getString()));

		place.setLongitude(Float.parseFloat(placeType.getLong().getString()));

		place.setNote(MongoUtils.createLiteralMapFromList(placeType.getNoteList()));
		place.setPrefLabel(MongoUtils.createLiteralMapFromList(placeType.getPrefLabelList()));
		place.setAltLabel(MongoUtils.createLiteralMapFromList(placeType.getAltLabelList()));

		place.setIsPartOf(MongoUtils.createResourceOrLiteralMapFromList(placeType
				.getIsPartOfList()));
		
		place.setAltitude(Float.parseFloat(placeType.getAlt().getString()));
		place.setDcTermsHasPart(MongoUtils.createResourceOrLiteralMapFromList(placeType.getHasPartList()));
		place.setOwlSameAs(SolrUtils.resourceListToArray(placeType
				.getSameAList()));
		return place;
	}
	
	private static Map<String,Float> convertToFloat(Map<String,String> map){
		Map<String,Float> repMap = new HashMap<String,Float>();
		if(map!=null){
		for (Entry<String, String> entry:map.entrySet()){
			repMap.put(entry.getKey(), Float.parseFloat(entry.getValue()));
		}
		}
		return repMap;
	}
}
