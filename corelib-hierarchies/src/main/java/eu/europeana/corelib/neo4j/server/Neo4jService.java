package eu.europeana.corelib.neo4j.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.europeana.corelib.neo4j.entity.*;
import eu.europeana.corelib.neo4j.exception.Neo4JException;
import eu.europeana.corelib.web.exception.ProblemType;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.apache.http.client.HttpClient;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.neo4j.helpers.collection.MapUtil.map;

/**
 * Created by luthien on 29/01/2019.
 */
public class Neo4jService {

    private static final Logger LOG = LogManager.getLogger(Neo4jService.class);

    private final String pluginPath;
    private String neo4jUser;
    private String neo4jPassword;
    private HttpClient client;
    private boolean authenticated = true;

    public Neo4jService(String pluginPath, String user, String password) {
        this.pluginPath = pluginPath;
        this.neo4jUser = user;
        this.neo4jPassword = password;
        this.client = HttpClientBuilder.create().setConnectionManager(new PoolingHttpClientConnectionManager()).build();
    }

    public Neo4jService(String pluginPath) {
        authenticated = false;
        this.pluginPath = pluginPath;
        this.client = HttpClientBuilder.create().setConnectionManager(new PoolingHttpClientConnectionManager()).build();
    }

    public String getServerPath() {
        return pluginPath;
    }


    public List<CustomNode> getSingleNode(String rdfAbout) throws Neo4JException {
        HttpGet request = new HttpGet(fixTrailingSlash(pluginPath) + "fetch/self/rdfAbout/"
                + StringUtils.replace(rdfAbout + "", "/", "%2F"));
        if (authenticated) addAuthHeader(request);
        try {
            HttpResponse resp = executeRequest(request, rdfAbout);
            ObjectMapper mapper = new ObjectMapper();
            Selfington selfington = mapper.readValue(resp.getEntity().getContent(), Selfington.class);
            return selfington.getSelf();
        } catch (IOException e) {
            LOG.error(e.getMessage());
        } finally {
            request.releaseConnection();
        }
        return Collections.emptyList();
    }

    public List<CustomNode> getChildren(String rdfAbout, int offset, int limit) throws Neo4JException {
        return nodeFetcher("children", rdfAbout, offset, limit);
    }

    public List<CustomNode> getFollowingSiblings(String rdfAbout, int offset, int limit) throws Neo4JException {
        return nodeFetcher("following", rdfAbout, offset, limit);
    }

    public List<CustomNode> getPrecedingSiblings(String rdfAbout, int offset, int limit) throws Neo4JException {
        return nodeFetcher("preceding", rdfAbout, offset, limit);
    }

    private List<CustomNode> nodeFetcher(String path, String rdfAbout, int offset, int limit) throws Neo4JException {
        HttpGet request = new HttpGet(fixTrailingSlash(pluginPath)
                + "fetch/" + path + "/rdfAbout/"
                + StringUtils.replace(rdfAbout + "", "/", "%2F")
                + "?offset=" + offset + "&limit=" + limit);
        if (authenticated) addAuthHeader(request);
        try {
            HttpResponse resp = executeRequest(request, rdfAbout);
            ObjectMapper mapper = new ObjectMapper();
            Siblington siblington = mapper.readValue(resp.getEntity().getContent(), Siblington.class);
            return siblington.getSiblings();
        } catch (IOException e) {
            LOG.error(e.getMessage());
        } finally {
            request.releaseConnection();
        }
        return Collections.emptyList();
    }

    public Hierarchy getInitialStruct(String rdfAbout) throws Neo4JException {
        HttpGet request = new HttpGet(fixTrailingSlash(pluginPath) + "fetch/hierarchy/rdfAbout/"
                + StringUtils.replace(rdfAbout, "/", "%2F"));
        LOG.debug("path: {}", request.getURI());
        if (authenticated) addAuthHeader(request);
        try {
            HttpResponse resp = executeRequest(request, rdfAbout);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(resp.getEntity().getContent(), Hierarchy.class);
        } catch (IOException e) {
            LOG.error(ProblemType.NEO4J_GENERAL_FAILURE.getMessage());
            throw new Neo4JException(ProblemType.NEO4J_GENERAL_FAILURE);
        } finally {
            request.releaseConnection();
        }
    }

    private HttpResponse executeRequest(HttpGet request, String rdfAbout) throws Neo4JException, IOException {
        HttpResponse resp = client.execute(request);
        if (resp.getStatusLine().getStatusCode() == 404) {
            LOG.error(ProblemType.NEO4J_404.getMessage() + " " + rdfAbout);
            throw new Neo4JException(ProblemType.NEO4J_404, rdfAbout);
        } else if (resp.getStatusLine().getStatusCode() == 502) {
            LOG.error(ProblemType.NEO4J_502.getMessage() + " " + rdfAbout);
            throw new Neo4JException(ProblemType.NEO4J_502, rdfAbout);
        } else if (resp.getStatusLine().getStatusCode() == 500) {
            LOG.error(ProblemType.NEO4J_500.getMessage() + " " + rdfAbout);
            throw new Neo4JException(ProblemType.NEO4J_500, rdfAbout);
        }
        return resp;
    }

    private void addAuthHeader(HttpGet httpGet){
        String auth = neo4jUser + ":" + neo4jPassword;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));
        String authHeader = "Basic " + new String(encodedAuth);
        httpGet.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
    }

    private String fixTrailingSlash(String path) {
        return path.endsWith("/") ? path : path + "/";
    }
}
