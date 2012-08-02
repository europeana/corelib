package eu.europeana.corelib.tools.rdf;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.solr.common.SolrInputDocument;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;
import org.junit.Test;
import org.xml.sax.SAXException;

import eu.europeana.corelib.definitions.jibx.EdmType;
import eu.europeana.corelib.definitions.jibx.RDF;
import eu.europeana.corelib.definitions.model.EdmLabel;

public class Solr2RdfTest {
	Solr2Rdf solr2rdf;
	
	{
		solr2rdf = new Solr2Rdf();
		try {
			solr2rdf.initialize();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testConstructFromSolr(){
		assertNotNull(solr2rdf);
		SolrInputDocument solrDocument = createSolrDocument();
		try {
			IBindingFactory bfact = BindingDirectory.getFactory(RDF.class);

			IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
			RDF rdf = (RDF) uctx.unmarshalDocument(new StringReader(solr2rdf.constructFromSolrDocument(solrDocument)));
			assertNotNull(rdf);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JiBXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private SolrInputDocument createSolrDocument() {
		SolrInputDocument solrDocument = new SolrInputDocument();
		for(EdmLabel edmLabel:EdmLabel.values()){
			if(edmLabel.equals(EdmLabel.EDM_ISEUROPEANA_PROXY)){
				solrDocument.addField(edmLabel.toString(), true);
			}
			else if(edmLabel.equals(EdmLabel.PROVIDER_EDM_TYPE)){
				solrDocument.addField(edmLabel.toString(), EdmType.SOUND.toString());
			}
			else {
				solrDocument.addField(edmLabel.toString(), "http://fdffdfdfd");
			}
		}
		return solrDocument;
	}

	@Test
	public void testConstructFromMap(){
		assertNotNull(solr2rdf);
		SolrInputDocument solrDocument = createSolrDocument();
		Map<String,List<String>> testMap = createMap();
		try {
			IBindingFactory bfact = BindingDirectory.getFactory(RDF.class);

			IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
			RDF rdf = (RDF) uctx.unmarshalDocument(new StringReader(solr2rdf.constructFromMap(solrDocument,testMap)));
			assertNotNull(rdf);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JiBXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Map<String, List<String>> createMap() {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> arrList = new ArrayList<String>();
		arrList.add("hasVersion");
		map.put(EdmLabel.PROXY_DCTERMS_HAS_VERSION.toString(), arrList);
		map.put(EdmLabel.AG_RDAGR2_BIOGRAPHICALINFORMATION.toString(), arrList);
		map.put(EdmLabel.AG_SKOS_PREF_LABEL.toString()+".en", arrList);
		return map;
	}
}
