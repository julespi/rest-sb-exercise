package com.julespi.restsbexercise.dao;

import com.julespi.restsbexercise.models.User;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class UserDaoImp extends GenericDao<User> {

    public List<User> listAll(){
        return entityManager.createQuery("FROM User",User.class).getResultList();
    }

}
