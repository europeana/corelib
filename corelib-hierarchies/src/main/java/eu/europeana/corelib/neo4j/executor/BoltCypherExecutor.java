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

package eu.europeana.corelib.neo4j.executor;

import org.neo4j.driver.v1.*;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by luthien on 29/01/2019.
 */
public class BoltCypherExecutor implements CypherExecutor {

    private final org.neo4j.driver.v1.Driver driver;

    public BoltCypherExecutor(String url) {
        this(url, null, null);
    }

    public BoltCypherExecutor(String url, String username, String password) {
        boolean hasPassword = password != null && !password.isEmpty();
        AuthToken token = hasPassword ? AuthTokens.basic(username, password) : AuthTokens.none();
        driver = GraphDatabase.driver(url, token, Config.build().withEncryptionLevel(Config.EncryptionLevel.NONE).toConfig());
    }

    @Override
    public Iterator<Map<String, Object>> query(String query, Map<String, Object> params) {
        try (Session session = driver.session()) {
            List<Map<String, Object>> list = session.run(query, params)
                    .list( r -> r.asMap(BoltCypherExecutor::convert));
            return list.iterator();
        }
    }

    static Object convert(Value value) {
        switch (value.type().name()) {
            case "PATH":
                return value.asList(BoltCypherExecutor::convert);
            case "NODE":
                return value.asNode();
            case "RELATIONSHIP":
                return value.asMap();
        }
        return value.asObject();
    }



}
