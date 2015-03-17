package eu.europeana.corelib.edm.utils.construct;

import eu.europeana.corelib.definitions.edm.entity.Concept;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.edm.utils.SolrUtils;

import org.apache.solr.common.SolrInputDocument;
/**
 * Generate Concept SOLR fields from Mongo
 * @author Yorgos.Mamakis@ europeana.eu
 *
 */
public class ConceptSolrCreator {
	/**
	 * Create SOLR fields from a Mongo concept
	 * @param doc The solr document to modify
	 * @param concept The concept mongo entity to append
	 */
	public void create(SolrInputDocument doc, Concept concept){
		SolrUtils.addFromString(doc, EdmLabel.SKOS_CONCEPT, concept.getAbout());
                SolrUtils.addFromMap(doc, EdmLabel.CC_SKOS_PREF_LABEL, concept.getPrefLabel());
                SolrUtils.addFromMap(doc, EdmLabel.CC_SKOS_ALT_LABEL, concept.getAltLabel());
                SolrUtils.addFromMap(doc, EdmLabel.CC_SKOS_HIDDEN_LABEL, concept.getHiddenLabel());
		SolrUtils.addFromMap(doc, EdmLabel.CC_SKOS_NOTE, concept.getNote());
                SolrUtils.addFromMap(doc, EdmLabel.CC_SKOS_NOTATIONS, concept.getNotation());
		SolrUtils.addFromStringArray(doc, EdmLabel.CC_SKOS_BROADER, concept.getBroader());
                SolrUtils.addFromStringArray(doc, EdmLabel.CC_SKOS_BROADMATCH, concept.getBroadMatch());
		SolrUtils.addFromStringArray(doc, EdmLabel.CC_SKOS_NARROWER, concept.getNarrower());
                SolrUtils.addFromStringArray(doc, EdmLabel.CC_SKOS_NARROWMATCH, concept.getNarrowMatch());
                SolrUtils.addFromStringArray(doc, EdmLabel.CC_SKOS_RELATED, concept.getRelated());
                SolrUtils.addFromStringArray(doc, EdmLabel.CC_SKOS_RELATEDMATCH, concept.getRelatedMatch());
		SolrUtils.addFromStringArray(doc, EdmLabel.CC_SKOS_EXACTMATCH, concept.getExactMatch());
                SolrUtils.addFromStringArray(doc, EdmLabel.CC_SKOS_CLOSEMATCH, concept.getCloseMatch());
		SolrUtils.addFromStringArray(doc, EdmLabel.CC_SKOS_INSCHEME, concept.getInScheme());
	}
}
