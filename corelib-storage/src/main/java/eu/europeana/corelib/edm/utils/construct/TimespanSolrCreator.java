package eu.europeana.corelib.edm.utils.construct;

import org.apache.solr.common.SolrInputDocument;

import eu.europeana.corelib.definitions.edm.entity.Timespan;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.edm.utils.SolrUtils;

/**
 * Generate Timespan SOLR fields from Mongo
 * @author Yorgos.Mamakis@ europeana.eu
 *
 */
public class TimespanSolrCreator {
	/**
	 * Create SOLR fields from a Mongo concept
	 * @param doc The solr document to modify
	 * @param ts The timespan mongo entity to append
	 */
	public void create(SolrInputDocument doc, Timespan ts) {
		SolrUtils.addFromString(doc, EdmLabel.EDM_TIMESPAN, ts.getAbout());
                SolrUtils.addFromMap(doc, EdmLabel.TS_SKOS_PREF_LABEL, ts.getPrefLabel());
		SolrUtils.addFromMap(doc, EdmLabel.TS_SKOS_ALT_LABEL, ts.getAltLabel());
                SolrUtils.addFromMap(doc, EdmLabel.TS_SKOS_NOTE, ts.getNote());
		SolrUtils.addFromMap(doc, EdmLabel.TS_SKOS_HIDDENLABEL, ts.getHiddenLabel());
		SolrUtils.addFromStringArray(doc, EdmLabel.TS_SKOS_PREF_LABEL, ts.getOwlSameAs());
		SolrUtils.addFromMap(doc, EdmLabel.TS_DCTERMS_HASPART, ts.getDctermsHasPart());
		SolrUtils.addFromMap(doc, EdmLabel.TS_DCTERMS_ISPART_OF, ts.getIsPartOf());
		SolrUtils.addFromMap(doc, EdmLabel.TS_EDM_BEGIN, ts.getBegin());
		SolrUtils.addFromMap(doc, EdmLabel.TS_EDM_END, ts.getEnd());
	}
}
