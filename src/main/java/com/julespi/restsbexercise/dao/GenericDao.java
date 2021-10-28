package com.julespi.restsbexercise.dao;

import com.julespi.restsbexercise.utils.DBException;
import lombok.extern.log4j.Log4j2;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Transactional
@Log4j2
public abstract class GenericDao<T> {

    @PersistenceContext
    public EntityManager entityManager;

    public T save(T t) {
        log.info("[DAO] - SAVE called");
        entityManager.persist(t);
        return t;
    }

    public List<T> list(Class<T> clazz) {
        log.info("[DAO] - LIST called");
        String query = "FROM " + clazz.getSimpleName();
        return new ArrayList<>(entityManager.createQuery(query, clazz).getResultList());
    }

    public T findById(Class<T> clazz, String id) throws DBException {
        log.info("[DAO] - FINDBYID called");
        T t = entityManager.find(clazz, id);
        if (t == null) {
            throw new DBException("No Record Found with id " + id);
        }
        return t;
    }

    public void update(T t) {
        log.info("[DAO] - UPDATE called");
        entityManager.merge(t);
    }
}