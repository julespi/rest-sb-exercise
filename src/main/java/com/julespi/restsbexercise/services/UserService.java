package com.julespi.restsbexercise.services;

import com.julespi.restsbexercise.dao.UserDaoImp;
import com.julespi.restsbexercise.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDaoImp userDaoImp;

    public User adduser(User user){
        return userDaoImp.save(user);
    }
}
