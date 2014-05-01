/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.europeana.corelib.solr.utils.construct;

import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.definitions.solr.entity.Agent;
import eu.europeana.corelib.definitions.solr.entity.Concept;
import eu.europeana.corelib.definitions.solr.entity.Place;
import eu.europeana.corelib.definitions.solr.entity.Proxy;
import eu.europeana.corelib.definitions.solr.entity.Timespan;
import eu.europeana.corelib.solr.bean.impl.FullBeanImpl;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;

/**
 *
 * @author Yorgos.Mamakis@ europeana.eu
 */
public class SolrDocumentHandler {
    public void save(FullBeanImpl fBean, HttpSolrServer solrServer){
        
        SolrInputDocument doc = new SolrInputDocument();
        new ProvidedChoSolrCreator().create(doc, fBean.getProvidedCHOs().get(0));
        new AggregationSolrCreator().create(doc, fBean.getAggregations().get(0));
        new EuropeanaAggregationSolrCreator().create(doc, fBean.getEuropeanaAggregation());
        for(Proxy prx: fBean.getProxies()){
            new ProxySolrCreator().create(doc, prx);
        }
        for(Concept concept: fBean.getConcepts()){
            new ConceptSolrCreator().create(doc, concept);
        }
        for(Timespan ts: fBean.getTimespans()){
            new TimespanSolrCreator().create(doc, ts);
        }
        for(Agent agent: fBean.getAgents()){
            new AgentSolrCreator().create(doc, agent);
        }
        for(Place place: fBean.getPlaces()){
            new PlaceSolrCreator().create(doc, place);
        }
        doc.addField(EdmLabel.EUROPEANA_COMPLETENESS.toString(), fBean.getEuropeanaCompleteness());
        doc.addField(EdmLabel.EUROPEANA_COLLECTIONNAME.toString(),fBean.getEuropeanaCollectionName()[0]);
        doc.addField("timestamp_created",fBean.getTimestampCreated());
        doc.addField("timestamp_update", fBean.getTimestampUpdated());
        
        try {
            solrServer.add(doc);
        } catch (SolrServerException ex) {
            Logger.getLogger(SolrDocumentHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SolrDocumentHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
