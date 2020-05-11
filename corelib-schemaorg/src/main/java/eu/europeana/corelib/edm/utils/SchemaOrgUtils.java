package eu.europeana.corelib.edm.utils;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import eu.europeana.corelib.edm.model.schemaorg.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.europeana.corelib.definitions.edm.entity.Agent;
import eu.europeana.corelib.definitions.edm.entity.Aggregation;
import eu.europeana.corelib.definitions.edm.entity.Concept;
import eu.europeana.corelib.definitions.edm.entity.ContextualClass;
import eu.europeana.corelib.definitions.edm.entity.WebResource;
import eu.europeana.corelib.solr.bean.impl.FullBeanImpl;
import eu.europeana.corelib.solr.entity.AgentImpl;
import eu.europeana.corelib.solr.entity.AggregationImpl;
import eu.europeana.corelib.solr.entity.ConceptImpl;
import eu.europeana.corelib.solr.entity.PlaceImpl;
import eu.europeana.corelib.solr.entity.ProvidedCHOImpl;
import eu.europeana.corelib.solr.entity.ProxyImpl;
import eu.europeana.corelib.solr.entity.TimespanImpl;
import eu.europeana.corelib.utils.DateUtils;
import eu.europeana.corelib.utils.EuropeanaUriUtils;

public final class SchemaOrgUtils {

    private static final Logger LOG = LogManager.getLogger(SchemaOrgUtils.class);

    private static final String URL_PREFIX = "http://data.europeana.eu";

    private static final String PLACE_PREFIX = "http://data.europeana.eu/place";

    private static final String TIMESPAN_PREFIX = "http://semium.org";

    private static final String UNIT_CODE_E37 = "E37";

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy",
            Locale.ENGLISH);

    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);

    protected static List<String> linkedContextualEntities = new ArrayList<>();


    private SchemaOrgUtils() {
        // empty constructor prevent initialization
    }

    /**
     * Convert full bean to schema.org jsonld
     *
     * @param bean bean with metadata
     * @return string representation of schema.org object
     */
    public static String toSchemaOrg(FullBeanImpl bean) {
        String jsonld = null;

        List<Thing> objectsToSerialize = new ArrayList<>();
        Thing object = SchemaOrgTypeFactory.createObject(bean);
        objectsToSerialize.add(object);
        getSchemaOrg(bean, objectsToSerialize, object);
        JsonLdSerializer serializer = new JsonLdSerializer();
        try {
            jsonld = serializer.serialize(objectsToSerialize);
        } catch (IOException e) {
            LOG.error("Serialization to schema.org failed for " + object.getId(), e);
        }

        return jsonld;
    }

    protected static void getSchemaOrg(FullBeanImpl bean, List<Thing> objectsToSerialize, Thing object) {
        processProvidedCHO((CreativeWork) object, bean);
        processProxies((CreativeWork) object, bean);
        objectsToSerialize.addAll(processAggregations((CreativeWork) object, bean));
        //remove duplicates from the linkedContextualEntities
        removeDuplicates(linkedContextualEntities);
        objectsToSerialize.addAll(processAgents(bean.getAgents()));
        objectsToSerialize.addAll(processPlaces(bean.getPlaces()));
        objectsToSerialize.addAll(processConcepts(bean.getConcepts()));
    }
    /**
     * Update properties of the given Schema.Org Thing using data from the given EDM
     * Contextual Class
     *
     * @param entity source EDM Contextual Class
     * @param thing  Schema.Org Contextual Entity to update
     */
    public static void processEntity(ContextualClass entity,
                                     eu.europeana.corelib.edm.model.schemaorg.ContextualEntity thing) {

        if (entity instanceof Concept) {
            SchemaOrgUtils.processConcept((Concept) entity, thing);
        } else if (entity instanceof eu.europeana.corelib.definitions.edm.entity.Place) {
            SchemaOrgUtils.processPlace((eu.europeana.corelib.definitions.edm.entity.Place) entity, (Place) thing);
        } else if (entity instanceof eu.europeana.corelib.definitions.edm.entity.Organization) {
            SchemaOrgUtils.processOrganization((eu.europeana.corelib.definitions.edm.entity.Organization) entity, thing);
        } else if (entity instanceof Agent) {
            SchemaOrgUtils.processAgent((Agent) entity, thing);
        }
    }

    /**
     * Process all concepts on the given list and create a Thing object for each.
     *
     * @param concepts concepts to be processed
     * @return list of created Thing objects for each concept
     */
    private static List<Thing> processConcepts(List<ConceptImpl> concepts) {
        List<Thing> referencedObjects = new ArrayList<>();

        for (ConceptImpl concept : concepts) {
            if(linkedContextualEntities.contains(concept.getAbout())) {
                eu.europeana.corelib.edm.model.schemaorg.ContextualEntity conceptObject = new eu.europeana.corelib.edm.model.schemaorg.Concept();
                referencedObjects.add(conceptObject);

                processConcept(concept, conceptObject);
            }
        }
        return referencedObjects;
    }

    /**
     * Fill the properties of the Schema.Org Thing from equivalent attributes of EDM
     * concept.
     *
     * @param concept       source EDM concept
     * @param conceptObject Schema.Org Contextual Entity object in which the
     *                      properties will be filled in
     */
    public static void processConcept(Concept concept,
                                      eu.europeana.corelib.edm.model.schemaorg.ContextualEntity conceptObject) {
        // @id
        conceptObject.setId(concept.getAbout());

        // name
        addMultilingualProperties(conceptObject, concept.getPrefLabel(), SchemaOrgConstants.PROPERTY_NAME);

        // alternateName
        addMultilingualProperties(conceptObject, concept.getAltLabel(), SchemaOrgConstants.PROPERTY_ALTERNATE_NAME);

        // description
        addMultilingualProperties(conceptObject, concept.getNote(), SchemaOrgConstants.PROPERTY_DESCRIPTION);

        // sameAs
        addTextProperties(conceptObject, toList(concept.getExactMatch()), SchemaOrgConstants.PROPERTY_SAME_AS);

        // url
        if(StringUtils.startsWithIgnoreCase(concept.getAbout(), URL_PREFIX)) {
            String entityPageUrl = String.format(SchemaOrgConstants.ENTITY_PAGE_URL_CONCEPT_PATTERN,
                    concept.getEntityIdentifier());
            conceptObject.setEntityPageUrl(entityPageUrl);
        }
        // image
        conceptObject.setImage(concept.getFoafDepiction());
    }

    /**
     * Process list of places and create Place for each.
     *
     * @param places places list to process
     * @return list of Place objects created from the input list
     */
    private static List<Thing> processPlaces(List<PlaceImpl> places) {
        List<Thing> referencedObjects = new ArrayList<>();

        for (PlaceImpl place : places) {
            if(linkedContextualEntities.contains(place.getAbout())) {
                Place placeObject = new Place();
                referencedObjects.add(placeObject);

                processPlace(place, placeObject);
            }
        }
        return referencedObjects;
    }

    /**
     * Update the properties of the given Schema.Org Place using data from EDM place
     *
     * @param edmPlace    source EDM place
     * @param placeObject Schema.Org Place object to update
     */
    public static void processPlace(eu.europeana.corelib.definitions.edm.entity.Place edmPlace, Place placeObject) {
        if (edmPlace == null) {
            return;
        }

        // @id
        placeObject.setId(edmPlace.getAbout());

        // name
        addMultilingualProperties(placeObject, edmPlace.getPrefLabel(), SchemaOrgConstants.PROPERTY_NAME);

        // alternateName
        addMultilingualProperties(placeObject, edmPlace.getAltLabel(), SchemaOrgConstants.PROPERTY_ALTERNATE_NAME);

        // geo
        createGeoCoordinates(edmPlace, placeObject);

        // description
        addMultilingualProperties(placeObject, edmPlace.getNote(), SchemaOrgConstants.PROPERTY_DESCRIPTION);

        // containsPlace
        addReferences(placeObject, edmPlace.getDcTermsHasPart(), SchemaOrgConstants.PROPERTY_CONTAINS_PLACE,
                Place.class);

        // containedInPlace
        addReferences(placeObject, edmPlace.getIsPartOf(), SchemaOrgConstants.PROPERTY_CONTAINED_IN_PLACE, Place.class);

        // sameAs
        addTextProperties(placeObject, toList(edmPlace.getOwlSameAs()), SchemaOrgConstants.PROPERTY_SAME_AS);

        // url
        if(StringUtils.startsWithIgnoreCase(edmPlace.getAbout(), URL_PREFIX)) {
            String entityPageUrl = String.format(SchemaOrgConstants.ENTITY_PAGE_URL_PLACE_PATTERN,
                    edmPlace.getEntityIdentifier());
            // Not available yet
            // placeObject.setEntityPageUrl(entityPageUrl);
        }
        // image
        placeObject.setImage(edmPlace.getFoafDepiction());
    }

    /**
     * Create GeoCoordinates object for the latitude, longitude and altitude taken
     * from EDM Place and set the corresponding properties in Schema.Org Place.
     *
     * @param edmPlace    EDM Place with necessary data
     * @param placeObject Schema.Org Place object to update
     */
    private static void createGeoCoordinates(eu.europeana.corelib.definitions.edm.entity.Place edmPlace,
                                             Place placeObject) {
        GeoCoordinates geoCoordinates = new GeoCoordinates();

        // latitude
        if (edmPlace.getLatitude() != null) {
            geoCoordinates.addLatitude(new Text(String.valueOf(edmPlace.getLatitude())));
        }
        // TODO Try to retrieve the latitude from position if possible

        // longitude
        if (edmPlace.getLongitude() != null) {
            geoCoordinates.addLongitude(new Text(String.valueOf(edmPlace.getLongitude())));
        }
        // TODO Try to retrieve the longitude from position if possible

        // elevation
        if (edmPlace.getAltitude() != null) {
            geoCoordinates.addElevation(new Text(String.valueOf(edmPlace.getAltitude())));
        }

        // geo
        placeObject.addGeo(geoCoordinates);
    }

    /**
     * Process agents from the given list and create a proper schema.org object for
     * each
     *
     * @param agents agents to process
     * @return list of Person and / or Organization objects created from given
     * agents
     */
    private static List<Thing> processAgents(List<AgentImpl> agents) {
        List<Thing> referencedObjects = new ArrayList<>();
        for (Agent agent : agents) {
             if(linkedContextualEntities.contains(agent.getAbout())) {
                 eu.europeana.corelib.edm.model.schemaorg.ContextualEntity agentObject = SchemaOrgTypeFactory
                         .createAgent(agent);
                 referencedObjects.add(agentObject);

                 processAgent(agent, agentObject);
             }
        }
        return referencedObjects;
    }

    /**
     * Update properties of the given Schema.Org Thing (Person or Organization)
     * using data from the given EDM Agent
     *
     * @param agentObject Schema.Org Contextual Entity object to update
     * @param agent       source EDM agent
     */
    public static void processAgent(Agent agent,
                                    eu.europeana.corelib.edm.model.schemaorg.ContextualEntity agentObject) {
        // @id
        agentObject.setId(agent.getAbout());

        // name
        addMultilingualProperties(agentObject, agent.getPrefLabel(), SchemaOrgConstants.PROPERTY_NAME);
        addMultilingualProperties(agentObject, agent.getFoafName(), SchemaOrgConstants.PROPERTY_NAME);

        // alternateName
        addMultilingualProperties(agentObject, agent.getAltLabel(), SchemaOrgConstants.PROPERTY_ALTERNATE_NAME);

        // description
        addMultilingualProperties(agentObject, agent.getNote(), SchemaOrgConstants.PROPERTY_DESCRIPTION);
        addMultilingualProperties(agentObject, agent.getRdaGr2BiographicalInformation(),
                SchemaOrgConstants.PROPERTY_DESCRIPTION);

        if (agentObject instanceof Person) {

            // birthDate
            if (agent.getRdaGr2DateOfBirth() != null) {
                addStringProperties(agentObject, agent.getRdaGr2DateOfBirth(),
                        SchemaOrgConstants.PROPERTY_BIRTH_DATE);
            } else if (agent.getBegin() != null) {
                addStringProperties(agentObject, agent.getBegin(), SchemaOrgConstants.PROPERTY_BIRTH_DATE);
            }

            // deathDate
            if (agent.getRdaGr2DateOfDeath() != null) {
                addStringProperties(agentObject, agent.getRdaGr2DateOfDeath(),
                        SchemaOrgConstants.PROPERTY_DEATH_DATE);
            } else if (agent.getEnd() != null) {
                addStringProperties(agentObject, agent.getEnd(), SchemaOrgConstants.PROPERTY_DEATH_DATE);
            }

            // gender
            addStringProperties(agentObject, agent.getRdaGr2Gender(), SchemaOrgConstants.PROPERTY_GENDER);

            // jobTitle
            addMultilingualProperties(agentObject, agent.getRdaGr2ProfessionOrOccupation(),
                    SchemaOrgConstants.PROPERTY_JOB_TITLE);

            // birthPlace
            addResourceOrReferenceProperties(agentObject, agent.getRdaGr2PlaceOfBirth(),
                    SchemaOrgConstants.PROPERTY_BIRTH_PLACE, Place.class);

            // deathPlace
            addResourceOrReferenceProperties(agentObject, agent.getRdaGr2PlaceOfDeath(),
                    SchemaOrgConstants.PROPERTY_DEATH_PLACE, Place.class);
        }

        if (agentObject instanceof Organization) {
            // foundingDate
            if (agent.getRdaGr2DateOfEstablishment() != null) {
                addStringProperties(agentObject, agent.getRdaGr2DateOfEstablishment(),
                        SchemaOrgConstants.PROPERTY_FOUNDING_DATE);
            }

            // dissolutionDate
            if (agent.getRdaGr2DateOfTermination() != null) {
                addStringProperties(agentObject, agent.getRdaGr2DateOfTermination(),
                        SchemaOrgConstants.PROPERTY_DISSOLUTION_DATE);
            }
        }

        // sameAs
        addTextProperties(agentObject, toList(agent.getOwlSameAs()), SchemaOrgConstants.PROPERTY_SAME_AS);

        // url
        if(StringUtils.startsWithIgnoreCase(agent.getAbout(), URL_PREFIX)) {
            String entityPageUrl = String.format(SchemaOrgConstants.ENTITY_PAGE_URL_AGENT_PATTERN,
                    agent.getEntityIdentifier());
            agentObject.setEntityPageUrl(entityPageUrl);
        }
        // image
        agentObject.setImage(agent.getFoafDepiction());
    }

    /**
     * Update properties of the given Schema.Org Thing (EdmOrganization)
     * using data from the given EDM Organization
     *
     * @param entityObject Schema.Org Contextual Entity object to update
     * @param organization source EDM Organization
     */
    public static void processOrganization(eu.europeana.corelib.definitions.edm.entity.Organization organization,
                                           eu.europeana.corelib.edm.model.schemaorg.ContextualEntity entityObject) {
        // @id
        entityObject.setId(organization.getAbout());

        // name
        addMultilingualProperties(entityObject, organization.getPrefLabel(), SchemaOrgConstants.PROPERTY_NAME);

        // alternateName
        addMultilingualProperties(entityObject, organization.getAltLabel(), SchemaOrgConstants.PROPERTY_ALTERNATE_NAME);
        addMultilingualProperties(entityObject, organization.getEdmAcronym(), SchemaOrgConstants.PROPERTY_ALTERNATE_NAME);

        // description
        addMultilingualProperties(entityObject, SchemaOrgConstants.PROPERTY_DESCRIPTION, organization.getDcDescription());

        // mainEntityOfPage
        addTextProperties(entityObject, Arrays.asList(organization.getFoafHomepage()), SchemaOrgConstants.PROPERTY_MAIN_ENTITY_OF_PAGE);

        // logo
        addTextProperties(entityObject, Arrays.asList(organization.getFoafLogo()), SchemaOrgConstants.PROPERTY_LOGO);

        // telephone
        addTextProperties(entityObject, organization.getFoafPhone(), SchemaOrgConstants.PROPERTY_TELEPHONE);

        // address
        createPostalAddress(organization, (EdmOrganization) entityObject);

        // identifier
        List<String> dcIdentifier = null;
        if (organization.getDcIdentifier() != null && organization.getDcIdentifier().containsKey(SchemaOrgConstants.DEFAULT_LANGUAGE)) {
            dcIdentifier = organization.getDcIdentifier().get(SchemaOrgConstants.DEFAULT_LANGUAGE);
            addTextProperties(entityObject, dcIdentifier, SchemaOrgConstants.PROPERTY_IDENTIFIER);
        }

        // sameAs
        addTextProperties(entityObject, toList(organization.getOwlSameAs()), SchemaOrgConstants.PROPERTY_SAME_AS);

        // url
        if(StringUtils.startsWithIgnoreCase(organization.getAbout(), URL_PREFIX)) {
            String entityPageUrl = String.format(SchemaOrgConstants.ENTITY_PAGE_URL_EDM_ORGANIZATION_PATTERN,
                    organization.getEntityIdentifier());
            entityObject.setEntityPageUrl(entityPageUrl);
        }

        // image
        entityObject.setImage(organization.getFoafDepiction());
    }

    /**
     * Create PostalAddress object for the properties taken
     * from EDM Organization and set the corresponding properties in Schema.Org EdmOrganization.
     *
     * @param organization       EDM Organization with necessary data
     * @param organizationObject Schema.Org EdmOrganization object to update
     */
    private static void createPostalAddress(eu.europeana.corelib.definitions.edm.entity.Organization organization,
                                            EdmOrganization organizationObject) {
        PostalAddress postalAddress = new PostalAddress();

        eu.europeana.corelib.definitions.edm.entity.Address address = organization.getAddress();
        if (address == null)
            return;

        // id
        postalAddress.setId(address.getAbout());

        // streetAddress
        addTextProperties(postalAddress, Arrays.asList(address.getVcardStreetAddress()), SchemaOrgConstants.PROPERTY_STREET_ADDRESS);

        // postalCode
        addTextProperties(postalAddress, Arrays.asList(address.getVcardPostalCode()), SchemaOrgConstants.PROPERTY_POSTAL_CODE);

        // postOfficeBoxNumber
        addTextProperties(postalAddress, Arrays.asList(address.getVcardPostOfficeBox()), SchemaOrgConstants.PROPERTY_POST_OFFICE_BOX_NUMBER);

        // addressLocality
        addTextProperties(postalAddress, Arrays.asList(address.getVcardLocality()), SchemaOrgConstants.PROPERTY_ADDRESS_LOCALITY);

        // addressRegion
        //addTextProperties(postalAddress, Arrays.asList(address.get), SchemaOrgConstants.PROPERTY_ADDRESS_REGION);

        // addressCountry
        addTextProperties(postalAddress, Arrays.asList(address.getVcardCountryName()), SchemaOrgConstants.PROPERTY_ADDRESS_COUNTRY);

        // postalAddress
        organizationObject.addAddress(postalAddress);
    }

    /**
     * Process necessary properties from provided CHOs
     *
     * @param object object for which the properties will be added
     * @param bean   bean with all properties
     */
    private static void processProvidedCHO(CreativeWork object, FullBeanImpl bean) {
        for (ProvidedCHOImpl providedCHO : bean.getProvidedCHOs()) {
            // @id
            if (!notNullNorEmpty(object.getId())) {
                object.setId(URL_PREFIX + providedCHO.getAbout());
            }
            // sameAs
            addTextProperties(object, toList(providedCHO.getOwlSameAs()), SchemaOrgConstants.PROPERTY_SAME_AS);

            if (bean.getEuropeanaAggregation() != null) {
                // url
                if (notNullNorEmpty(bean.getEuropeanaAggregation().getEdmLandingPage())) {
                    object.addUrl(new Text(bean.getEuropeanaAggregation().getEdmLandingPage()));
                }

                // thumbnailUrl
                if (notNullNorEmpty(bean.getEuropeanaAggregation().getEdmPreview())) {
                    object.addThumbnailUrl(new Text(bean.getEuropeanaAggregation().getEdmPreview()));
                }
            }
        }
    }

    /**
     * Process necessary properties from Aggregations
     *
     * @param object object for which the properties will be added
     * @param bean   bean with all properties
     * @return list of referenced objects created while processing aggregations
     */
    private static List<Thing> processAggregations(CreativeWork object, FullBeanImpl bean) {
        List<Thing> referencedObjects = new ArrayList<>();
        for (AggregationImpl aggregation : bean.getAggregations()) {
            // sameAs
            if (aggregation.getEdmIsShownAt() != null) {
                object.addSameAs(new Text(aggregation.getEdmIsShownAt()));
            }

            // provider
            Map<String, List<String>> providerMap = new HashMap<>();
            addDistinctValues(providerMap, aggregation.getEdmDataProvider());
            addDistinctValues(providerMap, aggregation.getEdmProvider());
            addDistinctValues(providerMap, aggregation.getEdmIntermediateProvider());
            addResourceOrReferenceProperties(object, providerMap, SchemaOrgConstants.PROPERTY_PROVIDER,
                    Organization.class);

            // associatedMedia
            Set<String> medias = new HashSet<>();
            addDistinctValues(medias, aggregation.getHasView());
            addDistinctValues(medias, aggregation.getEdmIsShownBy());
            referencedObjects.addAll(processWebResources(object, medias, aggregation, bean));
        }
        return referencedObjects;
    }

    /**
     * For each web resource that has non null mime type and was found on hasView or
     * edm:isShownBy creates a reference in associatedMedia property and creates the
     * corresponding MediaObject with additional information. Each MediaObject is of
     * the specific subclass based on the mime type.
     *
     * @param object      object to add references
     * @param mediaUrls   urls of web resources retrieved from hasView and
     *                    edm:isShownBy
     * @param aggregation current aggregation
     * @param bean
     * @return a list of created referenced media objects
     */
    private static List<Thing> processWebResources(CreativeWork object, Set<String> mediaUrls, Aggregation aggregation,
                                                   FullBeanImpl bean) {
        List<Thing> mediaObjects = new ArrayList<>();
        for (WebResource resource : aggregation.getWebResources()) {
            if (resource.getEbucoreHasMimeType() != null && mediaUrls.contains(resource.getAbout())) {
                MediaObject mediaObject = SchemaOrgTypeFactory.createMediaObject(resource.getEbucoreHasMimeType());
                addReference(object, resource.getAbout(), SchemaOrgConstants.PROPERTY_ASSOCIATED_MEDIA,
                        mediaObject.getClass());

                processWebResource(mediaObject, resource, bean, aggregation);

                mediaObjects.add(mediaObject);
            }
        }
        return mediaObjects;
    }

    /**
     * Adds specific properties to the media object based on the web resource
     *
     * @param mediaObject media object to which the properties will be added
     * @param resource    web resource to retrieve property values
     * @param bean        bean with all the data needed to generate some mappings
     * @param aggregation aggregation object needed to generate some mappings
     */
    private static void processWebResource(MediaObject mediaObject, WebResource resource, FullBeanImpl bean,
                                           Aggregation aggregation) {
        // @id
        mediaObject.setId(resource.getAbout());

        // name
        for (ProxyImpl proxy : bean.getProxies()) {
            addMultilingualProperties(mediaObject, proxy.getDcTitle(), SchemaOrgConstants.PROPERTY_NAME);
        }

        if (mediaObject instanceof VideoObject) {
            if (bean.getEuropeanaAggregation() != null
                    && notNullNorEmpty(bean.getEuropeanaAggregation().getEdmPreview())) {
                // thumbnailUrl (only for VideoObject)
                mediaObject.addThumbnailUrl(new Text(bean.getEuropeanaAggregation().getEdmPreview()));
            }

            // uploadDate (only for VideoObject)
            mediaObject.addUploadDate(new Text(DateUtils.format(bean.getTimestampCreated())));
        }

        // contentUrl
        mediaObject.addContentUrl( new Text(resource.getAbout()));

        // creator
        addResourceOrReferenceProperties(mediaObject, resource.getDcCreator(),
                SchemaOrgConstants.PROPERTY_CREATOR, Thing.class);

        // description::  if missing take ore:Proxy/dc:description or ore:Proxy.dc:title
        if(resource.getDcDescription() != null && !resource.getDcDescription().isEmpty()) {
            addMultilingualProperties(mediaObject, resource.getDcDescription(), SchemaOrgConstants.PROPERTY_DESCRIPTION);
        } else {
            for (ProxyImpl proxy : bean.getProxies()) {
                if(proxy.getDcDescription()!= null && !proxy.getDcDescription().isEmpty()) {
                    addMultilingualProperties(mediaObject, proxy.getDcDescription(), SchemaOrgConstants.PROPERTY_DESCRIPTION);
                } else if (proxy.getDcTitle()!= null && !proxy.getDcTitle().isEmpty()) {

                    addMultilingualProperties(mediaObject, proxy.getDcTitle(), SchemaOrgConstants.PROPERTY_DESCRIPTION);
                }
            }
        }

        // encodesCreativeWork
        if (!bean.getProvidedCHOs().isEmpty()) {
            addReference(mediaObject, URL_PREFIX + bean.getProvidedCHOs().get(0).getAbout(),
                    SchemaOrgConstants.PROPERTY_ENCODES_CREATIVE_WORK, null);
        }

        // dateCreated
        addDateProperty(mediaObject, resource.getDctermsCreated(), SchemaOrgConstants.PROPERTY_DATE_CREATED,
                bean.getTimespans(), false);

        // hasPart
        addReferences(mediaObject, resource.getDctermsHasPart(), SchemaOrgConstants.PROPERTY_HAS_PART, MediaObject.class);

        // isPartOf
        addReferences(mediaObject, resource.getDctermsIsPartOf(), SchemaOrgConstants.PROPERTY_IS_PART_OF, MediaObject.class);

        // datePublished
        addDateProperty(mediaObject, resource.getDctermsIssued(), SchemaOrgConstants.PROPERTY_DATE_PUBLISHED,
                bean.getTimespans(), false);

        // license
        if (resource.getWebResourceEdmRights() != null && !resource.getWebResourceEdmRights().isEmpty()) {
            addStringProperties(mediaObject, resource.getWebResourceEdmRights(),
                    SchemaOrgConstants.PROPERTY_LICENSE);
        } else {
            addStringProperties(mediaObject, aggregation.getEdmRights(), SchemaOrgConstants.PROPERTY_LICENSE);
        }

        // sameAs
        addTextProperties(mediaObject, toList(resource.getOwlSameAs()), SchemaOrgConstants.PROPERTY_SAME_AS);


        // encodingFormat
        mediaObject.addEncodingFormat(new Text(resource.getEbucoreHasMimeType()));

        // contentSize
        if (resource.getEbucoreFileByteSize() != null) {
            mediaObject.addContentSize(new Text(String.valueOf(resource.getEbucoreFileByteSize())));
        }

        if (!(mediaObject instanceof AudioObject)) {
            // height
            if (resource.getEbucoreHeight() != null) {
                addQuantitativeProperty(mediaObject, String.valueOf(resource.getEbucoreHeight()), UNIT_CODE_E37,
                        SchemaOrgConstants.PROPERTY_HEIGHT);
            }
            // width
            if (resource.getEbucoreHeight() != null) {
                addQuantitativeProperty(mediaObject, String.valueOf(resource.getEbucoreWidth()), UNIT_CODE_E37,
                        SchemaOrgConstants.PROPERTY_WIDTH);
            }
        }

        if (mediaObject instanceof AudioObject || mediaObject instanceof VideoObject) {
            // duration
            mediaObject.addDuration(new Text(createDuration(resource.getEbucoreDuration())));

            // bitrate
            mediaObject.addBitrate(new Text(String.valueOf(resource.getEbucoreBitRate())));
        }
    }

    /**
     * Process all proxies from the bean and add specific properties to the given
     * object.
     *
     * @param object object for which the properties will be added
     * @param bean   bean from database to get values for properties
     */
    private static void processProxies(CreativeWork object, FullBeanImpl bean) {
        for (ProxyImpl proxy : bean.getProxies()) {
            // contributor
            addResourceOrReferenceProperties(object, proxy.getDcContributor(),
                    SchemaOrgConstants.PROPERTY_CONTRIBUTOR, Thing.class);

            // about
            addResourceOrReferenceProperties(object, proxy.getDcSubject(), SchemaOrgConstants.PROPERTY_ABOUT,
                    Thing.class);
            addResourceOrReferenceProperties(object, proxy.getDcType(), SchemaOrgConstants.PROPERTY_ABOUT, Thing.class);
            addResourceOrReferenceProperties(object, proxy.getEdmHasType(), SchemaOrgConstants.PROPERTY_ABOUT,
                    Thing.class);
            addProperty(object, proxy.getEdmIsRepresentationOf(), "", SchemaOrgConstants.PROPERTY_ABOUT, null);
            // values from dc:coverage will be added later

            // creator
            addResourceOrReferenceProperties(object, proxy.getDcCreator(), SchemaOrgConstants.PROPERTY_CREATOR,
                    Thing.class);

            // description
            addMultilingualProperties(object, proxy.getDcDescription(), SchemaOrgConstants.PROPERTY_DESCRIPTION);

            // inLanguage
            addLanguageProperty(object, proxy.getDcLanguage(), SchemaOrgConstants.PROPERTY_IN_LANGUAGE, Language.class);

            // publisher
            addResourceOrReferenceProperties(object, proxy.getDcPublisher(),
                    SchemaOrgConstants.PROPERTY_PUBLISHER, Thing.class);

            // name
            addMultilingualProperties(object, proxy.getDcTitle(), SchemaOrgConstants.PROPERTY_NAME);

            // alternateName
            addMultilingualProperties(object, proxy.getDctermsAlternative(),
                    SchemaOrgConstants.PROPERTY_ALTERNATE_NAME);

            // dateCreated
            addDateProperty(object, proxy.getDctermsCreated(), SchemaOrgConstants.PROPERTY_DATE_CREATED,
                    bean.getTimespans(), true);

            // hasPart
            addResourceOrReferenceProperties(object, proxy.getDctermsHasPart(),
                    SchemaOrgConstants.PROPERTY_HAS_PART, CreativeWork.class);
            addMultilingualPropertiesWithReferences(object, toList(proxy.getEdmIncorporates()), "",
                    SchemaOrgConstants.PROPERTY_HAS_PART, CreativeWork.class);

            // exampleOfWork
            addResourceOrReferenceProperties(object, proxy.getDctermsIsFormatOf(),
                    SchemaOrgConstants.PROPERTY_EXAMPLE_OF_WORK, CreativeWork.class);
            addMultilingualPropertiesWithReferences(object, toList(proxy.getEdmRealizes()), "",
                    SchemaOrgConstants.PROPERTY_EXAMPLE_OF_WORK, CreativeWork.class);

            // isPartOf
            addResourceOrReferenceProperties(object, proxy.getDctermsIsPartOf(),
                    SchemaOrgConstants.PROPERTY_IS_PART_OF, CreativeWork.class);

            // datePublished
            addDateProperty(object, proxy.getDctermsIssued(), SchemaOrgConstants.PROPERTY_DATE_PUBLISHED,
                    bean.getTimespans(), true);

            // mentions
            addResourceOrReferenceProperties(object, proxy.getDctermsReferences(),
                    SchemaOrgConstants.PROPERTY_MENTIONS, Thing.class);

            // spatialCoverage
            Map<String, List<String>> dcCoverage = copyMap(proxy.getDcCoverage());
            Map<String, List<String>> places = filterPlaces(dcCoverage);
            addResourceOrReferenceProperties(object, places, SchemaOrgConstants.PROPERTY_SPATIAL_COVERAGE, Place.class);
            addResourceOrReferenceProperties(object, proxy.getDctermsSpatial(),
                    SchemaOrgConstants.PROPERTY_SPATIAL_COVERAGE, Place.class);

            // temporalCoverage
            Map<String, List<String>> dates = filterDates(dcCoverage);
            addDateProperty(object, dates, SchemaOrgConstants.PROPERTY_TEMPORAL_COVERAGE, bean.getTimespans(), false);
            addDateProperty(object, proxy.getDctermsTemporal(), SchemaOrgConstants.PROPERTY_TEMPORAL_COVERAGE,
                    bean.getTimespans(), false);

            // now dcCoverage should only contain values that should be added to about
            // about
            addResourceOrReferenceProperties(object, dcCoverage, SchemaOrgConstants.PROPERTY_ABOUT, null);

            // isBasedOn
            addReferences(object, toList(proxy.getEdmIsDerivativeOf()), SchemaOrgConstants.PROPERTY_IS_BASED_ON,
                    CreativeWork.class);

            // sameAs - none in ProxyImpl though there should be ore:Proxy/owl:sameAs

            if (object instanceof VisualArtwork) {
                // artform
                addMultilingualProperties(object, proxy.getDcFormat(), SchemaOrgConstants.PROPERTY_ARTFORM);

                // artMedium
                addMultilingualProperties(object, proxy.getDctermsMedium(), SchemaOrgConstants.PROPERTY_ART_MEDIUM);
            }
        }
    }

    /**
     * Adds multilingual string properties for all values lists in the given map.
     *
     * @param object       object for which the property values will be added
     * @param map          map of language to list of values to be added
     * @param propertyName name of property
     */
    private static void addMultilingualProperties(Thing object, Map<String, List<String>> map, String propertyName) {
        if (map == null) {
            return;
        }
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            addMultilingualProperties(object, entry.getValue(),
                    SchemaOrgConstants.DEFAULT_LANGUAGE.equals(entry.getKey()) ? "" : entry.getKey(), propertyName);
        }
    }

    private static void addMultilingualProperties(Thing object, String propertyName, Map<String, String> map) {
        if (map == null) {
            return;
        }
        String language = null;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            language = SchemaOrgConstants.DEFAULT_LANGUAGE.equals(entry.getKey()) ? "" : entry.getKey();
            addMultilingualProperty(object, entry.getValue(), language, propertyName);
        }
    }

    /**
     * Adds multilingual string properties to property named with
     * <code>propertyName</code> for all values present in the values list. The
     * given language will be used for each value.
     *
     * @param object       object for which the property values will be added
     * @param values       values to be added
     * @param language     language used for each value
     * @param propertyName name of property
     */
    private static void addMultilingualProperties(Thing object, List<String> values, String language,
                                                  String propertyName) {
        if (values == null) {
            return;
        }
        for (String value : values) {
            if (notNullNorEmpty(value)) {
                addMultilingualProperty(object, value, language, propertyName);
            }
        }
    }

    private static void addMultilingualProperty(Thing object, String value, String language, String propertyName) {
        MultilingualString property = new MultilingualString();
        property.setLanguage(language);
        property.setValue(value);
        object.addProperty(propertyName, property);
    }

    /**
     * Adds String properties for all values lists in the given map.
     *
     * @param object       object for which the property values will be added
     * @param map          map of language to list of values to be added
     * @param propertyName name of property
     */
    private static void addStringProperties(Thing object, Map<String, List<String>> map, String propertyName) {
        if (map == null) {
            return;
        }
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            addStringProperties(object, entry.getValue(),  SchemaOrgConstants.DEFAULT_LANGUAGE.equals(entry.getKey()) ? "" : entry.getKey()
                    ,propertyName);
        }
    }

    /**
     * Adds string properties to property named with
     * <code>propertyName</code> for all values present in the values list. The
     * given language will be used for each value.
     *
     * @param object       object for which the property values will be added
     * @param values       values to be added
     * @param language     language used for each value
     * @param propertyName name of property
     */
    private static  void addStringProperties(Thing object, List<String> values,String language, String propertyName) {
        if (values == null) {
            return;
        }
        for (String value : values) {
            if (notNullNorEmpty(value)) {
                addStringProperty(object, value, language, propertyName);
            }
        }
    }

    private static void addStringProperty(Thing object, String value, String language, String propertyName) {
        object.addProperty(propertyName, new Text(value));
    }

    /**
     * For all entries in the map adds a ISO8601 date range if a corresponding
     * timespan can be found or the unchanged value otherwise.
     *
     * @param object       object for which the property will be added
     * @param map          map of values (key is language)
     * @param propertyName name of property
     * @param timespans    list of timespans
     * @param allowInvalid when true all values are added, otherwise only valid
     *                     dates are added
     */
    private static void addDateProperty(Thing object, Map<String, List<String>> map, String propertyName,
                                        List<TimespanImpl> timespans, boolean allowInvalid) {
        if (map == null) {
            return;
        }
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            for (String value : entry.getValue()) {
                processDateValue(object, propertyName, timespans, allowInvalid, entry.getKey(), value);
            }
        }
    }

    /**
     * Adds multilingual properties or typed references. When a reference is
     * detected its type will be set to <code>referenceClass</code>
     *
     * @param object         object for which the properties will be added
     * @param map            map of values where key is the language and value is a
     *                       values list
     * @param propertyName   name of property
     * @param referenceClass class of reference
     */
    private static void addMultilingualPropertiesWithReferences(Thing object, Map<String, List<String>> map,
                                                                String propertyName, Class<? extends Thing> referenceClass) {
        if (map == null) {
            return;
        }
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            addMultilingualPropertiesWithReferences(object, entry.getValue(), entry.getKey(), propertyName,
                    referenceClass);
        }
    }

    /**
     * Adds typed references. When a reference is detected its type will be set to
     * <code>referenceClass</code>
     *
     * @param object         object for which the properties will be added
     * @param map            map of values where key is the language and value is a
     *                       values list
     * @param propertyName   name of property
     * @param referenceClass class of reference
     */
    private static void addResourceOrReferenceProperties(Thing object, Map<String, List<String>> map,
                                                         String propertyName, Class<? extends Thing> referenceClass) {
        if (map == null) {
            return;
        }
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            for (String value : entry.getValue()) {
                addLinkedContextualEntities(propertyName,value);
                if (EuropeanaUriUtils.isUri(value)) {
                    //while creating reference for about, contibutor, creator reference class should be null.
                    if(referenceNull(propertyName)) {
                        referenceClass = null;
                    }
                    addProperty(object, value, entry.getKey(), propertyName, referenceClass);
                } else {
                    addResourceProperty(object, value, entry.getKey(), propertyName, referenceClass);
                }
            }
        }
    }
    /**
     * Adds language property for typed reference.
     *
     * @param object         object for which the properties will be added
     * @param map            map of values where key is the language and value is a
     *                       values list
     * @param propertyName   name of property
     * @param referenceClass class of reference
     */

    private static void addLanguageProperty(Thing object, Map<String, List<String>> map, String propertyName, Class<? extends Thing> referenceClass) {
        if (map == null) {
            return;
        }

        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            for (String value : entry.getValue()) {
                processLanguageValue(object, propertyName, value, referenceClass);
            }
        }
    }
    private static void processLanguageValue(Thing object, String propertyName, String value,  Class<? extends Thing> referenceClass) {
        if (notNullNorEmpty(value)) {
            Thing resource = instantiateResourceObject(referenceClass);
            resource.addProperty(SchemaOrgConstants.PROPERTY_NAME, new Text(value));
            object.addProperty(propertyName, resource);
        }
    }

    /**
     * Adds references for all values in the array. References may have a specific
     * type if <code>referenceClass</code> is specified.
     *
     * @param object         object for which the properties will be added
     * @param values         values to be added as references
     * @param propertyName   name of property
     * @param referenceClass class of reference
     */
    private static void addReferences(Thing object, List<String> values, String propertyName,
                                      Class<? extends Thing> referenceClass) {
        if (values == null) {
            return;
        }
        for (String value : values) {
            addReference(object, value, propertyName, referenceClass);
        }
    }

    /**
     * Do the actual processing of the value to be added as a property
     *
     * @param object       object for which the property will be added
     * @param propertyName name of property
     * @param timespans    list of timespans
     * @param allowInvalid when true all values are added, otherwise only valid
     *                     dates are added
     * @param language     language of the value
     * @param value        value to process
     */
    public static void processDateValue(Thing object, String propertyName, List<TimespanImpl> timespans,
                                         boolean allowInvalid, String language, String value) {
        if (notNullNorEmpty(value)) {
            String valueToAdd = value;
            if (EuropeanaUriUtils.isUri(value) && timespans != null) {
                // value might be timespan
                valueToAdd = createDateRange(value, language, timespans);
                //if the valuetoAdd is still URI and property is temporalCoverage. Create a reference
                if(EuropeanaUriUtils.isUri(valueToAdd) && StringUtils.equals(propertyName, SchemaOrgConstants.PROPERTY_TEMPORAL_COVERAGE)) {
                    addReference(object,valueToAdd,propertyName, null );
                } else {
                    object.addProperty(propertyName, new Text(valueToAdd));
                }
            } else if (allowInvalid || DateUtils.isYear(value) || DateUtils.isIsoDate(value) || DateUtils.isIsoDateTime(value)) {
                   object.addProperty(propertyName, new Text(valueToAdd));
            }
        }
    }

    /**
     * Looks for timespan containing value and creates the ISO8601 date range from
     * its begin/end dates. If no timespan found the unchanged value is returned.
     *
     * @param value     value to create data range
     * @param language  language of the specified value
     * @param timespans the list of timespans
     * @return ISO8601 date range if a proper timespan was found, the input value
     * otherwise
     */
    private static String createDateRange(String value, String language, List<TimespanImpl> timespans) {
        for (TimespanImpl timespan : timespans) {
            if (timespan.getAbout().equals(value) && timespan.getBegin() != null && timespan.getEnd() != null
                    && timespan.getBegin().get(language) != null && timespan.getEnd().get(language) != null) {

                try {
                    LocalDateTime beginDate = LocalDateTime.parse(timespan.getBegin().get(language).get(0), formatter);
                    LocalDateTime endDate = LocalDateTime.parse(timespan.getEnd().get(language).get(0), formatter);
                    return beginDate.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "/"
                            + endDate.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                } catch (DateTimeParseException e) {
                    try {
                        // we can try different format
                        LocalDate beginDate = LocalDate.parse(timespan.getBegin().get(language).get(0), dateFormatter);
                        LocalDate endDate = LocalDate.parse(timespan.getEnd().get(language).get(0), dateFormatter);
                        return beginDate.format(DateTimeFormatter.ISO_LOCAL_DATE) + "/"
                                + endDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
                    } catch (DateTimeParseException e1) {
                        LOG.warn("Could not parse dates: " + timespan.getBegin().get(language).get(0) + ", "
                                + timespan.getEnd().get(language).get(0));
                    }
                }
            }
        }
        return value;
    }

    /**
     * Returns true if the string is a ISO8601 date or a reference to timespan
     *
     * @param value value to check
     * @return true when the string is a ISO8601 date or a reference to timespan
     */
    private static boolean isDateOrTimespan(String value) {
        return (EuropeanaUriUtils.isUri(value) && value.startsWith(TIMESPAN_PREFIX)) || DateUtils.isYear(value)
                || DateUtils.isIsoDate(value) || DateUtils.isIsoDateTime(value);
    }





    /**
     * Filter the date values from the map. The date values are either strings that
     * are a parsable ISO8601 date or a reference to the timespan. The filtered
     * values are returned and removed from the source map.
     *
     * @param map map to filter
     * @return filtered values
     */
    private static Map<String, List<String>> filterDates(Map<String, List<String>> map) {
        Map<String, List<String>> dates = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            for (String value : entry.getValue()) {
                if (isDateOrTimespan(value)) {
                    List<String> datesList = dates.computeIfAbsent(entry.getKey(), k -> new ArrayList<>());
                    datesList.add(value);
                }
            }
            if (dates.get(entry.getKey()) != null) {
                entry.getValue().removeAll(dates.get(entry.getKey()));
            }
        }
        return dates;
    }

    /**
     * Searches for places in the provided map and returns the map containing them.
     * They are also removed from the provided map
     *
     * @param map a map containing list of values for each language (key)
     * @return map of places
     */
    private static Map<String, List<String>> filterPlaces(Map<String, List<String>> map) {
        Map<String, List<String>> places = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            for (String value : entry.getValue()) {
                if (isPlace(value)) {
                    List<String> placesList = places.computeIfAbsent(entry.getKey(), k -> new ArrayList<>());
                    placesList.add(value);
                }
            }
            if (places.get(entry.getKey()) != null) {
                entry.getValue().removeAll(places.get(entry.getKey()));
            }
        }
        return places;
    }

    /**
     * Make a copy of a map if it's not null
     *
     * @param map map to copy
     * @return copy of a map or empty one if the source map is null
     */
    private static Map<String, List<String>> copyMap(Map<String, List<String>> map) {
        if (map == null) {
            return new HashMap<>();
        }
        return new HashMap<>(map);
    }

    /**
     * Returns true when a value is URI and it starts with place prefix
     * http://data.europeana.eu/place
     *
     * @param value value to check
     * @return true when value is uri starting with http://data.europeana.eu/place
     */
    private static boolean isPlace(String value) {
        return EuropeanaUriUtils.isUri(value) && value.startsWith(PLACE_PREFIX);
    }

    /**
     * Checks whether value is Uri adding a reference in this case or adding the
     * multilingual string otherwise.
     *
     * @param object       object for which the property will be added
     * @param propertyName name of property
     * @param language     language string, may be empty
     * @param value        value to add
     */
    private static void addProperty(Thing object, String value, String language, String propertyName,
                                    Class<? extends Thing> referenceClass) {
        if (notNullNorEmpty(value)) {
            if (EuropeanaUriUtils.isUri(value)) {
                addReference(object, value, propertyName, referenceClass);
            } else {
                addMultilingualProperty(object, value,
                        SchemaOrgConstants.DEFAULT_LANGUAGE.equals(language) ? "" : language, propertyName);
            }
        }
    }

    /**
     * Creates a general (not typed) reference and adds it to the specified object
     * as a property value of the given property name.
     *
     * @param object         object for which the reference will be added
     * @param id             id of the reference
     * @param propertyName   name of property
     * @param referenceClass class of reference that should be used for Reference
     *                       object
     */
    private static void addReference(Thing object, String id, String propertyName,
                                     Class<? extends Thing> referenceClass) {
        Reference reference = new Reference(referenceClass);
        reference.setId(id);
        object.addProperty(propertyName, reference);
    }

    /**
     * Creates a reference object, adds the multilingual string to the reference and
     * adds the reference to the object.
     *
     * @param object         object for which the property will be added
     * @param propertyName   name of property
     * @param language       language string, may be empty
     * @param value          value to add
     * @param referenceClass class of reference that should be used for Reference
     *                       object
     */
    private static void addResourceProperty(Thing object, String value, String language, String propertyName,
                                            Class<? extends Thing> referenceClass) {
        if (notNullNorEmpty(value)) {
            Thing resource = instantiateResourceObject(referenceClass);
            if(StringUtils.equals(language, SchemaOrgConstants.DEFAULT_LANGUAGE)) {
                resource.addProperty(SchemaOrgConstants.PROPERTY_NAME, new Text(value));
            } else {
                addMultilingualProperty(resource, value,
                           language, SchemaOrgConstants.PROPERTY_NAME);
            }
            object.addProperty(propertyName, resource);
        }
    }

    /**
     * Adds multilingual properties or typed references. When a reference is
     * detected the corresponding object is being searched on
     * <code>referenced</code> list and an additional object of type
     * <code>referenceClass</code> is created with properties.
     *
     * @param object         object for which the properties will be added
     * @param values         array of values
     * @param propertyName   name of property
     * @param referenceClass class of reference
     */
    private static void addMultilingualPropertiesWithReferences(Thing object, List<String> values, String language,
                                                                String propertyName, Class<? extends Thing> referenceClass) {
        if (values == null) {
            return;
        }
        for (String value : values) {
            addProperty(object, value, language, propertyName, referenceClass);
        }
    }

    /**
     * Adds multilingual properties or typed references. When a reference is
     * detected its type will be set to <code>referenceClass</code>
     *
     * @param object         object for which the properties will be added
     * @param map            map of values where key is the language and value is a
     *                       values list
     * @param propertyName   name of property
     * @param referenceClass class of reference
     */
    private static void addReferences(Thing object, Map<String, List<String>> map, String propertyName,
                                      Class<? extends Thing> referenceClass) {
        if (map == null) {
            return;
        }
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            addReferences(object, entry.getValue(), propertyName, referenceClass);
        }
    }

    /**
     * Adds Text properties from the given values. Those are language independent
     *
     * @param object       Thing object to update
     * @param values       string values to put under property with the given
     *                     property name
     * @param propertyName name of property
     */
    private static void addTextProperties(Thing object, List<String> values, String propertyName) {
        if (values == null) {
            return;
        }
        for (String value : values) {
            if (notNullNorEmpty(value)) {
                object.addProperty(propertyName, new Text(value));
            }
        }
    }

    /**
     * Create QuantitativeValue object and set it in the proper media object
     * property
     *
     * @param mediaObject  media object to update
     * @param value        value for {@link QuantitativeValue} object
     * @param untiCode     unit code for {@link QuantitativeValue} object
     * @param propertyName name of the property
     */
    private static void addQuantitativeProperty(MediaObject mediaObject, String value, String untiCode,
                                                String propertyName) {
        QuantitativeValue quantitativeValue = new QuantitativeValue();
        quantitativeValue.setValue(value);
        quantitativeValue.setUnitCode(untiCode);
        mediaObject.addProperty(propertyName, quantitativeValue);
    }

    /*
     * Instantiates the reference class object, otherwise a Thing object will be
     * instantiate.
     *
     * @param referenceClass class to instantiate
     */
    private static Thing instantiateResourceObject(Class<? extends Thing> referenceClass) {
        if (referenceClass == null)
            return new Thing();
        Thing resource = null;
        try {
            resource = referenceClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            resource = new Thing();
            LOG.info("Cannot instantiate object of class " + referenceClass.getCanonicalName()
                    + ". Instance of Thing is used instead!");
        }
        return resource;
    }

    /**
     * Checks whether the given parameter is neither null nor empty
     *
     * @param value value to check
     * @return true when the given value is neither null nor empty, false otherwise
     */
    private static boolean notNullNorEmpty(String value) {
        return value != null && !value.isEmpty();
    }

    /**
     * Helper method to convert array of strings to a list
     *
     * @param array array of strings
     * @return empty list when the array is null, result of Arrays.asList otherwise
     */
    private static List<String> toList(String[] array) {
        if (array == null) {
            return new ArrayList<>();
        }
        return Arrays.asList(array);
    }

    /**
     * Try to cnovert string value to miliseconds and then to ISO8601 time.
     *
     * @param value value to create duration
     * @return ISO8601 time or the input value if error occurs
     */
    private static String createDuration(String value) {
        try {
            return Duration.ofMillis(Long.valueOf(value)).toString();
        } catch (NumberFormatException e) {
            return value;
        }
    }

    /**
     * Add values to the map only if they don't exist
     *
     * @param map   map to which values will be added
     * @param toAdd values to be added
     */
    private static void addDistinctValues(Map<String, List<String>> map, Map<String, List<String>> toAdd) {
        if (toAdd != null) {
            for (Map.Entry<String, List<String>> entry : toAdd.entrySet()) {
                List<String> values = map.computeIfAbsent(entry.getKey(), k -> new ArrayList<>());
                entry.getValue().forEach(value -> {
                    if (!values.contains(value)) {
                        values.add(value);
                    }
                });
            }
        }
    }

    /**
     * Add values to the set
     *
     * @param set   set to which values will be added
     * @param toAdd values to be added
     */
    private static void addDistinctValues(Set<String> set, String... toAdd) {
        if (toAdd != null) {
            Collections.addAll(set, toAdd);
        }
    }

    private static boolean referenceNull(String propertyName) {
        return (StringUtils.equals(propertyName, SchemaOrgConstants.PROPERTY_ABOUT) || StringUtils.equals(propertyName, SchemaOrgConstants.PROPERTY_CONTRIBUTOR)
                || StringUtils.equals(propertyName, SchemaOrgConstants.PROPERTY_CREATOR) || StringUtils.equals(propertyName, SchemaOrgConstants.PROPERTY_PUBLISHER));
    }

    private static void addLinkedContextualEntities(String propertyName, String value) {
        if(referenceNull(propertyName)) {
            linkedContextualEntities.add(value);
        }
    }
    private static void removeDuplicates(List<String> list) {
        List<String> listWithoutDuplicates = list.stream().distinct().collect(Collectors.toList());
        list.clear();
        list.addAll(listWithoutDuplicates);
    }
}
