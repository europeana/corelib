/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.europeana.corelib.neo4j.entity;

import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.neo4j.graphdb.Node;

/**
 *
 * @author gmamakis
 */
@JsonSerialize
public class Hierarchy {
    List<CustomNode> parents = new ArrayList<CustomNode>();
    List<CustomNode> siblings = new ArrayList<CustomNode>();
    List<CustomNode> previousSiblings = new ArrayList<CustomNode>();

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

    public List<CustomNode> getPreviousSiblings() {
        return previousSiblings;
    }

    public void setPreviousSiblings(List<CustomNode> previousSiblings) {
        this.previousSiblings = previousSiblings;
    }
}
