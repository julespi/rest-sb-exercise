package com.julespi.restsbexercise.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.julespi.restsbexercise.dto.JwtResponseDto;
import com.julespi.restsbexercise.dto.PhoneDto;
import com.julespi.restsbexercise.dto.UserDto;
import com.julespi.restsbexercise.services.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserService userService;

    private UserDto userDto;

    @BeforeAll
    public void setup() {
        userDto = this.restTemplate.getForEntity("/api/initdb", UserDto.class).getBody();
    }


    @Test
    @Transactional
    public void testListUser() {
        ResponseEntity<UserDto> forbResponse = this.restTemplate.getForEntity("/api/user", UserDto.class);
        Assertions.assertEquals(HttpStatus.FORBIDDEN, forbResponse.getStatusCode());
        HttpEntity<HttpHeaders> httpEntity = doLogin(this.userDto.getEmail());
        ResponseEntity<List<UserDto>> response =
                this.restTemplate.exchange(
                        "/api/user",
                        HttpMethod.GET,
                        httpEntity,
                        new ParameterizedTypeReference<List<UserDto>>() {
                        }
                );
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertDoesNotThrow(() -> {
            response.getBody().isEmpty();
        });
        Assertions.assertFalse(response.getBody().isEmpty());
        Assertions.assertFalse(
                response.getBody().get(0).getId().isEmpty()
        );
        Assertions.assertFalse(
                response.getBody().get(0).getPhones().isEmpty()
        );
    }


    @Test
    @Transactional
    public void testDetailUser() {
        ResponseEntity<UserDto> forbResponse =
                this.restTemplate.getForEntity("/api/user/" + userDto.getId(), UserDto.class);
        Assertions.assertEquals(HttpStatus.FORBIDDEN, forbResponse.getStatusCode());
        HttpEntity<HttpHeaders> httpEntity = doLogin(this.userDto.getEmail());
        ResponseEntity<UserDto> response =
                this.restTemplate.exchange(
                        "/api/user/" + userDto.getId(),
                        HttpMethod.GET,
                        httpEntity,
                        UserDto.class
                );
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(
                userDto.getId(),
                response.getBody().getId()
        );
    }

    @Test
    @Transactional
    public void testDeleteUser() {
        ResponseEntity<String> forbResponse =
                this.restTemplate.exchange(
                        "/api/user/" + this.userDto.getId(),
                        HttpMethod.DELETE,
                        null,
                        String.class
                );
        Assertions.assertEquals(HttpStatus.FORBIDDEN, forbResponse.getStatusCode());
        HttpEntity<HttpHeaders> httpEntity = doLogin(this.userDto.getEmail());
        ResponseEntity<Object> response =
                this.restTemplate.exchange(
                        "/api/user/" + this.userDto.getId(),
                        HttpMethod.DELETE,
                        httpEntity,
                        Object.class
                );
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @Transactional
    public void testUpdateUser() {
        ResponseEntity<String> forbResponse =
                this.restTemplate.exchange(
                        "/api/user/" + this.userDto.getId(),
                        HttpMethod.PUT,
                        null,
                        String.class
                );
        Assertions.assertEquals(HttpStatus.FORBIDDEN, forbResponse.getStatusCode());
        HttpEntity<HttpHeaders> httpEntity = doLogin(this.userDto.getEmail());

        String updatedName = "Updated Name";
        userDto.setPassword("Password88"); //If I don't do this, it updates and re hashes the password. This should be done differently
        userDto.setName(updatedName);
        String body = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            body = mapper.writeValueAsString(userDto);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpEntity<String> httpEntityForUpdate = new HttpEntity<>(body, httpEntity.getHeaders());

        ResponseEntity<UserDto> response =
                this.restTemplate.exchange(
                        "/api/user/" + this.userDto.getId(),
                        HttpMethod.PUT,
                        httpEntityForUpdate,
                        UserDto.class
                );
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(userDto.getId(), response.getBody().getId());
        Assertions.assertEquals(updatedName, response.getBody().getName());
    }


    @Test
    @Transactional
    public void testNewUser() {
        ResponseEntity<String> forbResponse =
                this.restTemplate.exchange(
                        "/api/user",
                        HttpMethod.POST,
                        null,
                        String.class
                );
        Assertions.assertEquals(HttpStatus.FORBIDDEN, forbResponse.getStatusCode());
        HttpEntity<HttpHeaders> httpEntity = doLogin(this.userDto.getEmail());

        UserDto newUser = new UserDto(
                "New User",
                "new@user.com",
                "Newpassword88"
        );
        PhoneDto phone = new PhoneDto(
                "123456789",
                "221",
                "54"
        );
        newUser.getPhones().add(phone);
        String body = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            body = mapper.writeValueAsString(newUser);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpEntity<String> httpEntityForNew = new HttpEntity<>(body, httpEntity.getHeaders());

        ResponseEntity<UserDto> response =
                this.restTemplate.exchange(
                        "/api/user",
                        HttpMethod.POST,
                        httpEntityForNew,
                        UserDto.class
                );
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(newUser.getName(), response.getBody().getName());
    }


    @Test
    @Transactional
    public void testLoginAccepted() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.ALL));

        Map<String, String> map = new HashMap<>();
        map.put("email", userDto.getEmail());
        map.put("password", "Password88"); //TODO this should be cleaner


        HttpEntity<Map<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<JwtResponseDto> tokenResponse = this.restTemplate
                .postForEntity("/api/login", request, JwtResponseDto.class);

        Assertions.assertEquals(HttpStatus.ACCEPTED, tokenResponse.getStatusCode());
        Assertions.assertDoesNotThrow(() -> {
            tokenResponse.getBody().getToken();
        });
        String token = tokenResponse.getBody().getToken();
        Assertions.assertNotNull(token);
        Assertions.assertFalse(token.isEmpty());
        Assertions.assertEquals(userDto.getEmail(), tokenResponse.getBody().getEmail());
    }

    @Test
    @Transactional
    public void testLoginRejected() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.ALL));

        Map<String, String> map = new HashMap<>();
        map.put("email", "some@email.com");
        map.put("password", "Notapassword88");


        HttpEntity<Map<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<JwtResponseDto> tokenResponse = this.restTemplate
                .postForEntity("/api/login", request, JwtResponseDto.class);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, tokenResponse.getStatusCode());
    }

    private HttpEntity<HttpHeaders> doLogin(String email) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.ALL));

        Map<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("password", "Password88"); //TODO this should be cleaner

        HttpEntity<Map<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<JwtResponseDto> tokenResponse = this.restTemplate
                .postForEntity("/api/login", request, JwtResponseDto.class);
        String token = tokenResponse.getBody().getToken();

        HttpHeaders authHeader = new HttpHeaders();
        authHeader.add("Authorization", token);
        authHeader.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity<>(authHeader);
    }
}
