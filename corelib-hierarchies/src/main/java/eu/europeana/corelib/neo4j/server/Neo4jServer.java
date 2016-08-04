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

import eu.europeana.corelib.definitions.exception.Neo4JException;
import org.neo4j.graphdb.Node;
import eu.europeana.corelib.neo4j.entity.Hierarchy;
import eu.europeana.corelib.neo4j.entity.CustomNode;

/**
 * Neo4j server connection interface
 * 
 * @author Yorgos.Mamakis@ europeana.eu
 * @author Maike.Dulk@ europeana.eu
 */
public interface Neo4jServer {

	/**
	 * get node by its rdfAbout
	 * 
	 * @param rdfAbout the rdfAbout (id) to search on
	 * @return         a Neo4j node
	 * @throws         Neo4JException
	 */
	Node getNode(String rdfAbout) throws Neo4JException;

	/**
	 * Retrieve the children nodes of a parent node (parent-child relation
	 * dcterms:hasPart). Returns null if null is present
	 * @param node The node for which to retrieve the children
	 * @param offset from which child to start receiving from
	 * @param limit  how many children to receive
	 * @return A list of children nodes with size <= to the specified limit
	 * and from the specified offset
	 */
	List<CustomNode> getChildren(Node node, int offset, int limit);

        /**
         * Convenience override
         * @param rdfAbout The node with this rdf:about to start from
         * @param offset from which child to start receiving from
         * @param limit  how many children to receive
         * @return A list of children nodes with size  <= to the specified limit
         * and from the specified offset
         */
	List<CustomNode> getChildren(String rdfAbout, int offset, int limit);

	/**
	 * Retrieve the parent node of a node (parent relation dcterms:isPartOf)
	 * 
	 * @param node The node for which to retrieve the parent
	 * @return The parent node
	 */
	Node getParent(Node node);

	/**
	 * Retrieve ordered list of siblings using a specific node as a start
	 * following outbound edm:isNextInSequence or isFakeOrder relations
	 * 
	 * @param node The node to start from
	 * @param limit the number of nodes to retrieve
	 * @return A list of sibling nodes with size <= to the specified limit
	 */
	List<CustomNode> getFollowingSiblings(Node node, int limit);

	/**
	 * Retrieve ordered list of siblings using a specific node as a start
	 * following outbound edm:isNextInSequence or isFakeOrder relations
	 * 
	 * @param rdfAbout The node with this rdf:about to start from
	 * @param limit the number of siblings to retrieve
	 * @return A list of sibling nodes with size <= to the specified limit
	 */
	List<CustomNode> getFollowingSiblings(String rdfAbout, int limit);

	/**
         * Convenience override
	 * Retrieve ordered list of siblings using a specific node as a start
	 * following inbound edm:isNextInSequence or isFakeOrder relations
	 *
	 * @param node The node to start from
	 * @param limit the number of nodes to retrieve
	 * @return A list of sibling nodes with size <= to the specified limit
	 */
	List<CustomNode> getPrecedingSiblings(Node node, int limit);

	/**
	 * Retrieve ordered list of siblings using a specific node as a start
	 * following inbound edm:isNextInSequence or isFakeOrder relations
	 *
	 * @param rdfAbout The node with this rdf:about to start from
	 * @param limit the number of nodes to retrieve
	 * @return A list of sibling nodes with size <= to the specified limit
	 */
	List<CustomNode> getPrecedingSiblings(String rdfAbout, int limit);
	
	/**
	 * Retrieve the number of children for a specific node
	 * @param node The node to search on
	 * @return The number of children for the specific node
	 */
	long getChildrenCount(Node node);

	/**
	 * Get the order of the node within its hierarchy (local index)
	 * @param node A specific neo4j node
	 * @return The index number of the node
	 */
	long getNodeIndex(Node node);

	/**
         * Get the order of the node within its hierarchy (local index)
	 * @param nodeId the numerical id of the node
	 * @return The index number of the node
	 */
	long getNodeIndex(String nodeId);

        /**
         * Get the order of the node within its hierarchy (local index)
	 * @param rdfAbout the rdf:about string of a specific neo4j node
	 * @return The index number of the node
	 */
	long getNodeIndexByRdfAbout(String rdfAbout) throws Neo4JException;

	/**
	 * Get the initial hierachy for a specific Europeana id
	 * @param id The id to search on
	 * @return A full Hierarchy consisting of parents- parents of parents- and nodes
	 */
	Hierarchy getInitialStruct(String id) throws Neo4JException;

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
	boolean isHierarchy(String id) throws Neo4JException;
}
