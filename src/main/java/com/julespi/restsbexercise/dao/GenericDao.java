package com.julespi.restsbexercise.dao;


import com.julespi.restsbexercise.models.User;

public abstract class GenericDao<T>{
    public T save(T t){
        return t;
    }

    public void prueba2(User user){
        System.out.println("generic Dao --"+user.getName());
    }

}