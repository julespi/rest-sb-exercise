package com.julespi.restsbexercise.services;

import com.julespi.restsbexercise.dao.UserDaoImp;
import com.julespi.restsbexercise.dto.JwtResponseDto;
import com.julespi.restsbexercise.dto.PhoneDto;
import com.julespi.restsbexercise.dto.UserDto;
import com.julespi.restsbexercise.dto.JwtRequestDto;
import com.julespi.restsbexercise.models.Phone;
import com.julespi.restsbexercise.models.User;
import com.julespi.restsbexercise.utils.DBException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.julespi.restsbexercise.utils.JwtUtils.getJWTToken;

@Service
@Log4j2
public class UserService {

    @Autowired
    private UserDaoImp userDaoImp;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public UserDto addUser(UserDto userDto) {
        log.info("[SERVICE] - ADDUSER called");
        User newUser = new User();
        mapUserDtoToUser(userDto, newUser);
        if (!isEmailAvailable(newUser.getEmail())) {
            throw new DBException("email already taken");
        }
        newUser.setIsActive(true);
        newUser.updateLastLogin();
        User dbUser = userDaoImp.save(newUser);
        UserDto newUserDto = new UserDto();
        mapUserToUserDto(dbUser, newUserDto);
        return newUserDto;
    }

    public List<UserDto> listAllUsers() {
        log.info("[SERVICE] - LISTALLUSERS called");
        List<User> allUsers = userDaoImp.list(User.class);
        List<UserDto> usersDto = new ArrayList<>();
        for (User user : allUsers) {
            UserDto userDto = new UserDto();
            mapUserToUserDto(user, userDto);
            usersDto.add(userDto);
        }
        return usersDto;
    }

    public UserDto getUser(String id) throws DBException{
        log.info("[SERVICE] - GETUSER called");
        User dbUser = userDaoImp.findById(User.class, id);
        UserDto userDto = new UserDto();
        mapUserToUserDto(dbUser, userDto);
        return userDto;
    }

    public UserDto updateUser(UserDto userDto, String id) throws DBException {
        log.info("[SERVICE] - UPDATEUSER called");
        User dbUser = userDaoImp.findById(User.class, id);
        mapUserDtoToUser(userDto, dbUser);
        userDaoImp.update(dbUser);
        UserDto updatedUserDto = new UserDto();
        mapUserToUserDto(dbUser, updatedUserDto);
        return updatedUserDto;
    }

    public void deleteUser(String id) {
        log.info("[SERVICE] - DELETEUSER called");
        User dbUser = userDaoImp.findById(User.class, id);
        dbUser.setIsActive(false);
        userDaoImp.update(dbUser);
    }

    public JwtResponseDto login(JwtRequestDto jwtRequestDto) {
        log.info("[SERVICE] - LOGIN called");
        User dbUser = userDaoImp.findByEmail(jwtRequestDto.getEmail());
        if (dbUser == null || !passwordEncoder.matches(jwtRequestDto.getPassword(), dbUser.getPassword())) {
            throw new DBException("invalid email or password");
        }
        String token = getJWTToken(dbUser.getEmail());
        dbUser.setToken(token);
        dbUser.updateLastLogin();
        userDaoImp.update(dbUser);
        JwtResponseDto response = new JwtResponseDto();
        response.setId(dbUser.getId());
        response.setToken(dbUser.getToken());
        response.setEmail(dbUser.getEmail());
        return response;
    }

    private Boolean isEmailAvailable(String email) {
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
