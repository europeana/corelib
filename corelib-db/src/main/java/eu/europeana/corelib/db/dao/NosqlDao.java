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
