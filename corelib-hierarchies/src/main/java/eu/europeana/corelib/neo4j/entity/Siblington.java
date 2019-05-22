package eu.europeana.corelib.neo4j.entity;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Class representing a collection of related nodes; ie. children under one parent,
 * following or preceding siblings, etc
 * Created by luthien (maike.dulk@ europeana.eu) 15/10/2015.
 *
 */

@JsonSerialize
public class Siblington {

    private List<CustomNode> siblings = new ArrayList<>();

    public List<CustomNode> getSiblings() {
        return siblings;
    }

    public void setSiblings(List<CustomNode> siblings) {
        this.siblings = siblings;
    }
}
