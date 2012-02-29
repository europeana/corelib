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

import eu.europeana.corelib.definitions.jibx.*;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.solr.entity.PlaceImpl;
import eu.europeana.corelib.solr.server.MongoDBServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.common.SolrInputDocument;
/**
 * Constructor of a Place EDM Entity
 * 
 * @author Yorgos.Mamakis@ kb.nl
 */
public class PlaceFieldInput {

    /**
     * Create a SolrInputDocument with the Place field values filled in
     * 
     * @param place
     *              The JiBX Place Entity
     * @param solrInputDocument
     *              The SolrInputDocument to alter
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
            solrInputDocument.addField(EdmLabel.PL_POSITION.toString(), place.getPosLat().getPosLat()
                    + ","
                    + place.getPosLong().getPosLong());
        }
        return solrInputDocument;
    }

    /**
     * Create a MongoDB Place Entity
     * 
     * @param placeType
     *              The JiBX Place Entity
     * @param mongoServer
     *              The MongoDB Server to save the Entity
     * @return The MongoDB Place Entity
     * @throws InstantiationException
     * @throws IllegalAccessException 
     */
    public static PlaceImpl createPlaceMongoFields(PlaceType placeType,
            MongoDBServer mongoServer) throws InstantiationException,
            IllegalAccessException {

        // If place exists in mongo

        PlaceImpl place = (PlaceImpl) mongoServer.searchByAbout(PlaceImpl.class,
                placeType.getAbout());

        // if it does not exist
        if (place == null) {
            place = new PlaceImpl();
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
                place.setIsPartOf(isPartOfList.toArray(new String[isPartOfList.size()]));
            }
            mongoServer.getDatastore().save(place);
        } else {
            //UPDATE
        }

        return place;
    }
}
