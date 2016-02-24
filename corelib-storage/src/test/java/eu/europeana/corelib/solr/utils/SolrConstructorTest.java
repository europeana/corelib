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
package eu.europeana.corelib.solr.utils;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import org.apache.commons.io.FileUtils;
import org.apache.solr.common.SolrInputDocument;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;
import org.junit.Test;

import eu.europeana.corelib.definitions.jibx.RDF;
import eu.europeana.corelib.edm.utils.SolrConstructor;

/**
 * Solr Constructor Unit tests
 * @author Yorgos.Mamakis@ kb.nl
 *
 */
public class SolrConstructorTest {
	
	@Test
	public void test(){
		IBindingFactory bfact;
		try {
			bfact = BindingDirectory.getFactory(RDF.class);
			IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
			RDF rdf = (RDF) uctx.unmarshalDocument(new StringReader(FileUtils.readFileToString(new File(
					"../corelib-search/src/test/resources/test_files/edm_new.xml"))));
			SolrInputDocument doc = new SolrConstructor().constructSolrDocument(rdf);
			assertNotNull(doc);
			assertTrue(doc.containsKey("europeana_id"));
		} catch (JiBXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
