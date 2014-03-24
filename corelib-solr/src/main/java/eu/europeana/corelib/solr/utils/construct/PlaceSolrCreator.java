package eu.europeana.corelib.solr.utils.construct;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.solr.common.SolrInputDocument;

import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.solr.entity.PlaceImpl;

public class PlaceSolrCreator {
	public void create(SolrInputDocument doc, PlaceImpl place) {

		Collection<Object> values = doc.getFieldValues(EdmLabel.EDM_PLACE.toString());
		if (values == null) {
			values = new ArrayList<Object>();
		}
		values.add(place.getAbout());
		doc.setField(EdmLabel.EDM_PLACE.toString(), values);

		if (place.getPrefLabel() != null) {
			for (String key : place.getPrefLabel().keySet()) {
				values = doc.getFieldValues(EdmLabel.PL_SKOS_PREF_LABEL.toString()
						+ "." + key);
				if (values == null) {
					values = new ArrayList<Object>();
				}
				values.addAll(place.getPrefLabel().get(key));
				doc.setField(
						EdmLabel.PL_SKOS_PREF_LABEL.toString() + "." + key,
						values);
			}
		}
		if (place.getAltLabel() != null) {
			for (String key : place.getAltLabel().keySet()) {
				values = doc.getFieldValues(EdmLabel.PL_SKOS_ALT_LABEL.toString()
						+ "." + key);
				if (values == null) {
					values = new ArrayList<Object>();
				}
				values.addAll(place.getAltLabel().get(key));
				doc.setField(EdmLabel.PL_SKOS_ALT_LABEL.toString() + "." + key,
						values);
			}
		}

		if (place.getNote() != null) {
			for (String key : place.getNote().keySet()) {
				values = doc.getFieldValues(EdmLabel.PL_SKOS_NOTE.toString());
				if (values == null) {
					values = new ArrayList<Object>();
				}
				values.addAll(place.getNote().get(key));
				doc.setField(EdmLabel.PL_SKOS_NOTE.toString(), values);
			}
		}

		if (place.getHiddenLabel() != null) {
			for (String key : place.getHiddenLabel().keySet()) {
				values = doc.getFieldValues(EdmLabel.PL_SKOS_HIDDENLABEL.toString()
						+ "." + key);
				if (values == null) {
					values = new ArrayList<Object>();
				}
				values.addAll(place.getHiddenLabel().get(key));
				doc.setField(EdmLabel.PL_SKOS_HIDDENLABEL.toString() + "."
						+ key, values);
			}
		}

		if (place.getOwlSameAs() != null) {
			for (String key : place.getOwlSameAs()) {
				values = doc.getFieldValues(EdmLabel.PL_OWL_SAMEAS.toString());
				if (values == null) {
					values = new ArrayList<Object>();
				}
				values.add(key);
				doc.setField(EdmLabel.PL_OWL_SAMEAS.toString(), values);
			}
		}

		if (place.getDcTermsHasPart() != null) {
			for (String key : place.getDcTermsHasPart().keySet()) {
				values = doc.getFieldValues(EdmLabel.PL_DCTERMS_HASPART.toString());
				if (values == null) {
					values = new ArrayList<Object>();
				}
				values.addAll(place.getDcTermsHasPart().get(key));
				doc.setField(EdmLabel.PL_DCTERMS_HASPART.toString(), values);
			}
		}

		if (place.getIsPartOf() != null) {
			for (String key : place.getIsPartOf().keySet()) {
				values = doc.getFieldValues(EdmLabel.PL_DCTERMS_ISPART_OF.toString());
				if (values == null) {
					values = new ArrayList<Object>();
				}
				values.addAll(place.getIsPartOf().get(key));
				doc.setField(EdmLabel.PL_DCTERMS_ISPART_OF.toString(), values);
			}
		}

		if (place.getLatitude() != null && place.getLatitude() != 0) {
			values = doc.getFieldValues(EdmLabel.PL_WGS84_POS_LAT.toString());
			if (values == null) {
				values = new ArrayList<Object>();
			}
			values.add(place.getLatitude());
			doc.setField(EdmLabel.PL_WGS84_POS_LAT.toString(), values);
		}

		if (place.getLongitude() != null && place.getLongitude() != 0) {
			values = doc.getFieldValues(EdmLabel.PL_WGS84_POS_LONG.toString());
			if (values == null) {
				values = new ArrayList<Object>();
			}
			values.add(place.getLongitude());
			doc.setField(EdmLabel.PL_WGS84_POS_LONG.toString(), values);
		}
		if (place.getAltitude() != null && place.getAltitude() != 0) {
			values = doc.getFieldValues(EdmLabel.PL_WGS84_POS_ALT.toString());
			if (values == null) {
				values = new ArrayList<Object>();
			}
			values.add(place.getAltitude());
			doc.setField(EdmLabel.PL_WGS84_POS_ALT.toString(), values);
		}
	}
}
