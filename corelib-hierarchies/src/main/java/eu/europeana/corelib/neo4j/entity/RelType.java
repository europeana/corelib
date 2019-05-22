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
