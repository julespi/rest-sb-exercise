package com.julespi.restsbexercise.services;

import com.julespi.restsbexercise.dao.PhoneDaoImp;
import com.julespi.restsbexercise.dao.UserDaoImp;
import com.julespi.restsbexercise.models.Phone;
import com.julespi.restsbexercise.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserDaoImp userDaoImp;

    @Autowired
    private PhoneDaoImp phoneDaoImp;

    public User adduser(User reqUser){
        reqUser.setIsActive(true);
        Set<Phone> phones = reqUser.getPhones();
        reqUser.setPhones(null);
        User dbUser = userDaoImp.save(reqUser);
        phoneDaoImp.saveAll(phones, dbUser);
        return dbUser;
    }
}
