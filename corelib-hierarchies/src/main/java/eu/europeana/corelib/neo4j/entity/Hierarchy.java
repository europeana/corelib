/*
 * Copyright 2007-2012 The Europeana Foundation
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

import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Class representing an initial hierarchy 
 * @author Yorgos.Mamakis@ europeana.eu
 */
@JsonSerialize
public class Hierarchy {
	private List<CustomNode> parents = new ArrayList<CustomNode>();
	private List<CustomNode> siblings = new ArrayList<CustomNode>();
	private List<CustomNode> precedingSiblings = new ArrayList<CustomNode>();
	private List<CustomNode> followingSiblings = new ArrayList<CustomNode>();
	private List<CustomNode> precedingSiblingChildren = new ArrayList<CustomNode>();
	private List<CustomNode> followingSiblingChildren = new ArrayList<CustomNode>();

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
