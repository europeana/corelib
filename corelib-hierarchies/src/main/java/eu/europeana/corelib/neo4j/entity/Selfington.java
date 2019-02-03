/*
 * Copyright 2007-2019 The Europeana Foundation
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
