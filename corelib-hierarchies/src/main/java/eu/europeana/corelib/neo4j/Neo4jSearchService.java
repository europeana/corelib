package eu.europeana.corelib.neo4j;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.TextNode;
import eu.europeana.corelib.definitions.solr.DocType;
import eu.europeana.corelib.neo4j.entity.*;
import eu.europeana.corelib.neo4j.exception.Neo4JException;
import eu.europeana.corelib.neo4j.server.CypherService;
import eu.europeana.corelib.web.exception.ProblemType;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.springframework.context.annotation.Lazy;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;

/**
 * Lookup hierarchical information from Neo4j server
 * @author Patrick Ehlert
 * Created on 01-03-2018
 * @author Lúthien - refactored to use neo4j v.3.5.2 03-02-2019
 */
@Lazy
public class Neo4jSearchService {

    private static final String KEYXMLLANG          = "_xml:lang_";
    private static final String RDFABOUT            = "rdf:about";
    private static final String EDMTYPE             = "edm:type";
    private static final String HASCHILDREN         = "hasChildren";
    private static final String HASPARENT           = "hasParent";
    private static final String TRUE                = "true";
    private static final String CHILDRENCOUNT       = "childrenCount";
    private static final String RELBEFORE           = "relBefore";
    private static final String INDEX               = "index";
    private static final String NULL                = "(null)";

    @Resource(name = "corelib_neo4j_cypherservice" )
    protected CypherService cypherService;

    @PostConstruct
    private void init() {
        LogManager.getLogger(Neo4jSearchService.class).info("Connected to {}", cypherService.getServerPath());
    }

    /**
     * Get the 'self' node
     *
     * @param  rdfAbout The ID of the node
     * @return Neo4jBean representing 'self' node
     */
    public Neo4jBean getSingle(String rdfAbout) throws Neo4JException {
        List<CustomNode> selfList = cypherService.getSingleNode(rdfAbout);
        if (!selfList.isEmpty()) {
            return toNeo4jBean(selfList.get(0), -1L);
        } else {
            throw new Neo4JException(ProblemType.NEO4J_CANNOTGETNODE,
                    " \n\n... can't find node with ID: " + rdfAbout);
        }
    }

    /**
     * Get the children of the node (max 10)
     *
     * @param  rdfAbout The ID of the record
     * @param  offset The offset of the first child
     * @param  limit  The number of records to retrieve
     * @return node's children
     */
    public List<Neo4jBean> getChildren(String rdfAbout, int offset, int limit) {
        List<Neo4jBean> beans = new ArrayList<>();
        long startIndex = offset;
        List<CustomNode> children = cypherService.getChildren(rdfAbout, offset, limit);
        for (CustomNode child : children) {
            startIndex += 1L;
            beans.add(toNeo4jBean(child, startIndex));
        }
        return beans;
    }

    public List<Neo4jBean> getPrecedingSiblings(String rdfAbout, int offset, int limit, long selfIndex) {
        List<Neo4jBean> beans = new ArrayList<>();
        List<CustomNode> precedingSiblings = cypherService.getPrecedingSiblings(rdfAbout, offset, limit);
        long startIndex = selfIndex - offset;
        for (CustomNode precedingSibling : precedingSiblings) {
            startIndex -= 1L;
            beans.add(toNeo4jBean(precedingSibling, startIndex));
        }
        return beans;
    }

    /**
     * Get the nodes following siblings
     *
     * @param  rdfAbout The ID of the record
     * @param  offset  How many siblings to skip
     * @param  limit  How many siblings to retrieve
     * @return node's preceding siblings
     */
    public List<Neo4jBean> getFollowingSiblings(String rdfAbout, int offset, int limit, long selfIndex) {
        List<Neo4jBean> beans = new ArrayList<>();
        List<CustomNode> followingSiblings = cypherService.getFollowingSiblings(rdfAbout, offset, limit);
        long startIndex = selfIndex + offset;
        for (CustomNode followingSibling : followingSiblings) {
            startIndex += 1L;
            beans.add(toNeo4jBean(followingSibling, startIndex));
        }
        return beans;
    }

    /**
     * Get the initial structure, which contains self, the ancestors,
     * preceding and following siblings
     *
     * @param  rdfAbout The ID of the record
     * @return The hierarchical structure
     */
    public Neo4jStructBean getInitialStruct(String rdfAbout, long selfIndex) throws Neo4JException {
        return Objects.requireNonNull(toNeo4jStruct(cypherService.getInitialStruct(rdfAbout), selfIndex));
    }

    /**
     * Overridden, added long node.index Convert a custom node as retrieved from
     * Neo4j initial structure call to a Neo4jBean for inclusion in the
     * hierarchy Note that at this point the node's rdf:about identifier is
     * renamed to 'id' in the Neo4jBean
     *
     * @param node The CustomNode to convert
     * @return The Neo4jBean
     */
    @SuppressWarnings("unchecked")
    private Neo4jBean toNeo4jBean(CustomNode node, long selfIndex) {
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

            setLangAwareProperties(neo4jBean, node);

            if (node.hasProperty(HASPARENT)) {
                neo4jBean.setParent(StringUtils.replace(node.getProperty(HASPARENT).toString(), "\"", ""));
            }

            if (selfIndex > 0L){
                neo4jBean.setIndex(selfIndex);
            } else if (node.hasProperty(INDEX)) {
                IntNode index = (IntNode) node.getProperty(INDEX);
                neo4jBean.setIndex(index.asLong());
            } else {
                neo4jBean.setIndex(0L);
            }

            if (node.hasProperty(RELBEFORE)) {
                neo4jBean.setRelBefore(Boolean.parseBoolean(node.getProperty(RELBEFORE).toString()));
            }
            return neo4jBean;
        }
        return null;
    }

    private void addLangAwareProperties(String key, String lang
            , Map<String, List<String>> langAwareProperties, CustomNode customnode){
        List<String> langAwareValues = langAwareProperties.get(lang) != null ? langAwareProperties.get(lang) : new ArrayList<>();
        if (customnode.getProperty(key) instanceof ArrayNode) {
            langAwareValues.addAll(extractArrayNode(customnode, key));
        } else {
            langAwareValues.addAll((List<String>) customnode.getProperty(key));
        }
        langAwareProperties.put(lang, langAwareValues);
    }

    private void setLangAwareProperties(Neo4jBean bean, CustomNode customnode){
        Map<String, List<String>> titles = new HashMap<>();
        Map<String, List<String>> descriptions = new HashMap<>();
        for (String key : customnode.getPropertyKeys()){
            if (key.startsWith("dc:description")) {
                addLangAwareProperties(
                        key, StringUtils.substringAfter(
                        key, "dc:description" + KEYXMLLANG), descriptions, customnode);
            } else if (key.startsWith("dc:title")) {
                addLangAwareProperties(
                        key, StringUtils.substringAfter(
                        key, "dc:title" + KEYXMLLANG), titles, customnode);
            }
        }
        bean.setTitle(titles);
        bean.setDescription(descriptions);
    }

    private List<String> extractArrayNode(CustomNode node, String key) {
        List<String> values = new ArrayList<>();
        int size = ((ArrayNode) node.getProperty(key)).size();
        for (int i = 0; i < size; i++) {
            values.add(((ArrayNode) node.getProperty(key)).get(i).asText());
        }
        return values;
    }

    /**
     * Convert a hierarchy to a Neo4jStructBean
     *
     * @param hierarchy The hierarchy to convert
     * @param index     the index of the corresponding Neo4j start node
     * @return Hierarchical structure: self; parent; previous- & following siblings; their children
     */
    private Neo4jStructBean toNeo4jStruct(Hierarchy hierarchy, long index) {

        Neo4jStructBean struct = new Neo4jStructBean();
        if (hierarchy == null || hierarchy.getParents().isEmpty()) {
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
        int i = 0;
        for (CustomNode parentNode : parentNodes) {
            if (i == 0) {
                struct.setSelf(toNeo4jBean(parentNode, index));
            } else {
                parentBeans.add(toNeo4jBean(parentNode, -1L));
            }
            i++;
        }

        long previousIndex = index;
        int j = 0;
        for (CustomNode pSiblingNode : pSiblingNodes) {
            if (null != pSiblingNode){
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
        }

        long followingIndex = index;
        int k = 0;
        for (CustomNode fSiblingNode : fSiblingNodes) {
            if (null != fSiblingNode){
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
        }

        struct.setParents(parentBeans);
        struct.setPrecedingSiblings(pSiblingBeans);
        struct.setFollowingSiblings(fSiblingBeans);
        struct.setPrecedingSiblingChildren(psChildBeans);
        struct.setFollowingSiblingChildren(fsChildBeans);
        return struct;
    }

    private Neo4jBean createEmptyNeo4JBean() {
        Neo4jBean neo4jBean = new Neo4jBean();
        final List<String> nullVal = new ArrayList<String>() {
            private static final long serialVersionUID = 1974692064190530659L;
            { add(NULL); }};
        Map<String, List<String>> titles = new HashMap<String, List<String>>() {
            private static final long serialVersionUID = 8846401931134342010L;
            { put("def", nullVal); }};
        neo4jBean.setId(NULL);
        neo4jBean.setType(DocType.safeValueOf(NULL));
        neo4jBean.setTitle(titles);
        neo4jBean.setHasChildren(Boolean.FALSE);
        neo4jBean.setIndex(0L);
        neo4jBean.setParent(NULL);
        neo4jBean.setRelBefore(Boolean.FALSE);
        neo4jBean.setChildrenCount(0L);
        return neo4jBean;
    }
}
