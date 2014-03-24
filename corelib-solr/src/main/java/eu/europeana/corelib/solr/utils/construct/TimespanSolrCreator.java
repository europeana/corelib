package eu.europeana.corelib.solr.utils.construct;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.solr.common.SolrInputDocument;

import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.solr.entity.TimespanImpl;


public class TimespanSolrCreator {

	public void create(SolrInputDocument doc, TimespanImpl ts) {
		Collection<Object> values = doc.getFieldValues(
				EdmLabel.EDM_TIMESPAN.toString());
		if (values == null) {
			values = new ArrayList<Object>();
		}
		values.add(ts.getAbout());
		doc.setField(EdmLabel.EDM_TIMESPAN.toString(), values);

		if (ts.getPrefLabel() != null) {
			for (String key : ts.getPrefLabel().keySet()) {
				values = doc.getFieldValues(
						EdmLabel.TS_SKOS_PREF_LABEL.toString() + "." + key);
				if (values == null) {
					values = new ArrayList<Object>();
				}
				values.addAll(ts.getPrefLabel().get(key));
				doc.setField(
						EdmLabel.TS_SKOS_PREF_LABEL.toString() + "." + key,
						values);
			}
		}
		if (ts.getAltLabel() != null) {
			for (String key : ts.getAltLabel().keySet()) {
				values = doc.getFieldValues(
						EdmLabel.TS_SKOS_ALT_LABEL.toString() + "." + key);
				if (values == null) {
					values = new ArrayList<Object>();
				}
				values.addAll(ts.getAltLabel().get(key));
				doc.setField(EdmLabel.TS_SKOS_ALT_LABEL.toString() + "." + key,
						values);
			}
		}
		
		if (ts.getNote() != null) {
			for (String key : ts.getNote().keySet()) {
				values = doc.getFieldValues(
						EdmLabel.TS_SKOS_NOTE.toString());
				if (values == null) {
					values = new ArrayList<Object>();
				}
				values.addAll(ts.getNote().get(key));
				doc.setField(EdmLabel.TS_SKOS_NOTE.toString(),
						values);
			}
		}
		
		if (ts.getHiddenLabel() != null) {
			for (String key : ts.getHiddenLabel().keySet()) {
				values = doc.getFieldValues(
						EdmLabel.TS_SKOS_HIDDENLABEL.toString() + "." + key);
				if (values == null) {
					values = new ArrayList<Object>();
				}
				values.addAll(ts.getHiddenLabel().get(key));
				doc.setField(EdmLabel.TS_SKOS_HIDDENLABEL.toString() + "." + key,
						values);
			}
		}
		
		if (ts.getOwlSameAs() != null) {
			for (String key : ts.getOwlSameAs()) {
				values = doc.getFieldValues(
						EdmLabel.TS_OWL_SAMEAS.toString());
				if (values == null) {
					values = new ArrayList<Object>();
				}
				values.add(key);
				doc.setField(EdmLabel.TS_OWL_SAMEAS.toString(),
						values);
			}
		}
		
		if (ts.getDctermsHasPart() != null) {
			for (String key : ts.getDctermsHasPart().keySet()) {
				values = doc.getFieldValues(
						EdmLabel.TS_DCTERMS_HASPART.toString());
				if (values == null) {
					values = new ArrayList<Object>();
				}
				values.addAll(ts.getDctermsHasPart().get(key));
				doc.setField(EdmLabel.TS_DCTERMS_HASPART.toString(),
						values);
			}
		}
		
		if (ts.getIsPartOf() != null) {
			for (String key : ts.getIsPartOf().keySet()) {
				values = doc.getFieldValues(
						EdmLabel.TS_DCTERMS_ISPART_OF.toString());
				if (values == null) {
					values = new ArrayList<Object>();
				}
				values.addAll(ts.getIsPartOf().get(key));
				doc.setField(EdmLabel.TS_DCTERMS_ISPART_OF.toString(),
						values);
			}
		}
		
		if (ts.getBegin() != null) {
			for (String key : ts.getBegin().keySet()) {
				values = doc.getFieldValues(
						EdmLabel.TS_EDM_BEGIN.toString());
				if (values == null) {
					values = new ArrayList<Object>();
				}
				values.addAll(ts.getBegin().get(key));
				doc.setField(EdmLabel.TS_EDM_BEGIN.toString(),
						values);
			}
		}
		
		if (ts.getEnd() != null) {
			for (String key : ts.getBegin().keySet()) {
				values = doc.getFieldValues(
						EdmLabel.TS_EDM_END.toString());
				if (values == null) {
					values = new ArrayList<Object>();
				}
				values.addAll(ts.getEnd().get(key));
				doc.setField(EdmLabel.TS_EDM_END.toString(),
						values);
			}
		}
	}
}
