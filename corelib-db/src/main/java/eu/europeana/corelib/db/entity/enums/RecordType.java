package eu.europeana.corelib.db.entity.enums;

/**
 * Record type enumeration. This denotes whether an API call was for a search result or for a specific object
 *
 * @author Yorgos.Mamakis@ kb.nl
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * @deprecated May 2020
 */
@Deprecated
public enum RecordType {
    OBJECT, OBJECT_JSONLD, OBJECT_RDF, SEARCH, SEARCH_KML, LIMIT, REDIRECT,
    TRANSLATE_QUERY, OBJECT_SCHEMA_ORG, OBJECT_TURTLE;
}
