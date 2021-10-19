package com.julespi.restsbexercise.services;

import com.julespi.restsbexercise.dao.UserDaoImp;
import com.julespi.restsbexercise.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDaoImp userDaoImp;

    public void adduser(User user){
        System.out.println("servicio -- "+user.getName());
        userDaoImp.prueba2(user);
    }
}
