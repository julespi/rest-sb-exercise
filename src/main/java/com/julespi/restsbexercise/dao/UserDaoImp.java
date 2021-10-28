package com.julespi.restsbexercise.dao;

import com.julespi.restsbexercise.models.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Log4j2
public class UserDaoImp extends GenericDao<User> {

    public User findByEmail(String email) {
        log.info("[USERDAOIMP] - FINDBYEMAIL called");
        String query = "FROM User WHERE email = :email";
        List<User> users = entityManager
                .createQuery(query, User.class)
                .setParameter("email", email)
                .getResultList();
        if (!users.isEmpty()) {
            return users.get(0); // should throw same exception as findById()
        }
        return null;
    }
}
