package eu.europeana.corelib.neo4j.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.TextNode;
import org.neo4j.graphdb.Node;

import eu.europeana.corelib.definitions.solr.DocType;
import eu.europeana.corelib.logging.Logger;

/**
 * Converter from Node to Neo4jBean
 *
 * @author Yorgos.Mamakis@ europeana.eu
 */
public class Node2Neo4jBeanConverter {

	private static Logger log = Logger.getLogger(Node2Neo4jBeanConverter.class.getCanonicalName());

	public static Neo4jBean toNeo4jBean(Node node, long index) {
		if (node != null) {
			Neo4jBean neo4jBean = new Neo4jBean();
			neo4jBean.setId((String) node.getProperty("rdf:about"));
			neo4jBean.setType(DocType.valueOf((String) node
					.getProperty("edm:type")));
			neo4jBean.setHasChildren(node.hasProperty("hasChildren"));
			Map<String, List<String>> titles = new HashMap<String, List<String>>();
			Map<String, List<String>> descriptions = new HashMap<String, List<String>>();
			Iterable<String> keys = node.getPropertyKeys();
			Iterator<String> keyIterator = keys.iterator();
			while (keyIterator.hasNext()) {
				String key = keyIterator.next();
				if (key.startsWith("dc:description")) {
					List<String> descriptionValue = descriptions
							.get(StringUtils.substringAfter(key,
									"dc:description_xml:lang_"));
					if (descriptionValue == null) {
						descriptionValue = new ArrayList<String>();
					}
					descriptionValue.addAll((List<String>) node
							.getProperty(key));
					descriptions.put(StringUtils.substringAfter(key,
							"dc:description_xml:lang_"), descriptionValue);
				} else if (key.startsWith("dc:title")) {
					List<String> titleValue = titles.get(StringUtils
							.substringAfter(key, "dc:title_xml:lang_"));
					if (titleValue == null) {
						titleValue = new ArrayList<String>();
					}
					titleValue.addAll(Arrays.asList((String[]) node
							.getProperty(key)));
					titles.put(StringUtils.substringAfter(key,
							"dc:title_xml:lang_"), titleValue);
				}
			}
			neo4jBean.setTitle(titles);
			neo4jBean.setDescription(descriptions);
			if (node.hasProperty("hasParent")) {
				neo4jBean.setParent((String) node.getProperty("hasParent"));
			}
			neo4jBean.setIndex(index);
			return neo4jBean;
		}
		return null;
	}

	public static Neo4jStructBean toNeo4jStruct(Hierarchy hierarchy) {
		Neo4jStructBean struct = new Neo4jStructBean();
		if (hierarchy == null) {
			return struct;
		}

		List<CustomNode> parents = hierarchy.getParents();
		List<CustomNode> previous = hierarchy.getPreceedingSiblings();
		List<CustomNode> next = hierarchy.getFollowingSiblings();
		int i = 0;
		List<Neo4jBean> pars = new ArrayList<>();
		List<Neo4jBean> preceeding = new ArrayList<>();
		List<Neo4jBean> following = new ArrayList<>();
		for (CustomNode node : parents) {
			if (i == 0) {
				struct.setSelf(toNeo4jBean(node));
			} else {
				pars.add(toNeo4jBean(node));
			}
			i++;
		}

		for (CustomNode node : previous) {
			preceeding.add(toNeo4jBean(node));
		}

		for (CustomNode node : next) {
			following.add(toNeo4jBean(node));
		}

		struct.setParents(pars);
		struct.setPreceedingSiblings(preceeding);
		struct.setFollowingSiblings(following);
		return struct;
	}

	public static Neo4jBean toNeo4jBean(CustomNode node) {
		if (node != null) {
			Neo4jBean neo4jBean = new Neo4jBean();
			neo4jBean
					.setId(((TextNode) node.getProperty("rdf:about")).asText());
			neo4jBean.setType(DocType.safeValueOf(node.getProperty("edm:type")
					.toString()));
			neo4jBean.setHasChildren(node.hasProperty("hasChildren"));
			Map<String, List<String>> titles = new HashMap<String, List<String>>();
			Map<String, List<String>> descriptions = new HashMap<String, List<String>>();

			Set<String> keySet = node.getPropertyKeys();
			for (String key : keySet) {
				if (key.startsWith("dc:description")) {
					List<String> descriptionValue = descriptions
							.get(StringUtils.substringAfter(key,
									"dc:description_xml:lang_"));
					if (descriptionValue == null) {
						descriptionValue = new ArrayList<String>();
					}
					if (node.getProperty(key) instanceof ArrayNode) {
						descriptionValue = extractArrayNode(node, key);
					} else {
						descriptionValue.addAll((List<String>) node.getProperty(key));
					}
					descriptions.put(StringUtils.substringAfter(key,
							"dc:description_xml:lang_"), descriptionValue);
				} else if (key.startsWith("dc:title")) {
					List<String> titleValue = titles.get(StringUtils
							.substringAfter(key, "dc:title_xml:lang_"));
					if (titleValue == null) {
						titleValue = new ArrayList<String>();
					}
					if (node.getProperty(key) instanceof ArrayNode) {
						titleValue = extractArrayNode(node, key);
					} else {
						titleValue.addAll(Arrays.asList((String[]) node.getProperty(key)));
					}
					titles.put(StringUtils.substringAfter(key, "dc:title_xml:lang_"), titleValue);
				}
			}
			neo4jBean.setTitle(titles);
			neo4jBean.setDescription(descriptions);
			if (node.hasProperty("hasParent")) {
				neo4jBean.setParent(node.getProperty("hasParent").toString());
			}
			neo4jBean.setIndex(Long.parseLong(node.getProperty("index").toString()));
			return neo4jBean;
		}
		return null;
	}

	private static List<String> extractArrayNode(CustomNode node, String key) {
		List<String> values = new ArrayList<String>();
		int size = ((ArrayNode) node.getProperty(key)).size();
		for (int i = 0; i < size; i++) {
			values.add(((ArrayNode) node.getProperty(key)).get(i).asText());
		}
		return values;
	}

}
