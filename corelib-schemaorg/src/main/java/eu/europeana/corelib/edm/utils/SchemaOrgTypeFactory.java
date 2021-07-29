package eu.europeana.corelib.edm.utils;

import eu.europeana.corelib.definitions.edm.entity.Agent;
import eu.europeana.corelib.definitions.edm.entity.ContextualClass;
import eu.europeana.corelib.edm.model.schemaorg.*;
import eu.europeana.corelib.solr.bean.impl.FullBeanImpl;
import eu.europeana.corelib.solr.entity.ConceptImpl;
import eu.europeana.corelib.solr.entity.ProxyImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Factory class for creating a new schema.org object from an EDM FulLBean
 */
public final class SchemaOrgTypeFactory {
    private static final Map<String, String> conceptTypes;

    static {
        conceptTypes = new HashMap<>();
        conceptTypes.put("http://data.europeana.eu/concept/base/6", SchemaOrgConstants.TYPE_BOOK);
        conceptTypes.put("http://data.europeana.eu/concept/base/43", SchemaOrgConstants.TYPE_MAP);
        conceptTypes.put("http://data.europeana.eu/concept/base/47", SchemaOrgConstants.TYPE_PAINTING);
        conceptTypes.put("http://data.europeana.eu/concept/base/48", SchemaOrgConstants.TYPE_PHOTOGRAPH);
        conceptTypes.put("http://data.europeana.eu/concept/base/51", SchemaOrgConstants.TYPE_SCULPTURE);
        conceptTypes.put("http://data.europeana.eu/concept/base/190", SchemaOrgConstants.TYPE_VISUAL_ARTWORK);
        conceptTypes.put("http://data.europeana.eu/concept/base/18", SchemaOrgConstants.TYPE_NEWSPAPER);
    }

    private SchemaOrgTypeFactory() {}

    public static MediaObject createMediaObject(String mimeType) {
        if (mimeType == null) {
            return new MediaObject();
        }

        if (mimeType.startsWith("image/")) {
            return new ImageObject();
        }
        if (mimeType.startsWith("audio/") || mimeType.startsWith("sound/")) {
            return new AudioObject();
        }
        if (mimeType.startsWith("video/")) {
            return new VideoObject();
        }
        return new MediaObject();
    }

    public static eu.europeana.corelib.edm.model.schemaorg.ContextualEntity createAgent(Agent agent) {
        if (agent == null) {
            return null;
        }

        if (agent.getRdaGr2DateOfEstablishment() != null || agent.getRdaGr2DateOfTermination() != null) {
            //just Group of Persons, not EDM Organization
            return new Organization();
        }
        return new Person();
    }
    
    public static ContextualEntity createContextualEntity(ContextualClass entity) {
	ContextualEntity thingObject;
	if(entity instanceof eu.europeana.corelib.definitions.edm.entity.Organization) {
	    thingObject = new EdmOrganization();
	}
	else if(entity instanceof eu.europeana.corelib.definitions.edm.entity.Concept) {
	    thingObject = new Concept();
	}
	else if(entity instanceof eu.europeana.corelib.definitions.edm.entity.Agent) {
	    thingObject = createAgent((Agent) entity);
	}
	else if(entity instanceof eu.europeana.corelib.definitions.edm.entity.Place) {
	    thingObject = new Place();
	}
	else if(entity instanceof eu.europeana.corelib.definitions.edm.entity.Timespan) {
	    thingObject = new Timespan();
	}
	else {
	    throw new RuntimeException(
			"The given type is not supported by the schema.org interface");
	}
	return thingObject;
    }

    public static Thing createObject(FullBeanImpl bean) {
        if (bean == null) {
            return null;
        }

        String type = getTypeFromProxies(bean);
        if (type == null) {
            type = getTypeFromConcepts(bean);
        }

        if (type == null || conceptTypes.get(type) == null) {
            return new CreativeWork();
        }
        return createObjectFromType(conceptTypes.get(type));
    }

    private static Thing createObjectFromType(String type) {
        if (SchemaOrgConstants.TYPE_BOOK.equals(type)) {
            return new Book();
        }
        if (SchemaOrgConstants.TYPE_MAP.equals(type)) {
            return new eu.europeana.corelib.edm.model.schemaorg.Map();
        }
        if (SchemaOrgConstants.TYPE_PAINTING.equals(type)) {
            return new Painting();
        }
        if (SchemaOrgConstants.TYPE_PHOTOGRAPH.equals(type)) {
            return new Photograph();
        }
        if (SchemaOrgConstants.TYPE_SCULPTURE.equals(type)) {
            return new Sculpture();
        }
        if (SchemaOrgConstants.TYPE_VISUAL_ARTWORK.equals(type)) {
            return new VisualArtwork();
        }
        if (SchemaOrgConstants.TYPE_NEWSPAPER.equals(type)) {
            return new Newspaper();
        }
        return new CreativeWork();
    }

    private static String getTypeFromProxies(FullBeanImpl bean) {
        String type = null;
        if (bean.getProxies() != null) {
            for (ProxyImpl proxy : bean.getProxies()) {
                // first check dc:type
                type = getType(proxy.getDcType());
                if (type == null) {
                    type = getType(proxy.getDcSubject());
                }
                if (type == null) {
                    // then check edm:hasType
                    type = getType(proxy.getEdmHasType());
                }
                if (type != null) {
                    break;
                }
            }
        }
        return type;
    }

    private static String getTypeFromConcepts(FullBeanImpl bean) {
        String type = null;
        if (bean.getConcepts() != null) {
            for (ConceptImpl concept : bean.getConcepts()) {
                // try about
                if (conceptTypes.containsKey(concept.getAbout())) {
                    type = concept.getAbout();
                    break;
                }
                // try skos:exactMatch
                type = getType(concept.getExactMatch());
                if (type != null) {
                    break;
                }
            }
        }
        return type;
    }

    private static String getType(Map<String, List<String>> valueMap) {
        if (valueMap != null && !valueMap.isEmpty()) {
            for (List<String> types : valueMap.values()) {
                for (String type : types) {
                    if (conceptTypes.containsKey(type)) {
                        return type;
                    }
                }
            }
        }
        return null;
    }

    private static String getType(String[] values) {
        if (values != null) {
            for (String type : values) {
                if (conceptTypes.containsKey(type)) {
                    return type;
                }
            }
        }
        return null;
    }
}
