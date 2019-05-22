package eu.europeana.corelib.neo4j.entity;

import eu.europeana.corelib.definitions.solr.DocType;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

/**
 * Neo4jBean
 *
 * @author Yorgos.Mamakis@ europeana.eu
 */
@JsonSerialize(include = Inclusion.NON_EMPTY)
@JsonInclude(NON_EMPTY)
public class Neo4jBean {

	private String id;

	private Map<String, List<String>> description;

	private Map<String, List<String>> title;

	private DocType type;

	private Boolean children;

	private String parent;

	private Long index;

	private Long childrenCount = 0L;

	private Boolean relBefore;


	/**
	 * Get the node's id. In Neo4j, this value is labeled rdf:about.
	 * It is renamed to id here in order to accommodate existing clients
	 *
	 * @return id: the rdf:about identifier of the node
	 */
	public String getId() {
		return id;
	}

	/**
	 * Set the node's id. In Neo4j, this value is labeled rdf:about.
	 * It is renamed to id here in order to accommodate existing clients
	 *
	 * @param id: the rdf:about identifier of the node
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Get the node's description (Map<String, List<String>>) property
	 *
	 * @return description Map<String, List<String>>
	 */
	public Map<String, List<String>> getDescription() {
		return description;
	}

	/**
	 * Set the node's description (Map<String, List<String>>) property
	 *
	 * @param description Map<String, List<String>>
	 */
	public void setDescription(Map<String, List<String>> description) {
		this.description = description;
	}

	/**
	 * Get the node's title (Map<String, List<String>>) property
	 *
	 * @return title Map<String, List<String>>
	 */
	public Map<String, List<String>> getTitle() {
		return title;
	}

	/**
	 * Set the node's title (Map<String, List<String>>) property
	 *
	 * @param title Map<String, List<String>>
	 */
	public void setTitle(Map<String, List<String>> title) {
		this.title = title;
	}

	/**
	 * Get the node's Type (DocType) property
	 *
	 * @return type DocType
	 */
	public DocType getType() {
		return type;
	}

	/**
	 * Set the node's Type (DocType) property
	 *
	 * @param type DocType
	 */
	public void setType(DocType type) {
		this.type = type;
	}

	/**
	 * Whether or not the node has children nodes
	 *
	 * @return children Boolean
	 */
	@JsonProperty("hasChildren")
	public Boolean hasChildren() {
		return children;
	}

	/**
	 * Set the node's children property
	 * Indicates whether or not this node has children
	 *
	 * @param children Boolean
	 */
	public void setHasChildren(Boolean children) {
		this.children = children;
	}

	/**
	 * Get the node's index (= its relative position among its siblings)
	 *
	 * @return index
	 */
	public Long getIndex() {
		return index;
	}

	/**
	 * Set the node's index (= its relative position among its siblings)
	 *
	 * @param index
	 */
	public void setIndex(Long index) {
		this.index = index;
	}

	/**
	 * Get the node's number of children
	 *
	 * @return childrenCount
	 */
	public Long getChildrenCount() {
		return childrenCount;
	}

	/**
	 * Set the node's number of children
	 *
	 * @return childrenCount
	 */
	public void setChildrenCount(Long childrenCount) {
		this.childrenCount = childrenCount;
	}

	/**
	 * Get the node's parent node identifier
	 *
	 * @return parent (String)
	 */
	public String getParent() {
		return parent;
	}


	/**
	 * Set the node's parent node identifier
	 *
	 * @return parent (String)
	 */
	public void setParent(String parent) {
		this.parent = parent;
	}

	/**
	 * Set the node's relBefore property
	 * Indicates whether or not this node has a preceding sibling
	 *
	 * @param relBefore Boolean
	 */
	public void setRelBefore(Boolean relBefore){
		this.relBefore = relBefore;
	}

	/**
	 * Whether or not the node has a preceding sibling
	 *
	 * @return relBefore Boolean
	 */
	public Boolean isRelBefore(){
		return this.relBefore;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((children == null) ? 0 : children.hashCode());
		result = prime * result
				+ ((childrenCount == null) ? 0 : childrenCount.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((index == null) ? 0 : index.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (getClass() != obj.getClass()) {
			return false;
		}

		Neo4jBean other = (Neo4jBean) obj;
		if (children == null) {
			if (other.children != null) {
				return false;
			}
		} else if (!children.equals(other.children)) {
			return false;
		}

		if (childrenCount == null) {
			if (other.childrenCount != null) {
				return false;
			}
		} else if (!childrenCount.equals(other.childrenCount)) {
			return false;
		}

		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}

		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}

		if (index == null) {
			if (other.index != null) {
				return false;
			}
		} else if (!index.equals(other.index)) {
			return false;
		}

		if (parent == null) {
			if (other.parent != null) {
				return false;
			}
		} else if (!parent.equals(other.parent)) {
			return false;
		}

		if (title == null) {
			if (other.title != null) {
				return false;
			}
		} else if (!title.equals(other.title)) {
			return false;
		}

		return type == other.type;
	}

}
