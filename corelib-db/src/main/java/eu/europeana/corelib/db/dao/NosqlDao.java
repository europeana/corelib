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

package eu.europeana.corelib.db.dao;

import org.mongodb.morphia.dao.DAO;
import eu.europeana.corelib.db.entity.nosql.abstracts.NoSqlEntity;

import java.io.Serializable;

/**
 * Generic DAO service layer. Used in combination with a DAO instance for every type
 * of object, although some methods are generic and can be used for every entity.
 *
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public interface NosqlDao<E extends NoSqlEntity, T extends Serializable> extends DAO<E, T> {

    /**
     * Only for internal (test) usage, clears a table...
     */
    void deleteAll();

}
