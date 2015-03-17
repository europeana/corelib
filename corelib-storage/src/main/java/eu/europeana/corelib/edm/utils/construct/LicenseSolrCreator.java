package eu.europeana.corelib.edm.utils.construct;

import org.apache.solr.common.SolrInputDocument;

import eu.europeana.corelib.definitions.edm.entity.License;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.edm.utils.SolrUtils;


public class LicenseSolrCreator {
	 public void create(SolrInputDocument doc, License agent, boolean isAggregation) {
             if(isAggregation){
		 SolrUtils.addFromString(doc, EdmLabel.PROVIDER_AGGREGATION_CC_LICENSE, agent.getAbout());
		 doc.addField(EdmLabel.PROVIDER_AGGREGATION_CC_DEPRECATED_ON.toString(), agent.getCcDeprecatedOn());
		 SolrUtils.addFromString(doc,EdmLabel.PROVIDER_AGGREGATION_ODRL_INHERITED_FROM, agent.getOdrlInheritFrom());
             } else {
                  SolrUtils.addFromString(doc, EdmLabel.WR_CC_LICENSE, agent.getAbout());
		 doc.addField(EdmLabel.WR_CC_DEPRECATED_ON.toString(), agent.getCcDeprecatedOn());
		 SolrUtils.addFromString(doc,EdmLabel.WR_ODRL_INHERITED_FROM, agent.getOdrlInheritFrom());
             }
	 }

}
