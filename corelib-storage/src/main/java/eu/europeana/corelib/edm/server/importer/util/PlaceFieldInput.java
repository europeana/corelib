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
package eu.europeana.corelib.edm.server.importer.util;

import org.apache.solr.common.SolrInputDocument;

import eu.europeana.corelib.definitions.jibx.AltLabel;
import eu.europeana.corelib.definitions.jibx.HasPart;
import eu.europeana.corelib.definitions.jibx.IsPartOf;
import eu.europeana.corelib.definitions.jibx.Note;
import eu.europeana.corelib.definitions.jibx.PlaceType;
import eu.europeana.corelib.definitions.jibx.PrefLabel;
import eu.europeana.corelib.definitions.jibx.SameAs;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.edm.utils.MongoUtils;
import eu.europeana.corelib.edm.utils.SolrUtils;
import eu.europeana.corelib.solr.entity.PlaceImpl;

/**
 * Constructor of a Place EDM Entity
 * 
 * @author Yorgos.Mamakis@ kb.nl
 */
public final class PlaceFieldInput {

	public PlaceFieldInput() {

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
	public SolrInputDocument createPlaceSolrFields(PlaceType place,
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
				solrInputDocument = SolrUtils.addFieldFromLiteral(
						solrInputDocument, prefLabel,
						EdmLabel.PL_SKOS_PREF_LABEL);
			}
		}
		if (place.getIsPartOfList() != null) {
			for (IsPartOf isPartOf : place.getIsPartOfList()) {
				solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(
						solrInputDocument, isPartOf,
						EdmLabel.PL_DCTERMS_ISPART_OF);
			}
		}
		if (place.getNoteList() != null) {
			for (Note note : place.getNoteList()) {
				solrInputDocument = SolrUtils.addFieldFromLiteral(
						solrInputDocument, note, EdmLabel.PL_SKOS_NOTE);
			}
		}
		if (place.getLong() != null && place.getLat() != null) {
			solrInputDocument.addField(
					EdmLabel.PL_WGS84_POS_LAT_LONG.toString(), place.getLat()
							.getLat() + "," + place.getLong().getLong());
			solrInputDocument.addField(EdmLabel.PL_WGS84_POS_LAT.toString(),
					place.getLat().getLat());
			solrInputDocument.addField(EdmLabel.PL_WGS84_POS_LONG.toString(),
					place.getLong().getLong());
		}

		if (place.getAlt() != null) {
			solrInputDocument.addField(EdmLabel.PL_WGS84_POS_ALT.toString(),
					place.getAlt().getAlt());

		}
		if (place.getHasPartList() != null) {
			for (HasPart hasPart : place.getHasPartList()) {
				solrInputDocument = SolrUtils
						.addFieldFromResourceOrLiteral(solrInputDocument,
								hasPart, EdmLabel.PL_DCTERMS_HASPART);
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
	 * Create a new Place Mongo Entity from a JiBX palce
	 * 
	 * @param placeType
	 *            The JiBX Place Entity
	 * @return A new Mongo Place Entity
	 */
	public PlaceImpl createNewPlace(PlaceType placeType) {
		PlaceImpl place = new PlaceImpl();
		// place.setId(new ObjectId());
		place.setAbout(placeType.getAbout());
		if (placeType.getLat() != null) {
			place.setLatitude(placeType.getLat().getLat());
		}
		if (placeType.getLong() != null) {
			place.setLongitude(placeType.getLong().getLong());
		}
		place.setNote(MongoUtils.createLiteralMapFromList(placeType
				.getNoteList()));
		place.setPrefLabel(MongoUtils.createLiteralMapFromList(placeType
				.getPrefLabelList()));
		place.setAltLabel(MongoUtils.createLiteralMapFromList(placeType
				.getAltLabelList()));

		place.setIsPartOf(MongoUtils
				.createResourceOrLiteralMapFromList(placeType.getIsPartOfList()));
		if (placeType.getAlt() != null) {
			place.setAltitude(placeType.getAlt().getAlt());
		}
		place.setDcTermsHasPart(MongoUtils
				.createResourceOrLiteralMapFromList(placeType.getHasPartList()));
		place.setOwlSameAs(SolrUtils.resourceListToArray(placeType
				.getSameAList()));
		return place;
	}

}
