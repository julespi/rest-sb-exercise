package com.julespi.restsbexercise.services;

import com.julespi.restsbexercise.dao.PhoneDaoImp;
import com.julespi.restsbexercise.dao.UserDaoImp;
import com.julespi.restsbexercise.dto.PhoneDto;
import com.julespi.restsbexercise.dto.UserDto;
import com.julespi.restsbexercise.models.Phone;
import com.julespi.restsbexercise.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserDaoImp userDaoImp;

    @Autowired
    private PhoneDaoImp phoneDaoImp;

    public UserDto addUser(UserDto userDto){
        User newUser = new User();

        mapUserDtoToUser(userDto, newUser);
        newUser.setIsActive(true);

        //Set<Phone> phones = new HashSet<Phone>(newUser.getPhones());
        //newUser.setPhones(null);

        User dbUser = userDaoImp.save(newUser);

        //Set<Phone> phonesDb = phoneDaoImp.saveAll(phones,dbUser);
        UserDto newUserDto = new UserDto();
        mapUserToUserDto(dbUser, newUserDto);
        return newUserDto;
    }



    //    ----------   NUEVO      ---------
    private void mapUserDtoToUser(UserDto userDto, User user) {
        // TODO id?
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setIsActive(userDto.getIsActive());
        user.setCreated(userDto.getCreated());
        user.setModified(userDto.getModified());
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

    private void mapPhonesDtoToPhones(List<PhoneDto> phonesDto, Set<Phone> phones){
        for (PhoneDto phoneDto : phonesDto) {
            Phone phone = new Phone();
            phone.setNumber(phoneDto.getNumber());
            phone.setCityCode(phoneDto.getCitycode());
            phone.setCountryCode(phoneDto.getContrycode());
            phones.add(phone);
        }
    }

    private void mapPhonesToPhonesDto(Set<Phone> phones, List<PhoneDto> phonesDto) {
        if(phones == null ){return;}
        for (Phone phone : phones) {
            PhoneDto phoneDto = new PhoneDto();
            phoneDto.setNumber(phone.getNumber());
            phoneDto.setCitycode(phone.getCityCode());
            phoneDto.setContrycode(phone.getCountryCode());
            phoneDto.setId(phone.getId());
            phonesDto.add(phoneDto);
        }
    }


    public List<UserDto> listAllUsers() {
        List<User> usuarios = userDaoImp.listAll();
        List<UserDto> usersDto = new ArrayList<>();
        for (User user:usuarios) {
            UserDto userDto = new UserDto();
            mapUserToUserDto(user, userDto);
            usersDto.add(userDto);
        }
        return usersDto;
    }
}
