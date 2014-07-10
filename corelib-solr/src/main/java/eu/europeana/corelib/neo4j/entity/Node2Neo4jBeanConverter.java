
package eu.europeana.corelib.neo4j.entity;

import eu.europeana.corelib.definitions.solr.DocType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.neo4j.graphdb.Node;

/**
 * Converter from Node to Neo4jBean
 * 
 * @author Yorgos.Mamakis@ europeana.eu
 */
public class Node2Neo4jBeanConverter {

    public static Neo4jBean toNeo4jBean(Node node) {
        Neo4jBean neo4jBean = new Neo4jBean();
        neo4jBean.setId((String) node.getProperty("rdf:about"));
        neo4jBean.setType(DocType.valueOf((String) node.getProperty("edm:type")));
        Map<String, List<String>> titles = new HashMap<String, List<String>>();

        Map<String, List<String>> descriptions = new HashMap<String, List<String>>();
        Iterable<String> keys = node.getPropertyKeys();
        Iterator<String> keyIterator = keys.iterator();
        while (keyIterator.hasNext()) {
            String key = keyIterator.next();
            if (key.startsWith("dc:description")) {
                List<String> descriptionValue = descriptions.get(StringUtils.substringAfter(key,
                        "dc:description_xml:lang_"));
                if (descriptionValue == null) {
                    descriptionValue = new ArrayList<String>();
                }
                descriptionValue.addAll((List<String>) node.getProperty(key));
                descriptions.put(StringUtils.substringAfter(key, "dc:description_xml:lang_"), descriptionValue);
            } else if (key.startsWith("dc:title")) {
                List<String> titleValue = titles.get(StringUtils.substringAfter(key, "dc:title_xml:lang_"));
                if (titleValue == null) {
                    titleValue = new ArrayList<String>();
                }
                titleValue.addAll(Arrays.asList((String[]) node.getProperty(key)));
                titles.put(StringUtils.substringAfter(key, "dc:title_xml:lang_"), titleValue);
            }
        }
        neo4jBean.setTitle(titles);
        neo4jBean.setDescription(descriptions);
        neo4jBean.setHasChildren(node.hasRelationship(new Relation(RelType.DCTERMS_HASPART.getRelType())));

        return neo4jBean;
    }
}
