package eu.europeana.corelib.db.entity.enums;

/**
 * Record type enumeration. This denotes whether an API call was for a search result or for a specific object
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * 
 */
public enum RecordType {
	OBJECT, OBJECT_JSONLD, OBJECT_RDF, OBJECT_SRW, SEARCH, SEARCH_KML, LIMIT, REDIRECT,
	PROVIDERS, PROVIDER, PROVIDER_DATASETS, DATASETS, DATASET,
	HIERARCHY_SELF, HIERARCHY_CHILDREN, HIERARCHY_PARENT, HIERARCHY_FOLLOWING_SIBLINGS,
	HIERARCHY_PRECEDING_SIBLINGS, HIERARCHY_ANCESTOR_SELF_SIBLINGS,
	TRANSLATE_QUERY, OBJECT_SCHEMA_ORG;
}
