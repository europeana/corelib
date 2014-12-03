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
import org.codehaus.jackson.node.IntNode;
import org.codehaus.jackson.node.TextNode;
import org.neo4j.graphdb.Node;

import eu.europeana.corelib.definitions.solr.DocType;

/**
 * Converter from a Neo4j Node to Neo4jBean
 *
 * @author Yorgos.Mamakis@ europeana.eu
 */
public class Node2Neo4jBeanConverter {

	/**
	 * Convert a neo4j node to a Neo4jBean
	 * 
	 * @param node
	 *            The node to convert
	 * @param index
	 *            The index of the node
	 * @return The Neo4jBean representing the node
	 */
	public static Neo4jBean toNeo4jBean(Node node, long index) {
		if (node != null) {
			Neo4jBean neo4jBean = new Neo4jBean();
			neo4jBean.setId((String) node.getProperty("rdf:about"));
			neo4jBean.setType(DocType.valueOf(((String) node
					.getProperty("edm:type")).replace("\"", "")));
			neo4jBean.setHasChildren(node.hasProperty("hasChildren"));
			Map<String, List<String>> titles = new HashMap<String, List<String>>();
			Map<String, List<String>> descriptions = new HashMap<String, List<String>>();
			Iterable<String> keys = node.getPropertyKeys();
			Iterator<String> keyIterator = keys.iterator();
			while (keyIterator.hasNext()) {
				String key = keyIterator.next();
				String languageKey;
				if (key.startsWith("dc:description")) {
					languageKey = StringUtils.substringAfter(key,
							"dc:description_xml:lang_");
					List<String> descriptionValue = descriptions
							.get(languageKey);
					if (descriptionValue == null) {
						descriptionValue = new ArrayList<String>();
					}
					descriptionValue.addAll(Arrays.asList((String[]) node
							.getProperty(key)));
					descriptions.put(languageKey, descriptionValue);
				} else if (key.startsWith("dc:title")) {
					languageKey = StringUtils.substringAfter(key,
							"dc:title_xml:lang_");
					List<String> titleValue = titles.get(languageKey);
					if (titleValue == null) {
						titleValue = new ArrayList<String>();
					}
					titleValue.addAll(Arrays.asList((String[]) node
							.getProperty(key)));
					titles.put(languageKey, titleValue);
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

	/**
	 * Convert a hierarchy to a Neo4jStructBean
	 * 
	 * @param hierarchy
	 *            The hierarchy to convert
	 * @return The complex initial hierarchical structure
	 */
	public static Neo4jStructBean toNeo4jStruct(Hierarchy hierarchy) {
		Neo4jStructBean struct = new Neo4jStructBean();
		if (hierarchy == null || hierarchy.getParents().size() == 0) {
			return null;
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

	/**
	 * Convert a custom node as retrieved from Neo4j initial structure call to a
	 * Neo4jBean for inclusion in the hierarchy
	 * 
	 * @param node The CustomNode to convert
	 * @return The Neo4jBean
	 */
	@SuppressWarnings("unchecked")
	public static Neo4jBean toNeo4jBean(CustomNode node) {
		if (node != null) {
			Neo4jBean neo4jBean = new Neo4jBean();
			neo4jBean
					.setId(((TextNode) node.getProperty("rdf:about")).asText());
			neo4jBean.setType(DocType.safeValueOf(node.getProperty("edm:type")
					.toString().replace("\"", "")));

			neo4jBean.setHasChildren(node.hasProperty("hasChildren"));
			if (node.hasProperty("hasChildren")
					&& node.hasProperty("childrenCount")) {
				IntNode childrenCount = (IntNode) node
						.getProperty("childrenCount");
				neo4jBean.setChildrenCount(childrenCount.asLong());
			}
			Map<String, List<String>> titles = new HashMap<String, List<String>>();
			Map<String, List<String>> descriptions = new HashMap<String, List<String>>();

			Set<String> keySet = node.getPropertyKeys();
			for (String key : keySet) {
				String languageKey;
				if (key.startsWith("dc:description")) {
					languageKey = StringUtils.substringAfter(key,
							"dc:description_xml:lang_");
					List<String> descriptionValue = descriptions
							.get(languageKey);
					if (descriptionValue == null) {
						descriptionValue = new ArrayList<String>();
					}
					if (node.getProperty(key) instanceof ArrayNode) {
						descriptionValue = extractArrayNode(node, key);
					} else {
						descriptionValue.addAll((List<String>) node
								.getProperty(key));
					}
					descriptions.put(languageKey, descriptionValue);
				} else if (key.startsWith("dc:title")) {
					languageKey = StringUtils.substringAfter(key,
							"dc:title_xml:lang_");
					List<String> titleValue = titles.get(languageKey);
					if (titleValue == null) {
						titleValue = new ArrayList<String>();
					}
					if (node.getProperty(key) instanceof ArrayNode) {
						titleValue = extractArrayNode(node, key);
					} else {
						titleValue.addAll(Arrays.asList((String[]) node
								.getProperty(key)));
					}
					titles.put(languageKey, titleValue);
				}
			}
			neo4jBean.setTitle(titles);
			neo4jBean.setDescription(descriptions);
			if (node.hasProperty("hasParent")) {
				neo4jBean.setParent(node.getProperty("hasParent").toString());
			}
			neo4jBean.setIndex(Long.parseLong(node.getProperty("index")
					.toString()));
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
