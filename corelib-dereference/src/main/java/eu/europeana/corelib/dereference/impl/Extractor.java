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
package eu.europeana.corelib.dereference.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang.StringUtils;

import com.ctc.wstx.stax.WstxInputFactory;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import eu.europeana.corelib.definitions.ApplicationContextContainer;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.dereference.ControlledVocabulary;
import eu.europeana.corelib.dereference.VocabularyMongoServer;
import eu.europeana.corelib.dereference.exceptions.VocabularyNotFoundException;

/**
 * Denormalization Utility. It retrieves the description of a reference URI
 * according to the stored mappings
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public class Extractor {

	protected VocabularyMongoServer mongoServer;
	protected ControlledVocabularyImpl vocabulary;

	/**
	 * Constructor for use with object injection
	 * 
	 * @param controlledVocabulary
	 *            - The vocabulary to use
	 */
	public Extractor(ControlledVocabularyImpl controlledVocabulary) {
		vocabulary = controlledVocabulary;
		mongoServer = ApplicationContextContainer.getBean(
				VocabularyMongoServer.class,
				"corelib_solr_vocabularyMongoServer");
	}

	/**
	 * Default constructor
	 */
	public Extractor() {

	}

	/**
	 * Constructor with the MongoDBServer for use without object Injection
	 * 
	 * @param controlledvocabulary
	 *            - The vocabulary to use
	 * @param server
	 *            - The server to connect to
	 */
	public Extractor(ControlledVocabularyImpl controlledVocabulary,
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
	 * Append a value to a Concept field
	 * 
	 * @param val
	 *            - the value to append
	 * @param edmLabel
	 *            - the Solr field to append the value to
	 * @param concept
	 *            - the Concept object
	 * @return The concept
	 * 
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */

	/**
	 * Retrieve the value of a field. The value retrieved is part of the
	 * EdmLabel enumeration
	 * 
	 * @param field
	 *            The field to check
	 * @return The EdmLabel the field has been mapped to
	 */
	public List<EdmMappedField> getEdmLabel(String field) {

		return vocabulary != null ? vocabulary.getElements().get(field)
				: new ArrayList<EdmMappedField>();
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
	public void setMappedField(String fieldToMap, EdmLabel europeanaField,
			String attribute) {
		HashMap<String, List<EdmMappedField>> elements = vocabulary
				.getElements() != null ? (HashMap<String, List<EdmMappedField>>) vocabulary
				.getElements() : new HashMap<String, List<EdmMappedField>>();
		List<EdmMappedField> field = elements.get(fieldToMap);
		if (field == null) {
			elements.put(fieldToMap, new ArrayList<EdmMappedField>());
			field = elements.get(fieldToMap);
		}

		if (!field.contains(europeanaField) && europeanaField != null) {
			EdmMappedField edmMappedField = new EdmMappedField();
			edmMappedField.setLabel(europeanaField.toString());
			edmMappedField
					.setAttribute(StringUtils.isNotBlank(attribute) ? attribute
							: null);

			field.add(edmMappedField);
			elements.put(fieldToMap, field);

		} else if (europeanaField == null) {
			elements.put(fieldToMap, new ArrayList<EdmMappedField>());
		}
		vocabulary.setElements(elements);
		ControlledVocabularyImpl vocRet = mongoServer
				.getControlledVocabularyByName(vocabulary.getName());
		if (vocRet == null) {
			mongoServer.getDatastore().save(vocabulary);
		} else {
			UpdateOperations<ControlledVocabularyImpl> ops = mongoServer
					.getDatastore().createUpdateOperations(
							ControlledVocabularyImpl.class);
			Query<ControlledVocabularyImpl> updateQuery = mongoServer
					.getDatastore().createQuery(ControlledVocabularyImpl.class)
					.field("name").equal(vocabulary.getName());

			vocRet.setElements(elements);
			if (validate(vocRet)) {
				ops.set("elements", elements);
				mongoServer.getDatastore().update(updateQuery, ops);
			}

		}
	}

	/**
	 * Get the mapped field value from an EdmLabel
	 * 
	 * @param europeanaField
	 *            - the EdmLabel field string representation
	 * @return
	 */
	public String getMappedField(EdmLabel europeanaField) {
		for (String key : vocabulary.getElements().keySet()) {
			if (europeanaField.toString().equals(
					vocabulary.getElements().get(key).get(0).getLabel()
							.toString())) {
				return key;
			}
		}
		return null;
	}

	/**
	 * Method to check if a field is mapped or not
	 * 
	 * @param field
	 *            - The string representation of the field
	 * @return true if it is mapped, false otherwise
	 */
	public boolean isMapped(String field) {

		if (vocabulary != null) {
			for (Entry<String, List<EdmMappedField>> entry : vocabulary
					.getElements().entrySet()) {
				if (StringUtils.contains(entry.getKey(), field)
						&& entry.getValue().size() > 0) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Read an XML file from a location (locally)
	 * 
	 * @param location
	 *            The file path of the XML file
	 * @return an empty HashMap with keys being the Controlled Vocabulary fields
	 *         and empty values
	 */
	public HashMap<String, List<EdmMappedField>> readSchema(String location) {

		vocabulary.setElements(readFromFile(location));
		return (HashMap<String, List<EdmMappedField>>) vocabulary.getElements();
	}

	/**
	 * Remove a controlled vocabulary and its mappings
	 * 
	 * @param vocabularyName
	 */
	public void removeVocabulary(String vocabularyName) {

		mongoServer.getDatastore().delete(
				ControlledVocabularyImpl.class,
				mongoServer.getDatastore().find(ControlledVocabularyImpl.class)
						.filter("name", vocabularyName).get().getId());
	}

	/**
	 * Save the mapping of a controlled vocabulary
	 */
	public boolean saveMapping(int iterations, String[] vocabularyRules,
			String vocabularyUrl) {
		if (vocabulary != null && validate(vocabulary)) {
			if (mongoServer.getControlledVocabularyByUri(vocabulary.getURI(),
					vocabulary.getName()) != null) {
				Query<ControlledVocabularyImpl> updateQuery = mongoServer
						.getDatastore()
						.createQuery(ControlledVocabularyImpl.class)
						.field("name").equal(vocabulary.getName());
				UpdateOperations<ControlledVocabularyImpl> ops = mongoServer
						.getDatastore()
						.createUpdateOperations(ControlledVocabularyImpl.class)
						.set("elements", vocabulary.getElements());
				ops.set("iterations", iterations);
				ops.set("URI", vocabularyUrl);
				ops.set("rules", vocabularyRules);
				mongoServer.getDatastore().update(updateQuery, ops);

			} else {
				mongoServer.getDatastore().save(vocabulary);
			}
			return true;
		}
		return false;
	}

	private boolean validate(ControlledVocabulary voc) {

		Map<String, List<EdmMappedField>> elems = voc.getElements();
		List<EdmMappedField> validateList = new ArrayList<EdmMappedField>();
		for (Entry<String, List<EdmMappedField>> entry : elems.entrySet()) {
			if (entry.getValue() != null) {
				validateList.addAll(entry.getValue());
			} else {
				// if it is null ignore for validation and normalize

				elems.put(entry.getKey(), new ArrayList<EdmMappedField>());
			}
		}
		Set<EdmLabel> edmLabelSet = new HashSet<EdmLabel>();
		for (EdmMappedField label : validateList) {
			if (StringUtils.equals(EdmLabel.EDM_AGENT.toString(),
					label.getLabel())) {
				edmLabelSet.add(EdmLabel.EDM_AGENT);
			} else if (StringUtils.equals(EdmLabel.SKOS_CONCEPT.toString(),
					label.getLabel())) {
				edmLabelSet.add(EdmLabel.SKOS_CONCEPT);
			} else if (StringUtils.equals(EdmLabel.EDM_TIMESPAN.toString(),
					label.getLabel())) {
				edmLabelSet.add(EdmLabel.EDM_TIMESPAN);
			} else if (StringUtils.equals(EdmLabel.EDM_PLACE.toString(),
					label.getLabel())) {
				edmLabelSet.add(EdmLabel.EDM_PLACE);
			}

		}
		for (EdmMappedField label : validateList) {
			if (label.getLabel().startsWith("ag")) {
				if (!edmLabelSet.contains(EdmLabel.EDM_AGENT)) {
					return false;
				}
			} else if (label.getLabel().startsWith("ts")) {
				if (!edmLabelSet.contains(EdmLabel.EDM_TIMESPAN)) {
					return false;
				}
			} else if (label.getLabel().startsWith("pl")) {
				if (!edmLabelSet.contains(EdmLabel.EDM_PLACE)) {
					return false;
				}
			} else if (label.getLabel().startsWith("cc")) {
				if (!edmLabelSet.contains(EdmLabel.SKOS_CONCEPT)) {
					return false;
				}
			}

		}
		voc.setElements(elems);
		return true;
	}

	/**
	 * Read an XML file representing the controlled vocabulary
	 * 
	 * @param localLocation
	 *            - the file path of the XML file
	 * @return an empty hashmap with its keys being the controlled vocabularies
	 *         mappable fileds and the values null
	 */
	private HashMap<String, List<EdmMappedField>> readFromFile(
			String localLocation) {
		HashMap<String, List<EdmMappedField>> elements = new HashMap<String, List<EdmMappedField>>();
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
					break;
				case XMLStreamConstants.START_ELEMENT:
					element = (xml.getName().getPrefix().length() > 0 ? xml
							.getName().getPrefix() + ":" : "")
							+ xml.getName().getLocalPart();
					elements.put(element, new ArrayList<EdmMappedField>());
					int i = 0;
					while (i < xml.getAttributeCount()) {
						attribute = element + "_" + xml.getAttributePrefix(i)
								+ ":" + xml.getAttributeLocalName(i);
						elements.put(attribute, new ArrayList<EdmMappedField>());
						i++;
					}
					break;
				default:
					break;
				}
				xml.next();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {

			e.printStackTrace();
		}
		return elements;
	}

	/**
	 * Find a Dereferencing vocabulary by name and URI
	 * 
	 * @param name
	 * @param uri
	 * @return
	 */
	public ControlledVocabularyImpl findVocabularyByName(String name, String uri) {
		try {
			this.vocabulary = mongoServer.getControlledVocabularyByUri(uri,
					name);
			return this.vocabulary;
		} catch (VocabularyNotFoundException e) {
			throw e;
		}

	}

	/**
	 * Set a MongoServer
	 * 
	 * @param server
	 *            The server to use
	 */
	public void setMongoServer(VocabularyMongoServer server) {
		this.mongoServer = server;
	}

	/**
	 * Set active Vocabulary
	 * 
	 * @param vocabulary
	 *            The vocabulary currently being used
	 */
	public void setVocabulary(ControlledVocabularyImpl vocabulary) {
		this.vocabulary = vocabulary;
	}
}
