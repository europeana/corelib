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
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle.Control;

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

import eu.europeana.corelib.definitions.jibx.AgentType;
import eu.europeana.corelib.definitions.jibx.Concept;
import eu.europeana.corelib.definitions.jibx.LiteralType;
import eu.europeana.corelib.definitions.jibx.PlaceType;
import eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType;
import eu.europeana.corelib.definitions.jibx.ResourceType;
import eu.europeana.corelib.definitions.jibx.TimeSpanType;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.dereference.ControlledVocabulary;
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
	public Map<String, List> denormalize(String resource,
			ControlledVocabularyImpl controlledVocabulary)
			throws SecurityException, IllegalArgumentException,
			InstantiationException, IllegalAccessException,
			NoSuchMethodException, InvocationTargetException {

		Map<String, List> denormalizedValues = new HashMap<String, List>();
		List<Concept> concepts = new ArrayList<Concept>();
		List<AgentType> agents = new ArrayList<AgentType>();
		List<TimeSpanType> timespans = new ArrayList<TimeSpanType>();
		List<PlaceType> places = new ArrayList<PlaceType>();
		Concept lastConcept = null;
		AgentType lastAgent = null;
		TimeSpanType lastTimespan = null;
		PlaceType lastPlace = null;
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
						switch (xml.getEventType()) {
						case XMLStreamConstants.START_DOCUMENT:

							break;
						case XMLStreamConstants.START_ELEMENT:
							element = (xml.getPrefix() != null ? xml
									.getPrefix() + ":" : "")
									+ xml.getLocalName();
							// If it is mapped then
							if (isMapped(element)) {
								// does it have any attribute?
								for (EdmLabel label : getEdmLabel(element)) {
									if (StringUtils.equals(label.toString(),
											SKOS_CONCEPT)) {
										if (xml.hasText()) {
											if (lastConcept != null) {
												concepts.add(lastConcept);
											}
											lastConcept = createNewEntity(
													Concept.class,
													xml.getElementText());

										}
									} else if (StringUtils.equals(
											label.toString(), EDM_AGENT)) {
										if (xml.hasText()) {
											if (lastAgent != null) {
												agents.add(lastAgent);
											}
											lastAgent = createNewEntity(
													AgentType.class,
													xml.getElementText());

										}
									} else if (StringUtils.equals(
											label.toString(), EDM_TIMESPAN)) {
										if (xml.hasText()) {
											if (lastTimespan != null) {
												timespans.add(lastTimespan);
											}
											lastTimespan = createNewEntity(
													TimeSpanType.class,
													xml.getElementText());

										}
									} else if (StringUtils.equals(
											label.toString(), EDM_PLACE)) {
										if (xml.hasText()) {
											if (lastPlace != null) {
												places.add(lastPlace);
											}
											lastPlace = createNewEntity(
													PlaceType.class,
													xml.getElementText());

										}
									}

								}

								if (xml.getAttributeCount() > 0) {
									String attribute = xml
											.getAttributePrefix(0)
											+ ":"
											+ xml.getAttributeLocalName(0);
									// Is the attribute mapped?
									if (isMapped(element + "_" + attribute)) {
										for (EdmLabel label : getEdmLabel(element
												+ "_" + attribute)) {
											if (StringUtils.equals(
													label.toString(),
													SKOS_CONCEPT)) {
												if (lastConcept != null) {
													concepts.add(lastConcept);
												}

												lastConcept = createNewEntity(
														Concept.class,
														xml.getAttributeValue(0));
											} else

											if (StringUtils
													.equals(label.toString(),
															EDM_AGENT)) {
												if (lastAgent != null) {
													agents.add(lastAgent);
												}

												lastAgent = createNewEntity(
														AgentType.class,
														xml.getAttributeValue(0));
											} else

											if (StringUtils.equals(
													label.toString(),
													EDM_TIMESPAN)) {
												if (lastTimespan != null) {
													timespans.add(lastTimespan);
												}

												lastTimespan = createNewEntity(
														TimeSpanType.class,
														xml.getAttributeValue(0));
											} else

											if (StringUtils
													.equals(label.toString(),
															EDM_PLACE)) {
												if (lastPlace != null) {
													places.add(lastPlace);
												}
												lastPlace = createNewEntity(
														PlaceType.class,
														xml.getAttributeValue(0));
											} else {
												if (StringUtils.startsWith(
														label.toString(), "cc")) {
													appendConceptValue(
															xml.getAttributeValue(0),
															label.toString(),
															lastConcept);
												}

												else if (StringUtils
														.startsWith(label
																.toString(),
																"ts")) {
													appendValue(
															xml.getAttributeValue(0),
															label.toString(),
															TimeSpanType.class,
															lastTimespan);
												} else if (StringUtils
														.startsWith(label
																.toString(),
																"ag")) {
													appendValue(
															xml.getAttributeValue(0),
															label.toString(),
															AgentType.class,
															lastAgent);
												} else if (StringUtils
														.startsWith(label
																.toString(),
																"pl")) {
													appendValue(
															xml.getAttributeValue(0),
															label.toString(),
															PlaceType.class,
															lastPlace);
												}
											}
										}
									}
									// Since the attribute is not mapped
									else {
										for (EdmLabel label : getEdmLabel(element)) {
											if (StringUtils.equals(
													label.toString(),
													SKOS_CONCEPT)) {
												if (xml.hasText()) {
													if (lastConcept != null) {
														concepts.add(lastConcept);
													}
													lastConcept = createNewEntity(
															Concept.class,
															xml.getElementText());

												}
											} else if (StringUtils
													.equals(label.toString(),
															EDM_AGENT)) {
												if (xml.hasText()) {
													if (lastAgent != null) {
														agents.add(lastAgent);
													}
													lastAgent = createNewEntity(
															AgentType.class,
															xml.getElementText());

												}
											} else if (StringUtils.equals(
													label.toString(),
													EDM_TIMESPAN)) {
												if (xml.hasText()) {
													if (lastTimespan != null) {
														timespans
																.add(lastTimespan);
													}
													lastTimespan = createNewEntity(
															TimeSpanType.class,
															xml.getElementText());

												}
											} else if (StringUtils
													.equals(label.toString(),
															EDM_PLACE)) {
												if (xml.hasText()) {
													if (lastPlace != null) {
														places.add(lastPlace);
													}
													lastPlace = createNewEntity(
															PlaceType.class,
															xml.getElementText());

												}
											}
											if (StringUtils.startsWith(
													label.toString(), "cc")) {
												appendConceptValue(
														xml.getElementText(),
														label.toString(),
														lastConcept);
											}

											else if (StringUtils.startsWith(
													label.toString(), "ts")) {
												appendValue(
														xml.getElementText(),
														label.toString(),
														TimeSpanType.class,
														lastTimespan);
											} else if (StringUtils.startsWith(
													label.toString(), "ag")) {
												appendValue(
														xml.getElementText(),
														label.toString(),
														AgentType.class,
														lastAgent);
											} else if (StringUtils.startsWith(
													label.toString(), "pl")) {
												appendValue(
														xml.getElementText(),
														label.toString(),
														PlaceType.class,
														lastPlace);

											}
										}
									}
								}
								// Since it does not have attributes
								else {
									for (EdmLabel label : getEdmLabel(element)) {
										if (StringUtils.equals(
												label.toString(), SKOS_CONCEPT)) {
											if (xml.hasText()) {
												if (lastConcept != null) {
													concepts.add(lastConcept);
												}
												lastConcept = createNewEntity(
														Concept.class,
														xml.getElementText());

											}
										} else if (StringUtils.equals(
												label.toString(), EDM_AGENT)) {
											if (xml.hasText()) {
												if (lastAgent != null) {
													agents.add(lastAgent);
												}
												lastAgent = createNewEntity(
														AgentType.class,
														xml.getElementText());

											}
										} else if (StringUtils.equals(
												label.toString(), EDM_TIMESPAN)) {
											if (xml.hasText()) {
												if (lastTimespan != null) {
													timespans.add(lastTimespan);
												}
												lastTimespan = createNewEntity(
														TimeSpanType.class,
														xml.getElementText());

											}
										} else if (StringUtils.equals(
												label.toString(), EDM_PLACE)) {
											if (xml.hasText()) {
												if (lastPlace != null) {
													places.add(lastPlace);
												}
												lastPlace = createNewEntity(
														PlaceType.class,
														xml.getElementText());

											}
										}

										if (StringUtils.startsWith(
												label.toString(), "cc")) {
											if (xml.hasText()) {
												appendConceptValue(
														xml.getElementText(),
														label.toString(),
														lastConcept);
											}
										}

										else if (StringUtils.startsWith(
												label.toString(), "ts")) {
											if (xml.hasText()) {
												appendValue(
														xml.getElementText(),
														label.toString(),
														TimeSpanType.class,
														lastTimespan);
											}
										} else if (StringUtils.startsWith(
												label.toString(), "ag")) {
											if (xml.hasText()) {
												appendValue(
														xml.getElementText(),
														label.toString(),
														AgentType.class,
														lastAgent);
											}
										} else if (StringUtils.startsWith(
												label.toString(), "pl")) {
											if (xml.hasText()) {
												appendValue(
														xml.getElementText(),
														label.toString(),
														PlaceType.class,
														lastPlace);
											}
										}
									}

								}
							}
							// The element is not mapped, but does it have any
							// mapped attributes?
							else {
								if (xml.getAttributeCount() > 0) {
									String attribute = xml
											.getAttributePrefix(0)
											+ ":"
											+ xml.getAttributeLocalName(0);
									// Is the attribute mapped?
									if (isMapped(element + "_" + attribute)) {
										for (EdmLabel label : getEdmLabel(element
												+ "_" + attribute)) {
											if (StringUtils.equals(
													label.toString(),
													SKOS_CONCEPT)) {
												if (lastConcept != null) {
													concepts.add(lastConcept);
												}

												lastConcept = createNewEntity(
														Concept.class,
														xml.getAttributeValue(0));
											} else

											if (StringUtils
													.equals(label.toString(),
															EDM_AGENT)) {
												if (lastAgent != null) {
													agents.add(lastAgent);
												}

												lastAgent = createNewEntity(
														AgentType.class,
														xml.getAttributeValue(0));
											} else

											if (StringUtils.equals(
													label.toString(),
													EDM_TIMESPAN)) {
												if (lastTimespan != null) {
													timespans.add(lastTimespan);
												}

												lastTimespan = createNewEntity(
														TimeSpanType.class,
														xml.getAttributeValue(0));
											} else

											if (StringUtils
													.equals(label.toString(),
															EDM_PLACE)) {
												if (lastPlace != null) {
													places.add(lastPlace);
												}
												lastPlace = createNewEntity(
														PlaceType.class,
														xml.getAttributeValue(0));
											} else {
												if (StringUtils.startsWith(
														label.toString(), "cc")) {
													appendConceptValue(
															xml.getAttributeValue(0),
															label.toString(),
															lastConcept);
												}

												else if (StringUtils
														.startsWith(label
																.toString(),
																"ts")) {
													appendValue(
															xml.getAttributeValue(0),
															label.toString(),
															TimeSpanType.class,
															lastTimespan);
												} else if (StringUtils
														.startsWith(label
																.toString(),
																"ag")) {
													appendValue(
															xml.getAttributeValue(0),
															label.toString(),
															AgentType.class,
															lastAgent);
												} else if (StringUtils
														.startsWith(label
																.toString(),
																"pl")) {
													appendValue(
															xml.getAttributeValue(0),
															label.toString(),
															PlaceType.class,
															lastPlace);
												}
											}
										}
									}
								}
							}
							break;
						case XMLStreamConstants.END_DOCUMENT:
							if (lastConcept != null) {
								concepts.add(lastConcept);
							}
							if (lastAgent != null) {
								agents.add(lastAgent);
							}
							if (lastTimespan != null) {
								timespans.add(lastTimespan);
							}
							if (lastPlace != null) {
								places.add(lastPlace);
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
	private <T> T appendValue(String val, String edmLabel, Class<T> clazz, T obj)
			throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		RdfMethod rdf = null;
		for (RdfMethod rdfMethod : RdfMethod.values()) {
			if (StringUtils.equals(rdfMethod.getSolrField(), edmLabel)) {
				rdf = rdfMethod;
			}
		}

		//
		if (rdf.getMethodName().endsWith("List")) {

			Method mthd = clazz.getMethod(rdf.getMethodName());

			List lst = mthd.invoke(obj) != null ? (ArrayList) mthd.invoke(obj)
					: new ArrayList();
			if (rdf.getClazz().getSuperclass()
					.isAssignableFrom(ResourceType.class)) {

				ResourceType rs = new ResourceType();
				rs.setResource(val);
				lst.add(rs);

			} else if (rdf.getClazz().getSuperclass()
					.isAssignableFrom(ResourceOrLiteralType.class)) {
				ResourceOrLiteralType rs = new ResourceOrLiteralType();
				if (isURI(val)) {
					rs.setResource(val);
				} else {
					rs.setString(val);
				}
				lst.add(rs);
			} else if (rdf.getClazz().getSuperclass()
					.isAssignableFrom(LiteralType.class)) {
				LiteralType rs = new LiteralType();
				rs.setString(val);
				lst.add(rs);
			}

			Class<?>[] cls = new Class<?>[1];
			cls[0] = clazz;
			Method method = obj.getClass().getMethod(
					StringUtils.replace(rdf.getMethodName(), GET, SET), cls);
			method.invoke(obj, lst);
		} else {
			if (rdf.getClazz().isAssignableFrom(ResourceType.class)) {
				ResourceType rs = new ResourceType();
				rs.setResource(val);
				Class<?>[] cls = new Class<?>[1];
				cls[0] = rdf.getClazz();
				Method method = obj.getClass()
						.getMethod(
								StringUtils.replace(rdf.getMethodName(), GET,
										SET), cls);
				method.invoke(obj, rdf.returnObject(rdf.getClazz(), rs));
			} else if (rdf.getClazz().isAssignableFrom(LiteralType.class)) {
				LiteralType rs = new LiteralType();
				rs.setString(val);
				Class<?>[] cls = new Class<?>[1];
				cls[0] = rdf.getClazz();
				Method method = obj.getClass()
						.getMethod(
								StringUtils.replace(rdf.getMethodName(), GET,
										SET), cls);
				method.invoke(obj, rdf.returnObject(rdf.getClazz(), rs));

			} else if (rdf.getClazz().isAssignableFrom(
					ResourceOrLiteralType.class)) {
				ResourceOrLiteralType rs = new ResourceOrLiteralType();
				if (isURI(val)) {
					rs.setResource(val);
				} else {
					rs.setString(val);
				}
				Class<?>[] cls = new Class<?>[1];
				cls[0] = clazz;
				Method method = obj.getClass()
						.getMethod(
								StringUtils.replace(rdf.getMethodName(), GET,
										SET), cls);
				method.invoke(obj, rdf.returnObject(rdf.getClazz(), rs));
			}
		}

		//
		return obj;
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
	private Concept appendConceptValue(String val, String edmLabel,
			Concept concept) throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		RdfMethod rdf = null;
		for (RdfMethod rdfMethod : RdfMethod.values()) {
			if (StringUtils.equals(rdfMethod.getSolrField(), edmLabel)) {
				rdf = rdfMethod;
			}
		}
		List<Concept.Choice> lst = concept.getChoiceList() != null ? concept
				.getChoiceList() : new ArrayList<Concept.Choice>();
		if (rdf.getClazz().getSuperclass().isAssignableFrom(ResourceType.class)) {

			ResourceType obj = new ResourceType();
			obj.setResource(val);
			Class<?>[] cls = new Class<?>[1];
			cls[0] = rdf.getClazz();
			Concept.Choice choice = new Concept.Choice();
			Method method = choice.getClass().getMethod(
					StringUtils.replace(rdf.getMethodName(), GET, SET), cls);
			method.invoke(choice, rdf.returnObject(rdf.getClazz(), obj));
			lst.add(choice);

		} else if (rdf.getClazz().getSuperclass()
				.isAssignableFrom(ResourceOrLiteralType.class)) {

			ResourceOrLiteralType obj = new ResourceOrLiteralType();

			if (isURI(val)) {
				obj.setResource(val);
			} else {
				obj.setString(val);
			}
			Class<?>[] cls = new Class<?>[1];
			cls[0] = rdf.getClazz();
			Concept.Choice choice = new Concept.Choice();
			Method method = choice.getClass().getMethod(
					StringUtils.replace(rdf.getMethodName(), GET, SET), cls);
			method.invoke(choice, rdf.returnObject(rdf.getClazz(), obj));
			lst.add(choice);

		} else if (rdf.getClazz().getSuperclass()
				.isAssignableFrom(LiteralType.class)) {
			LiteralType obj = new LiteralType();
			obj.setString(val);
			Class<?>[] cls = new Class<?>[1];
			cls[0] = rdf.getClazz();
			Concept.Choice choice = new Concept.Choice();
			Method method = choice.getClass().getMethod(
					StringUtils.replace(rdf.getMethodName(), GET, SET), cls);
			method.invoke(choice, rdf.returnObject(rdf.getClazz(), obj));
			lst.add(choice);
		}
		concept.setChoiceList(lst);
		return concept;
	}

	/**
	 * Retrieve the value of a field. The value retrieved is part of the
	 * EdmLabel enumeration
	 * 
	 * @param field
	 *            The field to check
	 * @return The EdmLabel the field has been mapped to
	 */
	public List<EdmLabel> getEdmLabel(String field) {

		return vocabulary != null ? vocabulary.getElements().get(field)
				: new ArrayList<EdmLabel>();
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
		HashMap<String, List<EdmLabel>> elements = vocabulary.getElements() != null ? (HashMap<String, List<EdmLabel>>) vocabulary
				.getElements() : new HashMap<String, List<EdmLabel>>();
		List<EdmLabel> field = elements.get(fieldToMap);
		if (europeanaField == null) {
			elements.put(fieldToMap, new ArrayList<EdmLabel>());
		} else if (!field.contains(europeanaField)) {
			field.add(europeanaField);
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
			if (europeanaField.equals(vocabulary.getElements().get(key))) {
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
			for (Entry<String, List<EdmLabel>> entry : vocabulary.getElements()
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
	public HashMap<String, List<EdmLabel>> readSchema(String location) {

		vocabulary.setElements(readFromFile(location));
		return (HashMap<String, List<EdmLabel>>) vocabulary.getElements();
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
	public boolean saveMapping() {
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
				mongoServer.getDatastore().update(updateQuery, ops);
			} else {
				mongoServer.getDatastore().save(vocabulary);
			}
			return true;
		}
		return false;
	}

	
	private boolean validate(ControlledVocabulary voc) {

		Map<String, List<EdmLabel>> elems = voc.getElements();
		List<EdmLabel> validateList = new ArrayList<EdmLabel>();
		for (Entry<String, List<EdmLabel>> entry : elems.entrySet()) {
			if (entry.getValue()!=null){
			validateList.addAll(entry.getValue());
			} else {
				//if it is null ignore for validation and normalize
				
				elems.put(entry.getKey(), new ArrayList<EdmLabel>());
			}
		}

		for (EdmLabel label : validateList) {
			if (label.toString().startsWith("ag")) {
				if (validateList.indexOf(EdmLabel.EDM_AGENT) == -1) {
					return false;
				}
			} else if (label.toString().startsWith("cc")) {
				if (validateList.indexOf(EdmLabel.SKOS_CONCEPT) == -1) {
					return false;
				}
			} else if (label.toString().startsWith("ts")) {
				if (validateList.indexOf(EdmLabel.EDM_TIMESPAN) == -1) {
					return false;
				}
			} else if (label.toString().startsWith("pl")) {
				if (validateList.indexOf(EdmLabel.EDM_PLACE) == -1) {
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
	private HashMap<String, List<EdmLabel>> readFromFile(String localLocation) {
		HashMap<String, List<EdmLabel>> elements = new HashMap<String, List<EdmLabel>>();
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
					elements.put(element, new ArrayList<EdmLabel>());
					int i = 0;
					while (i < xml.getAttributeCount()) {
						attribute = element + "_" + xml.getAttributePrefix(i)
								+ ":" + xml.getAttributeLocalName(i);
						elements.put(attribute, new ArrayList<EdmLabel>());
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
