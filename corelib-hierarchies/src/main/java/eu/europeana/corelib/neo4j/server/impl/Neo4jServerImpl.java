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
import java.util.concurrent.*;

import eu.europeana.corelib.web.exception.ProblemType;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.log4j.Logger;
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

import eu.europeana.corelib.neo4j.entity.Siblington;
import eu.europeana.corelib.neo4j.entity.CustomNode;
import eu.europeana.corelib.neo4j.entity.CustomResponse;
import eu.europeana.corelib.neo4j.entity.Hierarchy;
import eu.europeana.corelib.neo4j.entity.IndexObject;
import eu.europeana.corelib.neo4j.entity.RelType;
import eu.europeana.corelib.neo4j.entity.Relation;
import eu.europeana.corelib.neo4j.server.Neo4jServer;

import eu.europeana.corelib.neo4j.exception.Neo4JException;

/**
 * @see eu.europeana.corelib.neo4j.server.Neo4jServer
 * @author Yorgos.Mamakis@ europeana.eu
 * @author Maike.Dulk@ europeana.eu
 */
@SuppressWarnings("deprecation")
public class Neo4jServerImpl implements Neo4jServer {

	private final static Logger LOG = Logger.getLogger(Neo4jServerImpl.class);

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
    private static final Relation ISFAKEORDERRELATION = new Relation(
            RelType.ISFAKEORDER.getRelType());

	/**
	 * Neo4j contructor
	 * 
     * @param serverPath The path of the Neo4j server
     * @param index The name of the index to search on
     * @param customPath The path of the custom Europeana Neo4j plugins
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
     * Node.Index = relative index of node in its hierarchical sequence
     * Node.Id = Neo4j numerical Id
     * Node.rdfAbout = hexadecimal rdf:about collection/item identifier
	 * Note: initial "/" prefixed here again, it was taken out of the rdfAbout to avoid path separator problems
	 */
	public Neo4jServerImpl() {
	}

	@Override
    public Node getNode(String rdfAbout) throws Neo4JException {
		Node node = null;
		try {
        IndexHits<Node> nodes = index.get("rdf_about", (!rdfAbout.startsWith("/") &&
			(rdfAbout.contains("/") || rdfAbout.contains("%2F"))) ? "/" + rdfAbout : rdfAbout);
		if (nodes.size() > 0 && hasRelationships(nodes)) {
                node = nodes.getSingle();
		}
		} catch (Exception e) {
			throw new Neo4JException(e, ProblemType.NEO4J_CANNOTGETNODE);
		}
		return node;
	}

    @Override
    public long getNodeIndex(Node node) {
        return getNodeIndex(node.getId() + "");
    }

    @Override
    public long getNodeIndex(String nodeId){
        HttpGet method = new HttpGet(fixTrailingSlash(customPath)
                + "europeana/hierarchycount/nodeId/" + nodeId);
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
        return 0;
    }

    public long getNodeIndexByRdfAbout(String rdfAbout) throws Neo4JException {
         return getNodeIndex(getNode(rdfAbout));
     }

	private boolean hasRelationships(IndexHits<Node> nodes) {
		return nodes.getSingle().hasRelationship(DCTERMSISPARTOFRELATION)
				|| nodes.getSingle().hasRelationship(DCTERMSHASPARTRELATION);
	}

	@Override
    public boolean isHierarchy(String rdfAbout) throws Neo4JException {
        return getNode(rdfAbout) != null;
	}

	public boolean isHierarchyTimeLimited(String rdfAbout, int hierarchyTimeout) throws Neo4JException,
			InterruptedException, ExecutionException, TimeoutException {
		final ExecutorService timeoutExecutorService = Executors.newSingleThreadExecutor();
		Future<Boolean> future = timeoutExecutorService.submit(() -> isHierarchy(rdfAbout));
		return future.get(hierarchyTimeout, TimeUnit.MILLISECONDS);
	}

	// note: first "/" in rdfAbout was removed; this is added again in the neo4j plugin
	@Override
    public List<CustomNode> getChildren(String rdfAbout, int offset, int limit) {
        HttpGet method = new HttpGet(fixTrailingSlash(customPath)
                + "fetch/children/nodeId/"
                + StringUtils.replace(rdfAbout + "", "/", "%2F")
                + "?offset=" + offset + "&limit=" + limit);
        try {
            HttpResponse resp = client.execute(method);
            ObjectMapper mapper = new ObjectMapper();
            Siblington siblington = mapper.readValue(resp.getEntity().getContent(),
                    Siblington.class);
            return siblington.getSiblings();
        } catch (IOException e) {
            LOG.error(e.getMessage());
        } finally {
            method.releaseConnection();
				}
        return null;
	}

	@Override
    public List<CustomNode> getChildren(Node node, int offset, int limit) {
        return getChildren(node.getProperty("rdf:about") + "", offset, limit);
    }

    @Override
    public Node getParent(Node node) {
        List<Node> nodes = getRelatedNodes(node, 1, 0, Direction.OUTGOING,
				DCTERMSISPARTOFRELATION);
		if (nodes.size() > 0) {
			return nodes.get(0);
		}
		return null;
	}

	// note: first "/" in rdfAbout was removed; this is added again in the neo4j plugin
	@Override
    public List<CustomNode> getFollowingSiblings(String rdfAbout, int limit) {
        HttpGet method = new HttpGet(fixTrailingSlash(customPath)
                + "fetch/following/nodeId/"
                + StringUtils.replace(rdfAbout + "", "/", "%2F")
                + "?limit=" + limit);
        try {
            HttpResponse resp = client.execute(method);
            ObjectMapper mapper = new ObjectMapper();
            Siblington siblington = mapper.readValue(resp.getEntity().getContent(),
                    Siblington.class);
            return siblington.getSiblings();
        } catch (IOException e) {
            LOG.error(e.getMessage());
        } finally {
            method.releaseConnection();
	}
        return null;
	}

    @Override
    public List<CustomNode> getFollowingSiblings(Node node, int limit) {
        return getFollowingSiblings(node.getProperty("rdf:about") + "", limit);
    }

    // REMOVEME?
    private List<Node> getFollowingSiblings(Node node, int limit, int offset) {
        return getRelatedNodes(node, limit, offset, Direction.INCOMING,
				EDMISNEXTINSEQUENCERELATION);
	}

	// note: first "/" in rdfAbout was removed; this is added again in the neo4j plugin
	@Override
    public List<CustomNode> getPrecedingSiblings(String rdfAbout, int limit) {
        HttpGet method = new HttpGet(fixTrailingSlash(customPath)
                + "fetch/preceding/nodeId/"
                + StringUtils.replace(rdfAbout + "", "/", "%2F")
                + "?limit=" + limit);
        try {
            HttpResponse resp = client.execute(method);
            ObjectMapper mapper = new ObjectMapper();
            Siblington siblington = mapper.readValue(resp.getEntity().getContent(),
                    Siblington.class);
            return siblington.getSiblings();
        } catch (IOException e) {
            LOG.error(e.getMessage());
        } finally {
            method.releaseConnection();
	}
        return null;
	}

    @Override
    public List<CustomNode> getPrecedingSiblings(Node node, int limit) {
        return getPrecedingSiblings(node.getProperty("rdf:about") + "", limit);
    }

    // REMOVEME?
    private List<Node> getPrecedingSiblings(Node node, int limit, int offset) {
        return getRelatedNodes(node, limit, offset, Direction.OUTGOING,
				EDMISNEXTINSEQUENCERELATION);
	}

    // TODO REMOVEME (?)
    private List<Node> getRelatedNodes(Node node, int limit, int offset,
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
        Traverser tr = traversal.traverse(node);
		Iterator<Node> resIter = tr.nodes().iterator();

		int i = 1;
		while (resIter.hasNext()) {
            Node relatedNode = resIter.next();
			if (i >= offset) {
				if (children.size() <= limit) {
                    children.add(relatedNode);
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
    public long getChildrenCount(Node node) {

		// start n = node(id) match (n)-[:HAS_PART]->(part) RETURN COUNT(part)
		// as children
		ObjectNode obj = JsonNodeFactory.instance.objectNode();
		ArrayNode statements = JsonNodeFactory.instance.arrayNode();
		obj.put("statements", statements);
		ObjectNode statement = JsonNodeFactory.instance.objectNode();
		statement.put("statement",
						"start n = node:edmsearch2(rdf_about={from}) match (n)-[:`dcterms:hasPart`]->(part)" +
								" WHERE NOT ID(n)=ID(part) RETURN COUNT(part) as children");
//								" RETURN COUNT(part) as children");
		ObjectNode parameters = statement.with("parameters");
		statements.add(statement);
        parameters.put("from", (String) node.getProperty("rdf:about"));
		HttpPost httpMethod = new HttpPost(fixTrailingSlash(serverPath) + "transaction/commit");
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

	// note: first "/" in rdfAbout was removed; this is added again in the neo4j plugin
	@Override
    public Hierarchy getInitialStruct(String rdfAbout) throws Neo4JException {
        if (!isHierarchy(rdfAbout)) {
			return null;
		}
		HttpGet method = new HttpGet(fixTrailingSlash(customPath) + "initial/startup/nodeId/"
                + StringUtils.replace(rdfAbout, "/", "%2F"));
		LOG.info("path: " + method.getURI());
		try {
			HttpResponse resp = client.execute(method);
			if (resp.getStatusLine().getStatusCode() == 502){
				LOG.error(ProblemType.NEO4J_INCONSISTENT_DATA.getMessage() + " by Neo4J plugin, for node with ID: " + rdfAbout);
				throw new Neo4JException(ProblemType.NEO4J_INCONSISTENT_DATA,
						" \n\n... thrown by Neo4J plugin, for node with ID: " + rdfAbout);
			}
			ObjectMapper mapper = new ObjectMapper();
			Hierarchy obj = mapper.readValue(resp.getEntity().getContent(),
					Hierarchy.class);
			return obj;
		} catch (IOException e) {
			LOG.error(ProblemType.NEO4J_CANNOTGETNODE.getMessage() + " with ID: " + rdfAbout);
			throw new Neo4JException(ProblemType.NEO4J_CANNOTGETNODE, " with ID: " + rdfAbout);
		} finally {
			method.releaseConnection();
		}
	}

	@Override
	public String getCustomPath() {
		return customPath;
	}

	private String fixTrailingSlash(String path){
    	return path.endsWith("/") ? path : path + "/";
	}
}
