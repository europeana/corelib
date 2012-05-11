package eu.europeana.corelib.solr.test.importer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IUnmarshallingContext;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.europeana.corelib.definitions.jibx.RDF;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.dereference.impl.ControlledVocabularyImpl;
import eu.europeana.corelib.dereference.impl.Extractor;
import eu.europeana.corelib.dereference.impl.VocabularyMongoServer;
import eu.europeana.corelib.solr.MongoServer;
import eu.europeana.corelib.solr.bean.impl.FullBeanImpl;
import eu.europeana.corelib.solr.utils.MongoConstructor;
import eu.europeana.corelib.solr.utils.SolrConstructor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/corelib-solr-context.xml", "/corelib-solr-test.xml" })
public class DereferencerTest {
	@Resource(name = "corelib_solr_mongoServer")
	MongoServer mongoDBServer;
	@Resource(name = "corelib_solr_solrEmbedded")
	SolrServer solrServer;
	@Resource(name = "corelib_solr_vocabularyMongoServer")
	VocabularyMongoServer vocabularyMongoDBServer;

	private static String CONTRIBUTOR = "CONTIBUTOR";
	private static String PUBLISHER = "PUBLISHER";
	private static String ALTERNATIVE = "ALTERNATIVE";
	@Test
	public void testDereference() {
		ControlledVocabularyImpl controlledVocabulary = new ControlledVocabularyImpl();
		controlledVocabulary.setName("dereferencetest");
		controlledVocabulary.setLocation("location");
		controlledVocabulary.setSuffix(".xml");
		controlledVocabulary.setURI("file:///home/gmamakis/test");
		Extractor extractor = new Extractor(controlledVocabulary,
				vocabularyMongoDBServer);
		assertNotNull(extractor);
		extractor.readSchema("/home/gmamakis/test/sample.xml");
		extractor.setMappedField("test_contributor", EdmLabel.DC_CONTRIBUTOR);
		extractor.setMappedField("test_publisher", EdmLabel.DC_PUBLISHER);
		extractor.setMappedField("test_created", EdmLabel.DCTERMS_CREATED);
		extractor.setMappedField("test_alternative_rdf:resource", EdmLabel.DCTERMS_IS_REFERENCED_BY);
		extractor.saveMapping();
		MongoConstructor mongoConstructor = new MongoConstructor();
		mongoConstructor.setMongoServer(mongoDBServer);
		try {
			IBindingFactory bfact = BindingDirectory.getFactory(RDF.class);
			IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
			RDF rdf = (RDF) uctx.unmarshalDocument(new FileInputStream(
					new File("/home/gmamakis/test/edm.xml")), null);
			FullBeanImpl fullBean = mongoConstructor.constructFullBean(rdf);
			mongoDBServer.getDatastore().save(fullBean);
			SolrInputDocument solrDocument = SolrConstructor
					.constructSolrDocument(rdf);
			solrServer.add(solrDocument);
			solrServer.commit();
			solrServer.optimize();
			assertEquals(fullBean.getDcPublisher().length,2);
			assertEquals(fullBean.getDcContributor().length,2);			
			assertTrue(contains(fullBean.getDcPublisher(),PUBLISHER));
			assertTrue(contains(fullBean.getDcContributor(),CONTRIBUTOR));
			assertEquals(fullBean.getDctermsIsReferencedBy().length,2);
			assertTrue(contains(fullBean.getDctermsIsReferencedBy(),ALTERNATIVE));
		} catch (Exception e) {
			e.printStackTrace();
		}
		// export the output and compare fields

	}

	private boolean contains(String[] array,String value) {
		for (String string : array){
			if (StringUtils.equals(string, value)){
				return true;
			}
		}
		return false;
	}

	@After
	public void cleanup() {
		mongoDBServer.getDatastore().getDB().dropDatabase();
		vocabularyMongoDBServer.getDatastore().getDB().dropDatabase();
	}
}
