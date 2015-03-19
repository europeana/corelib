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
