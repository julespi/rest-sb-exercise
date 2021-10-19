package com.julespi.restsbexercise.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;


@Transactional
public abstract class GenericDao<T>{

    @PersistenceContext
    private EntityManager entityManager;

    public T save(T t){
        entityManager.persist(t);
        return t;
    }

}