package eu.europeana.corelib.neo4j.entity;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Class representing an initial hierarchy 
 * @author Yorgos.Mamakis@ europeana.eu
 */
@JsonSerialize
public class Hierarchy {
	private List<CustomNode> parents = new ArrayList<>();
	private List<CustomNode> siblings = new ArrayList<>();
	private List<CustomNode> precedingSiblings = new ArrayList<>();
	private List<CustomNode> followingSiblings = new ArrayList<>();
	private List<CustomNode> precedingSiblingChildren = new ArrayList<>();
	private List<CustomNode> followingSiblingChildren = new ArrayList<>();

	public List<CustomNode> getParents() {
		return parents;
	}

	public void setParents(List<CustomNode> parents) {
		this.parents = parents;
	}

	public List<CustomNode> getSiblings() {
		return siblings;
	}

	public void setSiblings(List<CustomNode> siblings) {
		this.siblings = siblings;
	}

	public List<CustomNode> getPrecedingSiblings() {
		return precedingSiblings;
	}

	public void setPrecedingSiblings(List<CustomNode> previousSiblings) {
		this.precedingSiblings = previousSiblings;
	}

	public List<CustomNode> getFollowingSiblings() {
		return followingSiblings;
	}

	public void setFollowingSiblings(List<CustomNode> followingSiblings) {
		this.followingSiblings = followingSiblings;
	}

	public List<CustomNode> getPrecedingSiblingChildren() {
		return precedingSiblingChildren;
	}

	public void setPrecedingSiblingChildren(List<CustomNode> previousSiblingChildren) {
		this.precedingSiblingChildren = previousSiblingChildren;
	}

	public List<CustomNode> getFollowingSiblingChildren() {
		return followingSiblingChildren;
	}

	public void setFollowingSiblingChildren(List<CustomNode> followingSiblingChildren) {
		this.followingSiblingChildren = followingSiblingChildren;
	}
}
