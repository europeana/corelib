/*
 * Copyright 2007-2019 The Europeana Foundation
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

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.europeana.corelib.neo4j.entity.*;
import eu.europeana.corelib.neo4j.exception.Neo4JException;
import eu.europeana.corelib.neo4j.executor.*;
import eu.europeana.corelib.web.exception.ProblemType;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.neo4j.driver.v1.types.Node;
import org.neo4j.helpers.collection.Iterators;

import org.apache.http.client.HttpClient;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import static org.neo4j.helpers.collection.MapUtil.map;

/**
 * Created by luthien on 29/01/2019.
 */
public class CypherService {

    private final static Logger LOG = LogManager.getLogger(CypherService.class);

    private final CypherExecutor cypher;
    private final String index;
    private final String pluginPath;
    private HttpClient client;

    public CypherService(String uri, String index, String pluginPath) {
        this.index = index;
        this.pluginPath = pluginPath;
        cypher = createCypherExecutor(uri);
    }

    private CypherExecutor createCypherExecutor(String uri) {
        try {
            String auth = new URL(uri.replace("bolt","http")).getUserInfo();
            if (auth != null) {
                String[] parts = auth.split(":");
                return new BoltCypherExecutor(uri,parts[0],parts[1]);
            }
            return new BoltCypherExecutor(uri);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid Neo4j-ServerURL " + uri);
        }
    }

    public Node getNode(String rdfAbout) throws Neo4JException {
        Map nodeMap = findNode(rdfAbout);
        if (!nodeMap.isEmpty() && nodeMap.containsKey("record")){
            return (Node) nodeMap.get("record");
        } else {
            throw new Neo4JException(ProblemType.NEO4J_CANNOTGETNODE);
        }
    }

    public Map findNode(String rdfAbout) {
        if (rdfAbout==null) return Collections.emptyMap();
        return Iterators.singleOrNull(cypher.query(
                "CALL apoc.index.nodes('" + index +
                "', {rdfAboutQuery}) YIELD node AS record RETURN * LIMIT 1",
                map("rdfAboutQuery", "rdf_about:\"" + rdfAbout + "\"")));
    }


    public List<CustomNode> getSingleNode(String rdfAbout) {
        HttpGet method = new HttpGet(fixTrailingSlash(pluginPath) + "fetch/self/rdfAbout/"
                + StringUtils.replace(rdfAbout + "", "/", "%2F"));
        try {
            HttpResponse resp   = client.execute(method);
            ObjectMapper mapper = new ObjectMapper();
            Selfington selfington = mapper.readValue(resp.getEntity().getContent(), Selfington.class);
            return selfington.getSelf();
        } catch (IOException e) {
            LOG.error(e.getMessage());
        } finally {
            method.releaseConnection();
        }
        return null;
    }

    public List<CustomNode> getChildren(String rdfAbout, int offset, int limit) {
        return nodeFetcher("children", rdfAbout, offset, limit);
    }

    public List<CustomNode> getFollowingSiblings(String rdfAbout, int limit) {
        return getFollowingSiblings(rdfAbout, 0, limit);
    }

    public List<CustomNode> getFollowingSiblings(String rdfAbout, int offset, int limit) {
        return nodeFetcher("following", rdfAbout, offset, limit);
    }

    public List<CustomNode> getPrecedingSiblings(String rdfAbout, int limit) {
        return getPrecedingSiblings(rdfAbout, 0, limit);
    }

    public List<CustomNode> getPrecedingSiblings(String rdfAbout, int offset, int limit) {
        return nodeFetcher("preceding", rdfAbout, offset, limit);
    }

    private List<CustomNode> nodeFetcher(String path, String rdfAbout, int offset, int limit){
        HttpGet method = new HttpGet(fixTrailingSlash(pluginPath)
                + "fetch/" + path + "/rdfAbout/"
                + StringUtils.replace(rdfAbout + "", "/", "%2F")
                + "?offset=" + offset + "&limit=" + limit);
        try {
            HttpResponse resp   = client.execute(method);
            ObjectMapper mapper = new ObjectMapper();
            Siblington siblington = mapper.readValue(resp.getEntity().getContent(), Siblington.class);
            return siblington.getSiblings();
        } catch (IOException e) {
            LOG.error(e.getMessage());
        } finally {
            method.releaseConnection();
        }
        return null;
    }

    public long getNodeIndex(String rdfAbout) {
        HttpGet method = new HttpGet(fixTrailingSlash(pluginPath) + "count/hierarchy/rdfAbout/" + rdfAbout);
        try {
            HttpResponse resp = client.execute(method);
            IndexObject obj = new ObjectMapper().readValue(resp.getEntity().getContent(), IndexObject.class);
            return obj.getLength();
        } catch (IOException e) {
            LOG.error(e.getMessage());
        } finally {
            method.releaseConnection();
        }
        return 0;
    }

    public Hierarchy getInitialStruct(String rdfAbout) throws Neo4JException {
        if (!isHierarchy(rdfAbout)) {
            return null;
        }
        HttpGet method = new HttpGet(fixTrailingSlash(pluginPath) + "fetch/hierarchy/rdfAbout/"
                + StringUtils.replace(rdfAbout, "/", "%2F"));
        LOG.debug("path: {}", method.getURI());
        try {
            HttpResponse resp = client.execute(method);
            if (resp.getStatusLine().getStatusCode() == 502) {
                LOG.error(ProblemType.NEO4J_INCONSISTENT_DATA.getMessage() + " by Neo4J plugin, for node with ID: " + rdfAbout);
                throw new Neo4JException(ProblemType.NEO4J_INCONSISTENT_DATA, " \n\n... thrown by Neo4J plugin, for node with ID: " + rdfAbout);
            }
            ObjectMapper mapper = new ObjectMapper();
            Hierarchy obj = mapper.readValue(resp.getEntity().getContent(), Hierarchy.class);
            return obj;
        } catch (IOException e) {
            LOG.error(ProblemType.NEO4J_CANNOTGETNODE.getMessage() + " with ID: " + rdfAbout);
            throw new Neo4JException(ProblemType.NEO4J_CANNOTGETNODE, " with ID: " + rdfAbout);
        } finally {
            method.releaseConnection();
        }
    }

    public boolean isHierarchy(String rdfAbout) {
        return getSingleNode(rdfAbout) != null;
    }


    // TODO change to query index with apoc procedure over cypher
    @SuppressWarnings("unchecked")
    public Iterable<Map<String,Object>> search(String query) {
        if (query==null || query.trim().isEmpty()) return Collections.emptyList();
        return Iterators.asCollection(cypher.query(
                "MATCH (movie:Movie)\n" +
                " WHERE lower(movie.title) CONTAINS {part}\n" +
                " RETURN movie",
                map("part", query.toLowerCase())));
    }

    // TODO change to query index with apoc procedure over cypher
    @SuppressWarnings("unchecked")
    public Map<String, Object> graph(int limit) {
        Iterator<Map<String,Object>> result = cypher.query(
                "MATCH (m:Movie)<-[:ACTED_IN]-(a:Person) " +
                " RETURN m.title as movie, collect(a.name) as cast " +
                " LIMIT {limit}", map("limit",limit));
        List nodes = new ArrayList();
        List rels= new ArrayList();
        int i=0;
        while (result.hasNext()) {
            Map<String, Object> row = result.next();
            nodes.add(map("title",row.get("movie"),"label","movie"));
            int target=i;
            i++;
            for (Object name : (Collection) row.get("cast")) {
                Map<String, Object> actor = map("title", name,"label","actor");
                int source = nodes.indexOf(actor);
                if (source == -1) {
                    nodes.add(actor);
                    source = i++;
                }
                rels.add(map("source",source,"target",target));
            }
        }
        return map("nodes", nodes, "links", rels);
    }



    private String fixTrailingSlash(String path) {
        return path.endsWith("/") ? path : path + "/";
    }
}
