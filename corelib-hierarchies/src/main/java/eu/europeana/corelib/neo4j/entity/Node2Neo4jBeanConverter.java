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
import org.codehaus.jackson.node.BooleanNode;
import org.neo4j.graphdb.Node;

import eu.europeana.corelib.definitions.solr.DocType;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.DynamicRelationshipType;

/**
 * Converter from a Neo4j Node to Neo4jBean
 *
 * @author Yorgos.Mamakis@ europeana.eu
 */
public class Node2Neo4jBeanConverter {

    /**
     * Convert a neo4j node to a Neo4jBean
     *
     * @param node  The node to convert
     * @param index The index of the node
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
                neo4jBean.setParent(StringUtils.replace((String) node.getProperty("hasParent"), "\\\"", ""));
            }
            neo4jBean.setIndex(index);
            if (node.hasRelationship(DynamicRelationshipType.withName("edm:isNextInSequance"), Direction.OUTGOING)) {
                neo4jBean.setRelBefore(Boolean.TRUE);
            } else if (node.hasRelationship(DynamicRelationshipType.withName("isFakeOrder"), Direction.OUTGOING)) {
                neo4jBean.setRelBefore(Boolean.FALSE);
            }
            return neo4jBean;
        }
        return null;
    }

    /**
     * Convert a neo4j node to a Neo4jBean keeping the original index
     *
     * @param node The node to convert
     * @return The Neo4jBean representing the node
     */
    public static Neo4jBean toNeo4jBean(Node node) {
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
                neo4jBean.setParent(StringUtils.replace((String) node.getProperty("hasParent"), "\\\"", ""));
            }
            neo4jBean.setIndex(Long.parseLong(node.getProperty("index").toString()));
            if (node.hasRelationship(DynamicRelationshipType.withName("edm:isNextInSequance"), Direction.OUTGOING)) {
                neo4jBean.setRelBefore(Boolean.TRUE);
            } else if (node.hasRelationship(DynamicRelationshipType.withName("isFakeOrder"), Direction.OUTGOING)) {
                neo4jBean.setRelBefore(Boolean.FALSE);
            }
            return neo4jBean;
        }
        return null;
    }

    /**
     * Convert a hierarchy to a Neo4jStructBean
     *
     * @param hierarchy The hierarchy to convert
     * @return The complex initial hierarchical structure
     */
    public static Neo4jStructBean toNeo4jStruct(Hierarchy hierarchy) {
        int i = 0, j = 0, k = 0;
//        BooleanNode booleanNode;
        Neo4jStructBean struct = new Neo4jStructBean();
        if (hierarchy == null || hierarchy.getParents().size() == 0) {
            return null;
        }

        List<CustomNode> parentNodes = hierarchy.getParents();
        List<CustomNode> pSiblingNodes = hierarchy.getPrecedingSiblings();
        List<CustomNode> fSiblingNodes = hierarchy.getFollowingSiblings();
        List<CustomNode> psChildNodes = hierarchy.getPrecedingSiblingChildren();
        List<CustomNode> fsChildNodes = hierarchy.getFollowingSiblingChildren();

        List<Neo4jBean> parentBeans = new ArrayList<>();
        List<Neo4jBean> pSiblingBeans = new ArrayList<>();
        List<Neo4jBean> fSiblingBeans = new ArrayList<>();
        List<Neo4jBean> psChildBeans = new ArrayList<>();
        List<Neo4jBean> fsChildBeans = new ArrayList<>();

        for (CustomNode parentNode : parentNodes) {
            if (i == 0) {
                struct.setSelf(toNeo4jBean(parentNode));
            } else {
                parentBeans.add(toNeo4jBean(parentNode));
            }
            i++;
        }

        for (CustomNode pSiblingNode : pSiblingNodes) {
            pSiblingBeans.add(toNeo4jBean(pSiblingNode));
            if (psChildNodes.size() > j && pSiblingNode.hasProperty("hasChildren") && ((BooleanNode) pSiblingNode.getProperty("hasChildren")).asBoolean()) {
                psChildBeans.add(toNeo4jBean(psChildNodes.get(j)));
                j++;
            } else {
                psChildBeans.add(createEmptyNeo4JBean());
            }
        }

        for (CustomNode fSiblingNode : fSiblingNodes) {
            fSiblingBeans.add(toNeo4jBean(fSiblingNode));
            if (fsChildNodes.size() > k && fSiblingNode.hasProperty("hasChildren") && ((BooleanNode) fSiblingNode.getProperty("hasChildren")).asBoolean()) {
                fsChildBeans.add(toNeo4jBean(fsChildNodes.get(k)));
                k++;
            } else {
                fsChildBeans.add(createEmptyNeo4JBean());
            }
        }

        struct.setParents(parentBeans);
        struct.setPrecedingSiblings(pSiblingBeans);
        struct.setFollowingSiblings(fSiblingBeans);
        struct.setPrecedingSiblingChildren(psChildBeans);
        struct.setFollowingSiblingChildren(fsChildBeans);
        return struct;
    }

    /**
     * Overridden, added long node.index Convert a hierarchy to a
     * Neo4jStructBean
     *
     * @param hierarchy The hierarchy to convert
     * @param index     the index of the corresponding Neo4j start node
     * @return The complex initial hierarchical structure
     */
    public static Neo4jStructBean toNeo4jStruct(Hierarchy hierarchy, long index) {
        int i = 0, j = 0, k = 0;
        Neo4jStructBean struct = new Neo4jStructBean();
        if (hierarchy == null || hierarchy.getParents().size() == 0) {
            return null;
        }

        List<CustomNode> parentNodes = hierarchy.getParents();
        List<CustomNode> pSiblingNodes = hierarchy.getPrecedingSiblings();
        List<CustomNode> fSiblingNodes = hierarchy.getFollowingSiblings();
        List<CustomNode> psChildNodes = hierarchy.getPrecedingSiblingChildren();
        List<CustomNode> fsChildNodes = hierarchy.getFollowingSiblingChildren();

        List<Neo4jBean> parentBeans = new ArrayList<>();
        List<Neo4jBean> pSiblingBeans = new ArrayList<>();
        List<Neo4jBean> fSiblingBeans = new ArrayList<>();
        List<Neo4jBean> psChildBeans = new ArrayList<>();
        List<Neo4jBean> fsChildBeans = new ArrayList<>();

        for (CustomNode parentNode : parentNodes) {
            if (i == 0) {
                struct.setSelf(toNeo4jBean(parentNode, index));
            } else {
                parentBeans.add(toNeo4jBean(parentNode, 1l));
            }
            i++;
        }

        long previousIndex = index;
        for (CustomNode pSiblingNode : pSiblingNodes) {
            previousIndex--;
            pSiblingBeans.add(toNeo4jBean(pSiblingNode, previousIndex));
            if (psChildNodes.size() > j && pSiblingNode.hasProperty("hasChildren") && ((BooleanNode) pSiblingNode.getProperty("hasChildren")).asBoolean()) {
                psChildBeans.add(toNeo4jBean(psChildNodes.get(j), 1l));
                j++;
            } else {
                psChildBeans.add(createEmptyNeo4JBean());
            }
        }

        long followingIndex = index;
        for (CustomNode fSiblingNode : fSiblingNodes) {
            followingIndex++;
            fSiblingBeans.add(toNeo4jBean(fSiblingNode, followingIndex));
            if (fsChildNodes.size() > k && fSiblingNode.hasProperty("hasChildren") && ((BooleanNode) fSiblingNode.getProperty("hasChildren")).asBoolean()) {
                fsChildBeans.add(toNeo4jBean(fsChildNodes.get(k), 1l));
                k++;
            } else {
                fsChildBeans.add(createEmptyNeo4JBean());
            }
        }

        struct.setParents(parentBeans);
        struct.setPrecedingSiblings(pSiblingBeans);
        struct.setFollowingSiblings(fSiblingBeans);
        struct.setPrecedingSiblingChildren(psChildBeans);
        struct.setFollowingSiblingChildren(fsChildBeans);
        return struct;
    }

    /**
     * Convert a custom node as retrieved from Neo4j initial structure call to a
     * Neo4jBean for inclusion in the hierarchy Note that at this point the
     * node's rdf:about identifier is renamed to 'id' in the Neo4jBean
     *
     * @param node the CustomNode to convert
     * @return the Neo4jBean
     */
    @SuppressWarnings("unchecked")
    public static Neo4jBean toNeo4jBean(CustomNode node) {
        if (node != null) {

            Neo4jBean neo4jBean = new Neo4jBean();
            neo4jBean.setId(((TextNode) node.getProperty("rdf:about")).asText());
            neo4jBean.setType(DocType.safeValueOf(node.getProperty("edm:type")
                    .toString().replace("\"", "")));
            neo4jBean.setHasChildren(node.hasProperty("hasChildren"));

            if (node.hasProperty("hasChildren") && node.hasProperty("childrenCount")) {
                IntNode childrenCount = (IntNode) node.getProperty("childrenCount");
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
                    List<String> descriptionValue = descriptions.get(languageKey);
                    if (descriptionValue == null) {
                        descriptionValue = new ArrayList<String>();
                    }
                    if (node.getProperty(key) instanceof ArrayNode) {
                        descriptionValue = extractArrayNode(node, key);
                    } else {
                        descriptionValue.addAll((List<String>) node.getProperty(key));
                    }
                    descriptions.put(languageKey, descriptionValue);
                } else if (key.startsWith("dc:title")) {
                    languageKey = StringUtils.substringAfter(key, "dc:title_xml:lang_");
                    List<String> titleValue = titles.get(languageKey);
                    if (titleValue == null) {
                        titleValue = new ArrayList<String>();
                    }
                    if (node.getProperty(key) instanceof ArrayNode) {
                        titleValue = extractArrayNode(node, key);
                    } else {
                        titleValue.addAll(Arrays.asList((String[]) node.getProperty(key)));
                    }
                    titles.put(languageKey, titleValue);
                }
            }
            neo4jBean.setTitle(titles);
            neo4jBean.setDescription(descriptions);
            if (node.hasProperty("hasParent")) {
                neo4jBean.setParent(StringUtils.replace(node.getProperty("hasParent").toString(), "\"", ""));
            }
            neo4jBean.setIndex(Long.parseLong(node.getProperty("index").toString()));
            if (node.hasProperty("relBefore")) {
                neo4jBean.setRelBefore(Boolean.parseBoolean(node.getProperty("relBefore").toString()));
            }
            return neo4jBean;
        }
        return null;
    }

    /**
     * Overridden, added long node.index Convert a custom node as retrieved from
     * Neo4j initial structure call to a Neo4jBean for inclusion in the
     * hierarchy Note that at this point the node's rdf:about identifier is
     * renamed to 'id' in the Neo4jBean
     *
     * @param node  The CustomNode to convert
     * @param index the index of the corresponding Neo4j node
     * @return The Neo4jBean
     */
    @SuppressWarnings("unchecked")
    public static Neo4jBean toNeo4jBean(CustomNode node, long index) {
        if (node != null) {
            Neo4jBean neo4jBean = new Neo4jBean();
            neo4jBean.setId(((TextNode) node.getProperty("rdf:about")).asText());
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
                neo4jBean.setParent(StringUtils.replace(node.getProperty("hasParent").toString(), "\"", ""));
            }
            neo4jBean.setIndex(index);
            if (node.hasProperty("relBefore")) {
                neo4jBean.setRelBefore(Boolean.parseBoolean(node.getProperty("relBefore").toString()));
            }
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

    private static Neo4jBean createEmptyNeo4JBean() {
        Neo4jBean neo4jBean = new Neo4jBean();
        final List<String> nullVal = new ArrayList<String>() {
            {
                add("(null)");
            }
        };
        Map<String, List<String>> titles = new HashMap<String, List<String>>() {
            {
                put("def", nullVal);
            }
        };
        neo4jBean.setId("(null)");
        neo4jBean.setType(DocType.safeValueOf("(null)"));
        neo4jBean.setTitle(titles);
        neo4jBean.setHasChildren(Boolean.FALSE);
        neo4jBean.setIndex(0l);
        neo4jBean.setParent("(null)");
        neo4jBean.setRelBefore(Boolean.FALSE);
        neo4jBean.setChildrenCount(0l);
        return neo4jBean;
    }
}
