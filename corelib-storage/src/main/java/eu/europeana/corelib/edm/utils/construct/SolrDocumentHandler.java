/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.europeana.corelib.edm.utils.construct;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.ModifiableSolrParams;

import eu.europeana.corelib.definitions.edm.entity.Agent;
import eu.europeana.corelib.definitions.edm.entity.Aggregation;
import eu.europeana.corelib.definitions.edm.entity.Concept;
import eu.europeana.corelib.definitions.edm.entity.License;
import eu.europeana.corelib.definitions.edm.entity.Place;
import eu.europeana.corelib.definitions.edm.entity.Proxy;
import eu.europeana.corelib.definitions.edm.entity.Timespan;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.solr.bean.impl.FullBeanImpl;
import eu.europeana.corelib.solr.entity.LicenseImpl;
import eu.europeana.publication.common.ICollection;
import eu.europeana.publication.common.IDocument;
import eu.europeana.publication.common.State;

/**
 * Class that converts a FullBean to a SolrInputDocument and saves it
 * 
 * @author Yorgos.Mamakis@ europeana.eu
 */
public class SolrDocumentHandler implements ICollection {
	private HttpSolrServer solrServer;
	/*
	 * <field name="is_fulltext" type="boolean" indexed="true" stored="true"
	 * multiValued="false"/> <field name="has_thumbnails" type="boolean"
	 * indexed="true" stored="true" multiValued="false"/> <field
	 * name="has_media" type="boolean" indexed="true" stored="true"
	 * multiValued="false"/> <field name="filter_tags" type="int" indexed="true"
	 * stored="true" multiValued="true"/> <field name="facet_tags" type="int"
	 * indexed="true" stored="true" multiValued="true"/>
	 */
	private final List<String> inputFields = new ArrayList<String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			add("is_fulltext");
			add("has_thumbnails");
			add("has_media");
			add("filter_tags");
			add("facet_tags");
		}
	};

	public SolrDocumentHandler(HttpSolrServer solrServer) {
		this.solrServer = solrServer;
	}

	/**
	 * Convert a FullBean to a SolrInputDocument and save it in a solr -
	 * Convenience method
	 * 
	 * @param fBean
	 *            The FullBean to convert
	 */
	public void save(FullBeanImpl fBean) {

		try {
			solrServer.add(generate(fBean));
		} catch (SolrServerException ex) {
			Logger.getLogger(SolrDocumentHandler.class.getName()).log(
					Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(SolrDocumentHandler.class.getName()).log(
					Level.SEVERE, null, ex);
		}
	}

	/**
	 * Convert a FullBean to a SolrInputDocument
	 * 
	 * @param fBean
	 *            The FullBean to convert to a SolrInputDocument
	 * @return The SolrInputDocument representation of the FullBean
	 */
	public SolrInputDocument generate(FullBeanImpl fBean)
			throws SolrServerException {
		SolrInputDocument doc = new SolrInputDocument();
		List<LicenseImpl> licenses = fBean.getLicenses();
		List<String> licIds = new ArrayList<String>();
		if (licenses != null && licenses.size() > 0) {
			for (LicenseImpl lic : licenses) {
				licIds.add(lic.getAbout());
			}
		}
		new ProvidedChoSolrCreator()
				.create(doc, fBean.getProvidedCHOs().get(0));
		new AggregationSolrCreator().create(doc,
				fBean.getAggregations().get(0), licIds);
		new EuropeanaAggregationSolrCreator().create(doc,
				fBean.getEuropeanaAggregation());
		for (Proxy prx : fBean.getProxies()) {
			new ProxySolrCreator().create(doc, prx);
		}
		if (fBean.getConcepts() != null) {
			for (Concept concept : fBean.getConcepts()) {
				new ConceptSolrCreator().create(doc, concept);
			}
		}
		if (fBean.getTimespans() != null) {
			for (Timespan ts : fBean.getTimespans()) {
				new TimespanSolrCreator().create(doc, ts);
			}
		}
		if (fBean.getAgents() != null) {
			for (Agent agent : fBean.getAgents()) {
				new AgentSolrCreator().create(doc, agent);
			}
		}
		if (fBean.getPlaces() != null) {
			for (Place place : fBean.getPlaces()) {
				new PlaceSolrCreator().create(doc, place);
			}
		}
		if (fBean.getLicenses() != null) {
			for (License lic : fBean.getLicenses()) {
				boolean isAggregation = false;
				for (Aggregation aggr : fBean.getAggregations()) {
					if (aggr.getEdmRights() != null
							&& aggr.getEdmRights().get("def")
									.contains(lic.getAbout())) {
						isAggregation = true;
						break;
					}
				}
				System.out.println(isAggregation);
				new LicenseSolrCreator().create(doc, lic, isAggregation);
			}
		}
		doc.addField(EdmLabel.EUROPEANA_COMPLETENESS.toString(),
				fBean.getEuropeanaCompleteness());
		doc.addField(EdmLabel.EUROPEANA_COLLECTIONNAME.toString(),
				fBean.getEuropeanaCollectionName()[0]);
		doc.addField("timestamp_created", fBean.getTimestampCreated());
		doc.addField("timestamp_update", fBean.getTimestampUpdated());
		
		extractCRFFields(doc, fBean.getAbout());
		return doc;
	}

	private void extractCRFFields(SolrInputDocument doc, String about)
			throws SolrServerException {
		ModifiableSolrParams params = new ModifiableSolrParams();

		params.add("q", "europeana_id:"+ClientUtils.escapeQueryChars(about));

		SolrDocumentList resultList = solrServer.query(params).getResults();
		if (resultList != null && resultList.size() > 0) {
			SolrDocument retrievedDocument = resultList.get(0);
			for (String field : inputFields) {
				if (retrievedDocument.getFieldValue(field) != null) {
					doc.addField(field, retrievedDocument.getFieldValue(field));
				}
			}
		}
	}

	@Override
	public List<IDocument> getDocumentsByStatesUsingBatch(
			List<State> stateVlues, Map<String, List<String>> queryChoices,
			int batchSize) {
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}

	@Override
	public IDocument getDocumentById(String id) {
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}

	@Override
	public void insertDocument(IDocument document) {
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}

	@Override
	public void updateDocumentUsingId(IDocument document) {
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}

	@Override
	public void cloneDocument(IDocument originalDocument,
			IDocument clonedDocument) {
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}

	@Override
	public void deleteDocument(IDocument id) {
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}

	@Override
	public void commit() throws Exception {
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}
}
