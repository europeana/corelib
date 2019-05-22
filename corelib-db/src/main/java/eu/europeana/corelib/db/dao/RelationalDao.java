package eu.europeana.corelib.db.dao;

import eu.europeana.corelib.db.exception.DatabaseException;
import eu.europeana.corelib.definitions.db.entity.relational.abstracts.IdentifiedEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Generic DAO service layer. Used in combination with a DAO instance for every type
 * of object, although some methods are generic and can be used for every entity.
 *
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public interface RelationalDao<E extends IdentifiedEntity<?>> {

	/*
     * FINDERS
	 */

    /**
     * Retrieve a row through a private key
     *
     * @param id A private key object
     * @return Returns a row
     * @throws DatabaseException
     */
    E findByPK(final Serializable id) throws DatabaseException;

    /**
     * Generic variation of findByPK where the required entity can be given...
     *
     * @param clazz The class of the Entity type to retrieve
     * @param id    A private key object
     * @return Returns a row
     * @throws DatabaseException
     */
    <T extends IdentifiedEntity<?>> T findByPK(Class<T> clazz, final Serializable id) throws DatabaseException;

    /**
     * Counts all records
     *
     * @return the number of records
     */
    long count();

    /**
     * Retrieve all entities from a table.
     *
     * @return a List with all entities
     */
    List<E> findAll();

    /**
     * Retrieve all entities from a table sorted by an given field
     *
     * @param orderBy   Name of the field to sort on
     * @param ascending Sort ascending (true) or descending (false)
     * @return a List with all entities
     */
    List<E> findAllOrderBy(String orderBy, boolean ascending);

    /**
     * Retrieve all entities from a table sorted by an given field
     *
     * @param orderBy   Name of the field to sort on
     * @param ascending Sort ascending (true) or descending (false)
     * @param offset    where to start from
     * @param limit     how many results to return
     * @return a List with all entities
     */
    List<E> findAllOrderBy(String orderBy, boolean ascending, int offset, int limit);

    /**
     * Find entities by named query. Given params will be inserted into query in order of params.
     *
     * @param query  Name of the Named Query
     * @param params Parameters in order as marked in Named Query
     * @return Search results, list is empty when no results match
     */
    List<E> findByNamedQuery(String query, Object... params);

    /**
     * Find entities by named query in a paginated way. Given params will be inserted into query in order of params.
     *
     * @param query  Name of the Named Query
     * @param offset where to start from
     * @param limit  how many results to return
     * @param params Parameters in order as marked in Named Query
     * @return Search results, list is empty when no results match
     */
    List<E> findByNamedQueryLimited(String query, int offset, int limit, Object... params);

    /**
     * @param clazz  The class of the Entity type to retrieve
     * @param qName  Name of the Named Query
     * @param params Parameters in order as marked in Named Query
     */
    <T> List<T> findByNamedQuery(Class<T> clazz, String qName, Object... params);

    /**
     * Workaround version for a bug in hibernate to use custom output objects in named queries
     * <p/>
     * bug report:
     * https://hibernate.atlassian.net/browse/HHH-6304
     *
     * @param clazz  The class of the Entity type to retrieve
     * @param qName  Name of the Named Query
     * @param params Parameters in order as marked in Named Query
     */
    <T> List<T> findByNamedQueryCustom(Class<T> clazz, String qName, Object... params);

    /**
     * Find entity by named query. Given params will be inserted into query in order of params.
     *
     * @param query  Name of the Named Query
     * @param params Params in order as marked in Named Query
     * @return First matching entity, null if no matches are found
     */
    E findOneByNamedQuery(String query, Object... params);

    /**
     * Find a custom entity by named query.
     * Given params will be inserted into query in order of params.
     *
     * @param clazz  The class of the Entity type to retrieve
     * @param qName  Name of the Named Query
     * @param params Params in order as marked in Named Query
     * @return First matching entity, null if no matches are found
     */
    <T> T findOneByNamedQuery(Class<T> clazz, String qName, Object... params);


	/*
     * MODIFIERS
	 */

    /**
     * insert a row in the database.
     * <p/>
     * (This method is generic and can be used for other entities as well.)
     *
     * @param entity Existing object to update
     */
    <T extends IdentifiedEntity<?>> T insert(T entity);

    /**
     * update a existing row in the database
     * <p/>
     * (This method is generic and can be used for other entities as well.)
     *
     * @param entity Existing object to update
     */
    <T extends IdentifiedEntity<?>> T update(T entity);

    /**
     * Delete a existing object in the database
     * <p/>
     * (This method is generic and can be used for other entities as well.)
     *
     * @param entity The existing row to delete
     */
    void delete(IdentifiedEntity<?> entity);

    /**
     * Only for internal (test) usage, clears a table...
     */
    void deleteAll();
}
