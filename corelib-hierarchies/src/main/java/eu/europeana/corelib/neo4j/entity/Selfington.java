package eu.europeana.corelib.neo4j.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.List;

/**
 * A List wrapper object to handle self node returned by plugin
 * Created by luthien (maike.dulk@ europeana.eu) 3/2/2019.
 *
 */

@JsonSerialize
public class Selfington {

    private List<CustomNode> self = new ArrayList<>();

    public List<CustomNode> getSelf() {
        return self;
    }

    public void setSelf(List<CustomNode> self) {
        this.self = self;
    }
}
