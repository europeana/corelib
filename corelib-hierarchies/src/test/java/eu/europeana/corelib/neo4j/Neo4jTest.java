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
package eu.europeana.corelib.neo4j;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import eu.europeana.corelib.definitions.exception.Neo4JException;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.node.TextNode;
import org.codehaus.plexus.archiver.tar.TarGZipUnArchiver;
import org.codehaus.plexus.logging.console.ConsoleLogger;
import org.codehaus.plexus.util.StringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.rest.graphdb.RestGraphDatabase;

import eu.europeana.corelib.definitions.solr.DocType;
import eu.europeana.corelib.neo4j.entity.CustomNode;
import eu.europeana.corelib.neo4j.entity.Hierarchy;
import eu.europeana.corelib.neo4j.entity.Neo4jBean;
import eu.europeana.corelib.neo4j.entity.Neo4jStructBean;
import eu.europeana.corelib.neo4j.entity.Node2Neo4jBeanConverter;
import eu.europeana.corelib.neo4j.server.Neo4jServer;
import eu.europeana.corelib.neo4j.server.impl.Neo4jServerImpl;

/**
 * Unit tests for neo4j
 * @author Yorgos.Mamakis@ europeana.eu
 *
 */
public class Neo4jTest {
	private static Process neo4j;
	private static RestGraphDatabase db;
	private static Neo4jServer server;
	private static int no_of_tests;
	private static int testCount;
	private static boolean dataLoaded = false;

	/**
	 * Unzip neo4j server, load and index data. This should happen only once
	 */
	@Before
	public void prepare() {
		if (!dataLoaded) {
			TarGZipUnArchiver unzip = new TarGZipUnArchiver();
			unzip.enableLogging(new ConsoleLogger(
					org.codehaus.plexus.logging.Logger.LEVEL_INFO, "UnArchiver"));
			try {

				unzip.extract(
						"src/test/resources/neo4j-community-2.1.2-unix.tar.gz",
						new File("src/test/resources"));
				neo4j = new ProcessBuilder("neo4j-community-2.1.2/bin/neo4j",
						"start").start();
				System.out.println(new String(IOUtils.toByteArray(neo4j
						.getInputStream())));
				db = new RestGraphDatabase("http://localhost:7474/db/data/");
				db.getRestAPI().index().forNodes("edmsearch2");
				prepareData();
				server = new Neo4jServerImpl("http://localhost:7474/db/data/",
						"edmsearch2", "http://localhost:7474");
				for (Method method : this.getClass().getMethods()) {
					for (Annotation annotation : method.getAnnotations()) {
						if (annotation.annotationType().equals(
								org.junit.Test.class)) {
							no_of_tests++;
						}
					}
				}
				dataLoaded = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void prepareData() {
		List<Record> records = JsonReader.loadRecords();
		Map<String, Node> nodes = new HashMap<>();
		Set<RelType> relationships = new HashSet<>();
		for (Record record : records) {
			Map<String, Object> props = new HashMap<>();
			props.put("rdf:about", record.getRdfAbout());
			props.put("edm:type", record.getEdmType());
			if (StringUtils.isNotEmpty(record.getHasParent())) {
				props.put("hasParent", record.getHasParent());
			}
			if (StringUtils.isEmpty(record.getHasChildren().toString())) {
				props.put("hasChildren", record.getHasChildren());
			}
			List<String> title = new ArrayList<>();
			title.add(record.getDcTitle());
			props.put("dc:title_xml:lang_en", title);
			List<String> description = new ArrayList<>();
			description.add(record.getDcDescription());
			props.put("dc:description_xml:lang_en", description);
			Transaction tx = db.beginTx();

			Node node = db.getRestAPI().createNode(props);
			tx.success();
			tx = db.beginTx();
			db.getRestAPI().addToIndex(node,
					db.getRestAPI().index().forNodes("edmsearch2"),
					"rdf_about", record.getRdfAbout());
			nodes.put(record.getRdfAbout(), node);

			if (record.getDcterms_hasPart() != null
					&& record.getDcterms_hasPart().length > 0) {
				for (String str : record.getDcterms_hasPart()) {
					if (StringUtils.isNotEmpty(str)) {
						RelType rel = new RelType();
						rel.fromNode = record.getRdfAbout();
						rel.toNode = str;
						rel.setRelType(DynamicRelationshipType
								.withName("dcterms:hasPart"));
						relationships.add(rel);
					}
				}
			}
			if (StringUtils.isNotEmpty(record.getDcterms_isPartOf())) {
				RelType rel = new RelType();
				rel.fromNode = record.getRdfAbout();
				rel.toNode = record.getDcterms_isPartOf();
				rel.setRelType(DynamicRelationshipType
						.withName("dcterms:isPartOf"));
				relationships.add(rel);
			}
			if (StringUtils.isNotEmpty(record.getEdmIsFirstInSequence())) {
				RelType rel = new RelType();
				rel.toNode = record.getEdmIsFirstInSequence();
						
				rel.fromNode = record.getRdfAbout();
				rel.setRelType(DynamicRelationshipType
						.withName("isFirstInSequence"));
				relationships.add(rel);
			}
			if (StringUtils.isNotEmpty(record.getEdmIsLastInSequence())) {
				RelType rel = new RelType();
				rel.toNode = record.getEdmIsLastInSequence();
						
				rel.fromNode = record.getRdfAbout();
				rel.setRelType(DynamicRelationshipType
						.withName("isLastInSequence"));
				relationships.add(rel);
			}

			if (StringUtils.isNotEmpty(record.getEdmIsNextInSequence())) {
				RelType rel = new RelType();
				rel.fromNode = record.getRdfAbout();
				rel.toNode = record.getEdmIsNextInSequence();
				rel.setRelType(DynamicRelationshipType
						.withName("edm:isNextInSequence"));
				relationships.add(rel);
			}
			tx.success();
		}

		for (RelType rel : relationships) {
			Node from = nodes.get(rel.fromNode);
			Node to = nodes.get(rel.toNode);
			Transaction tx = db.beginTx();
			db.getRestAPI().createRelationship(from, to, rel.relType, null);
			tx.success();
		}
	}

	/**
	 * Test Null object
	 */
	@Test
	public void testNull() throws Neo4JException {
		testCount++;
		Node node = server.getNode("test");
		Assert.assertNull(node);
		Assert.assertNull(Node2Neo4jBeanConverter.toNeo4jBean(node, 1));
	}

	/**
	 * Test non null object
	 */
	@Test
	public void testNotNull() throws Neo4JException {
		testCount++;
		Node node = server.getNode("uri2");
		Assert.assertNotNull(node);
		Neo4jBean bean = Node2Neo4jBeanConverter.toNeo4jBean(node, (server.getNodeIndex(node)));
		Assert.assertNotNull(bean);

		Assert.assertEquals(bean.getParent(), "uri0");
		Assert.assertEquals(bean.getChildrenCount().longValue(), 0L);
		Assert.assertEquals(bean.getType(), DocType.safeValueOf("TEXT"));
		Map<String, List<String>> title = new HashMap<>();
		List<String> titleList = new ArrayList<>();
		titleList.add("testtitle2");
		title.put("en", titleList);

		Assert.assertEquals(bean.getTitle(), title);
		Map<String, List<String>> description = new HashMap<>();
		List<String> descriptionList = new ArrayList<>();
		descriptionList.add("testdescription2");
		description.put("en", descriptionList);
		Assert.assertEquals(bean.getDescription(), description);
		Assert.assertEquals(bean.getIndex().longValue(), 2);

		Hierarchy hierarchy = server.getInitialStruct(bean.getId());
		Assert.assertNotNull(hierarchy);
		Neo4jStructBean structBean = Node2Neo4jBeanConverter.toNeo4jStruct(hierarchy, bean.getIndex());
		Assert.assertNotNull(structBean);

		Assert.assertEquals(structBean.getSelf().getId(), bean.getId());
		Assert.assertEquals(structBean.getSelf().getIndex(), bean.getIndex());
		Assert.assertEquals(structBean.getSelf().getChildrenCount(), bean.getChildrenCount());
		Assert.assertEquals(structBean.getSelf().getDescription(), bean.getDescription());
		Assert.assertEquals(structBean.getSelf().getTitle(), bean.getTitle());
		Assert.assertEquals(structBean.getSelf().getType(), bean.getType());
		Assert.assertEquals(structBean.getSelf().getParent(), bean.getParent());
		Assert.assertEquals(structBean.getSelf(), bean);
		Assert.assertEquals(structBean.getParents().size(), 1);
		Node parent = server.getParent(node);
		Assert.assertEquals(
				structBean.getParents().get(0),
				Node2Neo4jBeanConverter.toNeo4jBean(parent, server.getNodeIndex(parent)));
		Neo4jBean child1 = Node2Neo4jBeanConverter.toNeo4jBean(
				server.getNode("uri1"),
				server.getNodeIndexByRdfAbout("uri1"));
		Neo4jBean child2 = Node2Neo4jBeanConverter.toNeo4jBean(
				server.getNode("uri3"),
				server.getNodeIndexByRdfAbout("uri3"));
		Neo4jBean child3 = Node2Neo4jBeanConverter.toNeo4jBean(
				server.getNode("uri4"),
				server.getNodeIndexByRdfAbout("uri4"));

		Assert.assertEquals(structBean.getPrecedingSiblings().size(), 1);
		Assert.assertEquals(structBean.getPrecedingSiblings().get(0), child1);
		Assert.assertEquals(structBean.getFollowingSiblings().size(), 2);
		Assert.assertEquals(structBean.getFollowingSiblings().get(0), child2);
		Assert.assertEquals(structBean.getFollowingSiblings().get(1), child3);
		Assert.assertEquals(server.getChildrenCount(parent), 4);
		Assert.assertNotEquals(bean, child1);

		List<CustomNode> nodePSList = server.getPrecedingSiblings(node, 10);
		Assert.assertEquals(nodePSList.size(), 1);
		Assert.assertEquals(child1, Node2Neo4jBeanConverter.toNeo4jBean(nodePSList.get(0), getCustomNodeIndex(nodePSList.get(0))));
		List<CustomNode> nodeFSList = server.getFollowingSiblings(node, 10);
		Assert.assertEquals(nodeFSList.size(), 2);
		Assert.assertEquals(child2, Node2Neo4jBeanConverter.toNeo4jBean(nodeFSList.get(0), getCustomNodeIndex(nodeFSList.get(0))));
		Assert.assertEquals(child3, Node2Neo4jBeanConverter.toNeo4jBean(nodeFSList.get(1), getCustomNodeIndex(nodeFSList.get(1))));
		Assert.assertNotEquals(bean,child2);
		
		List<CustomNode> children = server.getChildren(parent, 0, 10);
		Assert.assertEquals(children.size(),server.getChildrenCount(parent));

		Assert.assertEquals(child1, Node2Neo4jBeanConverter.toNeo4jBean(children.get(0), getCustomNodeIndex(children.get(0))));
		Assert.assertEquals(bean, Node2Neo4jBeanConverter.toNeo4jBean(children.get(1), getCustomNodeIndex(children.get(1))));
		Assert.assertEquals(child2, Node2Neo4jBeanConverter.toNeo4jBean(children.get(2), getCustomNodeIndex(children.get(2))));
		Assert.assertEquals(child3, Node2Neo4jBeanConverter.toNeo4jBean(children.get(3), getCustomNodeIndex(children.get(3))));
		
		List<CustomNode> children2 = server.getChildren(parent, 3, 10);
		Assert.assertEquals(children2.size(), 1);
		Assert.assertEquals(child3, Node2Neo4jBeanConverter.toNeo4jBean(children2.get(0), getCustomNodeIndex(children2.get(0))));

		
		Assert.assertEquals("http://localhost:7474", server.getCustomPath());
		Assert.assertNull(server.getParent(parent));
	}

	/**
	 * Test that an object belongs to hierarchy
	 */
	@Test
	public void assertHierarchy() throws Neo4JException {
		testCount++;
		Assert.assertTrue(server.isHierarchy("uri4"));
	}
	/**
	 * Test that an object does not belong to hierarchy
	 */
	@Test
	public void assertNotHierarchy() throws Neo4JException {
		testCount++;
		Assert.assertFalse(server.isHierarchy("test"));
	}
	
	/**
	 * Test null hierarchy
	 */
	@Test
	public void assertNullHierarchy() throws Neo4JException {
		testCount++;
		Assert.assertNull(server.getInitialStruct("test"));
	}

	/**
	 * Shutdown neo4j and remove the temp folder
	 */
	@After
	public void destroy() {
		if (testCount == no_of_tests) {
			try {
				neo4j = new ProcessBuilder("neo4j-community-2.1.2/bin/neo4j",
						"stop").start();
				System.out.println(new String(IOUtils.toByteArray(neo4j
						.getInputStream())));
				FileUtils.deleteDirectory(new File("neo4j-community-2.1.2"));

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private Long getCustomNodeIndex(CustomNode node) throws Neo4JException {
		return server.getNodeIndexByRdfAbout(((TextNode) node.getProperty("rdf:about")).asText()) ;
	}

	/**
	 * Relationship holder
	 * @author Yorgos.Mamakis@ europeana.eu
	 *
	 */
	private class RelType {
		private String fromNode;
		private String toNode;
		private DynamicRelationshipType relType;
		
		/**
		 * Set the relation type between 2 nodes
		 */
		public void setRelType(DynamicRelationshipType relType) {
			this.relType = relType;
		}

	}
}
