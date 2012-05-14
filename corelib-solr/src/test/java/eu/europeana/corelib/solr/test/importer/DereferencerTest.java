package eu.europeana.corelib.solr.test.importer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

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

	String URI;
	private static String CONTRIBUTOR = "CONTIBUTOR";
	private static String PUBLISHER = "PUBLISHER";
	private static String ALTERNATIVE = "ALTERNATIVE";

	@Test
	public void testDereference() {
		ControlledVocabularyImpl controlledVocabulary = new ControlledVocabularyImpl();
		controlledVocabulary.setName("dereferencetest");
		controlledVocabulary.setLocation("location");
		controlledVocabulary.setSuffix(".xml");
		String workingDir = StringUtils.endsWith(
				System.getProperty("user.dir"), "corelib") ? System
				.getProperty("user.dir") + "/corelib-solr" : System
				.getProperty("user.dir");
		URI = "file://" + workingDir + "/src/test/resources/test_files";
		controlledVocabulary.setURI("file://" + workingDir
				+ "/src/test/resources/test_files");
		Extractor extractor = new Extractor(controlledVocabulary,
				vocabularyMongoDBServer);
		try {
			extractor.readSchema(workingDir
					+ "/src/test/resources/test_files/sample.xml");
		} catch (FileNotFoundException g) {
			g.printStackTrace();
		}

		extractor.setMappedField("test_contributor", EdmLabel.DC_CONTRIBUTOR);
		extractor.setMappedField("test_publisher", EdmLabel.DC_PUBLISHER);
		extractor.setMappedField("test_created", EdmLabel.DCTERMS_CREATED);
		extractor.setMappedField("test_alternative_rdf:resource",
				EdmLabel.DCTERMS_IS_REFERENCED_BY);
		extractor.saveMapping();
		MongoConstructor mongoConstructor = new MongoConstructor();
		mongoConstructor.setMongoServer(mongoDBServer);
		fixFile(new File(workingDir + "/src/test/resources/test_files/edm.xml"));
		try {
			IBindingFactory bfact = BindingDirectory.getFactory(RDF.class);
			IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
			RDF rdf = (RDF) uctx.unmarshalDocument(new FileInputStream(
					new File(workingDir
							+ "/src/test/resources/test_files/edm.xml")), null);
			FullBeanImpl fullBean = mongoConstructor.constructFullBean(rdf);

			mongoDBServer.getDatastore().save(fullBean);
			SolrInputDocument solrDocument = SolrConstructor
					.constructSolrDocument(rdf);
			solrServer.add(solrDocument);
			solrServer.commit();
			solrServer.optimize();
			assertEquals(2, fullBean.getDcPublisher().length);
			assertEquals(2, fullBean.getDcContributor().length);
			assertTrue(contains(fullBean.getDcPublisher(), PUBLISHER));
			assertTrue(contains(fullBean.getDcContributor(), CONTRIBUTOR));
			assertEquals(fullBean.getDctermsIsReferencedBy().length, 2);
			assertTrue(contains(fullBean.getDctermsIsReferencedBy(),
					ALTERNATIVE));
		} catch (Exception e) {
			e.printStackTrace();
		}
		// export the output and compare fields

	}

	private void fixFile(File file) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			Document doc = dbf.newDocumentBuilder().parse(file);
			Element root = doc.getDocumentElement();
			NodeList list = root.getElementsByTagName("dc:contributor");
			Text URIText = doc.createTextNode(URI+"/sample");
			Node child = list.item(0).getFirstChild();
				if(child!=null){
					list.item(0).removeChild(child);
				}
				list.item(0).appendChild(URIText);
				
				
			
			NodeList list2 = root.getElementsByTagName("dc:publisher");
			Node child2 = list2.item(0).getFirstChild();
			Text URIText2 = doc.createTextNode(URI+"/sample");
			if(child2!=null){
				list2.item(0).removeChild(child2);
			}
				list2.item(0).appendChild(URIText2);
			NodeList list3 = root.getElementsByTagName("dcterms:isReferencedBy");
			NamedNodeMap map = list3.item(0).getAttributes();
			Node resource = map.getNamedItem("rdf:resource");
			resource.setNodeValue(URI+"/sample");
			map.setNamedItem(resource);
			  TransformerFactory tranFactory = TransformerFactory.newInstance();  
			  Transformer aTransformer = tranFactory.newTransformer();  
			  Source src = new DOMSource(doc);  
			  Result dest = new StreamResult(file);
			  aTransformer.transform(src, dest);
			 
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private boolean contains(String[] array, String value) {
		for (String string : array) {
			if (StringUtils.equals(string, value)) {
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
