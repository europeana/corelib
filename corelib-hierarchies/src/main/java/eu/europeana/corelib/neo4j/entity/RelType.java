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
package eu.europeana.corelib.neo4j.entity;

import javax.management.InvalidAttributeValueException;

/**
 * Enumeration of relationships
 * @author Yorgos.Mamakis@ europeana.eu
 */
public enum RelType {

	EDM_ISNEXTINSEQUENCE("edm:isNextInSequence"),
	DCTERMS_ISPARTOF("dcterms:isPartOf"),
	DCTERMS_HASPART("dcterms:hasPart"),
	ISFIRSTINSEQUENCE("isFirstInSequence"),
	ISLASTINSEQUENCE("isLastInSequence"),
	ISFAKEORDER("isFakeOrder");

	private String relType;

	RelType(String relType) {
		this.relType = relType;
	}

	/**
	 * Get the relation type
	 * @return
	 */
	public String getRelType() {
		return this.relType;
	}

	/**
	 * Get the Relation based on a string
	 * @param relType The relation to search on
	 * @return The RelType representing the relation
	 * @throws InvalidAttributeValueException
	 */
	public static RelType getByRelType(String relType) throws InvalidAttributeValueException {
		for (RelType rel : RelType.values()) {
			if (rel.getRelType().equalsIgnoreCase(relType)) {
				return rel;
			}
		}
		throw new InvalidAttributeValueException();
	}
}
