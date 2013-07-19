/*
 * Copyright 2007-2012 The Europeana Foundation
 *
 *  Licenced under the EUPL, Version 1.1 (the "Licence") and subsequent versions as approved
 *  by the European Commission;
 *  You may not use this work except in compliance with the Licence.
 * 
 *  You may obtain a copy of the Licence at:
 *  http://joinup.ec.europa.eu/software/page/eupl
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under
 *  the Licence is distributed on an "AS IS" basis, without warranties or conditions of
 *  any kind, either express or implied.
 *  See the Licence for the specific language governing permissions and limitations under
 *  the Licence.
 */
package eu.europeana.corelib.solr.test.importer;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.common.SolrInputDocument;
import org.easymock.EasyMock;
import org.junit.Test;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Key;
import com.google.code.morphia.query.Query;

import eu.europeana.corelib.definitions.jibx.ProvidedCHOType;
import eu.europeana.corelib.definitions.jibx.SameAs;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.solr.entity.ProvidedCHOImpl;
import eu.europeana.corelib.solr.server.EdmMongoServer;
import eu.europeana.corelib.solr.server.importer.util.ProvidedCHOFieldInput;

/**
 * ProvidedCHO Field Input creator
 * @author Yoregos.Mamakis@ kb.nl
 *
 */
public class ProvidedCHOFieldInputTest {

	private EdmMongoServer mongoServer;

	@Test
	public void testProvidedCHO() {
		ProvidedCHOImpl providedCHOImpl = new ProvidedCHOImpl();
		providedCHOImpl.setAbout("test about");
		ProvidedCHOType providedCHO = new ProvidedCHOType();
		providedCHO.setAbout(providedCHOImpl.getAbout());

		mongoServer = EasyMock.createMock(EdmMongoServer.class);
		Datastore ds = EasyMock.createMock(Datastore.class);
		Query query = EasyMock.createMock(Query.class);
		Key<ProvidedCHOImpl> key = EasyMock.createMock(Key.class);
		EasyMock.expect(mongoServer.getDatastore()).andReturn(ds);
		EasyMock.expect(ds.find(ProvidedCHOImpl.class)).andReturn(query);
		EasyMock.expect(query.filter("about", "/item" + providedCHO.getAbout()))
				.andReturn(query);
		EasyMock.expect(query.get()).andReturn(null);
		EasyMock.expect(mongoServer.getDatastore()).andReturn(ds);
		providedCHOImpl.setAbout("/itemtest about");
		EasyMock.expect(ds.save(providedCHOImpl)).andReturn(key);

		EasyMock.replay(query, ds, mongoServer);
		List<SameAs> sameAsList = new ArrayList<SameAs>();
		SameAs sameAs = new SameAs();
		sameAs.setResource("test same as");
		sameAsList.add(sameAs);
		providedCHO.setSameAList(sameAsList);

		// create mongo providedCHO
		try {
			ProvidedCHOImpl providedCHOMongo = new ProvidedCHOFieldInput()
					.createProvidedCHOMongoFields(providedCHO, mongoServer);
			assertEquals("/item" + providedCHO.getAbout(),
					providedCHOMongo.getAbout());
			assertEquals(providedCHO.getSameAList().get(0).getResource(),
					providedCHOMongo.getOwlSameAs()[0]);

			SolrInputDocument solrDocument = new SolrInputDocument();
			solrDocument = new ProvidedCHOFieldInput().createProvidedCHOFields(
					providedCHO, solrDocument);
			assertEquals(
					providedCHO.getAbout(),
					solrDocument.getFieldValue(EdmLabel.EUROPEANA_ID.toString()));

			assertEquals(providedCHO.getSameAList().get(0).getResource(),
					solrDocument.getFieldValue(EdmLabel.PROXY_OWL_SAMEAS
							.toString()));
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
