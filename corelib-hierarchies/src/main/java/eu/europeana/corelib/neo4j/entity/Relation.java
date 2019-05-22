package eu.europeana.corelib.neo4j.entity;

import org.neo4j.graphdb.RelationshipType;

/**
 * Class representing a Relation in Neo4j
 * @author Yorgos.Mamakis@ europeana.eu
 *
 */
public class Relation implements RelationshipType {

	private String name;

	/**
	 * Default constructor for a Relation
	 * @param name The name of the neo4j relation
	 */
	public Relation(String name){
		this.name = name;
	}

	@Override
	public String name() {
		return this.name;
	}

}
