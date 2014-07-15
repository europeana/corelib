package eu.europeana.corelib.solr.server;

import java.util.List;
import org.neo4j.graphdb.Node;

public interface Neo4jServer extends AbstractServer{

	Node getNode(String id);
        
	List<Node> getChildren(Node id, int offset, int limit);
	
	Node getParent(Node id);
	
	List<Node> getFollowingSiblings(Node id, int limit);
	
	List<Node> getPreceedingSiblings(Node id, int limit);
	
	long getChildrenCount(Node id);

	long getNodeIndex(Node node);
	
}
