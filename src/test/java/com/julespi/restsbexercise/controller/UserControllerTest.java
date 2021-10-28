package com.julespi.restsbexercise.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.julespi.restsbexercise.dao.UserDaoImp;
import com.julespi.restsbexercise.dto.JwtResponseDto;
import com.julespi.restsbexercise.dto.PhoneDto;
import com.julespi.restsbexercise.dto.UserDto;
import com.julespi.restsbexercise.models.User;
import com.julespi.restsbexercise.services.UserService;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.transaction.Transactional;
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
    public void testDetailUser() {
        ResponseEntity<UserDto> forbResponse = this.restTemplate.getForEntity("/api/user", UserDto.class);
        Assertions.assertEquals(HttpStatus.FORBIDDEN, forbResponse.getStatusCode());
        HttpEntity<HttpHeaders> httpEntity = login(this.userDto.getEmail(), "Password88");
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
            response.getBody().size();
        });
        Assertions.assertEquals(1, response.getBody().size());
        Assertions.assertEquals(
                userDto.getName(),
                response.getBody().get(0).getName()
        );
        Assertions.assertEquals(
                userDto.getEmail(),
                response.getBody().get(0).getEmail()
        );
        Assertions.assertFalse(
                response.getBody().get(0).getPhones().isEmpty()
        );
        Assertions.assertEquals(
                userDto.getPhones().size(),
                response.getBody().get(0).getPhones().size()
        );
        Assertions.assertEquals(
                userDto.getPhones().get(0).getNumber(),
                response.getBody().get(0).getPhones().get(0).getNumber()

        );

    }

    @Test
    @Transactional
    public void testDeleteUser() {
        ResponseEntity<String> forbResponse =
                this.restTemplate.exchange(
                        "/api/user/"+this.userDto.getId(),
                        HttpMethod.DELETE,
                        null,
                        String.class
                );
        Assertions.assertEquals(HttpStatus.FORBIDDEN, forbResponse.getStatusCode());
        HttpEntity<HttpHeaders> httpEntity = login(this.userDto.getEmail(), "Password88");
        ResponseEntity<Object> response =
                this.restTemplate.exchange(
                        "/api/user/"+this.userDto.getId(),
                        HttpMethod.DELETE,
                        httpEntity,
                        Object.class
                );
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        // TODO tengo que probar que le hizo una baja logica?
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
        HttpEntity<HttpHeaders> httpEntity = login(this.userDto.getEmail(), "Password88");

        JSONObject body = new JSONObject();
        try {
            body.put("name",userDto.getName());
            body.put("email",userDto.getEmail());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        HttpEntity<String> httpEntityForUpdate = new HttpEntity<>(body.toString(), httpEntity.getHeaders());

        ResponseEntity<String> response =
                this.restTemplate.exchange(
                        "/api/user/"+this.userDto.getId(),
                        HttpMethod.PUT,
                        httpEntityForUpdate,
                        String.class
                );
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        // TODO validar respuesta?
    }

    /*private UserDto initDb() {
        ResponseEntity<UserDto> response = this.restTemplate.getForEntity("/api/initdb", UserDto.class);
        return response.getBody();
    }


    private UserDto createUserDto() {
        List<PhoneDto> phones = new ArrayList<>();
        PhoneDto phone1 = new PhoneDto();
        phone1.setNumber("123456789");
        phone1.setCitycode("221");
        phone1.setContrycode("54");
        PhoneDto phone2 = new PhoneDto();
        phone2.setNumber("987654321");
        phone2.setCitycode("11");
        phone2.setContrycode("55");
        phones.add(phone1);
        phones.add(phone2);
        UserDto newUserDto = new UserDto();
        newUserDto.setName("User Julian Test");
        newUserDto.setEmail("test@test.com");
        newUserDto.setPassword("Password123");
        newUserDto.setPhones(phones);
        return newUserDto;
    }*/

    private HttpEntity<HttpHeaders> login(String email, String password) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.ALL));

        Map<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("password", password); //TODO this should be cleaner


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


        HttpHeaders authHeader = new HttpHeaders();
        authHeader.add("Authorization", token);
        authHeader.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity<>(authHeader);
        /*ResponseEntity<List<UserDto>> responseFromSecuredEndPoint =
                this.restTemplate.exchange(
                        "/api/user",
                        HttpMethod.GET,
                        httpEntity,
                        new ParameterizedTypeReference<List<UserDto>>() {}
                );
        System.out.println("asd");*/
    }
}
