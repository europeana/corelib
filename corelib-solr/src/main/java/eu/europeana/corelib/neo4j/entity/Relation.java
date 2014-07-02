package eu.europeana.corelib.neo4j.entity;

import org.neo4j.graphdb.RelationshipType;

public class Relation implements RelationshipType {

	private String name;
	
	public Relation(String name){
		this.name = name;
	}
	
	@Override
	public String name() {
		return this.name;
	}

}
