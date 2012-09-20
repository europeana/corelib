package eu.europeana.corelib.dereference.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;

import com.ctc.wstx.stax.WstxInputFactory;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;

import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.dereference.ControlledVocabulary;
import eu.europeana.corelib.tools.AppContext;

/**
 * Denormalization Utility. It retrieves the description of a reference URI
 * according to the stored mappings
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public class Extractor {

	private VocabularyMongoServer mongoServer;
	private ControlledVocabulary vocabulary;
	private final static long UPDATETIMESTAMP = 5184000000l;

	/**
	 * Constructor for use with object injection
	 */
	public Extractor(ControlledVocabulary controlledVocabulary) {
		vocabulary = controlledVocabulary;
		ApplicationContext applicationContext = AppContext
				.getApplicationContext();
		mongoServer = (VocabularyMongoServer) applicationContext
				.getBean("corelib_solr_vocabularyMongoServer");
	}

	public Extractor() {

	}

	/**
	 * Constructor with the MongoDBServer for use without object Injection
	 * 
	 * @param server
	 */
	public Extractor(ControlledVocabulary controlledVocabulary,
			VocabularyMongoServer server) {
		vocabulary = controlledVocabulary;
		mongoServer = server;
	}

	/**
	 * Retrieve all the stored controlled vocabularies
	 * 
	 * @return A list with all the stored controlled vocabularies
	 */
	public List<ControlledVocabularyImpl> getControlledVocabularies() {
		return mongoServer.getDatastore().find(ControlledVocabularyImpl.class) != null ? mongoServer
				.getDatastore().find(ControlledVocabularyImpl.class).asList()
				: null;
	}

	/**
	 * Denormalization method
	 * 
	 * @param resource
	 *            The URI to retrieve the denormalized information from
	 * @param controlledVocabulary
	 *            The controlled vocabulary holding the mappings
	 * @return A List of pairs of strings representing the <Europeana Mapped
	 *         Field , Denormalized values>
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public Map<String, List<String>> denormalize(String resource,
			ControlledVocabularyImpl controlledVocabulary) {
		Map<String, List<String>> denormalizedValues = new HashMap<String, List<String>>();
		if (controlledVocabulary != null) {
			String suffix = controlledVocabulary.getSuffix() != null ? controlledVocabulary
					.getSuffix() : "";
			String xmlString = retrieveValueFromResource(resource + suffix != null ? resource
					+ suffix
					: "");
			XMLInputFactory inFactory = new WstxInputFactory();
			Source source;
			if (xmlString.length() > 0) {
				vocabulary = controlledVocabulary;
				try {
					source = new StreamSource(new ByteArrayInputStream(
							xmlString.getBytes()), "UTF-8");
					XMLStreamReader xml = inFactory
							.createXMLStreamReader(source);
					String element = "";
					while (xml.hasNext()) {
						List<String> tempList = new ArrayList<String>();
						switch (xml.getEventType()) {
						case XMLStreamConstants.START_DOCUMENT:

							break;
						case XMLStreamConstants.START_ELEMENT:
							element = (xml.getPrefix() != null ? xml
									.getPrefix() + ":" : "")
									+ xml.getLocalName();

							if (isMapped(element)) {
								if (xml.getAttributeCount() > 0) {
									String attribute = xml
											.getAttributePrefix(0)
											+ ":"
											+ xml.getAttributeLocalName(0);
									if (isMapped(element + "_" + attribute)) {
										if (!denormalizedValues
												.containsKey(element + "_"
														+ attribute)) {
											tempList.add(xml
													.getAttributeValue(0));
											denormalizedValues.put(
													getEdmLabel(element + "_"
															+ attribute),
													tempList);
										} else {
											tempList = denormalizedValues
													.get(getEdmLabel(element
															+ "_" + attribute));
											tempList.add(xml
													.getAttributeValue(0));
											denormalizedValues.put(
													getEdmLabel(element + "_"
															+ attribute),
													tempList);
										}

									} else {
										if (!denormalizedValues
												.containsKey(element)) {
											tempList.add(xml.getElementText());
											denormalizedValues.put(
													getEdmLabel(element),
													tempList);
											tempList = new ArrayList<String>();
										} else {
											tempList = denormalizedValues
													.get(getEdmLabel(element));
											denormalizedValues.put(
													getEdmLabel(element),
													tempList);
										}
									}
								}
							}
							break;

						default:
							break;
						}
						xml.next();
					}
				} catch (XMLStreamException e) {
					e.printStackTrace();
				}
			}
		}
		return denormalizedValues;
	}

	/**
	 * Retrieve an XML string from a ControlledVocabulary
	 * 
	 * @param resource
	 *            - The name of the resource to retrieve
	 * @return An XML string representing the RDF/XML resource from an online
	 *         Controlled Vocabulary
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	private String retrieveValueFromResource(String resource) {

		EntityImpl entity = mongoServer.getDatastore().find(EntityImpl.class)
				.filter("uri", resource).get();
		if (entity == null) {
				String val = retrieveValue(resource);
				if (val.length()>0){
				EntityImpl newEntity = new EntityImpl();
				newEntity.setUri(resource);
				newEntity.setTimestamp(new Date().getTime());
				newEntity.setContent(val);
				mongoServer.getDatastore().save(newEntity);
				}
				return val;
		}
		else {
			if(new Date().getTime()-entity.getTimestamp()<UPDATETIMESTAMP){
				return entity.getContent();
			}
			else {
				String val = retrieveValue(resource);
				Query<EntityImpl> updateQuery = mongoServer.getDatastore()
						.createQuery(EntityImpl.class).field("uri").equal(resource);
				UpdateOperations<EntityImpl> ops =mongoServer.getDatastore().createUpdateOperations(EntityImpl.class)
						.set("content", val);
				mongoServer.getDatastore().update(updateQuery, ops);
				return val;
			}
		}
	}

	private String retrieveValue(String resource) {
		URLConnection urlConnection;
		try {

			urlConnection = new URL(resource).openConnection();

			InputStream inputStream = urlConnection.getInputStream();
			StringWriter writer = new StringWriter();
			IOUtils.copy(inputStream, writer, "UTF-8");
			return writer.toString();
		} catch (MalformedURLException e) {
			return "";
		} catch (IOException e) {
			return "";
		}
	}

	/**
	 * Retrieve the value of a field. The value retrieved is part of the
	 * EdmLabel enumeration
	 * 
	 * @param field
	 *            The field to check
	 * @return The EdmLabel the field has been mapped to
	 */
	public String getEdmLabel(String field) {

		return vocabulary != null ? vocabulary.getElements().get(field)
				.toString() : EdmLabel.NULL.toString();
	}

	/**
	 * Map a field to an EdmLabel. Short for <code>
	 * Map<String,EdmLabel> mappings = new HashMap<String,EdmLabel>();
	 * mappings.put(field,edmLabel);
	 * vocabulary.setElements(mappings);
	 * </code>
	 * 
	 * @param fieldToMap
	 * @param europeanaField
	 */
	public void setMappedField(String fieldToMap, EdmLabel europeanaField) {
		HashMap<String, EdmLabel> elements = vocabulary.getElements() != null ? vocabulary
				.getElements() : new HashMap<String, EdmLabel>();
		elements.put(fieldToMap, europeanaField);
		vocabulary.setElements(elements);

	}

	public String getMappedField(EdmLabel europeanaField) {
		for (String key : vocabulary.getElements().keySet()) {
			if (europeanaField.equals(vocabulary.getElements().get(key))) {
				return key;
			}
		}
		return null;
	}

	public boolean isMapped(String field) {

		if (vocabulary != null) {
			for (Entry<String, EdmLabel> entry : vocabulary.getElements()
					.entrySet()) {
				if (StringUtils.contains(entry.getKey(), field)
						&& !entry.getValue().equals(EdmLabel.NULL)) {
					return true;
				}
			}
		}
		return false;
	}

	public HashMap<String, EdmLabel> readSchema(String location) {

		vocabulary.setElements(readFromFile(location));
		return vocabulary.getElements();
	}

	public void removeVocabulary(String vocabularyName) {

		mongoServer.getDatastore().delete(
				ControlledVocabularyImpl.class,
				mongoServer.getDatastore().find(ControlledVocabularyImpl.class)
						.filter("name", vocabularyName).get().getId());
	}

	public void saveMapping() {
		if (vocabulary != null) {
			mongoServer.getDatastore().save(vocabulary);
		}
	}

	private HashMap<String, EdmLabel> readFromFile(String localLocation) {
		HashMap<String, EdmLabel> elements = new HashMap<String, EdmLabel>();
		XMLInputFactory inFactory = new WstxInputFactory();
		Source source;
		try {
			source = new StreamSource(new FileInputStream(new File(
					localLocation)), "UTF-8");
			XMLStreamReader xml = inFactory.createXMLStreamReader(source);
			String element = "";
			String attribute = "";
			while (xml.hasNext()) {
				switch (xml.getEventType()) {
				case XMLStreamConstants.START_DOCUMENT:
					xml.next();
					break;
				case XMLStreamConstants.START_ELEMENT:
					element = (xml.getName().getPrefix().length() > 0 ? xml
							.getName().getPrefix() + ":" : "")
							+ xml.getName().getLocalPart();
					elements.put(element, EdmLabel.NULL);
					int i = 0;
					while (i < xml.getAttributeCount()) {
						attribute = element + "_" + xml.getAttributePrefix(i)
								+ ":" + xml.getAttributeLocalName(i);
						elements.put(attribute, EdmLabel.NULL);
						i++;
					}
					xml.next();
					break;
				default:
					xml.next();
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {

			e.printStackTrace();
		}
		return elements;
	}
}
