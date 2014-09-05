/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.europeana.corelib.solr.server.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.IndexHits;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.Traverser;
import org.neo4j.kernel.Uniqueness;
import org.neo4j.rest.graphdb.RestGraphDatabase;
import org.neo4j.rest.graphdb.index.RestIndex;
import org.neo4j.rest.graphdb.traversal.RestTraversal;
import org.springframework.util.StringUtils;

import eu.europeana.corelib.logging.Logger;
import eu.europeana.corelib.neo4j.entity.CustomResponse;
import eu.europeana.corelib.neo4j.entity.Hierarchy;
import eu.europeana.corelib.neo4j.entity.IndexObject;
import eu.europeana.corelib.neo4j.entity.RelType;
import eu.europeana.corelib.neo4j.entity.Relation;
import eu.europeana.corelib.solr.server.Neo4jServer;

/**
 *
 * @author Yorgos.Mamakis@ europeana.eu
 */
@SuppressWarnings("deprecation")
public class Neo4jServerImpl implements Neo4jServer {

	Logger log = Logger.getLogger(Neo4jServerImpl.class.getCanonicalName());

	private RestGraphDatabase graphDb;
	private RestIndex<Node> index;
	private HttpClient client;
	private String customPath;
	private String serverPath;

	private static final Relation edmIsNextInSequenceRelation = new Relation(RelType.EDM_ISNEXTINSEQUENCE.getRelType());
	private static final Relation isFirstInSequenceRelation = new Relation(RelType.ISFIRSTINSEQUENCE.getRelType());
	private static final Relation dctermsIsPartOfRelation = new Relation(RelType.DCTERMS_ISPARTOF.getRelType());

	public Neo4jServerImpl(String serverPath, String index, String customPath) {
		this.graphDb = new RestGraphDatabase(serverPath);
		this.index = this.graphDb.getRestAPI().getIndex(index);
		this.client = new HttpClient();
		this.customPath = customPath;
		this.serverPath = serverPath;
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
		RestTraversal traversal = (RestTraversal) graphDb.traversalDescription();

		traversal.evaluator(Evaluators.excludeStartPosition());

		traversal.uniqueness(Uniqueness.RELATIONSHIP_GLOBAL);
		traversal.breadthFirst();
		traversal.maxDepth(1);

		traversal.relationships(isFirstInSequenceRelation, Direction.INCOMING);

		Traverser tr = traversal.traverse(id);
		Iterator<Node> resIter = tr.nodes().iterator();

		while (resIter.hasNext()) {
			Node node = resIter.next();

			if (node != null) {
				if (offset == 0) {
					children.add(node);
					children.addAll(getFollowingSiblings(node, limit - 1, 0));
				} else {
					children.addAll(getFollowingSiblings(node, limit, offset));
				}
			}
		}

		return children;
	}

	@Override
	public Node getParent(Node id) {
		List<Node> nodes = getRelatedNodes(id, 1, 0, Direction.OUTGOING, dctermsIsPartOfRelation);
		if (nodes.size() > 0) {
			return nodes.get(0);
		}
		return null;
	}

	@Override
	public List<Node> getFollowingSiblings(Node id, int limit) {
		return getFollowingSiblings(id, limit, 0);
	}

	private List<Node> getFollowingSiblings(Node id, int limit, int offset) {
		return getRelatedNodes(id, limit, offset, Direction.INCOMING, edmIsNextInSequenceRelation);
	}

	@Override
	public List<Node> getPreceedingSiblings(Node id, int limit) {
		return getPreceedingSiblings(id, limit, 0);
	}

	private List<Node> getPreceedingSiblings(Node id, int limit, int offset) {
		return getRelatedNodes(id, limit, offset, Direction.OUTGOING, edmIsNextInSequenceRelation);
	}

	private List<Node> getRelatedNodes(Node id, int limit, int offset, Direction direction,
			Relation relType) {
		List<Node> children = new ArrayList<Node>();
		RestTraversal traversal = (RestTraversal) graphDb
				.traversalDescription();

		traversal.evaluator(Evaluators.excludeStartPosition());

		traversal.uniqueness(Uniqueness.RELATIONSHIP_GLOBAL);
		traversal.breadthFirst();
		traversal.maxDepth(offset + limit);

		traversal.relationships(relType, direction);
		Traverser tr = traversal.traverse(id);
		Iterator<Node> resIter = tr.nodes().iterator();

		int i = 1;
		while (resIter.hasNext()) {
			Node node = resIter.next();
			if (i >= offset) {
				if (children.size() <= limit) {
					children.add(node);
				}
				if (children.size() == limit) {
					break;
				}
			}
			i++;
		}

		return (children);
	}

	@Override
	public long getChildrenCount(Node id) {

		// start n = node(id) match (n)-[:HAS_PART]->(part) RETURN COUNT(part) as children
		ObjectNode obj = JsonNodeFactory.instance.objectNode();
		ArrayNode statements = JsonNodeFactory.instance.arrayNode();
		obj.put("statements", statements);
		ObjectNode statement = JsonNodeFactory.instance.objectNode();
		statement.put("statement",
			"start n = node:edmsearch2(rdf_about={from}) match (n)-[:`dcterms:hasPart`]->(part) RETURN COUNT(part) as children");
		ObjectNode parameters = statement.with("parameters");
		statements.add(statement);
		parameters.put("from", (String) id.getProperty("rdf:about"));
		try{
			String str = new ObjectMapper().writeValueAsString(obj);
			PostMethod httpMethod = new PostMethod(serverPath + "transaction/commit");
			httpMethod.setRequestBody(str);
			httpMethod.setRequestHeader("content-type", "application/json");
			client.executeMethod(httpMethod);

			CustomResponse cr = new ObjectMapper().readValue(
					httpMethod.getResponseBodyAsStream(), CustomResponse.class);
			if (cr.getResults() != null && cr.getResults().size() > 0
					&& cr.getResults().get(0) != null
					&& cr.getResults().get(0).getData()!=null
					&& cr.getResults().get(0).getData().get(0) != null
					&& cr.getResults().get(0).getData().get(0).get("row") != null
					&& cr.getResults().get(0).getData().get(0).get("row").size() > 0)
				return Long.parseLong(cr.getResults().get(0).getData().get(0).get("row").get(0));
		} catch (Exception e){
			log.error("error: " + e.getLocalizedMessage());
			e.printStackTrace();
		}

		return 0;
	}

	@Override
	public long getNodeIndex(Node node){
		GetMethod method = new GetMethod(customPath + "/europeana/hierarchycount/nodeId/" + node.getId());
		try {
			log.info("path: " + method.getPath());
			client.executeMethod(method);
			String respBody = method.getResponseBodyAsString();
			IndexObject obj = new ObjectMapper().readValue(respBody, IndexObject.class);
			return obj.getLength();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return 0;
	}

	@Override
	public Hierarchy  getInitialStruct(String id) {
		GetMethod method = new GetMethod(customPath + "/initial/startup/nodeId/" + StringUtils.replace(id, "/", "%2F"));
		log.info("path: " + method.getPath());
		try {
			client.executeMethod(method);
			String respBody = method.getResponseBodyAsString();
			ObjectMapper mapper = new ObjectMapper();
			Hierarchy obj = mapper.readValue(respBody, Hierarchy.class);
			return obj;
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getCustomPath(){
		return customPath;
	}
}
