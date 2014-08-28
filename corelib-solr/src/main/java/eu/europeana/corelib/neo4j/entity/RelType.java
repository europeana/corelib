/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.europeana.corelib.neo4j.entity;

import javax.management.InvalidAttributeValueException;

/**
 *
 * @author gmamakis
 */
public enum RelType {

	EDM_ISNEXTINSEQUENCE("edm:isNextInSequence"),
	DCTERMS_ISPARTOF("dcterms:isPartOf"),
	DCTERMS_HASPART("dcterms:hasPart"),
	ISFIRSTINSEQUENCE("isFirstInSequence"),
	ISLASTINSEQUENCE("isLastInSequence");

	private String relType;

	private RelType(String relType) {
		this.relType = relType;
	}

	public String getRelType() {
		return this.relType;
	}

	public static RelType getByRelType(String relType) throws InvalidAttributeValueException {
		for (RelType rel : RelType.values()) {
			if (rel.getRelType().equalsIgnoreCase(relType)) {
				return rel;
			}
		}
		throw new InvalidAttributeValueException();
	}
}
