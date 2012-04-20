package eu.europeana.corelib.solr.denormalization.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.IOUtils;

import com.ctc.wstx.stax.WstxInputFactory;

import eu.europeana.corelib.solr.server.MongoDBServer;

/**
 * Denormalization Utility. It retrieves the description of a reference URI according to the stored mappings
 * @author Yorgos.Mamakis@ kb.nl
 *
 */
public class Extractor {
	@Resource(name = "corelib_solr_mongoServer")
	private MongoDBServer mongoServer;

	/**
	 * Constructor for use with object injection
	 */
	public Extractor(){
		
	}
	
	/**
	 * Constructor with the MongoDBServer for use without object Injection
	 * @param server
	 */
	public Extractor(MongoDBServer server){
		this.mongoServer = server;
	}
	/**
	 * Return the stored controlled vocabulary from its URI
	 * @param URI The URI to search with
	 * @return The stored ControlledVocabulary to be used
	 */
	public ControlledVocabularyImpl getControlledVocabulary(String URI) {
		return mongoServer.getDatastore().find(ControlledVocabularyImpl.class)
				.filter("URI", URI).get();
	}

	/**
	 * Retrieve all the stored controlled vocabularies
	 * @return A list with all the stored controlled vocabularies
	 */
	public List<ControlledVocabularyImpl> getControlledVocabularies(){
		return mongoServer.getDatastore().find(ControlledVocabularyImpl.class).asList();
	}
	/**
	 * Denormalization method
	 * @param resource The URI to retrieve the denormalized information from
	 * @param controlledVocabulary The controlled vocabulary holding the mappings
	 * @return A List of pairs of strings representing the <Europeana Mapped Field , Denormalized values> 
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public List<List<String>> denormalize(String resource,
			ControlledVocabularyImpl controlledVocabulary) throws MalformedURLException, IOException {
		List<List<String>> denormalizedValues = new ArrayList<List<String>>();
		String suffix = controlledVocabulary.getSuffix();
		String xmlString = retrieveValueFromResource(resource + suffix != null ? suffix
				: "");
		XMLInputFactory inFactory = new WstxInputFactory();
		Source source;
		try {
			source = new StreamSource(new ByteArrayInputStream(
					xmlString.getBytes()), "UTF-8");
			XMLStreamReader xml = inFactory.createXMLStreamReader(source);
			String element="";
			boolean mapped = false;
			while (xml.hasNext()) {
				List<String> tempList = new ArrayList<String>();
				switch (xml.getEventType()) {
				case XMLStreamConstants.START_DOCUMENT:

					break;
				case XMLStreamConstants.START_ELEMENT:
					 element = xml.getPrefix() + ":"+ xml.getLocalName();
					 
					if (controlledVocabulary.isMapped(element)) {
						if(xml.getAttributeCount()>0){
							String attribute = xml.getAttributePrefix(0)+":"+xml.getAttributeLocalName(0);
							if(controlledVocabulary.isMapped(element+"_"+attribute)){
								mapped = false;
								tempList.add(controlledVocabulary.getEdmLabel(attribute).toString());
								tempList.add(xml.getAttributeValue(0));
								denormalizedValues.add(tempList);
								tempList= new ArrayList<String>();
							}
							xml.next();
						}
						else
						{
							tempList.add(controlledVocabulary.getEdmLabel(element).toString());
							tempList.add(xml.getElementText());
							mapped = true;
							denormalizedValues.add(tempList);
							tempList= new ArrayList<String>();
						}
					}
					break;
				case XMLStreamConstants.CHARACTERS:
					if(!mapped){
						tempList.add(controlledVocabulary.getEdmLabel(element).toString());
						tempList.add(xml.getElementText());
						denormalizedValues.add(tempList);
						tempList= new ArrayList<String>();
					}
				}
				xml.next();
			}
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return denormalizedValues;
	}
/**
 * Retrieve an XML string from a ControlledVocabulary
 * @param resource - The name of the resource to retrieve
 * @return An XML string representing the RDF/XML resource from an online Controlled Vocabulary
 * @throws MalformedURLException
 * @throws IOException
 */
	private String retrieveValueFromResource(String resource) throws MalformedURLException, IOException {
		URLConnection urlConnection = new URL(resource).openConnection();
		InputStream inputStream = urlConnection.getInputStream();
		StringWriter writer = new StringWriter();
		IOUtils.copy(inputStream, writer, "UTF-8");;
		return writer.toString();
	}
}
