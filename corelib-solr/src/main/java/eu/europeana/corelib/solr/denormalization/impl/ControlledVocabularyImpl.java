package eu.europeana.corelib.solr.denormalization.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.bson.types.ObjectId;

import com.ctc.wstx.stax.WstxInputFactory;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;

import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.solr.denormalization.ControlledVocabulary;

@Entity("ControlledVocabulary")
public class ControlledVocabularyImpl implements ControlledVocabulary {

	@Id
	ObjectId id;
	
	private String URI;
	@Indexed(unique = true)
	private String name;
	private String location;

	// Used to denote special characteristics of the Resource. For example the
	// Geonames always point to URI/ResourceCode/about.rdf
	// rather than URI/ResourceCode which redirects to the Geonames website.
	private String suffix;
	private Map<String, EdmLabel> elements;

	

	public ControlledVocabularyImpl(String name) {
		super();
		this.name = name;
		
	}

	
	@Override
	public String getLocation() {
		return location;
	}

	@Override
	public void setLocation(String location) {
		this.location = location;
	}
	@Override
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	@Override
	public String getSuffix() {
		return this.suffix;
	}

	@Override
	public void setURI(String URI) {
		this.URI = URI;
	}

	@Override
	public String getURI() {
		return this.URI;
	}

	@Override
	public Map<String, EdmLabel> getElements() {
		return this.elements;
	}

	@Override
	public boolean isMapped(String field) {
		return this.elements.containsKey(field);
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name){
		this.name = name;
	}
	@Override
	public EdmLabel getEdmLabel(String field) {
		return this.elements.get(field);
	}

	@Override
	public void setMappedField(String fieldToMap, EdmLabel europeanaField) {
		this.elements.put(fieldToMap, europeanaField);

	}

	@Override
	public String getMappedField(EdmLabel europeanaField) {
		for (String key : elements.keySet()) {
			if (europeanaField.equals(elements.get(key))) {
				return key;
			}
		}
		return null;
	}

	@Override
	public Map<String, EdmLabel> readSchema(String location) {
		return readFromFile(location);
	}


	private Map<String, EdmLabel> readFromFile(String localLocation) {

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
					element = xml.getName().getPrefix() + ":"
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
				}
			}

		} catch (FileNotFoundException e) {
			// Should never happen
			e.printStackTrace();
		} catch (XMLStreamException e) {
			
			e.printStackTrace();
		}
		return this.elements;
	}

	
}
