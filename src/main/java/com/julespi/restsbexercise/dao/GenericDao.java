package com.julespi.restsbexercise.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Transactional
public abstract class GenericDao<T>{

    @PersistenceContext
    public EntityManager entityManager;

    public T save(T t){
        entityManager.persist(t);
        return t;
    }

    public List<T> list(Class<T> clazz){
        String query = "FROM "+clazz.getSimpleName();
        return new ArrayList<>(entityManager.createQuery(query,clazz).getResultList());
    }

    public T findById(Class<T> clazz, String id) throws RuntimeException{
        T t = entityManager.find(clazz, id);
        if(t == null){
            throw new RuntimeException("No Record Found with id "+id);
        }
        return t;
    }

    public T update(T t){
        entityManager.merge(t);
        return t; // TODO volar este return
    }

    public boolean contains(T t){
        return entityManager.contains(t);
    } //TODO vuela este  metodo?
}