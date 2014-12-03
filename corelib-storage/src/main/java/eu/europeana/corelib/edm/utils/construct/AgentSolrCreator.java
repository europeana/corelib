package eu.europeana.corelib.edm.utils.construct;

import org.apache.solr.common.SolrInputDocument;

import eu.europeana.corelib.definitions.edm.entity.Agent;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.edm.utils.SolrUtils;

/**
 * Generate Agent SOLR fields from Mongo
 *
 * @author Yorgos.Mamakis@ europeana.eu
 *
 */
public class AgentSolrCreator {

    /**
     * Create SOLR fields from a Mongo Agent
     *
     * @param doc The solr document to modify
     * @param agent The agent mongo entity to append
     */
    public void create(SolrInputDocument doc, Agent agent) {

        SolrUtils.addFromString(doc, EdmLabel.EDM_AGENT, agent.getAbout());
        SolrUtils.addFromMap(doc, EdmLabel.AG_SKOS_PREF_LABEL, agent.getPrefLabel());
        SolrUtils.addFromMap(doc, EdmLabel.AG_SKOS_ALT_LABEL, agent.getAltLabel());
        SolrUtils.addFromMap(doc, EdmLabel.AG_SKOS_NOTE, agent.getNote());
        SolrUtils.addFromMap(doc, EdmLabel.AG_DC_DATE, agent.getDcDate());
        SolrUtils.addFromStringArray(doc, EdmLabel.AG_OWL_SAMEAS, agent.getOwlSameAs());
        SolrUtils.addFromMap(doc, EdmLabel.AG_DC_IDENTIFIER, agent.getDcIdentifier());
        SolrUtils.addFromMap(doc, EdmLabel.AG_EDM_BEGIN, agent.getBegin());
        SolrUtils.addFromMap(doc, EdmLabel.AG_EDM_END, agent.getEnd());
        SolrUtils.addFromStringArray(doc, EdmLabel.AG_EDM_WASPRESENTAT, agent.getEdmWasPresentAt());
        SolrUtils.addFromMap(doc, EdmLabel.AG_EDM_HASMET, agent.getEdmHasMet());
        SolrUtils.addFromMap(doc, EdmLabel.AG_EDM_ISRELATEDTO, agent.getEdmIsRelatedTo());
        SolrUtils.addFromMap(doc, EdmLabel.AG_FOAF_NAME, agent.getFoafName());
        SolrUtils.addFromMap(doc, EdmLabel.AG_RDAGR2_DATEOFBIRTH, agent.getRdaGr2DateOfBirth());
        SolrUtils.addFromMap(doc, EdmLabel.AG_RDAGR2_DATEOFDEATH, agent.getRdaGr2DateOfDeath());
        SolrUtils.addFromMap(doc, EdmLabel.AG_RDAGR2_PLACEOFBIRTH, agent.getRdaGr2PlaceOfBirth());
        SolrUtils.addFromMap(doc, EdmLabel.AG_RDAGR2_PLACEOFDEATH, agent.getRdaGr2PlaceOfDeath());
        SolrUtils.addFromMap(doc, EdmLabel.AG_RDAGR2_DATEOFESTABLISHMENT, agent.getRdaGr2DateOfEstablishment());
        SolrUtils.addFromMap(doc, EdmLabel.AG_RDAGR2_DATEOFTERMINATION, agent.getRdaGr2DateOfTermination());
        SolrUtils.addFromMap(doc, EdmLabel.AG_RDAGR2_GENDER, agent.getRdaGr2Gender());
        SolrUtils.addFromMap(doc, EdmLabel.AG_RDAGR2_PROFESSIONOROCCUPATION, agent.getRdaGr2ProfessionOrOccupation());
        SolrUtils.addFromMap(doc, EdmLabel.AG_RDAGR2_BIOGRAPHICALINFORMATION, agent.getRdaGr2BiographicalInformation());
    }
}
