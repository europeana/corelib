/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.europeana.corelib.solr.utils.construct;

import java.util.List;
import java.util.Map;

import org.apache.solr.common.SolrInputDocument;

import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.definitions.solr.entity.WebResource;
import eu.europeana.corelib.solr.utils.SolrUtils;

/**
 *
 * @author Yorgos.Mamakis@ europeana.eu
 */
public class WebResourceSolrCreator {

	public void create(SolrInputDocument doc, WebResource wr,
			List<String> licIds) {
		SolrUtils.addFromString(doc, EdmLabel.EDM_WEB_RESOURCE, wr.getAbout());
		SolrUtils.addFromString(doc, EdmLabel.WR_EDM_IS_NEXT_IN_SEQUENCE,
				wr.getIsNextInSequence());
		if (!contains(wr.getWebResourceEdmRights(), licIds)) {
			SolrUtils.addFromMap(doc, EdmLabel.WR_EDM_RIGHTS,
					wr.getWebResourceEdmRights());
		}
		SolrUtils.addFromMap(doc, EdmLabel.WR_DC_RIGHTS,
				wr.getWebResourceDcRights());
		SolrUtils.addFromMap(doc, EdmLabel.WR_DC_DESCRIPTION,
				wr.getDcDescription());
		SolrUtils.addFromMap(doc, EdmLabel.WR_DC_FORMAT, wr.getDcFormat());
		SolrUtils.addFromMap(doc, EdmLabel.WR_DC_SOURCE, wr.getDcSource());
		SolrUtils.addFromMap(doc, EdmLabel.WR_DC_CREATOR, wr.getDcCreator());
		SolrUtils.addFromMap(doc, EdmLabel.WR_DCTERMS_CONFORMSTO,
				wr.getDctermsConformsTo());
		SolrUtils.addFromMap(doc, EdmLabel.WR_DCTERMS_CREATED,
				wr.getDctermsCreated());
		SolrUtils.addFromMap(doc, EdmLabel.WR_DCTERMS_EXTENT,
				wr.getDctermsExtent());
		SolrUtils.addFromMap(doc, EdmLabel.WR_DCTERMS_HAS_PART,
				wr.getDctermsHasPart());
		SolrUtils.addFromMap(doc, EdmLabel.WR_DCTERMS_ISFORMATOF,
				wr.getDctermsIsFormatOf());
		SolrUtils.addFromMap(doc, EdmLabel.WR_DCTERMS_ISSUED,
				wr.getDctermsIssued());
		SolrUtils.addFromStringArray(doc, EdmLabel.WR_OWL_SAMEAS,
				wr.getOwlSameAs());
	}

	private boolean contains(Map<String, List<String>> webResourceEdmRights,
			List<String> licIds) {
		if (webResourceEdmRights == null) {
			return false;
		}
		return licIds.contains(webResourceEdmRights.values().iterator().next());
	}
}
