package eu.europeana.corelib.solr.server;

import java.util.List;
import org.neo4j.graphdb.Node;

public interface Neo4jServer extends AbstractServer{

	Node getNode(String id);
        
	List<Node> getChildren(Node id, int offset, int limit);
	
	Node getParent(Node id);
	
	List<Node> getNextSiblings(Node id, int limit);
	
	List<Node> getPreviousSiblings(Node id, int limit);
}
