package com.julespi.restsbexercise.services;

import com.julespi.restsbexercise.dao.UserDaoImp;
import com.julespi.restsbexercise.dto.JwtResponseDto;
import com.julespi.restsbexercise.dto.PhoneDto;
import com.julespi.restsbexercise.dto.UserDto;
import com.julespi.restsbexercise.dto.JwtRequestDto;
import com.julespi.restsbexercise.models.Phone;
import com.julespi.restsbexercise.models.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Log4j2
public class UserService {

    @Autowired
    private UserDaoImp userDaoImp;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDto addUser(UserDto userDto) {
        User newUser = new User();
        mapUserDtoToUser(userDto, newUser);
        if (!isEmailAvailable(newUser.getEmail())) {
            log.error("Email already taken:" + newUser.getEmail());
            throw new RuntimeException("email already taken");
        }
        newUser.setIsActive(true);
        newUser.updateLastLogin();
        User dbUser = userDaoImp.save(newUser);
        log.info("User added to database");
        UserDto newUserDto = new UserDto();
        mapUserToUserDto(dbUser, newUserDto);
        return newUserDto;
    }

    public List<UserDto> listAllUsers() {
        List<User> allUsers = userDaoImp.list(User.class);
        //List<User> activeUsers = allUsers.stream().filter(User::getIsActive).collect(Collectors.toList());
        List<UserDto> usersDto = new ArrayList<>();
        for (User user : allUsers) {
            UserDto userDto = new UserDto();
            mapUserToUserDto(user, userDto);
            usersDto.add(userDto);
        }
        return usersDto;
    }

    public UserDto getUser(String id) {
        User dbUser = userDaoImp.findById(User.class, id);
        UserDto userDto = new UserDto();
        mapUserToUserDto(dbUser, userDto);
        return userDto;
    }

    public UserDto updateUser(UserDto userDto, String id) throws RuntimeException {
        User dbUser = userDaoImp.findById(User.class, id);
        mapUserDtoToUser(userDto, dbUser);
        userDaoImp.update(dbUser);
        UserDto updatedUserDto = new UserDto();
        mapUserToUserDto(dbUser, updatedUserDto);
        return updatedUserDto;
    }

    public void deleteUser(String id) {
        User dbUser = userDaoImp.findById(User.class, id);
        dbUser.setIsActive(false);
        userDaoImp.update(dbUser);
    }

    /*public Boolean login(String email, String password) throws RuntimeException{
        User dbUser = userDaoImp.findByEmail(email);
        if(dbUser != null && passwordEncoder.matches(password, dbUser.getPassword())){
            return true;
        }
        throw new RuntimeException("invalid email or password");
    }*/

    public JwtResponseDto login(JwtRequestDto jwtRequestDto) {
        User dbUser = userDaoImp.findByEmail(jwtRequestDto.getEmail());
        if (dbUser == null || !passwordEncoder.matches(jwtRequestDto.getPassword(), dbUser.getPassword())) {
            log.error("Invalid email or password");
            throw new RuntimeException("invalid email or password");
        }
        String token = getJWTToken(dbUser.getEmail());
        // TODO ver como resolver esto. tira que tiene 259 caracteres
        dbUser.setToken(token);
        dbUser.updateLastLogin();
        userDaoImp.update(dbUser);
        log.info("User authenticated");
        JwtResponseDto response = new JwtResponseDto();
        response.setId(dbUser.getId());
        response.setToken(dbUser.getToken());
        response.setEmail(dbUser.getEmail());
        return response;

    }

    private String getJWTToken(String username) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }

    public Boolean isEmailAvailable(String email) {
        User dbUser = userDaoImp.findByEmail(email);
        return dbUser == null;
    }


    private void mapUserDtoToUser(UserDto userDto, User user) {
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setIsActive(userDto.getIsActive());
        user.setLast_login(userDto.getLast_login());
        Set<Phone> phones = new HashSet<Phone>();
        mapPhonesDtoToPhones(userDto.getPhones(), phones);
        user.addPhones(phones);
    }

    private void mapUserToUserDto(User user, UserDto userDto) {
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setIsActive(user.getIsActive());
        userDto.setCreated(user.getCreated());
        userDto.setModified(user.getModified());
        userDto.setLast_login(user.getLast_login());
        List<PhoneDto> phonesDto = new ArrayList<PhoneDto>();
        mapPhonesToPhonesDto(user.getPhones(), phonesDto);
        userDto.setPhones(phonesDto);
    }

    private void mapPhonesDtoToPhones(List<PhoneDto> phonesDto, Set<Phone> phones) {
        for (PhoneDto phoneDto : phonesDto) {
            Phone phone = new Phone();
            phone.setId(phoneDto.getId());
            phone.setNumber(phoneDto.getNumber());
            phone.setCityCode(phoneDto.getCitycode());
            phone.setCountryCode(phoneDto.getContrycode());
            phones.add(phone);
        }
    }

    private void mapPhonesToPhonesDto(Set<Phone> phones, List<PhoneDto> phonesDto) {
        if (phones == null) {
            return;
        }
        for (Phone phone : phones) {
            PhoneDto phoneDto = new PhoneDto();
            phoneDto.setId(phone.getId());
            phoneDto.setNumber(phone.getNumber());
            phoneDto.setCitycode(phone.getCityCode());
            phoneDto.setContrycode(phone.getCountryCode());
            phoneDto.setId(phone.getId());
            phonesDto.add(phoneDto);
        }
    }

}
