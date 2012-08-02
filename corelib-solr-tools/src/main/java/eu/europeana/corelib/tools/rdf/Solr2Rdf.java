package eu.europeana.corelib.tools.rdf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.SolrInputField;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import eu.europeana.corelib.definitions.jibx.RDF;

public class Solr2Rdf {
	ByteArrayOutputStream out;
	StreamResult streamResult;
	ContentHandler hd;
	public void initialize() throws TransformerConfigurationException,
			SAXException, IOException {
		out = new ByteArrayOutputStream();
		OutputFormat of = new OutputFormat("XML","UTF-8",true);
		of.setIndent(1);
		of.setIndenting(true);
		XMLSerializer serializer = new XMLSerializer(out,of);
		hd = serializer.asContentHandler();
		hd.startDocument();
		AttributesImpl atts = new AttributesImpl();
		hd.startElement("", "", "record", atts);
	}

	public RDF constructFromMap(SolrInputDocument solrDocument,
			Map<String, List<String>> map) throws SAXException, IOException,
			TransformerException, JiBXException {

		createXMLFromSolr(solrDocument);
		Map<String,List<String>> synchronizedMap = Collections.synchronizedMap(map);
		for (Entry<String, List<String>> mapField : synchronizedMap.entrySet()) {
			for (String str : mapField.getValue()) {
				createXMLElement(mapField.getKey(), (String) str);
			}
		}
		serializeDoc();
		return transformDoc();
	}

	private RDF transformDoc() throws TransformerException, JiBXException, IOException {
		String output = out.toString("UTF-8");
		System.out.println(output);
		
		ByteArrayOutputStream transformedOutput = new ByteArrayOutputStream();
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transform = tFactory.newTransformer(new StreamSource("src/main/resources/convertToEdm.xsl"));
		transform.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transform.setOutputProperty(OutputKeys.INDENT, "yes");
		transform.transform(new SAXSource(new InputSource(new ByteArrayInputStream(output.getBytes()))),new StreamResult(transformedOutput));
		transformedOutput.flush();
		String edmString = transformedOutput.toString("UTF-8");
		transformedOutput.close();
		IBindingFactory bfact = BindingDirectory.getFactory(RDF.class);

		IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
		out.close();
		return (RDF) uctx.unmarshalDocument(new StringReader(edmString));
	}

	private void serializeDoc() throws SAXException, IOException {
		hd.endElement("", "", "record");
	}

	public RDF constructFromSolrDocument(SolrInputDocument solrDocument)
			throws SAXException, IOException, TransformerException, JiBXException {

		createXMLFromSolr(solrDocument);
		serializeDoc();
		return transformDoc();
	}


	private void createXMLFromSolr(SolrInputDocument solrDocument)
			throws SAXException {
		for (Entry<String, SolrInputField> solrField : solrDocument.entrySet()) {
			if(solrField.getValue()!=null && solrField.getValue().getValues()!=null){
			for (Object str : solrField.getValue().getValues().toArray()) {
				if(str instanceof String){
					createXMLElement(solrField.getKey(), (String) str);
				}
				else if (str instanceof Boolean){
					createXMLElement(solrField.getKey(),Boolean.toString((Boolean)str));
				}
			}
			}
		}
	}

	private void createXMLElement(String name, String value)
			throws SAXException {
		AttributesImpl atts = new AttributesImpl();
		String[] elems = StringUtils.split(name,".");
		if(elems.length>1){
			atts.addAttribute("", "", "xml:lang", "CDATA", elems[1]);
		}
		hd.startElement("", "", elems[0], atts);
		hd.characters(value.toCharArray(), 0, value.length());
		hd.endElement("", "", name);
		
	}
}
