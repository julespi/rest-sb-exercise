package com.julespi.restsbexercise.dao;

import com.julespi.restsbexercise.models.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImp extends GenericDao<User> {

    public User findByEmail(String email){
        String query = "FROM User WHERE email = :email";
        List<User> users = entityManager
                .createQuery(query,User.class)
                .setParameter("email",email)
                .getResultList();
        if(!users.isEmpty()){
            return users.get(0);
        }
        return null;
    }
}
