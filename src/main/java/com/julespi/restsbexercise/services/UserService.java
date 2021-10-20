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

@Service
public class UserService {

    @Autowired
    private UserDaoImp userDaoImp;

    @Autowired
    private PhoneDaoImp phoneDaoImp;

    public UserDto addUser(UserDto userDto){
        User newUser = new User();
        ArrayList<Phone> phones = new ArrayList();

        mapUserDtoToUserAndPhones(userDto, newUser, phones);

        User dbUser = userDaoImp.save(newUser);
        ArrayList<Phone> phonesDb = phoneDaoImp.saveAll(phones,dbUser);

        UserDto newUserDto = new UserDto();
        mapUserAndPhonesToUserDto(dbUser, phonesDb, newUserDto);

        return newUserDto;
    }

    //user  ->   userDto
    private void mapUserAndPhonesToUserDto(User dbUser, ArrayList<Phone> phonesDb, UserDto newUserDto) {
        newUserDto.setName(dbUser.getName());
        newUserDto.setEmail(dbUser.getEmail());
        newUserDto.setPassword(dbUser.getPassword());
        newUserDto.setId(dbUser.getId());
        mapPhonesToPhonesDto(phonesDb,newUserDto.getPhones());
    }

    //phones --> phonesDto
    private void mapPhonesToPhonesDto(ArrayList<Phone> phonesDb, ArrayList<PhoneDto> phonesDto) {
        for (Phone phone : phonesDb) {
            PhoneDto phoneDto = new PhoneDto();
            phoneDto.setNumber(phone.getNumber());
            phoneDto.setCitycode(phone.getCityCode());
            phoneDto.setContrycode(phone.getCountryCode());
            phoneDto.setId(phone.getId());

            phonesDto.add(phoneDto);
        }
    }

    //userDto  ->   user
    private void mapUserDtoToUserAndPhones(UserDto userDto, User user, ArrayList<Phone> phones) {
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setIsActive(userDto.getIsActive());

        mapPhonesDtoToPhones(userDto.getPhones(),phones);
    }

    //phonesDto --> phones
    private void mapPhonesDtoToPhones(ArrayList<PhoneDto> phonesDto, ArrayList<Phone> phones){
        for (PhoneDto phoneDto : phonesDto) {
            Phone phone = new Phone();
            phone.setNumber(phoneDto.getNumber());
            phone.setCityCode(phoneDto.getCitycode());
            phone.setCountryCode(phoneDto.getContrycode());
            phones.add(phone);
        }
    }
}
