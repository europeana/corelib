/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.europeana.corelib.neo4j.entity;

import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author gmamakis
 */
@JsonSerialize
public class Hierarchy {
	List<CustomNode> parents = new ArrayList<CustomNode>();
	List<CustomNode> siblings = new ArrayList<CustomNode>();
	List<CustomNode> preceedingSiblings = new ArrayList<CustomNode>();
	List<CustomNode> followingSiblings = new ArrayList<CustomNode>();

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

	public List<CustomNode> getPreceedingSiblings() {
		return preceedingSiblings;
	}

	public void setPreceedingSiblings(List<CustomNode> previousSiblings) {
		this.preceedingSiblings = previousSiblings;
	}

	public List<CustomNode> getFollowingSiblings() {
		return followingSiblings;
	}

	public void setFollowingSiblings(List<CustomNode> followingSiblings) {
		this.followingSiblings = followingSiblings;
	}
}
