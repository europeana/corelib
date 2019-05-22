package eu.europeana.corelib.neo4j.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

/**
 * A complex Neo4j response structure, to contains self, parents, preceding siblings and following siblings.
 * @author gmamakis
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
@JsonInclude(NON_EMPTY)
public class Neo4jStructBean {

	/**
	 * The self object
	 */
	private Neo4jBean self;

	/**
	 * The self's parents
	 */
	private List<Neo4jBean> parents;

	/**
	 * Self's preceding siblings
	 */
	private List<Neo4jBean> precedingSiblings;

	/**
	 * Self's following siblings
	 */
	private List<Neo4jBean> followingSiblings;

	/**
	 * Preceding siblings' first children
	 */
	private List<Neo4jBean> precedingSiblingChildren;

	/**
	 * following siblings' first children
	 */
	private List<Neo4jBean> followingSiblingChildren;

	public Neo4jBean getSelf() {
		return self;
	}

	public void setSelf(Neo4jBean self) {
		this.self = self;
	}

	public List<Neo4jBean> getPrecedingSiblings() {
		return precedingSiblings;
	}

	public void setPrecedingSiblings(List<Neo4jBean> precedingSiblings) {
		this.precedingSiblings = precedingSiblings;
	}

	public List<Neo4jBean> getFollowingSiblings() {
		return followingSiblings;
	}

	public void setFollowingSiblings(List<Neo4jBean> followingSiblings) {
		this.followingSiblings = followingSiblings;
	}

	public List<Neo4jBean> getPrecedingSiblingChildren() {
		return precedingSiblingChildren;
	}

	public void setPrecedingSiblingChildren(List<Neo4jBean> precedingSiblingChildren) {
		this.precedingSiblingChildren = precedingSiblingChildren;
	}

	public List<Neo4jBean> getFollowingSiblingChildren() {
		return followingSiblingChildren;
	}

	public void setFollowingSiblingChildren(List<Neo4jBean> followingSiblingChildren) {
		this.followingSiblingChildren = followingSiblingChildren;
	}

	public List<Neo4jBean> getParents() {
		return parents;
	}

	public void setParents(List<Neo4jBean> parents) {
		this.parents = parents;
	}
}
