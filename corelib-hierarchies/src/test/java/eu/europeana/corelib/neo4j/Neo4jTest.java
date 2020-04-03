package eu.europeana.corelib.neo4j;

import org.apache.log4j.BasicConfigurator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assume;
import org.junit.BeforeClass;


//import eu.europeana.corelib.neo4j.exception.Neo4JException;
//import org.apache.commons.compress.utils.IOUtils;
//import org.apache.commons.io.FileUtils;
//import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
//import org.codehaus.jackson.node.TextNode;
//import org.codehaus.plexus.archiver.tar.TarGZipUnArchiver;
//import org.codehaus.plexus.logging.console.ConsoleLogger;
//import org.codehaus.plexus.util.StringUtils;
//import org.junit.*;
//import org.neo4j.graphdb.DynamicRelationshipType;
//import org.neo4j.graphdb.Node;
//import org.neo4j.graphdb.Transaction;
//import org.neo4j.rest.graphdb.RestGraphDatabase;
//
//import eu.europeana.corelib.definitions.solr.DocType;
//import eu.europeana.corelib.neo4j.entity.CustomNode;
//import eu.europeana.corelib.neo4j.entity.Hierarchy;
//import eu.europeana.corelib.neo4j.entity.Neo4jBean;
//import eu.europeana.corelib.neo4j.entity.Neo4jStructBean;
//import eu.europeana.corelib.neo4j.entity.Node2Neo4jBeanConverter;
//import eu.europeana.corelib.neo4j.server.Neo4jServer;
//import eu.europeana.corelib.neo4j.server.impl.Neo4jServerImpl;

/**
 * Unit tests for neo4j. This only works on linux computers
 *
 * @author Yorgos.Mamakis@ europeana.eu
 */
public class Neo4jTest {

    private static final Logger LOG = LogManager.getLogger(Neo4jTest.class);
    private static final String DB_SERVER_ADDRESS = "http://localhost:7474";
    private static final String DB_FOLDER = "/db/data/";

    private static Process     neo4j;
//    private static RestGraphDatabase db;
//    private static Neo4jServer server;

    /**
     * Run once at start of test. We want to make sure log4j is initiated
     */
    @BeforeClass
    public static void setup() {
        BasicConfigurator.configure();

        // skip all Neo4j tests on Windows computers
        String os = System.getProperty("os.name").toLowerCase();
        Assume.assumeFalse(os.contains("win"));

//        try {
//            TarGZipUnArchiver unzip = new TarGZipUnArchiver();
//            unzip.enableLogging(new ConsoleLogger(org.codehaus.plexus.logging.Logger.LEVEL_INFO, "UnArchiver"));
//            unzip.extract("src/test/resources/neo4j-community-2.1.5-unix.tar.gz", new File("src/test/resources"));
//
//            LOG.info("Creating neo4j process...");
//            // redirect error stream to prevent process from hanging in case of problems, see also http://stackoverflow.com/a/3285479/741249
//            ProcessBuilder pBuilder = new ProcessBuilder("neo4j-community-2.1.5/bin/neo4j", "start").redirectErrorStream(true);
//            neo4j = pBuilder.start();
//            LOG.info(new String(IOUtils.toByteArray(neo4j.getInputStream())));
//
//            db = new RestGraphDatabase(DB_SERVER_ADDRESS + DB_FOLDER);
//            LOG.info("Accessing Rest API...");
//            db.getRestAPI().index().forNodes("edmsearch2");
//            LOG.info("Preparing data...");
//            prepareData();
//
//            LOG.info("Starting server...");
//            server = new Neo4jServerImpl(DB_SERVER_ADDRESS + DB_FOLDER, "edmsearch2", DB_SERVER_ADDRESS);
//
//            LOG.info("Test is now prepared");
//        } catch (IOException e) {
//            LOG.error("Error starting neo4j", e);
//        }
    }

    /**
     * Shutdown neo4j and remove the temp folder
     */
    @AfterClass
    public static void destroy() {
//        try {
//            ProcessBuilder pBuilder = new ProcessBuilder("neo4j-community-2.1.5/bin/neo4j", "stop").redirectErrorStream(true);
//            neo4j = pBuilder.start();
//            LOG.info(new String(IOUtils.toByteArray(neo4j.getInputStream())));
//
//            FileUtils.deleteDirectory(new File("neo4j-community-2.1.5"));
//        } catch (IOException e) {
//            LOG.error("Error stopping neo4j", e);
//        }
    }

    private static void prepareData() {
//        List<Record>      records       = JsonReader.loadRecords();
//        Map<String, Node> nodes         = new HashMap<>();
//        Set<RelType>      relationships = new HashSet<>();
//        for (Record record : records) {
//            Map<String, Object> props = new HashMap<>();
//            props.put("rdf:about", record.getRdfAbout());
//            props.put("edm:type", record.getEdmType());
//            if (StringUtils.isNotEmpty(record.getHasParent())) {
//                props.put("hasParent", record.getHasParent());
//            }
//            if (StringUtils.isEmpty(record.getHasChildren().toString())) {
//                props.put("hasChildren", record.getHasChildren());
//            }
//            List<String> title = new ArrayList<>();
//            title.add(record.getDcTitle());
//            props.put("dc:title_xml:lang_en", title);
//            List<String> description = new ArrayList<>();
//            description.add(record.getDcDescription());
//            props.put("dc:description_xml:lang_en", description);
//            Transaction tx = db.beginTx();
//
//            Node node = db.getRestAPI().createNode(props);
//            tx.success();
//
//            tx = db.beginTx();
//            db.getRestAPI().addToIndex(node, db.getRestAPI().index().forNodes("edmsearch2"), "rdf_about", record.getRdfAbout());
//            nodes.put(record.getRdfAbout(), node);
//
//            if (record.getDcterms_hasPart() != null && record.getDcterms_hasPart().length > 0) {
//                for (String str : record.getDcterms_hasPart()) {
//                    if (StringUtils.isNotEmpty(str)) {
//                        RelType rel = new RelType();
//                        rel.fromNode = record.getRdfAbout();
//                        rel.toNode = str;
//                        rel.setRelType(DynamicRelationshipType.withName("dcterms:hasPart"));
//                        relationships.add(rel);
//                    }
//                }
//            }
//            if (StringUtils.isNotEmpty(record.getDcterms_isPartOf())) {
//                RelType rel = new RelType();
//                rel.fromNode = record.getRdfAbout();
//                rel.toNode = record.getDcterms_isPartOf();
//                rel.setRelType(DynamicRelationshipType.withName("dcterms:isPartOf"));
//                relationships.add(rel);
//            }
//            if (StringUtils.isNotEmpty(record.getEdmIsFirstInSequence())) {
//                RelType rel = new RelType();
//                rel.toNode = record.getEdmIsFirstInSequence();
//
//                rel.fromNode = record.getRdfAbout();
//                rel.setRelType(DynamicRelationshipType.withName("isFirstInSequence"));
//                relationships.add(rel);
//            }
//            if (StringUtils.isNotEmpty(record.getEdmIsLastInSequence())) {
//                RelType rel = new RelType();
//                rel.toNode = record.getEdmIsLastInSequence();
//
//                rel.fromNode = record.getRdfAbout();
//                rel.setRelType(DynamicRelationshipType.withName("isLastInSequence"));
//                relationships.add(rel);
//            }
//
//            if (StringUtils.isNotEmpty(record.getEdmIsNextInSequence())) {
//                RelType rel = new RelType();
//                rel.fromNode = record.getRdfAbout();
//                rel.toNode = record.getEdmIsNextInSequence();
//                rel.setRelType(DynamicRelationshipType.withName("edm:isNextInSequence"));
//                relationships.add(rel);
//            }
//            tx.success();
//        }
//
//        for (RelType rel : relationships) {
//            Node        from = nodes.get(rel.fromNode);
//            Node        to   = nodes.get(rel.toNode);
//            Transaction tx   = db.beginTx();
//            db.getRestAPI().createRelationship(from, to, rel.relType, null);
//            tx.success();
//        }
    }

    /**
     * Test Null object
     */
//    @Test
//    public void testNull() throws Neo4JException {
//        Node node = server.getNode("test");
//        Assert.assertNull(node);
//        Assert.assertNull(Node2Neo4jBeanConverter.toNeo4jBean(node, 1));
//    }

    /**
     * Test non null object
     */
//    @Test
//    public void testNotNull() throws Neo4JException {
//        Node node = server.getNode("uri2");
//        Assert.assertNotNull(node);
//        Neo4jBean bean = Node2Neo4jBeanConverter.toNeo4jBean(node, (server.getNodeIndex(node)));
//        Assert.assertNotNull(bean);
//
//        Assert.assertEquals(bean.getParent(), "uri0");
//        Assert.assertEquals(bean.getChildrenCount().longValue(), 0L);
//        Assert.assertEquals(bean.getType(), DocType.safeValueOf("TEXT"));
//        Map<String, List<String>> title     = new HashMap<>();
//        List<String>              titleList = new ArrayList<>();
//        titleList.add("testtitle2");
//        title.put("en", titleList);
//
//        Assert.assertEquals(bean.getTitle(), title);
//        Map<String, List<String>> description     = new HashMap<>();
//        List<String>              descriptionList = new ArrayList<>();
//        descriptionList.add("testdescription2");
//        description.put("en", descriptionList);
//        Assert.assertEquals(bean.getDescription(), description);
//        Assert.assertEquals(bean.getIndex().longValue(), 2);
//
//        Hierarchy hierarchy = server.getInitialStruct(bean.getId());
//        Assert.assertNotNull(hierarchy);
//        Neo4jStructBean structBean = Node2Neo4jBeanConverter.toNeo4jStruct(hierarchy, bean.getIndex());
//        Assert.assertNotNull(structBean);
//
//        Assert.assertEquals(structBean.getSingleNode().getId(), bean.getId());
//        Assert.assertEquals(structBean.getSingleNode().getIndex(), bean.getIndex());
//        Assert.assertEquals(structBean.getSingleNode().getChildrenCount(), bean.getChildrenCount());
//        Assert.assertEquals(structBean.getSingleNode().getDescription(), bean.getDescription());
//        Assert.assertEquals(structBean.getSingleNode().getTitle(), bean.getTitle());
//        Assert.assertEquals(structBean.getSingleNode().getType(), bean.getType());
//        Assert.assertEquals(structBean.getSingleNode().getParent(), bean.getParent());
//        Assert.assertEquals(structBean.getSingleNode(), bean);
//        Assert.assertEquals(structBean.getParents().size(), 1);
//        Node parent = server.getParent(node);
//        Assert.assertEquals(structBean.getParents().get(0), Node2Neo4jBeanConverter.toNeo4jBean(parent, server.getNodeIndex(parent)));
//        Neo4jBean child1 = Node2Neo4jBeanConverter.toNeo4jBean(server.getNode("uri1"), server.getNodeIndexByRdfAbout("uri1"));
//        Neo4jBean child2 = Node2Neo4jBeanConverter.toNeo4jBean(server.getNode("uri3"), server.getNodeIndexByRdfAbout("uri3"));
//        Neo4jBean child3 = Node2Neo4jBeanConverter.toNeo4jBean(server.getNode("uri4"), server.getNodeIndexByRdfAbout("uri4"));
//
//        Assert.assertEquals(structBean.getPrecedingSiblings().size(), 1);
//        Assert.assertEquals(structBean.getPrecedingSiblings().get(0), child1);
//        Assert.assertEquals(structBean.getFollowingSiblings().size(), 2);
//        Assert.assertEquals(structBean.getFollowingSiblings().get(0), child2);
//        Assert.assertEquals(structBean.getFollowingSiblings().get(1), child3);
//        Assert.assertEquals(server.getChildrenCount(parent), 4);
//        Assert.assertNotEquals(bean, child1);
//
//        List<CustomNode> nodePSList = server.getPrecedingSiblings(node, 10);
//        Assert.assertEquals(nodePSList.size(), 1);
//        Assert.assertEquals(child1, Node2Neo4jBeanConverter.toNeo4jBean(nodePSList.get(0), getCustomNodeIndex(nodePSList.get(0))));
//        List<CustomNode> nodeFSList = server.getFollowingSiblings(node, 10);
//        Assert.assertEquals(nodeFSList.size(), 2);
//        Assert.assertEquals(child2, Node2Neo4jBeanConverter.toNeo4jBean(nodeFSList.get(0), getCustomNodeIndex(nodeFSList.get(0))));
//        Assert.assertEquals(child3, Node2Neo4jBeanConverter.toNeo4jBean(nodeFSList.get(1), getCustomNodeIndex(nodeFSList.get(1))));
//        Assert.assertNotEquals(bean, child2);
//
//        List<CustomNode> children = server.getChildren(parent, 0, 10);
//        Assert.assertEquals(children.size(), server.getChildrenCount(parent));
//
//        Assert.assertEquals(child1, Node2Neo4jBeanConverter.toNeo4jBean(children.get(0), getCustomNodeIndex(children.get(0))));
//        Assert.assertEquals(bean, Node2Neo4jBeanConverter.toNeo4jBean(children.get(1), getCustomNodeIndex(children.get(1))));
//        Assert.assertEquals(child2, Node2Neo4jBeanConverter.toNeo4jBean(children.get(2), getCustomNodeIndex(children.get(2))));
//        Assert.assertEquals(child3, Node2Neo4jBeanConverter.toNeo4jBean(children.get(3), getCustomNodeIndex(children.get(3))));
//
//        List<CustomNode> children2 = server.getChildren(parent, 3, 10);
//        Assert.assertEquals(children2.size(), 1);
//        Assert.assertEquals(child3, Node2Neo4jBeanConverter.toNeo4jBean(children2.get(0), getCustomNodeIndex(children2.get(0))));
//
//
//        Assert.assertEquals(DB_SERVER_ADDRESS, server.getCustomPath());
//        Assert.assertNull(server.getParent(parent));
//    }

    /**
     * Test that an object belongs to hierarchy
     */
//    @Test
//    public void assertHierarchy() throws Neo4JException {
//        Assert.assertTrue(server.isHierarchy("uri4"));
//    }

    /**
     * Test that an object does not belong to hierarchy
     */
//    @Test
//    public void assertNotHierarchy() throws Neo4JException {
//        Assert.assertFalse(server.isHierarchy("test"));
//    }

    /**
     * Test null hierarchy
     */
//    @Test
//    public void assertNullHierarchy() throws Neo4JException {
//        Assert.assertNull(server.getInitialStruct("test"));
//    }

//    private Long getCustomNodeIndex(CustomNode node) throws Neo4JException {
//        return server.getNodeIndexByRdfAbout(((TextNode) node.getProperty("rdf:about")).asText());
//    }

    /**
     * Relationship holder
     *
     * @author Yorgos.Mamakis@ europeana.eu
     */
//    private static class RelType {
//        private String                  fromNode;
//        private String                  toNode;
//        private DynamicRelationshipType relType;
//
//        /**
//         * Set the relation type between 2 nodes
//         */
//        public void setRelType(DynamicRelationshipType relType) {
//            this.relType = relType;
//        }
//
//    }
}
