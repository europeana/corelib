package eu.europeana.corelib.solr.server.importer.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.common.SolrInputDocument;

import eu.europeana.corelib.definitions.jibx.ProvidedCHOType;
import eu.europeana.corelib.definitions.jibx.ResourceType;
import eu.europeana.corelib.definitions.jibx.SameAs;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.solr.entity.ProvidedCHOImpl;
import eu.europeana.corelib.solr.server.MongoDBServer;
import eu.europeana.corelib.solr.utils.SolrUtil;

public class ProvidedCHOFieldInput {
	public static SolrInputDocument createProvidedCHOFields(
			ProvidedCHOType providedCHO, SolrInputDocument solrInputDocument) throws InstantiationException, IllegalAccessException {
		solrInputDocument.addField(EdmLabel.EUROPEANA_ID.toString(),
				providedCHO.getAbout());
		if(providedCHO.getSameAList()!=null){
		for (SameAs sameAs : providedCHO.getSameAList()) {
			solrInputDocument.addField(EdmLabel.OWL_SAMEAS.toString(),
					sameAs.toString());
		}
		}
		solrInputDocument.addField(EdmLabel.EDM_IS_NEXT_IN_SEQUENCE.toString(),
				SolrUtil.exists(ResourceType.class,providedCHO.getIsNextInSequence()).getResource());

		return solrInputDocument;
	}

	public static ProvidedCHOImpl createProvidedCHOMongoFields(
			ProvidedCHOType providedCHO, MongoDBServer mongoServer,
			boolean action) throws InstantiationException,
			IllegalAccessException {
		if (action) {
			// TODO: Upgrade in Mongo
			return null;
		} else {

			ProvidedCHOImpl mongoProvidedCHO = new ProvidedCHOImpl();
			mongoProvidedCHO.setAbout(providedCHO.getAbout());

			mongoProvidedCHO.setEdmIsNextInSequence(SolrUtil.exists(
					ResourceType.class, providedCHO.getIsNextInSequence())
					.getResource());
			List<String> owlSameAsList = new ArrayList<String>();
			if (providedCHO.getSameAList() != null) {
				for (SameAs sameAs : providedCHO.getSameAList()) {
					owlSameAsList.add(sameAs.getResource());
				}
				mongoProvidedCHO.setOwlSameAs(owlSameAsList
						.toArray(new String[owlSameAsList.size()]));
			}
			mongoServer.getDatastore().save(mongoProvidedCHO);
			return mongoProvidedCHO;
		}
	}
}
