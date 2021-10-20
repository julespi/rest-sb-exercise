package com.julespi.restsbexercise.dao;

import com.julespi.restsbexercise.models.Phone;
import com.julespi.restsbexercise.models.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Set;

@Repository
public class PhoneDaoImp extends GenericDao<Phone>  {

    public ArrayList<Phone> saveAll(ArrayList<Phone> phones, User user){
        for (Phone phone : phones) {
            phone.setUser(user);
            entityManager.persist(phone);
        }
        return phones;
    }
}
