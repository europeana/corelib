/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.europeana.corelib.edm.utils.construct;

import eu.europeana.corelib.definitions.edm.entity.Proxy;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.edm.utils.SolrUtils;

import org.apache.solr.common.SolrInputDocument;

/**
 *
 * @author Yorgos.Mamakis@ europeana.eu
 */
public class ProxySolrCreator {
    
    public void create(SolrInputDocument doc, Proxy proxy){
         SolrUtils.addFromString(doc, EdmLabel.ORE_PROXY, proxy.getAbout());
         SolrUtils.addFromMap(doc, EdmLabel.PROXY_EDM_CURRENT_LOCATION, proxy.getEdmCurrentLocation());
         SolrUtils.addFromString(doc, EdmLabel.PROXY_EDM_ISREPRESENTATIONOF, proxy.getEdmIsRepresentationOf());
         SolrUtils.addFromString(doc, EdmLabel.PROXY_ORE_PROXY_FOR, proxy.getProxyFor());
         SolrUtils.addFromStringArray(doc, EdmLabel.PROXY_EDM_INCORPORATES, proxy.getEdmIncorporates());
         SolrUtils.addFromStringArray(doc, EdmLabel.PROXY_EDM_ISDERIVATIVE_OF, proxy.getEdmIsDerivativeOf());
         SolrUtils.addFromStringArray(doc, EdmLabel.PROXY_EDM_IS_NEXT_IN_SEQUENCE, proxy.getEdmIsNextInSequence());
         SolrUtils.addFromStringArray(doc, EdmLabel.PROXY_EDM_ISSIMILARTO, proxy.getEdmIsSimilarTo());
         SolrUtils.addFromStringArray(doc, EdmLabel.PROXY_EDM_ISSUCCESSOROF, proxy.getEdmIsSuccessorOf());
         SolrUtils.addFromStringArray(doc, EdmLabel.PROXY_EDM_REALIZES, proxy.getEdmRealizes());
         SolrUtils.addFromStringArray(doc, EdmLabel.PROXY_EDM_WASPRESENTAT, proxy.getEdmWasPresentAt());
         SolrUtils.addFromStringArray(doc, EdmLabel.PROXY_ORE_PROXY_IN, proxy.getProxyIn());
         SolrUtils.addFromMap(doc, EdmLabel.PROXY_DC_CONTRIBUTOR, proxy.getDcContributor()); 
         SolrUtils.addFromMap(doc, EdmLabel.PROXY_DC_COVERAGE, proxy.getDcCoverage());
         SolrUtils.addFromMap(doc, EdmLabel.PROXY_DC_CREATOR, proxy.getDcCreator());
         SolrUtils.addFromMap(doc, EdmLabel.PROXY_DC_DATE, proxy.getDcDate());
         SolrUtils.addFromMap(doc, EdmLabel.PROXY_DC_DESCRIPTION, proxy.getDcDescription());
         SolrUtils.addFromMap(doc, EdmLabel.PROXY_DC_FORMAT, proxy.getDcFormat());
         SolrUtils.addFromMap(doc, EdmLabel.PROXY_DC_IDENTIFIER, proxy.getDcIdentifier());
         SolrUtils.addFromMap(doc, EdmLabel.PROXY_DC_LANGUAGE, proxy.getDcLanguage());
         SolrUtils.addFromMap(doc, EdmLabel.PROXY_DC_PUBLISHER, proxy.getDcPublisher());
         SolrUtils.addFromMap(doc, EdmLabel.PROXY_DC_RELATION, proxy.getDcRelation());
         SolrUtils.addFromMap(doc, EdmLabel.PROXY_DC_RIGHTS, proxy.getDcRights());
         SolrUtils.addFromMap(doc, EdmLabel.PROXY_DC_SOURCE, proxy.getDcSource());
         SolrUtils.addFromMap(doc, EdmLabel.PROXY_DC_SUBJECT, proxy.getDcSubject()); 
         SolrUtils.addFromMap(doc, EdmLabel.PROXY_DC_TITLE, proxy.getDcTitle());
         SolrUtils.addFromMap(doc, EdmLabel.PROXY_DC_TYPE, proxy.getDcType());
         SolrUtils.addFromMap(doc, EdmLabel.PROXY_DCTERMS_ALTERNATIVE, proxy.getDctermsAlternative());
         SolrUtils.addFromMap(doc, EdmLabel.PROXY_DCTERMS_CONFORMS_TO, proxy.getDctermsConformsTo());
         SolrUtils.addFromMap(doc, EdmLabel.PROXY_DCTERMS_CREATED, proxy.getDctermsCreated());
         SolrUtils.addFromMap(doc, EdmLabel.PROXY_DCTERMS_EXTENT, proxy.getDctermsExtent());
         SolrUtils.addFromMap(doc, EdmLabel.PROXY_DCTERMS_HAS_FORMAT, proxy.getDctermsHasFormat());
         SolrUtils.addFromMap(doc, EdmLabel.PROXY_DCTERMS_HAS_PART, proxy.getDctermsHasPart());
         SolrUtils.addFromMap(doc, EdmLabel.PROXY_DCTERMS_HAS_VERSION, proxy.getDctermsHasVersion());
         SolrUtils.addFromMap(doc, EdmLabel.PROXY_DCTERMS_IS_FORMAT_OF, proxy.getDctermsIsFormatOf());
         SolrUtils.addFromMap(doc, EdmLabel.PROXY_DCTERMS_IS_PART_OF, proxy.getDctermsIsPartOf());
         SolrUtils.addFromMap(doc, EdmLabel.PROXY_DCTERMS_IS_REFERENCED_BY, proxy.getDctermsIsReferencedBy());
         SolrUtils.addFromMap(doc, EdmLabel.PROXY_DCTERMS_IS_REPLACED_BY, proxy.getDctermsIsReplacedBy());
         SolrUtils.addFromMap(doc, EdmLabel.PROXY_DCTERMS_IS_REQUIRED_BY, proxy.getDctermsIsRequiredBy());
         SolrUtils.addFromMap(doc, EdmLabel.PROXY_DCTERMS_IS_VERSION_OF, proxy.getDctermsIsVersionOf());
         SolrUtils.addFromMap(doc, EdmLabel.PROXY_DCTERMS_ISSUED, proxy.getDctermsIssued());
         SolrUtils.addFromMap(doc, EdmLabel.PROXY_DCTERMS_MEDIUM, proxy.getDctermsMedium());
         SolrUtils.addFromMap(doc, EdmLabel.PROXY_DCTERMS_PROVENANCE, proxy.getDctermsProvenance());
         SolrUtils.addFromMap(doc, EdmLabel.PROXY_DCTERMS_REFERENCES, proxy.getDctermsReferences());
         SolrUtils.addFromMap(doc, EdmLabel.PROXY_DCTERMS_REPLACES, proxy.getDctermsReplaces());
         SolrUtils.addFromMap(doc, EdmLabel.PROXY_DCTERMS_REQUIRES, proxy.getDctermsRequires());
         SolrUtils.addFromMap(doc, EdmLabel.PROXY_DCTERMS_SPATIAL, proxy.getDctermsSpatial());
         SolrUtils.addFromMap(doc, EdmLabel.PROXY_DCTERMS_TABLE_OF_CONTENTS, proxy.getDctermsTOC());
         SolrUtils.addFromMap(doc, EdmLabel.PROXY_DCTERMS_TEMPORAL, proxy.getDctermsTemporal());
         SolrUtils.addFromMap(doc, EdmLabel.PROXY_EDM_YEAR, proxy.getYear());
         SolrUtils.addFromMap(doc, EdmLabel.PROXY_EDM_HAS_TYPE, proxy.getEdmHasType());
         SolrUtils.addFromMap(doc, EdmLabel.PROXY_EDM_ISRELATEDTO, proxy.getEdmIsRelatedTo());
         SolrUtils.addFromMap(doc, EdmLabel.PROXY_EDM_RIGHTS, proxy.getEdmRights()); 
         if(proxy.getEdmType()!=null){
             doc.addField(EdmLabel.PROVIDER_EDM_TYPE.toString(), proxy.getEdmType());
         }
         
        doc.addField(EdmLabel.EDM_ISEUROPEANA_PROXY.toString(), proxy.isEuropeanaProxy());
        
    }
}
