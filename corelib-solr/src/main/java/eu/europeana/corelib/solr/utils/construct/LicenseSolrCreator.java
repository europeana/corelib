package eu.europeana.corelib.solr.utils.construct;

import org.apache.solr.common.SolrInputDocument;

import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.definitions.solr.entity.License;
import eu.europeana.corelib.solr.utils.SolrUtils;


public class LicenseSolrCreator {
	 public void create(SolrInputDocument doc, License agent) {
		 SolrUtils.addFromString(doc, EdmLabel.LIC_RDF_ABOUT, agent.getAbout());
		 doc.addField(EdmLabel.LIC_CC_DEPRECATED_ON.toString(), agent.getCcDeprecatedOn());
		 SolrUtils.addFromString(doc,EdmLabel.LIC_ODRL_INHERITED_FROM, agent.getOdrlInheritFrom());
	 }

}
