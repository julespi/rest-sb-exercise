package com.julespi.restsbexercise.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;


@Transactional
public abstract class GenericDao<T>{

    @PersistenceContext
    public EntityManager entityManager;

    public T save(T t){
        entityManager.persist(t);
        return t;
    }



}