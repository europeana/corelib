package eu.europeana.corelib.edm.utils.construct;

import eu.europeana.corelib.definitions.edm.entity.Place;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.edm.utils.SolrUtils;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.solr.common.SolrInputDocument;

/**
 * Generate Place SOLR fields from Mongo
 *
 * @author Yorgos.Mamakis@ europeana.eu
 *
 */
public class PlaceSolrCreator {

    /**
     * Create SOLR fields from a Mongo Place
     *
     * @param doc The solr document to modify
     * @param place The place mongo entity to append
     */
    public void create(SolrInputDocument doc, Place place) {

        SolrUtils.addFromString(doc, EdmLabel.EDM_PLACE, place.getAbout());
        SolrUtils.addFromMap(doc, EdmLabel.PL_SKOS_PREF_LABEL, place.getPrefLabel());
        SolrUtils.addFromMap(doc, EdmLabel.PL_SKOS_ALT_LABEL, place.getAltLabel());
        SolrUtils.addFromMap(doc, EdmLabel.PL_SKOS_NOTE, place.getNote());
        SolrUtils.addFromMap(doc, EdmLabel.PL_SKOS_HIDDENLABEL, place.getHiddenLabel());
        SolrUtils.addFromStringArray(doc, EdmLabel.PL_OWL_SAMEAS, place.getOwlSameAs());
        SolrUtils.addFromMap(doc, EdmLabel.PL_DCTERMS_HASPART, place.getDcTermsHasPart());
        SolrUtils.addFromMap(doc, EdmLabel.PL_DCTERMS_ISPART_OF, place.getIsPartOf());

        if (place.getLatitude() != null && place.getLatitude() != 0) {
            Collection<Object> values = doc.getFieldValues(EdmLabel.PL_WGS84_POS_LAT.toString());
            if (values == null) {
                values = new ArrayList<Object>();
            }
            values.add(place.getLatitude());
            doc.setField(EdmLabel.PL_WGS84_POS_LAT.toString(), values);
        }

        if (place.getLongitude() != null && place.getLongitude() != 0) {
            Collection<Object> values = doc.getFieldValues(EdmLabel.PL_WGS84_POS_LONG.toString());
            if (values == null) {
                values = new ArrayList<Object>();
            }
            values.add(place.getLongitude());
            doc.setField(EdmLabel.PL_WGS84_POS_LONG.toString(), values);
        }
        if (place.getAltitude() != null && place.getAltitude() != 0) {
            Collection<Object> values = doc.getFieldValues(EdmLabel.PL_WGS84_POS_ALT.toString());
            if (values == null) {
                values = new ArrayList<Object>();
            }
            values.add(place.getAltitude());
            doc.setField(EdmLabel.PL_WGS84_POS_ALT.toString(), values);
        }
    }
}
