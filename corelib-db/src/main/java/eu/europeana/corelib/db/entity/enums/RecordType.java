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
	TRANSLATE_QUERY;
}
