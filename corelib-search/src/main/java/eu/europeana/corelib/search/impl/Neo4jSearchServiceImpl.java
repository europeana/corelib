package eu.europeana.corelib.search.impl;

import eu.europeana.corelib.neo4j.entity.CustomNode;
import eu.europeana.corelib.neo4j.entity.Neo4jBean;
import eu.europeana.corelib.neo4j.entity.Neo4jStructBean;
import eu.europeana.corelib.neo4j.entity.Node2Neo4jBeanConverter;
import eu.europeana.corelib.neo4j.exception.Neo4JException;
import eu.europeana.corelib.neo4j.server.Neo4jServer;
import eu.europeana.corelib.search.Neo4jSearchService;
import org.apache.logging.log4j.LogManager;
import org.neo4j.graphdb.Node;
import org.springframework.context.annotation.Lazy;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Lookup hierarchical information from Neo4j server
 * @author Patrick Ehlert
 * Created on 01-03-2018
 */
@Lazy
public class Neo4jSearchServiceImpl implements Neo4jSearchService {

    @Resource(name = "corelib_solr_neo4jServer" )
    protected Neo4jServer neo4jServer;


    @PostConstruct
    private void init() {
        LogManager.getLogger(Neo4jSearchServiceImpl.class).info("Connected to {}", neo4jServer.getServerPath());
    }

    @Override
    public List<Neo4jBean> getChildren(String rdfAbout, int offset, int limit) {
        List<Neo4jBean> beans = new ArrayList<>();
        long startIndex = offset;
        List<CustomNode> children = neo4jServer.getChildren(rdfAbout, offset, limit);
        for (CustomNode child : children) {
            startIndex += 1L;
            beans.add(Node2Neo4jBeanConverter.toNeo4jBean(child, startIndex));
        }
        return beans;
    }

    @Override
    public List<Neo4jBean> getChildren(String rdfAbout, int offset) {
        return getChildren(rdfAbout, offset, 10);
    }

    @Override
    public List<Neo4jBean> getChildren(String rdfAbout) {
        return getChildren(rdfAbout, 0, 10);
    }

    private Node getNode(String rdfAbout) throws Neo4JException {
        return neo4jServer.getNode(rdfAbout);
    }

    @Override
    public Neo4jBean getHierarchicalBean(String rdfAbout) throws Neo4JException {
        Node node = getNode(rdfAbout);
        if (node != null) {
            return Node2Neo4jBeanConverter.toNeo4jBean(node, neo4jServer.getNodeIndex(node));
        }
        return null;
    }

    @Override
    public List<Neo4jBean> getPrecedingSiblings(String rdfAbout, int offset, int limit) throws Neo4JException {
        List<Neo4jBean> beans = new ArrayList<>();
        List<CustomNode> precedingSiblings = neo4jServer.getPrecedingSiblings(rdfAbout, offset, limit);
        long startIndex = neo4jServer.getNodeIndexByRdfAbout(rdfAbout) - offset;
        for (CustomNode precedingSibling : precedingSiblings) {
            startIndex -= 1L;
            beans.add(Node2Neo4jBeanConverter.toNeo4jBean(precedingSibling, startIndex));
        }
        return beans;
    }

    @Override
    public List<Neo4jBean> getPrecedingSiblings(String rdfAbout, int limit) throws Neo4JException {
        return getPrecedingSiblings(rdfAbout, 0, 10);
    }

    @Override
    public List<Neo4jBean> getPrecedingSiblings(String rdfAbout) throws Neo4JException {
        return getPrecedingSiblings(rdfAbout, 10);
    }

    @Override
    public List<Neo4jBean> getFollowingSiblings(String rdfAbout, int offset, int limit) throws Neo4JException {
        List<Neo4jBean> beans = new ArrayList<>();
        List<CustomNode> followingSiblings = neo4jServer.getFollowingSiblings(rdfAbout, offset, limit);
        long startIndex = neo4jServer.getNodeIndexByRdfAbout(rdfAbout) + offset;
        for (CustomNode followingSibling : followingSiblings) {
            startIndex += 1L;
            beans.add(Node2Neo4jBeanConverter.toNeo4jBean(followingSibling, startIndex));
        }
        return beans;
    }

    @Override
    public List<Neo4jBean> getFollowingSiblings(String rdfAbout, int limit) throws Neo4JException {
        return getFollowingSiblings(rdfAbout, 0, 10);
    }

    @Override
    public List<Neo4jBean> getFollowingSiblings(String rdfAbout) throws Neo4JException {
        return getFollowingSiblings(rdfAbout, 10);
    }

    @Override
    public long getChildrenCount(String rdfAbout) throws Neo4JException {
        return neo4jServer.getChildrenCount(getNode(rdfAbout));
    }

    // note that parents don't have their node indexes set in getInitialStruct
    // because they have to be fetched separately; therefore this is done afterwards
    @Override
    public Neo4jStructBean getInitialStruct(String nodeId) throws Neo4JException {
        return addParentNodeIndex(Node2Neo4jBeanConverter.toNeo4jStruct(neo4jServer.getInitialStruct(nodeId), neo4jServer.getNodeIndex(getNode(nodeId))));
    }

    private Neo4jStructBean addParentNodeIndex(Neo4jStructBean struct) throws Neo4JException {
        if (!struct.getParents().isEmpty()) {
            for (Neo4jBean parent : struct.getParents()) {
                parent.setIndex(neo4jServer.getNodeIndex(getNode(parent.getId())));
            }
        }
        return struct;
    }
}
