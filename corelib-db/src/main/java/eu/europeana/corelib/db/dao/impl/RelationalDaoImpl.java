package eu.europeana.corelib.db.dao.impl;

import java.io.Serializable;
import java.util.List;


import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.transaction.annotation.Transactional;
import eu.europeana.corelib.db.dao.RelationalDao;
import eu.europeana.corelib.db.exception.DatabaseException;
import eu.europeana.corelib.definitions.db.entity.relational.abstracts.IdentifiedEntity;
import eu.europeana.corelib.web.exception.ProblemType;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * @see eu.europeana.corelib.db.dao.RelationalDao
 */
@Transactional(readOnly = true)
public class RelationalDaoImpl<E extends IdentifiedEntity<?>> implements RelationalDao<E> {

    private static final Logger LOG = LogManager.getLogger(RelationalDaoImpl.class);

    @PersistenceContext(name = "corelib_db_entityManagerFactory")
    private EntityManager entityManager;

    private Class<E> domainClazz = null;

    /**
     * This constructor is used by bean configuration.
     *
     * @param clazz The class type references by the main generic type of the DAO instance.
     */
    public RelationalDaoImpl(Class<E> clazz) {
        domainClazz = clazz;
    }

    @Override
    public E findByPK(Serializable id) throws DatabaseException {
        return this.findByPK(domainClazz, id);
    }

    @Override
    public <T extends IdentifiedEntity<?>> T findByPK(Class<T> clazz, Serializable id) throws DatabaseException {
        try {
            return entityManager.find(clazz, id);
        } catch (IllegalArgumentException e) {
            throw new DatabaseException(e, ProblemType.INVALIDARGUMENTS);
        }
    }

    @Override
    public long count() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        cq.select(cb.count(cq.from(domainClazz)));
        return entityManager.createQuery(cq).getSingleResult();
    }

    @Override
    public List<E> findAll() {
        CriteriaQuery<E> cq = entityManager.getCriteriaBuilder().createQuery(domainClazz);
        cq.select(cq.from(domainClazz));
        return entityManager.createQuery(cq).getResultList();
    }


    @Override
    public List<E> findAllOrderBy(String orderBy, boolean ascending) {
        return entityManager
                .createQuery(createFindAllOrderBy(orderBy, ascending))
                .getResultList();
    }

    @Override
    public List<E> findAllOrderBy(String orderBy, boolean ascending, int offset, int limit) {
        CriteriaQuery<E> cq = createFindAllOrderBy(orderBy, ascending);
        return entityManager
                .createQuery(cq)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    private CriteriaQuery<E> createFindAllOrderBy(String orderBy, boolean ascending) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<E> cq = cb.createQuery(domainClazz);
        Root<E> root = cq.from(domainClazz);
        cq.orderBy(ascending ? cb.asc(root.get(orderBy)) : cb.desc(root.get(orderBy)));
        return cq;
    }


    private <T> TypedQuery<T> createNamedQuery(Class<T> clazz, String qName, Object... params) {
        int i = 1;
        TypedQuery<T> query = entityManager.createNamedQuery(qName, clazz);
        if ((params != null) && (params.length > 0)) {
            for (Object param : params) {
                query.setParameter(i, param);
                i++;
            }
        }
        return query;
    }

    @Override
    public List<E> findByNamedQuery(String qName, Object... params) {
        return createNamedQuery(domainClazz, qName, params).getResultList();
    }

    @Override
    public List<E> findByNamedQueryLimited(String qName, int offset, int limit, Object... params) {
        return createNamedQuery(domainClazz, qName, params).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Override
    public <T> List<T> findByNamedQuery(Class<T> clazz, String qName, Object... params) {
        return createNamedQuery(clazz, qName, params).getResultList();
    }

    @Override
    public <T> List<T> findByNamedQueryCustom(Class<T> clazz, String qName, Object... params) {
        String jpql = entityManager.createNamedQuery(qName)
                .unwrap(org.hibernate.Query.class)
                .getQueryString();
        int parnr = 1;
        TypedQuery<T> query = entityManager.createQuery(jpql, clazz);
        if ((params != null) && (params.length > 0)) {
            for (Object object : params) {
                query.setParameter(parnr++, object);
            }
        }
        return query.getResultList();
    }

    @Override
    public <T> T findOneByNamedQuery(Class<T> clazz, String qName, Object... params) {
        try {
            return createNamedQuery(clazz, qName, params).getSingleResult();
        } catch (NoResultException e) {
            LOG.error("Error creating named query", e);
        }
        return null;
    }

    @Override
    public E findOneByNamedQuery(String qName, Object... params) {
        return findOneByNamedQuery(domainClazz, qName, params);
    }

    @Override
    @Transactional(readOnly = false)
    public <T extends IdentifiedEntity<?>> T insert(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    @Transactional(readOnly = false)
    public <T extends IdentifiedEntity<?>> T update(T entity) {
        return entityManager.merge(entity);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(IdentifiedEntity<?> entity) {
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteAll() {
        findAll().forEach(this::delete);
    }
}
