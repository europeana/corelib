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
package eu.europeana.corelib.neo4j.server.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
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
import eu.europeana.corelib.neo4j.server.Neo4jServer;

/**
 * @see eu.europeana.corelib.neo4j.server.Neo4jServer
 * @author Yorgos.Mamakis@ europeana.eu
 */
@SuppressWarnings("deprecation")
public class Neo4jServerImpl implements Neo4jServer {

	private final static Logger LOG = Logger.getLogger(Neo4jServerImpl.class
			.getCanonicalName());

	private RestGraphDatabase graphDb;
	private RestIndex<Node> index;
	private org.apache.http.client.HttpClient client;
	private String customPath;
	private String serverPath;

	private static final Relation EDMISNEXTINSEQUENCERELATION = new Relation(
			RelType.EDM_ISNEXTINSEQUENCE.getRelType());
	private static final Relation ISFIRSTINSEQUENCERELATION = new Relation(
			RelType.ISFIRSTINSEQUENCE.getRelType());
	private static final Relation DCTERMSISPARTOFRELATION = new Relation(
			RelType.DCTERMS_ISPARTOF.getRelType());
	private static final Relation DCTERMSHASPARTRELATION = new Relation(
			RelType.DCTERMS_HASPART.getRelType());

	/**
	 * Neo4j contructor
	 * 
	 * @param serverPath
	 *            The path of the Neo4j server
	 * @param index
	 *            The name of the index to search on
	 * @param customPath
	 *            The path of the custom Europeana Neo4j plugins
	 */
	public Neo4jServerImpl(String serverPath, String index, String customPath) {
		this.graphDb = new RestGraphDatabase(serverPath);
		this.index = this.graphDb.getRestAPI().getIndex(index);
		this.client = HttpClientBuilder.create()
				.setConnectionManager(new PoolingHttpClientConnectionManager())
				.build();
		this.customPath = customPath;
		this.serverPath = serverPath;
	}

	/**
	 */
	public Neo4jServerImpl() {
	}

	@Override
	public Node getNode(String id) {
		IndexHits<Node> nodes = index.get("rdf_about", id);
		if (nodes.size() > 0 && hasRelationships(nodes)) {
			return nodes.getSingle();
		}
		return null;
	}

	private boolean hasRelationships(IndexHits<Node> nodes) {
		return nodes.getSingle().hasRelationship(DCTERMSISPARTOFRELATION)
				|| nodes.getSingle().hasRelationship(DCTERMSHASPARTRELATION);
	}

	@Override
	public boolean isHierarchy(String id) {
		return getNode(id) != null;
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

		traversal.relationships(ISFIRSTINSEQUENCERELATION, Direction.INCOMING);

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
		List<Node> nodes = getRelatedNodes(id, 1, 0, Direction.OUTGOING,
				DCTERMSISPARTOFRELATION);
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
		return getRelatedNodes(id, limit, offset, Direction.INCOMING,
				EDMISNEXTINSEQUENCERELATION);
	}

	@Override
	public List<Node> getPreceedingSiblings(Node id, int limit) {
		return getPreceedingSiblings(id, limit, 0);
	}

	private List<Node> getPreceedingSiblings(Node id, int limit, int offset) {
		return getRelatedNodes(id, limit, offset, Direction.OUTGOING,
				EDMISNEXTINSEQUENCERELATION);
	}

	private List<Node> getRelatedNodes(Node id, int limit, int offset,
			Direction direction, Relation relType) {
		List<Node> children = new ArrayList<Node>();
		Transaction tx = graphDb.beginTx();
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

		tx.success();
		tx.finish();
		return children;
	}

	@Override
	public long getChildrenCount(Node id) {

		// start n = node(id) match (n)-[:HAS_PART]->(part) RETURN COUNT(part)
		// as children
		ObjectNode obj = JsonNodeFactory.instance.objectNode();
		ArrayNode statements = JsonNodeFactory.instance.arrayNode();
		obj.put("statements", statements);
		ObjectNode statement = JsonNodeFactory.instance.objectNode();
		statement
				.put("statement",
						"start n = node:edmsearch2(rdf_about={from}) match (n)-[:`dcterms:hasPart`]->(part) RETURN COUNT(part) as children");
		ObjectNode parameters = statement.with("parameters");
		statements.add(statement);
		parameters.put("from", (String) id.getProperty("rdf:about"));
		HttpPost httpMethod = new HttpPost(serverPath + "transaction/commit");
		try {
			String str = new ObjectMapper().writeValueAsString(obj);
			httpMethod.setEntity(new StringEntity(str));
			httpMethod.setHeader("content-type", "application/json");
			HttpResponse resp = client.execute(httpMethod);

			CustomResponse cr = new ObjectMapper().readValue(resp.getEntity()
					.getContent(), CustomResponse.class);

			if (cr.getResults() != null
					&& !cr.getResults().isEmpty()
					&& cr.getResults().get(0) != null
					&& cr.getResults().get(0).getData() != null
					&& !cr.getResults().get(0).getData().isEmpty()
					&& cr.getResults().get(0).getData().get(0).get("row") != null
					&& !cr.getResults().get(0).getData().get(0).get("row")
							.isEmpty()) {
				return Long.parseLong(cr.getResults().get(0).getData().get(0)
						.get("row").get(0));
			}
		} catch (IllegalStateException|IOException e) {
			LOG.error(e.getMessage());
		}   finally {
			httpMethod.releaseConnection();
		}

		return 0;
	}

	@Override
	public long getNodeIndex(Node node) {
		HttpGet method = new HttpGet(customPath
				+ "/europeana/hierarchycount/nodeId/" + node.getId());
		try {
			HttpResponse resp = client.execute(method);

			IndexObject obj = new ObjectMapper().readValue(resp.getEntity()
					.getContent(), IndexObject.class);
			return obj.getLength();
		} catch (IOException e) {
			LOG.error(e.getMessage());
		} finally {
			method.releaseConnection();
		}
		return 1;
	}

	@Override
	public Hierarchy getInitialStruct(String id) {
		if (!isHierarchy(id)) {
			return null;
		}
		HttpGet method = new HttpGet(customPath + "/initial/startup/nodeId/"
				+ StringUtils.replace(id, "/", "%2F"));
		LOG.info("path: " + method.getURI());
		try {
			HttpResponse resp = client.execute(method);

			ObjectMapper mapper = new ObjectMapper();
			Hierarchy obj = mapper.readValue(resp.getEntity().getContent(),
					Hierarchy.class);
			return obj;
		} catch (IOException e) {
			LOG.error(e.getMessage());
		} finally {
			method.releaseConnection();
		}
		return null;
	}

	@Override
	public String getCustomPath() {
		return customPath;
	}
}
