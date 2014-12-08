package eu.europeana.corelib.neo4j;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
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

import eu.europeana.corelib.neo4j.server.Neo4jServer;
import eu.europeana.corelib.neo4j.server.impl.Neo4jServerImpl;

public class Neo4jTest {
	private Process neo4j;
	private RestGraphDatabase db;
	private Neo4jServer server;
	@Before
	public void prepare() {
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
			db = new RestGraphDatabase("http://localhost:7474/db/data");
			db.getRestAPI().index().forNodes("edmsearch2");
			prepareData();
			server = new Neo4jServerImpl(
					"http://localhost:7474/db/data", "edmsearch2",
					"http://localhost:7474");
		} catch (IOException e) {
			e.printStackTrace();
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
				rel.toNode = record.getRdfAbout();
				rel.fromNode = record.getEdmIsFirstInSequence();
				rel.setRelType(DynamicRelationshipType
						.withName("isFirstInSequence"));
				relationships.add(rel);
			}
			if (StringUtils.isNotEmpty(record.getEdmIsLastInSequence())) {
				RelType rel = new RelType();
				rel.toNode = record.getRdfAbout();
				rel.fromNode = record.getEdmIsLastInSequence();
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

	@Test
	public void testNull() {
		Assert.assertNull(server.getNode("test"));
		
	}
	
	@Test
	public void assertHierarchy(){
		Assert.assertTrue(server.isHierarchy("uri4"));
	}
	
	@Test
	public void assertNotHierarch(){
		Assert.assertFalse(server.isHierarchy("tes"));
	}

	@After
	public void destroy() {
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

	private class RelType {
		private String fromNode;
		private String toNode;
		private DynamicRelationshipType relType;


		public void setRelType(DynamicRelationshipType relType) {
			this.relType = relType;
		}

	}
}
