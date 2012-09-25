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
	 * @throws InvocationTargetException 
	 * @throws NoSuchMethodException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 * @throws MalformedURLException
	 * @throws IOException
	 */
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
								if (StringUtils.equals(getEdmLabel(element),
										"skos_concept")) {
									if (xml.hasText()) {
										if (lastConcept != null) {
											concepts.add(lastConcept);
										}
										lastConcept = createNewEntity(
												Concept.class,
												xml.getElementText());

									}
								} else if (StringUtils.equals(
										getEdmLabel(element), "edm_agent")) {
									if (xml.hasText()) {
										if (lastAgent != null) {
											agents.add(lastAgent);
										}
										lastAgent = createNewEntity(
												AgentType.class,
												xml.getElementText());

									}
								} else if (StringUtils.equals(
										getEdmLabel(element), "edm_timespan")) {
									if (xml.hasText()) {
										if (lastTimespan != null) {
											timespans.add(lastTimespan);
										}
										lastTimespan = createNewEntity(
												TimeSpanType.class,
												xml.getElementText());

									}
								} else if (StringUtils.equals(
										getEdmLabel(element), "edm_place")) {
									if (xml.hasText()) {
										if (lastPlace != null) {
											places.add(lastPlace);
										}
										lastPlace = createNewEntity(
												PlaceType.class,
												xml.getElementText());

									}
								}

								if (xml.getAttributeCount() > 0) {
									String attribute = xml
											.getAttributePrefix(0)
											+ ":"
											+ xml.getAttributeLocalName(0);
									// Is the attribute mapped?
									if (isMapped(element + "_" + attribute)) {

										if (StringUtils.equals(
												getEdmLabel(element + "_"
														+ attribute),
												"skos_concept")) {
											if (lastConcept != null) {
												concepts.add(lastConcept);
											}

											lastConcept = createNewEntity(
													Concept.class,
													xml.getAttributeValue(0));
										} else

										if (StringUtils.equals(
												getEdmLabel(element + "_"
														+ attribute),
												"edm_agent")) {
											if (lastAgent != null) {
												agents.add(lastAgent);
											}

											lastAgent = createNewEntity(
													AgentType.class,
													xml.getAttributeValue(0));
										} else

										if (StringUtils.equals(
												getEdmLabel(element + "_"
														+ attribute),
												"edm_timespan")) {
											if (lastTimespan != null) {
												timespans.add(lastTimespan);
											}

											lastTimespan = createNewEntity(
													TimeSpanType.class,
													xml.getAttributeValue(0));
										} else

										if (StringUtils.equals(
												getEdmLabel(element + "_"
														+ attribute),
												"edm_place")) {
											if (lastPlace != null) {
												places.add(lastPlace);
											}
											lastPlace = createNewEntity(
													PlaceType.class,
													xml.getAttributeValue(0));
										} else {
											if (StringUtils.startsWith(
													getEdmLabel(element + "_"
															+ attribute), "cc")) {
												appendConceptValue(
														xml.getAttributeValue(0),
														getEdmLabel(element
																+ "_"
																+ attribute),
														lastConcept);
											}

											else if (StringUtils.startsWith(
													getEdmLabel(element + "_"
															+ attribute), "ts")) {
												appendValue(
														xml.getAttributeValue(0),
														getEdmLabel(element
																+ "_"
																+ attribute),
														TimeSpanType.class,
														lastTimespan);
											} else if (StringUtils.startsWith(
													getEdmLabel(element + "_"
															+ attribute), "ag")) {
												appendValue(
														xml.getAttributeValue(0),
														getEdmLabel(element
																+ "_"
																+ attribute),
														AgentType.class,
														lastAgent);
											} else if (StringUtils.startsWith(
													getEdmLabel(element + "_"
															+ attribute), "pl")) {
												appendValue(
														xml.getAttributeValue(0),
														getEdmLabel(element
																+ "_"
																+ attribute),
														PlaceType.class,
														lastPlace);
											}
										}

									}
									// Since the attribute is not mapped
									else {

										if (StringUtils.equals(
												getEdmLabel(element),
												"skos_concept")) {
											if (xml.hasText()) {
												if (lastConcept != null) {
													concepts.add(lastConcept);
												}
												lastConcept = createNewEntity(
														Concept.class,
														xml.getElementText());

											}
										} else if (StringUtils.equals(
												getEdmLabel(element), "edm_agent")) {
											if (xml.hasText()) {
												if (lastAgent != null) {
													agents.add(lastAgent);
												}
												lastAgent = createNewEntity(
														AgentType.class,
														xml.getElementText());

											}
										} else if (StringUtils.equals(
												getEdmLabel(element),
												"edm_timespan")) {
											if (xml.hasText()) {
												if (lastTimespan != null) {
													timespans.add(lastTimespan);
												}
												lastTimespan = createNewEntity(
														TimeSpanType.class,
														xml.getElementText());

											}
										} else if (StringUtils.equals(
												getEdmLabel(element), "edm_place")) {
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
												getEdmLabel(element), "cc")) {
											appendConceptValue(
													xml.getElementText(),
													getEdmLabel(element),
													lastConcept);
										}

										else if (StringUtils.startsWith(
												getEdmLabel(element), "ts")) {
											appendValue(
													xml.getElementText(),
													getEdmLabel(element),
													TimeSpanType.class,
													lastTimespan);
										} else if (StringUtils.startsWith(
												getEdmLabel(element), "ag")) {
											appendValue(
													xml.getElementText(),
													getEdmLabel(element),
													AgentType.class, lastAgent);
										} else if (StringUtils.startsWith(
												getEdmLabel(element), "pl")) {
											appendValue(
													xml.getElementText(),
													getEdmLabel(element),
													PlaceType.class, lastPlace);

										}
									}

								}
								// Since it does not have attributes
								else {
									if (StringUtils.equals(
											getEdmLabel(element),
											"skos_concept")) {
										if (xml.hasText()) {
											if (lastConcept != null) {
												concepts.add(lastConcept);
											}
											lastConcept = createNewEntity(
													Concept.class,
													xml.getElementText());

										}
									} else if (StringUtils.equals(
											getEdmLabel(element), "edm_agent")) {
										if (xml.hasText()) {
											if (lastAgent != null) {
												agents.add(lastAgent);
											}
											lastAgent = createNewEntity(
													AgentType.class,
													xml.getElementText());

										}
									} else if (StringUtils.equals(
											getEdmLabel(element),
											"edm_timespan")) {
										if (xml.hasText()) {
											if (lastTimespan != null) {
												timespans.add(lastTimespan);
											}
											lastTimespan = createNewEntity(
													TimeSpanType.class,
													xml.getElementText());

										}
									} else if (StringUtils.equals(
											getEdmLabel(element), "edm_place")) {
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
											getEdmLabel(element), "cc")) {
										if (xml.hasText()) {
											appendConceptValue(
													xml.getElementText(),
													getEdmLabel(element),
													lastConcept);
										}
									}

									else if (StringUtils.startsWith(
											getEdmLabel(element), "ts")) {
										if (xml.hasText()) {
											appendValue(xml.getElementText(),
													getEdmLabel(element),
													TimeSpanType.class,
													lastTimespan);
										}
									} else if (StringUtils.startsWith(
											getEdmLabel(element), "ag")) {
										if (xml.hasText()) {
											appendValue(xml.getElementText(),
													getEdmLabel(element),
													AgentType.class, lastAgent);
										}
									} else if (StringUtils.startsWith(
											getEdmLabel(element), "pl")) {
										if (xml.hasText()) {
											appendValue(xml.getElementText(),
													getEdmLabel(element),
													PlaceType.class, lastPlace);
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

										if (StringUtils.equals(
												getEdmLabel(element + "_"
														+ attribute),
												"skos_concept")) {
											if (lastConcept != null) {
												concepts.add(lastConcept);
											}

											lastConcept = createNewEntity(
													Concept.class,
													xml.getAttributeValue(0));
										} else

										if (StringUtils.equals(
												getEdmLabel(element + "_"
														+ attribute),
												"edm_agent")) {
											if (lastAgent != null) {
												agents.add(lastAgent);
											}

											lastAgent = createNewEntity(
													AgentType.class,
													xml.getAttributeValue(0));
										} else

										if (StringUtils.equals(
												getEdmLabel(element + "_"
														+ attribute),
												"edm_timespan")) {
											if (lastTimespan != null) {
												timespans.add(lastTimespan);
											}

											lastTimespan = createNewEntity(
													TimeSpanType.class,
													xml.getAttributeValue(0));
										} else

										if (StringUtils.equals(
												getEdmLabel(element + "_"
														+ attribute),
												"edm_place")) {
											if (lastPlace != null) {
												places.add(lastPlace);
											}
											lastPlace = createNewEntity(
													PlaceType.class,
													xml.getAttributeValue(0));
										} else {
											if (StringUtils.startsWith(
													getEdmLabel(element + "_"
															+ attribute), "cc")) {
												appendConceptValue(
														xml.getAttributeValue(0),
														getEdmLabel(element
																+ "_"
																+ attribute),
														lastConcept);
											}

											else if (StringUtils.startsWith(
													getEdmLabel(element + "_"
															+ attribute), "ts")) {
												appendValue(
														xml.getAttributeValue(0),
														getEdmLabel(element
																+ "_"
																+ attribute),
														TimeSpanType.class,
														lastTimespan);
											} else if (StringUtils.startsWith(
													getEdmLabel(element + "_"
															+ attribute), "ag")) {
												appendValue(
														xml.getAttributeValue(0),
														getEdmLabel(element
																+ "_"
																+ attribute),
														AgentType.class,
														lastAgent);
											} else if (StringUtils.startsWith(
													getEdmLabel(element + "_"
															+ attribute), "pl")) {
												appendValue(
														xml.getAttributeValue(0),
														getEdmLabel(element
																+ "_"
																+ attribute),
														PlaceType.class,
														lastPlace);
											}
										}
									}
								}
							}
							break;
						case XMLStreamConstants.END_DOCUMENT:
							if (lastConcept != null)
								concepts.add(lastConcept);
							if (lastAgent != null)
								agents.add(lastAgent);
							if (lastTimespan != null)
								timespans.add(lastTimespan);
							if (lastPlace != null)
								places.add(lastPlace);
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
						.createUpdateOperations(EntityImpl.class).set(
								"content", val);
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

	private <T> T appendValue(String val, String edmLabel, Class<T> clazz, T obj)
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

			List lst = mthd.invoke(obj) != null ? (ArrayList) mthd.invoke(obj)
					: new ArrayList();
			if (RDF.getClazz().getSuperclass()
					.isAssignableFrom(ResourceType.class)) {

				ResourceType rs = new ResourceType();
				rs.setResource(val);
				lst.add(rs);

			} else if (RDF.getClazz().getSuperclass()
					.isAssignableFrom(ResourceOrLiteralType.class)) {
				ResourceOrLiteralType rs = new ResourceOrLiteralType();
				if (isURI(val)) {
					rs.setResource(val);
				} else {
					rs.setString(val);
				}
				lst.add(rs);
			} else if (RDF.getClazz().getSuperclass()
					.isAssignableFrom(LiteralType.class)) {
				LiteralType rs = new LiteralType();
				rs.setString(val);
				lst.add(rs);
			}

			Class<?>[] cls = new Class<?>[1];
			cls[0] = clazz;
			Method method = obj.getClass()
					.getMethod(
							StringUtils.replace(RDF.getMethodName(), "get",
									"set"), cls);
			method.invoke(obj, lst);
		} else {
			if (RDF.getClazz().isAssignableFrom(ResourceType.class)) {
				ResourceType rs = new ResourceType();
				rs.setResource(val);
				Class<?>[] cls = new Class<?>[1];
				cls[0] = RDF.getClazz();
				Method method = obj.getClass().getMethod(
						StringUtils.replace(RDF.getMethodName(), "get", "set"),
						cls);
				method.invoke(obj, RDF.returnObject(RDF.getClazz(), rs));
			} else if (RDF.getClazz().isAssignableFrom(LiteralType.class)) {
				LiteralType rs = new LiteralType();
				rs.setString(val);
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
					rs.setResource(val);
				} else {
					rs.setString(val);
				}
				Class<?>[] cls = new Class<?>[1];
				cls[0] = clazz;
				Method method = obj.getClass().getMethod(
						StringUtils.replace(RDF.getMethodName(), "get", "set"),
						cls);
				method.invoke(obj, RDF.returnObject(RDF.getClazz(), rs));
			}
		}

		//
		return obj;
	}

	private static boolean isURI(String uri) {

		try {
			new URL(uri);
			return true;
		} catch (MalformedURLException e) {
			return false;
		}

	}
	private Concept appendConceptValue(String val, String edmLabel,
			Concept concept) throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		RdfMethod RDF = null;
		for (RdfMethod rdfMethod : RdfMethod.values()) {
			if (StringUtils.equals(rdfMethod.getSolrField(), edmLabel)) {
				RDF = rdfMethod;
			}
		}
		List<Concept.Choice> lst = concept.getChoiceList() != null ? concept
				.getChoiceList() : new ArrayList<Concept.Choice>();
		if (RDF.getClazz().getSuperclass().isAssignableFrom(ResourceType.class)) {

			ResourceType obj = new ResourceType();
			obj.setResource(val);
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
				obj.setResource(val);
			} else {
				obj.setString(val);
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
