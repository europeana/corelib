package eu.europeana.corelib.solr.server.importer.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.common.SolrInputDocument;

import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.definitions.solr.entity.Place;
import eu.europeana.corelib.solr.entity.PlaceImpl;
import eu.europeana.corelib.solr.server.MongoDBServer;
import eu.europeana.corelib.definitions.jibx.AltLabel;
import eu.europeana.corelib.definitions.jibx.IsPartOf;
import eu.europeana.corelib.definitions.jibx.Note;
import eu.europeana.corelib.definitions.jibx.PlaceType;
import eu.europeana.corelib.definitions.jibx.PrefLabel;

public class PlaceFieldInput {
	public static SolrInputDocument createPlaceSolrFields(PlaceType place, SolrInputDocument solrInputDocument){
		solrInputDocument.addField(EdmLabel.EDM_PLACE.toString(), place.getAbout());
		for(AltLabel altLabel: place.getAltLabelList()){
			solrInputDocument.addField(EdmLabel.PL_SKOS_ALT_LABEL.toString()+"."+altLabel.getLang().getLang(), altLabel.getString());
		}
		for(PrefLabel prefLabel: place.getPrefLabelList()){
			solrInputDocument.addField(EdmLabel.PL_SKOS_PREF_LABEL.toString()+"."+prefLabel.getLang().getLang(), prefLabel.getString());
		}
		for(IsPartOf isPartOf: place.getIsPartOfList()){
			solrInputDocument.addField(EdmLabel.PL_DCTERMS_ISPART_OF.toString(),isPartOf.getString());
		}
		for(Note note:place.getNoteList()){
			solrInputDocument.addField(EdmLabel.PL_SKOS_NOTE.toString(),note.getString());
		}
		solrInputDocument.addField(EdmLabel.PL_POSITION.toString(), place.getPosLat().getPosLat()+","+ place.getPosLong().getPosLong());
		
		return solrInputDocument;
	}

	public static List<? extends Place> createPlaceMongoFields(PlaceType placeType,	MongoDBServer mongoServer) {
		List<Place> placeList = new ArrayList<Place>();
		//If place exists in mongo
		try {
			placeList.add((PlaceImpl) mongoServer.searchByAbout(placeType.getAbout()));
		}
		// if it does not exist
		catch (NullPointerException npe) {
			Place place = new PlaceImpl();
			place.setAbout(placeType.getAbout());
			place.setLatitude(placeType.getPosLat().getPosLat());
			place.setLongitude(placeType.getPosLong().getPosLong());
			
			List<String> noteList = new ArrayList<String>();
			for(Note note: placeType.getNoteList()){
				noteList.add(note.getString());
			}
			place.setNote(noteList.toArray(new String[noteList.size()]));

			Map<String, String> prefLabelMongo = new HashMap<String, String>();
			for (PrefLabel prefLabelJibx : placeType.getPrefLabelList()) {
				prefLabelMongo.put(prefLabelJibx.getLang().getLang(),prefLabelJibx.getString());
			}
			place.setPrefLabel(prefLabelMongo);

			Map<String, String> altLabelMongo = new HashMap<String, String>();
			for (AltLabel altLabelJibx : placeType.getAltLabelList()) {
				altLabelMongo.put(altLabelJibx.getLang().getLang(),
						altLabelJibx.getString());
			}
			place.setAltLabel(altLabelMongo);
			
			List<String> isPartOfList = new ArrayList<String>();
			for(IsPartOf isPartOf: placeType.getIsPartOfList()){
				noteList.add(isPartOf.getString());
			}
			place.setIsPartOf(isPartOfList.toArray(new String[isPartOfList.size()]));
			mongoServer.getDatastore().save(place);
		}
		return placeList;
	}
}
