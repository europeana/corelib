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
package eu.europeana.corelib.neo4j.entity;

import eu.europeana.corelib.definitions.solr.DocType;
import org.apache.commons.lang3.StringUtils;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.TextNode;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Node;

import java.util.*;

/**
 * Converter from a Neo4j Node to Neo4jBean
 *
 * @author Yorgos.Mamakis@ europeana.eu
 * @author Luthien
 */
public class Node2Neo4jBeanConverter {

    private Node2Neo4jBeanConverter(){}

    private static final String KEYXMLLANG          = "_xml:lang_";
    private static final String RDFABOUT            = "rdf:about";
    private static final String EDMTYPE             = "edm:type";
    private static final String HASCHILDREN         = "hasChildren";
    private static final String HASPARENT           = "hasParent";
    private static final String EDMISNEXTINSEQUENCE = "edm:isNextInSequence";
    private static final String ISFAKEORDER         = "isFakeOrder";
    private static final String TRUE                = "true";
    private static final String CHILDRENCOUNT       = "childrenCount";
    private static final String RELBEFORE           = "relBefore";


    /**
     * Convert a neo4j node to a Neo4jBean
     *
     * @param node The node to convert
     * @param index The index of the node
     * @return The Neo4jBean representing the node
     */
    public static Neo4jBean toNeo4jBean(Node node, long index) {
        if (node != null) {
            Neo4jBean neo4jBean = new Neo4jBean();
            neo4jBean.setId((String) node.getProperty(RDFABOUT));
            neo4jBean.setType(DocType.valueOf(((String) node.getProperty(EDMTYPE)).replace("\"", "")));
            neo4jBean.setHasChildren(node.hasProperty(HASCHILDREN));

            neo4jBean = retrieveLangAwareProperties(neo4jBean, node);

            if (node.hasProperty(HASPARENT)) {
                neo4jBean.setParent(StringUtils.replace((String) node.getProperty(HASPARENT), "\\\"", ""));
            }
            neo4jBean.setIndex(index);
            if (node.hasRelationship(RelationshipType.withName(EDMISNEXTINSEQUENCE), Direction.OUTGOING)) {
                neo4jBean.setRelBefore(Boolean.TRUE);
            } else if (node.hasRelationship(RelationshipType.withName(ISFAKEORDER), Direction.OUTGOING)) {
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
    // TODO check if this method (not provinding an index) can be deprecated
    public static Neo4jBean toNeo4jBean(Node node) {
        if (node != null) {
            Neo4jBean neo4jBean = new Neo4jBean();
            neo4jBean.setId((String) node.getProperty(RDFABOUT));
            neo4jBean.setType(DocType.valueOf(((String) node
                    .getProperty(EDMTYPE)).replace("\"", "")));
            neo4jBean.setHasChildren(node.hasProperty(HASCHILDREN));

            neo4jBean = retrieveLangAwareProperties(neo4jBean, node);

            if (node.hasProperty(HASPARENT)) {
                neo4jBean.setParent(StringUtils.replace((String) node.getProperty(HASPARENT), "\\\"", ""));
            }
            if (node.hasRelationship(RelationshipType.withName("edm:isNextInSequance"), Direction.OUTGOING)) {
                neo4jBean.setRelBefore(Boolean.TRUE);
            } else if (node.hasRelationship(RelationshipType.withName(ISFAKEORDER), Direction.OUTGOING)) {
                neo4jBean.setRelBefore(Boolean.FALSE);
            }
            return neo4jBean;
        }
        return null;
    }

    @SuppressWarnings("Duplicates")
    private static Neo4jBean retrieveLangAwareProperties(Neo4jBean bean, Node node){
        Map<String, List<String>> titles = new HashMap<>();
        Map<String, List<String>> descriptions = new HashMap<>();
        for (String key : node.getPropertyKeys()){
            if (key.startsWith("dc:description")) {
                descriptions = getLangAwareProperties(key, StringUtils.substringAfter(key, "dc:description" + KEYXMLLANG)
                        , descriptions, node);
            } else if (key.startsWith("dc:title")) {
                titles = getLangAwareProperties(key, StringUtils.substringAfter(key, "dc:title" + KEYXMLLANG)
                        , titles, node);
            }
        }
        bean.setTitle(titles);
        bean.setDescription(descriptions);
        return bean;
    }

    private static Map<String, List<String>> getLangAwareProperties(String key, String lang
            , Map<String, List<String>> langAwareProperties, Node node){
        List<String> langAwareValues = langAwareProperties.get(lang) != null ? langAwareProperties.get(lang) : new ArrayList<>();
        langAwareValues.addAll(Arrays.asList((String[]) node.getProperty(key)));
        langAwareProperties.put(lang, langAwareValues);
        return langAwareProperties;
    }

    /**
     * Convert a hierarchy to a Neo4jStructBean
     *
     * @param hierarchy The hierarchy to convert
     * @return The complex initial hierarchical structure
     */
    // TODO check if this method (not provinding an index) can be deprecated
    @SuppressWarnings("Duplicates")
    public static Neo4jStructBean toNeo4jStruct(Hierarchy hierarchy) {
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
                struct.setSelf(toNeo4jBean(parentNode));
            } else {
                parentBeans.add(toNeo4jBean(parentNode));
            }
            i++;
        }

        for (CustomNode pSiblingNode : pSiblingNodes) {
            pSiblingBeans.add(toNeo4jBean(pSiblingNode));
            if (psChildNodes.size() > j && pSiblingNode.hasProperty(HASCHILDREN) &&
                    (StringUtils.equalsIgnoreCase(pSiblingNode.getProperty(HASCHILDREN).toString(), TRUE))) {
                psChildBeans.add(toNeo4jBean(psChildNodes.get(j)));
                j++;
            } else {
                psChildBeans.add(createEmptyNeo4JBean());
            }
        }

        for (CustomNode fSiblingNode : fSiblingNodes) {
            fSiblingBeans.add(toNeo4jBean(fSiblingNode));
            if (fsChildNodes.size() > k && fSiblingNode.hasProperty(HASCHILDREN) &&
                (StringUtils.equalsIgnoreCase(fSiblingNode.getProperty(HASCHILDREN).toString(), TRUE))) {
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
                parentBeans.add(toNeo4jBean(parentNode, 1L));
            }
            i++;
        }

        long previousIndex = index;
        for (CustomNode pSiblingNode : pSiblingNodes) {
            previousIndex--;
            pSiblingBeans.add(toNeo4jBean(pSiblingNode, previousIndex));
            if (psChildNodes.size() > j && pSiblingNode.hasProperty(HASCHILDREN) &&
                (StringUtils.equalsIgnoreCase(pSiblingNode.getProperty(HASCHILDREN).toString(), TRUE))) {
                psChildBeans.add(toNeo4jBean(psChildNodes.get(j), 1L));
                j++;
            } else {
                psChildBeans.add(createEmptyNeo4JBean());
            }
        }

        long followingIndex = index;
        for (CustomNode fSiblingNode : fSiblingNodes) {
            followingIndex++;
            fSiblingBeans.add(toNeo4jBean(fSiblingNode, followingIndex));
            if (fsChildNodes.size() > k && fSiblingNode.hasProperty(HASCHILDREN) &&
                (StringUtils.equalsIgnoreCase(fSiblingNode.getProperty(HASCHILDREN).toString(), TRUE))) {
                fsChildBeans.add(toNeo4jBean(fsChildNodes.get(k), 1L));
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
    // TODO check if this method (not provinding an index) can be deprecated
    @SuppressWarnings("unchecked")
    public static Neo4jBean toNeo4jBean(CustomNode node) {
        if (node != null) {

            Neo4jBean neo4jBean = new Neo4jBean();
            neo4jBean.setId(((TextNode) node.getProperty(RDFABOUT)).asText());
            neo4jBean.setType(DocType.safeValueOf(node.getProperty(EDMTYPE)
                    .toString().replace("\"", "")));
            neo4jBean.setHasChildren(node.hasProperty(HASCHILDREN));

            if (node.hasProperty(HASCHILDREN) && node.hasProperty(CHILDRENCOUNT)) {
                IntNode childrenCount = (IntNode) node.getProperty(CHILDRENCOUNT);
                neo4jBean.setChildrenCount(childrenCount.asLong());
            }

            neo4jBean = retrieveLangAwareProperties(neo4jBean, node);

            if (node.hasProperty(HASPARENT)) {
                neo4jBean.setParent(StringUtils.replace(node.getProperty(HASPARENT).toString(), "\"", ""));
            }
            if (node.hasProperty(RELBEFORE)) {
                neo4jBean.setRelBefore(Boolean.parseBoolean(node.getProperty(RELBEFORE).toString()));
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
     * @param node The CustomNode to convert
     * @param index the index of the corresponding Neo4j node
     * @return The Neo4jBean
     */
    @SuppressWarnings("unchecked")
    public static Neo4jBean toNeo4jBean(CustomNode node, long index) {
        if (node != null) {
            Neo4jBean neo4jBean = new Neo4jBean();
            neo4jBean.setId(((TextNode) node.getProperty(RDFABOUT)).asText());
            neo4jBean.setType(DocType.safeValueOf(node.getProperty(EDMTYPE)
                    .toString().replace("\"", "")));

            neo4jBean.setHasChildren(node.hasProperty(HASCHILDREN));
            if (node.hasProperty(HASCHILDREN) && node.hasProperty(CHILDRENCOUNT)) {
                IntNode childrenCount = (IntNode) node.getProperty(CHILDRENCOUNT);
                neo4jBean.setChildrenCount(childrenCount.asLong());
            }

            neo4jBean = retrieveLangAwareProperties(neo4jBean, node);

            if (node.hasProperty(HASPARENT)) {
                neo4jBean.setParent(StringUtils.replace(node.getProperty(HASPARENT).toString(), "\"", ""));
            }
            neo4jBean.setIndex(index);
            if (node.hasProperty(RELBEFORE)) {
                neo4jBean.setRelBefore(Boolean.parseBoolean(node.getProperty(RELBEFORE).toString()));
            }
            return neo4jBean;
        }
        return null;
    }

    private static List<String> extractArrayNode(CustomNode node, String key) {
        List<String> values = new ArrayList<>();
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
        neo4jBean.setIndex(0L);
        neo4jBean.setParent("(null)");
        neo4jBean.setRelBefore(Boolean.FALSE);
        neo4jBean.setChildrenCount(0L);
        return neo4jBean;
    }

    @SuppressWarnings("Duplicates")
    private static Neo4jBean retrieveLangAwareProperties(Neo4jBean bean, CustomNode customnode){
        Map<String, List<String>> titles = new HashMap<>();
        Map<String, List<String>> descriptions = new HashMap<>();
        for (String key : customnode.getPropertyKeys()){
            if (key.startsWith("dc:description")) {
                descriptions = getLangAwareProperties(key, StringUtils.substringAfter(key, "dc:description" + KEYXMLLANG)
                        , descriptions, customnode);
            } else if (key.startsWith("dc:title")) {
                titles = getLangAwareProperties(key, StringUtils.substringAfter(key, "dc:title" + KEYXMLLANG)
                        , titles, customnode);
            }
        }
        bean.setTitle(titles);
        bean.setDescription(descriptions);
        return bean;
    }


    private static Map<String, List<String>> getLangAwareProperties(String key, String lang
            , Map<String, List<String>> langAwareProperties, CustomNode customnode){
        List<String> langAwareValues = langAwareProperties.get(lang) != null ? langAwareProperties.get(lang) : new ArrayList<>();
        if (customnode.getProperty(key) instanceof ArrayNode) {
            langAwareValues.addAll(extractArrayNode(customnode, key));
        } else {
            langAwareValues.addAll((List<String>) customnode.getProperty(key));
        }
        langAwareProperties.put(lang, langAwareValues);
        return langAwareProperties;
    }
}
