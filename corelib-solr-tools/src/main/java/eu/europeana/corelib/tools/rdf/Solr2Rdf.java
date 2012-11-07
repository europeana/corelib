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
package eu.europeana.corelib.tools.rdf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
import org.jibx.runtime.JiBXException;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;
/**
 * A converter from a Solr Document to RDF
 * @author Yorgos.Mamakis@ kb.nl
 *
 */
public class Solr2Rdf {
	ByteArrayOutputStream out;
	StreamResult streamResult;
	ContentHandler hd;

	/**
	 * Initialize the xml document
	 * @throws TransformerConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void initialize() throws TransformerConfigurationException,
			SAXException, IOException {
		out = new ByteArrayOutputStream();
		OutputFormat of = new OutputFormat("XML", "UTF-8", true);
		of.setIndent(1);
		of.setIndenting(true);
		XMLSerializer serializer = new XMLSerializer(out, of);
		hd = serializer.asContentHandler();
		hd.startDocument();
		AttributesImpl atts = new AttributesImpl();
		hd.startElement("", "", "record", atts);
	}

	/**
	 * convert a solr document to xml and append the map values
	 * @param solrDocument
	 * @param map
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 * @throws TransformerException
	 * @throws JiBXException
	 */
	public String constructFromMap(SolrInputDocument solrDocument,
			Map<String, List<String>> map) throws SAXException, IOException,
			TransformerException, JiBXException {

		createXMLFromSolr(solrDocument);
		Map<String, List<String>> synchronizedMap = Collections
				.synchronizedMap(map);
		for (Entry<String, List<String>> mapField : synchronizedMap.entrySet()) {
			for (String str : mapField.getValue()) {
				createXMLElement(mapField.getKey(), (String) str);
			}
		}
		serializeDoc();
		return transformDoc();
	}

	private String transformDoc() throws TransformerException, JiBXException,
			IOException {
		String output = out.toString("UTF-8");
		System.out.println(output);
		ByteArrayOutputStream transformedOutput = new ByteArrayOutputStream();
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transform = tFactory.newTransformer(new StreamSource(
				"src/main/resources/convertToEdm.xsl"));
		transform.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transform.setOutputProperty(OutputKeys.INDENT, "yes");
		transform.transform(new SAXSource(new InputSource(
				new ByteArrayInputStream(output.getBytes()))),
				new StreamResult(transformedOutput));
		transformedOutput.flush();
		String edmString = transformedOutput.toString("UTF-8");
		transformedOutput.close();

		out.close();

		System.out.println(edmString);
		return edmString;
	}

	private void serializeDoc() throws SAXException, IOException {
		hd.endElement("", "", "record");
	}

	/**
	 * Construct an XML string from a solrDocument
	 * @param solrDocument
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 * @throws TransformerException
	 * @throws JiBXException
	 */
	public String constructFromSolrDocument(SolrInputDocument solrDocument)
			throws SAXException, IOException, TransformerException,
			JiBXException {

		createXMLFromSolr(solrDocument);
		serializeDoc();
		return transformDoc();
	}

	private void createXMLFromSolr(SolrInputDocument solrDocument)
			throws SAXException {
		for (Entry<String, SolrInputField> solrField : solrDocument.entrySet()) {
			if (solrField.getValue() != null
					&& solrField.getValue().getValues() != null) {
				for (Object str : solrField.getValue().getValues().toArray()) {
					if (str instanceof String) {
						createXMLElement(solrField.getKey(), (String) str);
					} else if (str instanceof Boolean) {
						createXMLElement(solrField.getKey(),
								Boolean.toString((Boolean) str));
					}
				}
			}
		}
	}

	private void createXMLElement(String name, String value)
			throws SAXException {
		if (!StringUtils.equals(name, "original")) {
			AttributesImpl atts = new AttributesImpl();
			String[] elems = StringUtils.split(name, ".");
			if (elems.length > 1) {
				atts.addAttribute("", "", "xml:lang", "CDATA", elems[1]);
			}
			hd.startElement("", "", elems[0], atts);
			hd.characters(value.toCharArray(), 0, value.length());
			hd.endElement("", "", name);
		}
	}
}
