package com.julespi.restsbexercise.dao;

import com.julespi.restsbexercise.models.Phone;
import com.julespi.restsbexercise.models.User;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class PhoneDaoImp extends GenericDao<Phone>  {

    public void saveAll(Set<Phone> reqPhones, User dbUser){
        for (Phone reqPhone : reqPhones) {
            reqPhone.setUser(dbUser);
            entityManager.persist(reqPhone);
        }

    }
}
