package eu.europeana.corelib.neo4j.entity;

import eu.europeana.corelib.definitions.solr.DocType;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * Neo4jBean
 * 
 * @author Yorgos.Mamakis@ europeana.eu
 */
@JsonSerialize(include = Inclusion.NON_EMPTY)
public class Neo4jBean {

	private String id;

	private Map<String, List<String>> description;

	private Map<String, List<String>> title;

	private DocType type;

	private Boolean children;

	private String parent;

	private Long index;

	private Long childrenCount = 0L;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map<String, List<String>> getDescription() {
		return description;
	}

	public void setDescription(Map<String, List<String>> description) {
		this.description = description;
	}

	public Map<String, List<String>> getTitle() {
		return title;
	}

	public void setTitle(Map<String, List<String>> title) {
		this.title = title;
	}

	public DocType getType() {
		return type;
	}

	public void setType(DocType type) {
		this.type = type;
	}

	@JsonProperty("hasChildren")
	public Boolean hasChildren() {
		return children;
	}

	public void setHasChildren(Boolean children) {
		this.children = children;
	}

	public Long getIndex() {
		return index + 1;
	}

	public void setIndex(Long index) {
		this.index = index;
	}

	public Long getChildrenCount() {
		return childrenCount;
	}

	public void setChildrenCount(Long childrenCount) {
		this.childrenCount = childrenCount;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}
}
