package com.julespi.restsbexercise.dao;

import com.julespi.restsbexercise.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
public class UserDaoImpTest {

    @Autowired
    UserDaoImp userDaoImp;

    @Autowired
    EntityManager entityManager;

    @BeforeAll
    public static void setUp() {
        System.out.println("------------- TEST --------------");
    }


    @Test
    @Transactional
    public void testSave() {
        User newUser = new User();
        newUser.setName("User Julian Test");
        newUser.setEmail("test@test.com");
        userDaoImp.save(newUser);
        User expectedUser = userDaoImp.findById(User.class, newUser.getId());
        Assertions.assertEquals(newUser.getName(), expectedUser.getName());
        Assertions.assertEquals(newUser.getEmail(), expectedUser.getEmail());
    }

    @Test
    @Transactional
    public void testList() {
        User newUser1 = new User();
        newUser1.setName("User Julian Test");
        newUser1.setEmail("test@test.com");
        userDaoImp.save(newUser1);

        User newUser2 = new User();
        newUser2.setName("User Julian Test Two");
        newUser2.setEmail("test2@test2.com");
        userDaoImp.save(newUser2);

        List<User> list = userDaoImp.list(User.class);
        Assertions.assertFalse(list.isEmpty());
        Assertions.assertTrue(list.contains(newUser1));
        Assertions.assertTrue(list.contains(newUser2));
        Assertions.assertEquals(2, list.size());
    }

    @Test
    @Transactional
    public void testFindById() {
        User newUser1 = new User();
        newUser1.setName("User Julian Test");
        newUser1.setEmail("test@test.com");
        userDaoImp.save(newUser1);

        User expectedUser = userDaoImp.findById(User.class, newUser1.getId());
        Assertions.assertEquals(newUser1.getName(), expectedUser.getName());
        Assertions.assertEquals(newUser1.getEmail(), expectedUser.getEmail());
        // GlobalLogic: Java 8 new feature in use: Lambda function
        Assertions.assertThrows(RuntimeException.class, () -> {
            userDaoImp.findById(User.class, "Test01");
        });
    }

    @Test
    @Transactional
    public void testFindByEmail() {
        User newUser1 = new User();
        newUser1.setName("User Julian Test");
        newUser1.setEmail("test@test.com");
        userDaoImp.save(newUser1);

        User expectedUser = userDaoImp.findByEmail(newUser1.getEmail());
        Assertions.assertEquals(newUser1.getName(), expectedUser.getName());
        Assertions.assertEquals(newUser1.getEmail(), expectedUser.getEmail());
        Assertions.assertNull(userDaoImp.findByEmail("asd@asd.com"));
    }

    @Test
    @Transactional
    public void testUpdate() {
        User newUser1 = new User();
        newUser1.setName("User Julian Test");
        newUser1.setEmail("test@test.com");
        userDaoImp.save(newUser1);
        String id = newUser1.getId();
        newUser1.setName("New Name");
        newUser1.setEmail("new@email.com");
        userDaoImp.update(newUser1);
        User updatedUser = userDaoImp.findById(User.class, id);
        Assertions.assertEquals(updatedUser.getName(), newUser1.getName());
        Assertions.assertEquals(updatedUser.getEmail(), newUser1.getEmail());
    }
}
