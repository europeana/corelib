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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;

import com.ctc.wstx.stax.WstxInputFactory;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import com.mongodb.MongoException;

import eu.europeana.corelib.definitions.jibx.AgentType;
import eu.europeana.corelib.definitions.jibx.Concept;
import eu.europeana.corelib.definitions.jibx.LiteralType;
import eu.europeana.corelib.definitions.jibx.PlaceType;
import eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType;
import eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Lang;
import eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource;
import eu.europeana.corelib.definitions.jibx.ResourceType;
import eu.europeana.corelib.definitions.jibx.TimeSpanType;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.dereference.ControlledVocabulary;
import eu.europeana.corelib.dereference.VocabularyMongoServer;
import eu.europeana.corelib.dereference.exceptions.VocabularyNotFoundException;
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
	private ControlledVocabularyImpl vocabulary;
	private final static long UPDATETIMESTAMP = 5184000000l;
	private final static String SKOS_CONCEPT = "skos_concept";
	private final static String EDM_AGENT = "edm_agent";
	private final static String EDM_TIMESPAN = "edm_timespan";
	private final static String EDM_PLACE = "edm_place";
	private final static String GET = "get";
	private final static String SET = "set";

	/**
	 * Constructor for use with object injection
	 * 
	 * @param controlledVocabulary
	 *            - The vocabulary to use
	 */
	public Extractor(ControlledVocabularyImpl controlledVocabulary) {
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
	 * 
	 * Dereferencing method
	 * 
	 * @param resource
	 *            The URI to retrieve the denormalized information from
	 * @param controlledVocabulary
	 *            The controlled vocabulary holding the mappings
	 * @return A List of pairs of strings representing the <Europeana Mapped
	 *         Field , Denormalized values>
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("rawtypes")
	private Map<String, List> createDereferencingMap(String xmlString,
			int iterations) throws SecurityException, IllegalArgumentException,
			InstantiationException, IllegalAccessException,
			NoSuchMethodException, InvocationTargetException {
		XMLInputFactory inFactory = new WstxInputFactory();
		Map<String, List> denormalizedValues = new HashMap<String, List>();
		List<Concept> concepts = new ArrayList<Concept>();
		List<AgentType> agents = new ArrayList<AgentType>();
		List<TimeSpanType> timespans = new ArrayList<TimeSpanType>();
		List<PlaceType> places = new ArrayList<PlaceType>();

		Concept lastConcept = null;
		AgentType lastAgent = null;
		TimeSpanType lastTimespan = null;
		PlaceType lastPlace = null;
		try {
			Source source = new StreamSource(new ByteArrayInputStream(
					xmlString.getBytes()), "UTF-8");
			XMLEventReader xml = inFactory.createXMLEventReader(source);

			String element = "";
			while (xml.hasNext()) {
				XMLEvent evt = xml.nextEvent();
				if (evt.isStartElement()) {
					StartElement sElem = evt.asStartElement();
					element = (sElem.getName().getPrefix() != null ? sElem
							.getName().getPrefix() + ":" : "")
							+ sElem.getName().getLocalPart();
					// If it is mapped then
					if (isMapped(element)) {

						for (EdmMappedField edmLabel : getEdmLabel(element)) {

							if (sElem.getAttributes().hasNext()) {
								Attribute attr = (Attribute) sElem
										.getAttributes().next();
								String attribute = attr.getName().getPrefix()

								+ ":" + attr.getName().getLocalPart();
								// Is the attribute mapped?
								if (isMapped(element + "_" + attribute)) {
									for (EdmMappedField label : getEdmLabel(element
											+ "_" + attribute)) {
										String attrVal = attr.getValue();
										String elem = null;
										if (xml.peek().isCharacters()) {
											elem = xml.nextEvent()
													.asCharacters().getData();
										}

										if (StringUtils.equals(label.getLabel()
												.toString(), "skos_concept")) {
											if (lastConcept != null) {
												if (lastConcept.getAbout() != null) {
													concepts.add(lastConcept);
													lastConcept = createNewEntity(
															Concept.class,
															attrVal);
												} else {
													lastConcept
															.setAbout(attrVal);
												}
											} else {
												lastConcept = createNewEntity(
														Concept.class, attrVal);
											}

										} else if (StringUtils.equals(label
												.getLabel().toString(),
												"edm_agent")) {
											if (lastAgent != null) {
												if (lastAgent.getAbout() != null) {
													agents.add(lastAgent);

													lastAgent = createNewEntity(
															AgentType.class,
															attrVal);
												} else {
													lastAgent.setAbout(attrVal);
												}
											} else {
												lastAgent = createNewEntity(
														AgentType.class,
														attrVal);
											}

										} else if (StringUtils.equals(label
												.getLabel().toString(),
												"edm_timespan")) {
											if (lastTimespan != null) {
												if (lastTimespan.getAbout() != null) {
													timespans.add(lastTimespan);
													lastTimespan = createNewEntity(
															TimeSpanType.class,
															attrVal);
												} else {
													lastTimespan
															.setAbout(attrVal);
												}

											} else {
												lastTimespan = createNewEntity(
														TimeSpanType.class,
														attrVal);
											}

										} else if (StringUtils.equals(label
												.getLabel().toString(),
												"edm_place")) {
											if (lastPlace != null) {
												if (lastPlace.getAbout() != null) {
													places.add(lastPlace);
													lastPlace = createNewEntity(
															PlaceType.class,
															attrVal);
												} else {
													lastPlace.setAbout(attrVal);
												}
											} else {
												lastPlace = createNewEntity(
														PlaceType.class,
														attrVal);
											}

										} else {
											if (StringUtils.startsWith(
													label.toString(), "cc")) {

												appendConceptValue(
														lastConcept == null ? new Concept()
																: lastConcept,
														label.getLabel()
																.toString(),
														elem,
														label.getAttribute(),
														attrVal, iterations);
											}

											else if (StringUtils.startsWith(
													label.toString(), "ts")) {

												appendValue(
														TimeSpanType.class,
														lastTimespan == null ? new TimeSpanType()
																: lastTimespan,
														label.getLabel().toString(), elem,
														label.getAttribute(), attrVal,
														iterations);
											} else if (StringUtils.startsWith(
													label.toString(), "ag")) {

												appendValue(
														AgentType.class,
														lastAgent == null ? new AgentType()
																: lastAgent,
														label.getLabel().toString(), elem,
														label.getAttribute(), attrVal,
														iterations);
											} else if (StringUtils.startsWith(
													label.toString(), "pl")) {

												appendValue(
														PlaceType.class,
														lastPlace == null ? new PlaceType()
																: lastPlace,
														label.getLabel().toString(), elem,
														label.getAttribute(), attrVal,
														iterations);
											}
										}
									}
								}
								// Since the attribute is not mapped
								else {

									if (StringUtils.equals(edmLabel.toString(),
											"skos_concept")) {
										if (lastConcept != null) {
											if (lastConcept.getAbout() != null) {
												concepts.add(lastConcept);
												lastConcept = createNewEntity(
														Concept.class,
														xml.getElementText());
											} else {
												lastConcept.setAbout(xml
														.getElementText());
											}

										} else {
											lastConcept = createNewEntity(
													Concept.class,
													xml.getElementText());
										}

									} else if (StringUtils.equals(
											edmLabel.toString(), "edm_agent")) {
										if (lastAgent != null) {
											if (lastAgent.getAbout() != null) {
												agents.add(lastAgent);
												lastAgent = createNewEntity(
														AgentType.class,
														xml.getElementText());
											} else {
												lastAgent.setAbout(xml
														.getElementText());
											}

										} else {
											lastAgent = createNewEntity(
													AgentType.class,
													xml.getElementText());
										}

									} else if (StringUtils
											.equals(edmLabel.toString(),
													"edm_timespan")) {
										if (lastTimespan != null) {
											if (lastTimespan.getAbout() != null) {
												timespans.add(lastTimespan);
												lastTimespan = createNewEntity(
														TimeSpanType.class,
														xml.getElementText());
											} else {
												lastTimespan.setAbout(xml
														.getElementText());
											}
										} else {
											lastTimespan = createNewEntity(
													TimeSpanType.class,
													xml.getElementText());
										}

									} else if (StringUtils.equals(
											edmLabel.toString(), "edm_place")) {
										if (lastPlace != null) {
											if (lastPlace.getAbout() != null) {
												places.add(lastPlace);
												lastPlace = createNewEntity(
														PlaceType.class,
														xml.getElementText());
											} else {
												lastPlace.setAbout(xml
														.getElementText());
											}
										} else {
											lastPlace = createNewEntity(
													PlaceType.class,
													xml.getElementText());
										}

									} else {
										if (StringUtils.startsWith(
												edmLabel.toString(), "cc")) {
											appendConceptValue(
													lastConcept == null ? new Concept()
															: lastConcept,
													edmLabel.toString(),
													xml.getElementText(), "",
													null, iterations);
										}

										else if (StringUtils.startsWith(
												edmLabel.toString(), "ts")) {
											appendValue(
													TimeSpanType.class,
													lastTimespan == null ? new TimeSpanType()
															: lastTimespan,
													edmLabel.toString(),
													xml.getElementText(), "",
													null, iterations);
										} else if (StringUtils.startsWith(
												edmLabel.toString(), "ag")) {
											appendValue(
													AgentType.class,
													lastAgent == null ? new AgentType()
															: lastAgent,
													edmLabel.toString(),
													xml.getElementText(), "",
													null, iterations);
										} else if (StringUtils.startsWith(
												edmLabel.toString(), "pl")) {
											appendValue(
													PlaceType.class,
													lastPlace == null ? new PlaceType()
															: lastPlace,
													edmLabel.toString(),
													xml.getElementText(), "",
													null, iterations);

										}
									}
								}

							}
							// Since it does not have attributes
							else {
								XMLEvent evt2 = xml.nextEvent();
								if (evt2.isCharacters()) {
									if (StringUtils.equals(edmLabel.toString(),
											"skos_concept")) {
										if (lastConcept != null) {
											concepts.add(lastConcept);
										}
										lastConcept = createNewEntity(
												Concept.class, evt2
														.asCharacters()
														.getData());

									} else if (StringUtils.equals(
											edmLabel.toString(), "edm_agent")) {
										if (lastAgent != null) {
											if (lastAgent.getAbout() != null) {
												agents.add(lastAgent);
												lastAgent = createNewEntity(
														AgentType.class, evt2
																.asCharacters()
																.getData());
											} else {
												lastAgent.setAbout(evt2
														.asCharacters()
														.getData());
											}
										}

										else {
											lastAgent = createNewEntity(
													AgentType.class, evt2
															.asCharacters()
															.getData());
										}
									} else if (StringUtils
											.equals(edmLabel.toString(),
													"edm_timespan")) {
										if (lastTimespan != null) {
											if (lastTimespan.getAbout() != null) {
												timespans.add(lastTimespan);
												lastTimespan = createNewEntity(
														TimeSpanType.class,
														evt2.asCharacters()
																.getData());
											} else {
												lastTimespan.setAbout(evt2
														.asCharacters()
														.getData());
											}

										} else {
											lastTimespan = createNewEntity(
													TimeSpanType.class, evt2
															.asCharacters()
															.getData());
										}

									} else if (StringUtils.equals(
											edmLabel.toString(), "edm_place")) {
										if (lastPlace != null) {
											if (lastPlace.getAbout() != null) {
												places.add(lastPlace);
												lastPlace = createNewEntity(
														PlaceType.class, evt2
																.asCharacters()
																.getData());
											} else {
												lastPlace.setAbout(evt2
														.asCharacters()
														.getData());
											}
										} else {
											lastPlace = createNewEntity(
													PlaceType.class, evt2
															.asCharacters()
															.getData());
										}

									} else {

										if (StringUtils.startsWith(
												edmLabel.toString(), "cc")) {
											appendConceptValue(
													lastConcept == null ? new Concept()
															: lastConcept,
													edmLabel.toString(), evt2
															.asCharacters()
															.getData(), "",
													null, iterations);
										}

										else if (StringUtils.startsWith(
												edmLabel.toString(), "ts")) {
											appendValue(
													TimeSpanType.class,
													lastTimespan == null ? new TimeSpanType()
															: lastTimespan,

													edmLabel.toString(), evt2
															.asCharacters()
															.getData(), "",
													null, iterations);
										} else if (StringUtils.startsWith(
												edmLabel.toString(), "ag")) {
											appendValue(
													AgentType.class,
													lastAgent == null ? new AgentType()
															: lastAgent,
													edmLabel.toString(), evt2
															.asCharacters()
															.getData(), "",
													null, iterations);
										} else if (StringUtils.startsWith(
												edmLabel.toString(), "pl")) {
											appendValue(
													PlaceType.class,
													lastPlace == null ? new PlaceType()
															: lastPlace,
													edmLabel.toString(), evt2
															.asCharacters()
															.getData(), "",
													null, iterations);
										}
									}
								}
							}
						}
					}
					// The element is not mapped, but does it have any
					// mapped attributes?
					else {
						if (sElem.getAttributes().hasNext()) {
							Attribute attr = (Attribute) sElem.getAttributes()
									.next();
							String attribute = attr.getName().getPrefix()

							+ ":" + attr.getName().getLocalPart();
							// Is the attribute mapped?
							xml.next();

							// Is the attribute mapped?
							if (isMapped(element + "_" + attribute)) {
								for (EdmMappedField label : getEdmLabel(element + "_"
										+ attribute)) {
									if (StringUtils.equals(label.getLabel().toString(),
											"skos_concept")) {
										if (lastConcept != null) {
											if (lastConcept.getAbout() != null) {
												concepts.add(lastConcept);
												lastConcept = createNewEntity(
														Concept.class,
														attr.getValue());
											} else {
												lastConcept.setAbout(attr
														.getValue());
											}
										} else {
											lastConcept = createNewEntity(
													Concept.class,
													attr.getValue());
										}
									}

									else

									if (StringUtils.equals(label.getLabel().toString(),
											"edm_agent")) {
										if (lastAgent != null) {
											if (lastAgent.getAbout() != null) {
												agents.add(lastAgent);
												lastAgent = createNewEntity(
														AgentType.class,
														attr.getValue());
											} else {
												lastAgent.setAbout(attr
														.getValue());
											}
										} else {
											lastAgent = createNewEntity(
													AgentType.class,
													attr.getValue());
										}

									} else

									if (StringUtils.equals(label.getLabel().toString(),
											"edm_timespan")) {
										if (lastTimespan != null) {
											if (lastTimespan.getAbout() != null) {
												timespans.add(lastTimespan);
												lastTimespan = createNewEntity(
														TimeSpanType.class,
														attr.getValue());
											} else {
												lastTimespan.setAbout(attr
														.getValue());
											}
										} else {
											lastTimespan = createNewEntity(
													TimeSpanType.class,
													attr.getValue());
										}

									} else

									if (StringUtils.equals(label.getLabel().toString(),
											"edm_place")) {
										if (lastPlace != null) {
											if (lastPlace.getAbout() != null) {
												places.add(lastPlace);
												lastPlace = createNewEntity(
														PlaceType.class,
														attr.getValue());
											} else {
												lastPlace.setAbout(attr
														.getValue());
											}

										} else {
											lastPlace = createNewEntity(
													PlaceType.class,
													attr.getValue());
										}

									} else {
										if (StringUtils.startsWith(
												label.getLabel().toString(), "cc")) {
											String elem = null;
											if (xml.peek().isCharacters()) {
												elem = xml.nextEvent()
														.asCharacters()
														.getData();
											}
											String attrVal = attr.getValue();

											appendConceptValue(
													lastConcept == null ? new Concept()
															: lastConcept,
													null, elem, label.getAttribute(),
													attrVal, iterations);
										}

										else if (StringUtils.startsWith(
												label.getLabel().toString(), "ts")) {
											String elem = null;
											if (xml.peek().isCharacters()) {
												elem = xml.nextEvent()
														.asCharacters()
														.getData();
											}
											String attrVal = attr.getValue();

											appendValue(
													TimeSpanType.class,
													lastTimespan == null ? new TimeSpanType()
															: lastTimespan,
													null, elem, label.getAttribute(),
													attrVal, iterations);
										} else if (StringUtils.startsWith(
												label.getLabel().toString(), "ag")) {
											String elem = null;
											if (xml.peek().isCharacters()) {
												elem = xml.nextEvent()
														.asCharacters()
														.getData();
											}
											String attrVal = attr.getValue();

											appendValue(
													AgentType.class,
													lastAgent == null ? new AgentType()
															: lastAgent, null,
													elem, label.getAttribute(), attrVal,
													iterations);
										} else if (StringUtils.startsWith(
												label.toString(), "pl")) {
											String elem = null;
											if (xml.peek().isCharacters()) {
												elem = xml.nextEvent()
														.asCharacters()
														.getData();
											}
											String attrVal = attr.getValue();

											appendValue(
													PlaceType.class,
													lastPlace == null ? new PlaceType()
															: lastPlace, null,
													elem, label.getAttribute(), attrVal,
													iterations);
										}
									}
								}
							}
						}
					}
				}

			}
			if (lastConcept != null)
				concepts.add(lastConcept);
			if (lastAgent != null)
				agents.add(lastAgent);
			if (lastTimespan != null)
				timespans.add(lastTimespan);
			if (lastPlace != null)
				places.add(lastPlace);
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}

		denormalizedValues.put("concepts", concepts);
		denormalizedValues.put("agents", agents);
		denormalizedValues.put("timespans", timespans);
		denormalizedValues.put("places", places);

		return denormalizedValues;
	}

	/**
	 * Retrieve an XML string from a ControlledVocabulary
	 * 
	 * @param resource
	 *            - The name of the resource to retrieve
	 * @return An XML string representing the rdf/XML resource from an online
	 *         Controlled Vocabulary
	 * @throws MalformedURLException
	 * @throws IOException
	 */

	private String retrieveValueFromResource(String resource) {

		EntityImpl entity = mongoServer.getDatastore().find(EntityImpl.class)
				.filter("uri", resource).get();
		if (entity == null) {
			String val = retrieveValue(resource);
			if (val.length() > 0) {
				EntityImpl newEntity = new EntityImpl();
				newEntity.setUri(resource);
				newEntity.setTimestamp(new Date().getTime());
				newEntity.setContent(val);
				mongoServer.getDatastore().save(newEntity);
			}
			return val;
		} else {
			if (new Date().getTime() - entity.getTimestamp() < UPDATETIMESTAMP) {
				return entity.getContent();
			} else {
				String val = retrieveValue(resource);
				Query<EntityImpl> updateQuery = mongoServer.getDatastore()
						.createQuery(EntityImpl.class).field("uri")
						.equal(resource);
				UpdateOperations<EntityImpl> ops = mongoServer.getDatastore()
						.createUpdateOperations(EntityImpl.class)
						.set("content", val);
				mongoServer.getDatastore().update(updateQuery, ops);
				return val;
			}
		}
	}

	/**
	 * Retrieve value from a remote resource
	 * 
	 * @param resource
	 *            - the URI of the resource
	 * @return The string contained in the resource
	 */
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
	 * Create a new Contextual Entity
	 * 
	 * @param clazz
	 *            - Any rdf Contextual Entity Class (AgentType, Concept,
	 *            PlaceType, TimeSpanType)
	 * @param val
	 *            - the rdf:about field for the class
	 * @return the newly created instance of the contextual entity
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */


	/**
	 * Append a value in a contextual entity. This method is not applicable to
	 * Concepts due to the fact that Concept sub-elements create a Choice rather
	 * than a sequence in the EDM schema
	 * 
	 * @param val
	 *            - the value to append
	 * @param edmLabel
	 *            - the field to store tha value in
	 * @param clazz
	 *            - the ClassType of the object to append the value to
	 * @param obj
	 *            - the object to append the value to
	 * @return the object
	 * 
	 * 
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private <T> T createNewEntity(Class<T> clazz, String val)
			throws InstantiationException, IllegalAccessException,
			SecurityException, NoSuchMethodException, IllegalArgumentException,
			InvocationTargetException {
		T obj = clazz.newInstance();
		Class<?>[] cls = new Class<?>[1];
		cls[0] = (String.class);
		Method method = clazz.getMethod("setAbout", cls);
		method.invoke(obj, val);
		return obj;
	}

	@SuppressWarnings("unchecked")
	private <T> T appendValue(Class<T> clazz, T obj, String edmLabel,
			String val, String edmAttr, String valAttr, int iterations)
			throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {

		RdfMethod RDF = null;
		for (RdfMethod rdfMethod : RdfMethod.values()) {
			if (StringUtils.equals(rdfMethod.getSolrField(), edmLabel)) {
				RDF = rdfMethod;
			}
		}

		//
		if (RDF.getMethodName().endsWith("List")) {

			Method mthd = clazz.getMethod(RDF.getMethodName());

			@SuppressWarnings("rawtypes")
			List lst = mthd.invoke(obj) != null ? (ArrayList) mthd.invoke(obj)
					: new ArrayList();
			if (RDF.getClazz().getSuperclass()
					.isAssignableFrom(ResourceType.class)) {

				ResourceType rs = new ResourceType();
				rs.setResource(val != null ? val : valAttr);
				if (isURI(rs.getResource())) {
					denormalize(rs.getResource(), iterations - 1);
				}
				lst.add(RDF.returnObject(RDF.getClazz(), rs));

			} else if (RDF.getClazz().getSuperclass()
					.isAssignableFrom(ResourceOrLiteralType.class)) {
				ResourceOrLiteralType rs = new ResourceOrLiteralType();
				if (isURI(val)) {					
					Resource res = new Resource();
					res.setResource(val);
					rs.setResource(res);
					denormalize(val, iterations - 1);
				} else {
					rs.setString(val);
				}
				if (edmAttr != null) {
					
						if (StringUtils.equals(
								edmAttr, "xml:lang")) {
							Lang lang = new Lang();
							lang.setLang(valAttr);
							rs.setLang(lang);
						}
					
				}
				lst.add(RDF.returnObject(RDF.getClazz(), rs));
			} else if (RDF.getClazz().getSuperclass()
					.isAssignableFrom(LiteralType.class)) {
				LiteralType rs = new LiteralType();
				rs.setString(val);
				if (edmAttr != null) {
					
						if (StringUtils.equals(
								edmAttr, "xml:lang")) {
							LiteralType.Lang lang = new LiteralType.Lang();
							lang.setLang(valAttr);
							rs.setLang(lang);
					}
				}
				lst.add(RDF.returnObject(RDF.getClazz(), rs));
			}

			Class<?>[] cls = new Class<?>[1];
			cls[0] = List.class;
			Method method = obj.getClass()
					.getMethod(
							StringUtils.replace(RDF.getMethodName(), "get",
									"set"), cls);
			method.invoke(obj, lst);
		} else {
			if (RDF.getClazz().isAssignableFrom(ResourceType.class)) {
				ResourceType rs = new ResourceType();
				rs.setResource(val != null ? val : valAttr);
				if (isURI(rs.getResource())) {
					denormalize(rs.getResource(), iterations - 1);
				}
				Class<?>[] cls = new Class<?>[1];
				cls[0] = RDF.getClazz();
				Method method = obj.getClass().getMethod(
						StringUtils.replace(RDF.getMethodName(), "get", "set"),
						cls);
				method.invoke(obj, RDF.returnObject(RDF.getClazz(), rs));
			} else if (RDF.getClazz().isAssignableFrom(LiteralType.class)) {
				LiteralType rs = new LiteralType();
				rs.setString(val);
				if (isURI(val)) {
					denormalize(val, iterations - 1);
				}
				if (edmAttr != null) {
					
						if (StringUtils.equals(
								edmAttr, "xml:lang")) {
							LiteralType.Lang lang = new LiteralType.Lang();
							lang.setLang(valAttr);
							rs.setLang(lang);
					}
				}
				Class<?>[] cls = new Class<?>[1];
				cls[0] = RDF.getClazz();
				Method method = obj.getClass().getMethod(
						StringUtils.replace(RDF.getMethodName(), "get", "set"),
						cls);
				method.invoke(obj, RDF.returnObject(RDF.getClazz(), rs));

			} else if (RDF.getClazz().isAssignableFrom(
					ResourceOrLiteralType.class)) {
				ResourceOrLiteralType rs = new ResourceOrLiteralType();
				if (isURI(val)) {
					Resource res =  new Resource();
					res.setResource(val);
					rs.setResource(res);
					denormalize(val, iterations - 1);
				} else {
					rs.setString(val);
				}
				if (edmAttr != null) {
				
						if (StringUtils.equals(
								edmAttr, "xml:lang")) {
							Lang lang = new Lang();
							lang.setLang(valAttr);
							rs.setLang(lang);
					}
				}
				Class<?>[] cls = new Class<?>[1];
				cls[0] = clazz;
				Method method = obj.getClass().getMethod(
						StringUtils.replace(RDF.getMethodName(), "get", "set"),
						cls);
				method.invoke(obj, RDF.returnObject(RDF.getClazz(), rs));
			}
		}
		return obj;
	}

	private Concept appendConceptValue(Concept concept, String edmLabel,
			String val, String edmAttr, String valAttr, int iterations)
			throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		RdfMethod RDF = null;
		for (RdfMethod rdfMethod : RdfMethod.values()) {
			if (StringUtils.equals(rdfMethod.getSolrField(), edmLabel)) {
				RDF = rdfMethod;
				break;
			}
		}
		List<Concept.Choice> lst = concept.getChoiceList() != null ? concept
				.getChoiceList() : new ArrayList<Concept.Choice>();
		if (RDF.getClazz().getSuperclass().isAssignableFrom(ResourceType.class)) {

			ResourceType obj = new ResourceType();
			obj.setResource(val != null ? val : valAttr);
			if (isURI(obj.getResource())) {
				denormalize(obj.getResource(), iterations - 1);
			}
			Class<?>[] cls = new Class<?>[1];
			cls[0] = RDF.getClazz();
			Concept.Choice choice = new Concept.Choice();
			Method method = choice.getClass()
					.getMethod(
							StringUtils.replace(RDF.getMethodName(), "get",
									"set"), cls);
			method.invoke(choice, RDF.returnObject(RDF.getClazz(), obj));
			lst.add(choice);

		} else if (RDF.getClazz().getSuperclass()
				.isAssignableFrom(ResourceOrLiteralType.class)) {

			ResourceOrLiteralType obj = new ResourceOrLiteralType();

			if (isURI(val)) {
				Resource res =  new Resource();
				res.setResource(val);
				obj.setResource(res);
				denormalize(val, iterations - 1);
			} else {
				obj.setString(val);
			}
			if (edmAttr != null) {

				if (StringUtils.equals(edmAttr, "xml:lang")) {
					Lang lang = new Lang();
					lang.setLang(valAttr);
					obj.setLang(lang);
				}
			}
			Class<?>[] cls = new Class<?>[1];
			cls[0] = RDF.getClazz();
			Concept.Choice choice = new Concept.Choice();
			Method method = choice.getClass()
					.getMethod(
							StringUtils.replace(RDF.getMethodName(), "get",
									"set"), cls);
			method.invoke(choice, RDF.returnObject(RDF.getClazz(), obj));
			lst.add(choice);

		} else if (RDF.getClazz().getSuperclass()
				.isAssignableFrom(LiteralType.class)) {
			LiteralType obj = new LiteralType();
			obj.setString(val);
			if (isURI(val)) {
				denormalize(val, iterations - 1);
			}
			if (edmAttr != null) {

				if (StringUtils.equals(edmAttr, "xml:lang")) {
					LiteralType.Lang lang = new LiteralType.Lang();
					lang.setLang(valAttr);
					obj.setLang(lang);
				}
			}
			Class<?>[] cls = new Class<?>[1];
			cls[0] = RDF.getClazz();
			Concept.Choice choice = new Concept.Choice();
			Method method = choice.getClass()
					.getMethod(
							StringUtils.replace(RDF.getMethodName(), "get",
									"set"), cls);
			method.invoke(choice, RDF.returnObject(RDF.getClazz(), obj));
			lst.add(choice);
		}
		concept.setChoiceList(lst);
		return concept;
	}
	private Map<String, List> denormalize(String val, int iterations) {
		try {
			//Dummy method..to clean up
			return new HashMap<String, List>();
		} catch (MongoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	/**
	 * Method to check if a String is a URI TODO: move it in some util class
	 * 
	 * @param uri
	 *            - the uri to check
	 * @return true, if the string represents a uri, false otherwise
	 */
	private static boolean isURI(String uri) {

		try {
			new URL(uri);
			return true;
		} catch (MalformedURLException e) {
			return false;
		}

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
	public void setMappedField(String fieldToMap, EdmLabel europeanaField, String attribute) {
		HashMap<String, List<EdmMappedField>> elements = vocabulary.getElements() != null ? (HashMap<String, List<EdmMappedField>>) vocabulary
				.getElements() : new HashMap<String, List<EdmMappedField>>();
		List<EdmMappedField> field = elements.get(fieldToMap);
		if (field == null) {
			elements.put(fieldToMap, new ArrayList<EdmMappedField>());
			field = elements.get(fieldToMap);
		} 
		if (!field.contains(europeanaField)) {
			EdmMappedField edmMappedField = new EdmMappedField();
			edmMappedField.setLabel(europeanaField.toString());
			edmMappedField.setAttribute(StringUtils.isNotBlank(attribute)?attribute:null);
				
			
			field.add(edmMappedField);
			elements.put(fieldToMap, field);

		}
		vocabulary.setElements(elements);
		ControlledVocabularyImpl vocRet = mongoServer.getControlledVocabularyByUri(vocabulary.getURI(), vocabulary.getName());
		if(vocRet==null){
			mongoServer.getDatastore().save(vocabulary);
		} else {
			UpdateOperations<ControlledVocabularyImpl> ops = mongoServer.getDatastore().createUpdateOperations(ControlledVocabularyImpl.class);
			Query<ControlledVocabularyImpl> updateQuery = mongoServer
					.getDatastore()
					.createQuery(ControlledVocabularyImpl.class)
					.field("name").equal(vocabulary.getName());
			
			vocRet.setElements(elements);
			validate(vocRet);
			ops.set("elements", vocRet.getElements());
			mongoServer.getDatastore().update(updateQuery,ops);
			vocabulary = mongoServer.getControlledVocabularyByUri(vocabulary.getURI(), vocabulary.getName());
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
			if (europeanaField.toString().equals(vocabulary.getElements().get(key).get(0).getLabel().toString())) {
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
			for (Entry<String, List<EdmMappedField>> entry : vocabulary.getElements()
					.entrySet()) {
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
	public boolean saveMapping(int iterations) {
		if (vocabulary != null && validate(vocabulary)) {
			if (mongoServer.getControlledVocabularyByUri(vocabulary.getURI(), vocabulary.getName()) != null) {
				Query<ControlledVocabularyImpl> updateQuery = mongoServer
						.getDatastore()
						.createQuery(ControlledVocabularyImpl.class)
						.field("name").equal(vocabulary.getName());
				UpdateOperations<ControlledVocabularyImpl> ops = mongoServer
						.getDatastore()
						.createUpdateOperations(ControlledVocabularyImpl.class)
						.set("elements", vocabulary.getElements());
						ops.set("iterations",iterations);
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
			if (entry.getValue()!=null){
			validateList.addAll(entry.getValue());
			} else {
				//if it is null ignore for validation and normalize
				
				elems.put(entry.getKey(), new ArrayList<EdmMappedField>());
			}
		}
		Set<EdmLabel> edmLabelSet = new HashSet<EdmLabel>();
		for (EdmMappedField label : validateList) {
			if(StringUtils.equals(EdmLabel.EDM_AGENT.toString(), label.getLabel())){
				edmLabelSet.add(EdmLabel.EDM_AGENT);
			} else if (StringUtils.equals(EdmLabel.SKOS_CONCEPT.toString(), label.getLabel())){
				edmLabelSet.add(EdmLabel.SKOS_CONCEPT);
			} else if (StringUtils.equals(EdmLabel.EDM_TIMESPAN.toString(), label.getLabel())){
				edmLabelSet.add(EdmLabel.EDM_TIMESPAN);
			} else if (StringUtils.equals(EdmLabel.EDM_PLACE.toString(), label.getLabel())){
				edmLabelSet.add(EdmLabel.EDM_PLACE);
			} 
			
		}
		for (EdmMappedField label : validateList) {
			if(label.getLabel().startsWith("ag")){
				if(!edmLabelSet.contains(EdmLabel.EDM_AGENT)){
					return false;
				} 
			} else if(label.getLabel().startsWith("ts")){
				if(!edmLabelSet.contains(EdmLabel.EDM_TIMESPAN)){
					return false;
				} 
			} else if(label.getLabel().startsWith("pl")){
				if(!edmLabelSet.contains(EdmLabel.EDM_PLACE)){
					return false;
				} 
			} else if(label.getLabel().startsWith("cc")){
				if(!edmLabelSet.contains(EdmLabel.SKOS_CONCEPT)){
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
	private HashMap<String, List<EdmMappedField>> readFromFile(String localLocation) {
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

	public ControlledVocabularyImpl findVocabularyByName(String name, String uri) {
		try {
			this.vocabulary = mongoServer.getControlledVocabularyByUri(uri,
					name);
			return this.vocabulary;
		} catch (VocabularyNotFoundException e) {
			throw e;
		}

	}
}
