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
package eu.europeana.corelib.neo4j.server;

import java.util.List;

import org.neo4j.graphdb.Node;

import eu.europeana.corelib.neo4j.entity.Hierarchy;

/**
 * Neo4j server connection interface
 * 
 * @author Yorgos.Mamakis@ europeana.eu
 *
 */
public interface Neo4jServer {

	/**
	 * Get node by its id
	 * 
	 * @param id
	 *            The id to search for
	 * @return A Neo4j node
	 */
	Node getNode(String id);

	/**
	 * Retrieve the children nodes of a parent node (parent-child relation
	 * dcterms:hasPart). Returns null if null is present
	 * 
	 * @param id
	 *            The node for which to retrieve the children
	 * @param offset
	 *            from which child to start receiving from
	 * @param limit
	 *            how many children to receive
	 * @return A list of children nodes with size <= to the specified limit
	 */
	List<Node> getChildren(Node id, int offset, int limit);

	/**
	 * Retrieve the parent node of a node (parent relation dcterms:isPartOf)
	 * 
	 * @param id
	 *            The node for which to retrieve the parent
	 * @return The parent node
	 */
	Node getParent(Node id);

	/**
	 * Retrieve ordered list of children using a specific node as a child
	 * following outbound edm:isNextInSequence relations
	 * 
	 * @param id The node to start from
	 * @param limit the number of nodes to retrieve
	 * @return A list of children nodes with size <= to the specified limit
	 */
	List<Node> getFollowingSiblings(Node id, int limit);
	/**
	 * Retrieve ordered list of children using a specific node as a child
	 * following inbound edm:isNextInSequence relations
	 * 
	 * @param id The node to start from
	 * @param limit the number of nodes to retrieve
	 * @return A list of children nodes with size <= to the specified limit
	 */
	List<Node> getPreceedingSiblings(Node id, int limit);
	
	/**
	 * Retrieve the number of children for a specific node
	 * @param id The node to search on
	 * @return The number of children for the specific node
	 */
	long getChildrenCount(Node id);

	/**
	 * Get the order of the node within its hierarchy (local index)
	 * @param node A specific neo4j node
	 * @return The index number of the node
	 */
	long getNodeIndex(Node node);

	/**
	 * Get the initial hierachy for a specific Europeana id
	 * @param id The id to search on
	 * @return A full Hierarchy consisting of parents- parents of parents- and nodes
	 */
	Hierarchy getInitialStruct(String id);

	/**
	 * The URI path for the REST cal to initial structure
	 * @return
	 */
	String getCustomPath();

	/**
	 * Check if the specified id belongs to a hierarchy
	 * @param id The Europeana id
	 * @return true if it belongs to a hierarchy
	 */
	boolean isHierarchy(String id);
}
