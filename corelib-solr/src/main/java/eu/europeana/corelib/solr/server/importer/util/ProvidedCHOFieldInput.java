package eu.europeana.corelib.solr.server.importer.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.common.SolrInputDocument;

import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.definitions.solr.entity.ProvidedCHO;
import eu.europeana.corelib.solr.entity.ProvidedCHOImpl;
import eu.europeana.corelib.solr.server.MongoDBServer;
import eu.europeana.corelib.definitions.jibx.ProvidedCHOType;
import eu.europeana.corelib.definitions.jibx.SameAs;

public class ProvidedCHOFieldInput {
	public static SolrInputDocument createProvidedCHOFields(ProvidedCHOType providedCHO, SolrInputDocument solrInputDocument){
		solrInputDocument.addField(EdmLabel.EUROPEANA_ID.toString(),providedCHO.getAbout());
		for (SameAs sameAs:providedCHO.getSameAList()){
			solrInputDocument.addField(EdmLabel.OWL_SAMEAS.toString(), sameAs.toString());
		}
		solrInputDocument.addField(EdmLabel.EDM_IS_NEXT_IN_SEQUENCE.toString(), providedCHO.getIsNextInSequence().getResource());
		
		return solrInputDocument;
	}

	public static List<? extends ProvidedCHO> createProvidedCHOMongoFields(
			ProvidedCHOType providedCHO, MongoDBServer mongoServer, boolean action) {
		if(action){
			//TODO: Upgrade in Mongo
			return null;
		}
		else{
			List<ProvidedCHO> providedCHOList = new ArrayList<ProvidedCHO>();
			ProvidedCHO mongoProvidedCHO = new ProvidedCHOImpl();
			mongoProvidedCHO.setEdmIsNextInSequence(providedCHO.getIsNextInSequence().toString());
			List<String> owlSameAsList = new ArrayList<String>();
			for(SameAs sameAs : providedCHO.getSameAList()){
				owlSameAsList.add(sameAs.getResource());
			}
			mongoProvidedCHO.setOwlSameAs(owlSameAsList.toArray(new String[owlSameAsList.size()]));
			mongoServer.getDatastore().save(mongoProvidedCHO);
			return providedCHOList;
		}
	}
}
