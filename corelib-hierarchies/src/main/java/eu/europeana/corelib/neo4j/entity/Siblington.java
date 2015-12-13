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
 * Class representing a collection of related nodes. ie. children under one parent,
 * following or preceding siblings etc
 * Created by luthien (maike.dulk@ europeana.eu) 15/10/2015.
 *
 */

@JsonSerialize
public class Siblington {
    private List<CustomNode> siblings = new ArrayList<CustomNode>();


    public List<CustomNode> getSiblings() {
        return siblings;
    }

    public void setSiblings(List<CustomNode> siblings) {
        this.siblings = siblings;
    }
}
