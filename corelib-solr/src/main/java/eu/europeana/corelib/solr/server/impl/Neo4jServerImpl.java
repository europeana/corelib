/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.europeana.corelib.solr.server.impl;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.index.IndexHits;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.Traverser;
import org.neo4j.kernel.Uniqueness;
import org.neo4j.rest.graphdb.RestGraphDatabase;
import org.neo4j.rest.graphdb.index.RestIndex;
import org.neo4j.rest.graphdb.traversal.RestTraversal;

import eu.europeana.corelib.neo4j.entity.RelType;
import eu.europeana.corelib.neo4j.entity.Relation;
import eu.europeana.corelib.solr.server.Neo4jServer;
import java.util.Iterator;

/**
 *
 * @author Yorgos.Mamakis@ europeana.eu
 */
@SuppressWarnings("deprecation")
public class Neo4jServerImpl implements Neo4jServer {

    private RestGraphDatabase graphDb;
    private RestIndex<Node> index;

    public Neo4jServerImpl(String serverPath, String index) {
        this.graphDb = new RestGraphDatabase(serverPath);
        this.index = this.graphDb.getRestAPI().getIndex(index);
    }

    @Override
    public Node getNode(String id) {
        IndexHits<Node> nodes = index.get("rdf_about", id);
        if (nodes.size() > 0) {
            return nodes.getSingle();
        }
        return null;
    }

    @Override
    public List<Node> getChildren(Node id, int offset, int limit) {

        List<Node> children = new ArrayList<Node>();
        RestTraversal traversal = (RestTraversal) graphDb
                .traversalDescription();

        traversal.evaluator(Evaluators.excludeStartPosition());

        traversal.uniqueness(Uniqueness.RELATIONSHIP_GLOBAL);
        traversal.breadthFirst();
        traversal.maxDepth(1);

        traversal.relationships(
                new Relation(RelType.ISFIRSTINSEQUENCE.getRelType()),
                Direction.OUTGOING);

        Traverser tr = traversal.traverse(id);
        Iterator<Node> resIter = tr.nodes().iterator();

        while (resIter.hasNext()) {
            Node node = resIter.next();

            if (node != null) {
                if(offset==0){
                children.add(node);
                children.addAll(getNextSiblings(node, limit-1));
                } else {
                    children.addAll(getNextSiblings(node,offset+limit));
                }

            }

        }
        return children.subList(offset, limit);
    }

    @Override
    public Node getParent(Node id) {

        List<Node> nodes = getRelatedNodes(id, 1, Direction.OUTGOING, new Relation(
                RelType.DCTERMS_ISPARTOF.getRelType()));
        if (nodes.size() > 0) {
            return nodes.get(0);
        }
        return null;
    }

    @Override
    public List<Node> getNextSiblings(Node id, int limit) {
        return getRelatedNodes(id, limit, Direction.OUTGOING, new Relation(
                RelType.EDM_ISNEXTINSEQUENCE.getRelType()));
    }

    @Override
    public List<Node> getPreviousSiblings(Node id, int limit) {
        return getRelatedNodes(id, limit, Direction.OUTGOING, new Relation(
                RelType.EDM_ISNEXTINSEQUENCE.getRelType()));
    }

    private List<Node> getRelatedNodes(Node id, int limit, Direction direction,
            Relation relType) {
        List<Node> children = new ArrayList<Node>();
        RestTraversal traversal = (RestTraversal) graphDb
                .traversalDescription();

        traversal.evaluator(Evaluators.excludeStartPosition());

        traversal.uniqueness(Uniqueness.RELATIONSHIP_GLOBAL);
        traversal.breadthFirst();
        traversal.maxDepth(limit);

        traversal.relationships(relType, direction);
        Traverser tr = traversal.traverse(id);
        Iterator<Node> resIter = tr.nodes().iterator();

        while (resIter.hasNext()) {
            Node node = resIter.next();

            children.add(node);

        }
        return (children);
    }
}
