package com.julespi.restsbexercise.dao;

import com.julespi.restsbexercise.models.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@SpringBootTest
public class UserDaoImpTest {

    @Autowired
    UserDaoImp userDaoImp;

    @BeforeAll
    public static void setUp() {
        /*User user = new User();
        user.setName("Julian Test");
        user.setEmail("test@test.com");*/
        System.out.println("------------- TEST --------------");
    }


    @Test
    @Rollback(true)
    public void testAddUser(){
        System.out.println("------------- LISTA1");
        List<User> lista = userDaoImp.list(User.class);
        for (User usuario:lista){
            System.out.println(usuario.getName());
        }

        User user = new User();
        user.setName("Julian Test");
        user.setEmail("test@test.com");

        userDaoImp.save(user);

        System.out.println("------------- LISTA2");
        List<User> lista2 = userDaoImp.list(User.class);
        for (User usuario:lista2){
            System.out.println(usuario.getName());
        }
    }

    @Test
    public void testRollback(){
        System.out.println("------------- LISTA3");
        List<User> lista = userDaoImp.list(User.class);
        for (User usuario:lista){
            System.out.println(usuario.getName());
        }
    }
}
