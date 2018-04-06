package org.sversh.sl2.model.domain.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.sversh.sl2.model.domain.constant.Constant;

/**
 * 
 * @author <>Sergey Vershinin</a>
 * @param <T>
 * @since Feb 19, 2018
 *
 */
public class ConstantDaoImpl implements ConstantDao {


    private static final String SELECT_FROM = "select o from ";
    private static final String WHERE_ID = " o where id = ";
    
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public <ID, T extends Constant<ID>> T find(Class<T> constClass, ID id) {
        return entityManager.find(constClass, id);
    }
    
    @SuppressWarnings("unchecked")
    @Transactional
    @Override
    public <T extends Constant<?>> T findByStringId(Class<T> constClass, String stringId) {
        // stringId is expected to contain a number
        if (stringId != null && stringId.matches("\\D")) {
            // security measure
            throw new RuntimeException("Insecure Id value: " + stringId);
        }
        try {
            return (T) entityManager.createQuery(
                SELECT_FROM + constClass.getName() + WHERE_ID + stringId).getSingleResult();
        } catch (NoResultException nre) { 
            return null;
        }
    }

//    @SuppressWarnings("unchecked")
//    @Override
//    @Transactional
//    public <PK, T extends Lookup<PK>> List<T> findAll(Class<T> lookupClass) {
//        return (List<T>) entityManager.createQuery("from " + lookupClass.getName()).getResultList();
//    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public <T extends Constant<?>> void save(T entity) {
        entityManager.persist(entity);        
    }

    
}
