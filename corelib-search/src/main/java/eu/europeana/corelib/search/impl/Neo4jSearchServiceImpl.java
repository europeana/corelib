package eu.europeana.corelib.search.impl;

import eu.europeana.corelib.neo4j.entity.CustomNode;
import eu.europeana.corelib.neo4j.entity.Neo4jBean;
import eu.europeana.corelib.neo4j.entity.Neo4jStructBean;
import eu.europeana.corelib.neo4j.entity.Node2Neo4jBeanConverter;
import eu.europeana.corelib.neo4j.exception.Neo4JException;
import eu.europeana.corelib.neo4j.server.CypherService;
import eu.europeana.corelib.neo4j.server.Neo4jServer;
import eu.europeana.corelib.search.Neo4jSearchService;
import eu.europeana.corelib.web.exception.ProblemType;
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
 * @author Lúthien - refactored to use neo4j v.3.5.2 03-02-2019
 */
@Lazy
public class Neo4jSearchServiceImpl implements Neo4jSearchService {

    @Resource(name = "corelib_solr_neo4jServer" )
    protected Neo4jServer neo4jServer;

    @Resource(name = "corelib_neo4j_cypherservice" )
    protected CypherService cypherService;


    @PostConstruct
    private void init() {
        LogManager.getLogger(Neo4jSearchServiceImpl.class).info("Connected to {}", neo4jServer.getServerPath());
    }

    @Override
    public Neo4jBean getSingle(String rdfAbout) throws Neo4JException {
        List<CustomNode> selfList = cypherService.getSingleNode(rdfAbout);
        if (!selfList.isEmpty()) {
            return Node2Neo4jBeanConverter.toNeo4jBean(selfList.get(0), 0l);
        } else {
            throw new Neo4JException(ProblemType.NEO4J_CANNOTGETNODE,
                    " \n\n... can't find node with ID: " + rdfAbout);
        }
    }

    @Override
    public List<Neo4jBean> getChildren(String rdfAbout, int offset, int limit) {
        List<Neo4jBean> beans = new ArrayList<>();
        long startIndex = offset;
        List<CustomNode> children = cypherService.getChildren(rdfAbout, offset, limit);
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
    public List<Neo4jBean> getPrecedingSiblings(String rdfAbout, int offset, int limit) {
        List<Neo4jBean> beans = new ArrayList<>();
        List<CustomNode> precedingSiblings = cypherService.getPrecedingSiblings(rdfAbout, offset, limit);
        long startIndex = cypherService.getNodeIndex(rdfAbout) - offset;
        for (CustomNode precedingSibling : precedingSiblings) {
            startIndex -= 1L;
            beans.add(Node2Neo4jBeanConverter.toNeo4jBean(precedingSibling, startIndex));
        }
        return beans;
    }

    @Override
    public List<Neo4jBean> getPrecedingSiblings(String rdfAbout, int limit) {
        return getPrecedingSiblings(rdfAbout, 0, 10);
    }

    @Override
    public List<Neo4jBean> getPrecedingSiblings(String rdfAbout) {
        return getPrecedingSiblings(rdfAbout, 10);
    }

    @Override
    public List<Neo4jBean> getFollowingSiblings(String rdfAbout, int offset, int limit) {
        List<Neo4jBean> beans = new ArrayList<>();
        List<CustomNode> followingSiblings = cypherService.getFollowingSiblings(rdfAbout, offset, limit);
        long startIndex = cypherService.getNodeIndex(rdfAbout) + offset;
        for (CustomNode followingSibling : followingSiblings) {
            startIndex += 1L;
            beans.add(Node2Neo4jBeanConverter.toNeo4jBean(followingSibling, startIndex));
        }
        return beans;
    }

    @Override
    public List<Neo4jBean> getFollowingSiblings(String rdfAbout, int limit) {
        return getFollowingSiblings(rdfAbout, 0, 10);
    }

    @Override
    public List<Neo4jBean> getFollowingSiblings(String rdfAbout)  {
        return getFollowingSiblings(rdfAbout, 10);
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
