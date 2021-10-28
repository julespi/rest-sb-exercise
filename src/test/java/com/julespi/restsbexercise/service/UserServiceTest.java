package com.julespi.restsbexercise.service;

import com.julespi.restsbexercise.dto.JwtRequestDto;
import com.julespi.restsbexercise.dto.JwtResponseDto;
import com.julespi.restsbexercise.dto.PhoneDto;
import com.julespi.restsbexercise.dto.UserDto;
import com.julespi.restsbexercise.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static com.julespi.restsbexercise.utils.JwtUtils.getJWTToken;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;


    private UserDto createUserDto() {
        List<PhoneDto> phones = new ArrayList<>();
        PhoneDto phone1 = new PhoneDto("123456789", "221", "54");
        PhoneDto phone2 = new PhoneDto("987654321", "11", "55");
        phones.add(phone1);
        phones.add(phone2);
        UserDto newUserDto = new UserDto("User Julian Test", "test@test.com", "Password123");
        newUserDto.setPhones(phones);
        return newUserDto;
    }


    @Test
    @Transactional
    public void testAddUser() {
        UserDto newUserDto = createUserDto();
        UserDto persistedUserDto = userService.addUser(newUserDto);

        Assertions.assertEquals(newUserDto.getName(), persistedUserDto.getName());
        Assertions.assertEquals(newUserDto.getEmail(), persistedUserDto.getEmail());
        Assertions.assertFalse(persistedUserDto.getPhones().isEmpty());
        Assertions.assertEquals(newUserDto.getPhones().size(), persistedUserDto.getPhones().size());

        UserDto expectedUserDto = userService.getUser(persistedUserDto.getId());
        Assertions.assertEquals(expectedUserDto.getId(), persistedUserDto.getId());
        Assertions.assertEquals(expectedUserDto.getName(), persistedUserDto.getName());
        Assertions.assertEquals(expectedUserDto.getEmail(), persistedUserDto.getEmail());
    }

    @Test
    @Transactional
    public void testListAllUsers() {
        UserDto userDto = userService.addUser(createUserDto());
        UserDto anotherUserDto = createUserDto();
        anotherUserDto.setName("Another Name");
        anotherUserDto.setEmail("another@email.com");
        anotherUserDto.getPhones().remove(0);
        UserDto userDtoTwo = userService.addUser(anotherUserDto);
        List<UserDto> list = userService.listAllUsers();
        Assertions.assertFalse(list.isEmpty());
        Assertions.assertEquals(2, list.size());
        Assertions.assertEquals(list.get(0).getId(), userDto.getId());
        Assertions.assertEquals(list.get(1).getId(), userDtoTwo.getId());
    }

    @Test
    @Transactional
    public void testGetUser() {
        UserDto newUserDto = userService.addUser(createUserDto());
        UserDto persisteduserDto = userService.getUser(newUserDto.getId());
        Assertions.assertEquals(newUserDto.getId(), persisteduserDto.getId());
        Assertions.assertThrows(RuntimeException.class, () -> {
            userService.getUser("Test01");
        });
    }

    @Test
    @Transactional
    public void testUpdateUser() {
        UserDto persistedUserDto = userService.addUser(createUserDto());

        persistedUserDto.setName("Updated Name");
        persistedUserDto.setEmail("updated@email.com");

        UserDto updatedUserDto = userService.updateUser(persistedUserDto, persistedUserDto.getId());

        Assertions.assertEquals(persistedUserDto.getName(), updatedUserDto.getName());
        Assertions.assertEquals(persistedUserDto.getEmail(), updatedUserDto.getEmail());
        Assertions.assertFalse(updatedUserDto.getPhones().isEmpty());
        Assertions.assertEquals(persistedUserDto.getPhones().size(), updatedUserDto.getPhones().size());
    }

    @Test
    @Transactional
    public void testDeleteUser() {
        UserDto newUserDto = userService.addUser(createUserDto());
        Assertions.assertTrue(newUserDto.getIsActive());
        userService.deleteUser(newUserDto.getId());
        UserDto deletedUser = userService.getUser(newUserDto.getId());
        Assertions.assertFalse(deletedUser.getIsActive());
    }

    @Test
    @Transactional
    public void testlogin() {
        UserDto userDto = createUserDto();
        String nonEncodedPassword = userDto.getPassword();
        userService.addUser(userDto);
        JwtRequestDto request = new JwtRequestDto();
        request.setEmail(userDto.getEmail());
        request.setPassword(nonEncodedPassword);
        JwtResponseDto response = userService.login(request);
        String token = getJWTToken(userDto.getEmail());
        Assertions.assertEquals(request.getEmail(), response.getEmail());
        // TODO should verify that the token is valid. This is really tested in the controller layer
        Assertions.assertEquals(response.getToken().length(), token.length());
    }
}
