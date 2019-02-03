package eu.europeana.corelib.search;

import eu.europeana.corelib.neo4j.entity.Neo4jBean;
import eu.europeana.corelib.neo4j.entity.Neo4jStructBean;
import eu.europeana.corelib.neo4j.exception.Neo4JException;

import java.util.List;

/**
 * Lookup hierarchical information from Neo4j server
 * @author Patrick Ehlert
 * Created on 01-03-2018
 */
public interface Neo4jSearchService {

    /**
     * Get the 'self' node
     *
     * @param  rdfAbout The ID of the node
     * @return Neo4jBean representing 'self' node
     */
    Neo4jBean getSingle(String rdfAbout) throws Neo4JException;

    /**
     * Get the children of the node (max 10)
     *
     * @param  nodeId The ID of the record
     * @param  offset The offset of the first child
     * @param  limit  The number of records to retrieve
     * @return node's children
     */
    List<Neo4jBean> getChildren(String nodeId, int offset, int limit);

    /**
     * Get the children of the node (max 10)
     *
     * @param  nodeId The ID of the record
     * @param  offset The offset of the first child
     * @return node's children
     */
    List<Neo4jBean> getChildren(String nodeId, int offset);

    /**
     * Get the children of the node (max 10)
     *
     * @param  nodeId The ID of the record
     * @return node's children
     */
    List<Neo4jBean> getChildren(String nodeId);

    /**
     * Get the nodes preceeding siblings
     *
     * @param  nodeId The ID of the record
     * @param  offset  How many siblings to skip
     * @param  limit  How many siblings to retrieve
     * @return node's preceding siblings
     */
    List<Neo4jBean> getPrecedingSiblings(String nodeId, int offset, int limit) throws Neo4JException;

    /**
     * Get the nodes preceeding siblings
     *
     * @param  nodeId The ID of the record
     * @param  limit  How many siblings to retrieve
     * @return node's preceding siblings
     */
    List<Neo4jBean> getPrecedingSiblings(String nodeId, int limit) throws Neo4JException;

    /**
     * Get the nodes preceding siblings (max 10)
     *
     * @param  nodeId The ID of the record
     * @return node's preceding siblings
     */
    List<Neo4jBean> getPrecedingSiblings(String nodeId) throws Neo4JException;

    /**
     * Get the nodes following siblings
     *
     * @param  nodeId The ID of the record
     * @param  limit  How many siblings to retrieve
     * @param  offset  How many siblings to skip
     * @return node's following siblings
     */
    List<Neo4jBean> getFollowingSiblings(String nodeId, int offset, int limit) throws Neo4JException;

    /**
     * Get the nodes following siblings
     *
     * @param  nodeId The ID of the record
     * @param  limit  How many siblings to retrieve
     * @return node's following siblings
     */
    List<Neo4jBean> getFollowingSiblings(String nodeId, int limit) throws Neo4JException;

    /**
     * Get the node's 10 following siblings
     * @param  nodeId The ID of the record
     * @return node's following siblings
     */
    List<Neo4jBean> getFollowingSiblings(String nodeId) throws Neo4JException;

    /**
     * Get the initial structure, which contains self, the ancestors,
     * preceding and following siblings
     *
     * @param  nodeId The ID of the record
     * @return The hierarchical structure
     */
    Neo4jStructBean getInitialStruct(String nodeId) throws Neo4JException;

    //	boolean isHierarchy(String nodeId, int hierarchyTimeout) throws Neo4JException;
}
