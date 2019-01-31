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

import eu.europeana.corelib.neo4j.exception.Neo4JException;
import eu.europeana.corelib.neo4j.executor.*;
import org.neo4j.driver.v1.types.Node;
import org.neo4j.helpers.collection.Iterators;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import static org.neo4j.helpers.collection.MapUtil.map;

/**
 * Created by luthien on 29/01/2019.
 */
public class CypherService {

    private final CypherExecutor cypher;
    private final String index;

    public CypherService(String uri, String index) {
        this.index = index;
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
        Map nodeFound = findNode(rdfAbout);
//        if (nodeFound.)
//        Node node = null;
//        try {
//            IndexHits<Node> nodes = index.get("rdf_about", (!rdfAbout.startsWith("/") && (rdfAbout.contains("/") || rdfAbout.contains("%2F"))) ? "/" + rdfAbout : rdfAbout);
//            if (nodes.size() > 0 && hasRelationships(nodes)) {
//                node = nodes.getSingle();
//            }
//        } catch (Exception e) {
//            throw new Neo4JException(e, ProblemType.NEO4J_CANNOTGETNODE);
//        }
        return (Node) findNode(rdfAbout);
    }


    // TODO change to query index with apoc procedure over cypher
    public Map findNode(String rdfAbout) {
        if (rdfAbout==null) return Collections.emptyMap();
        return Iterators.singleOrNull(cypher.query(
                "CALL apoc.index.nodes('" + index +
                "', {rdfAboutQuery}) YIELD node AS record RETURN * LIMIT 1",
                map("rdfAboutQuery", "rdf_about:\"" + rdfAbout + "\"")));
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


}
