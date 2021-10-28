package com.julespi.restsbexercise.controllers;

import com.julespi.restsbexercise.dto.JwtResponseDto;
import com.julespi.restsbexercise.dto.PhoneDto;
import com.julespi.restsbexercise.dto.UserDto;
import com.julespi.restsbexercise.dto.JwtRequestDto;
import com.julespi.restsbexercise.services.UserService;
import com.julespi.restsbexercise.utils.DBException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@Log4j2
public class UserController {

    @Autowired
    private UserService uService;

    // TODO This should not be done like this but in a script that clears the DB first
    @RequestMapping(value = "api/initdb", method = RequestMethod.GET)
    public ResponseEntity<UserDto> initDb() {
        UserDto userDto = new UserDto(
                "Julian Spinelli",
                "julespi@gmail.com",
                "Password88"
        );
        PhoneDto phone1 = new PhoneDto("123456789", "221", "54");
        List<PhoneDto> phones = new ArrayList<>();
        phones.add(phone1);
        userDto.setPhones(phones);
        UserDto returnUserDto = uService.addUser(userDto);
        log.info("[CONTROLLER] - DB initialized");
        return new ResponseEntity<>(returnUserDto, HttpStatus.CREATED);
    }

    // DETAIL
    @RequestMapping(value = "api/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserDto> detailUser(@PathVariable String id) {
        log.info("[CONTROLLER] - DETAILUSER endpoint called");
        UserDto userDto = uService.getUser(id);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    // DELETE
    @RequestMapping(value = "api/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<UserDto> deletelUsers(@PathVariable String id) {
        log.info("[CONTROLLER] - DELETEUSER endpoint called");
        uService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // LIST
    @RequestMapping(value = "api/user", method = RequestMethod.GET)
    public ResponseEntity<List<UserDto>> listAllUsers() {
        log.info("[CONTROLLER] - LISTALLUSERS endpoint called");
        return new ResponseEntity<>(uService.listAllUsers(), HttpStatus.OK);
    }

    // UPDATE
    @RequestMapping(value = "api/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable String id) {
        log.info("[CONTROLLER] - UPDATEUSER endpoint called");
        UserDto updatedUserDto = uService.updateUser(userDto, id);
        return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
    }

    //   NEW
    @RequestMapping(value = "api/user", method = RequestMethod.POST)
    public ResponseEntity<UserDto> createUsers(@Valid @RequestBody UserDto userDto) {
        log.info("[CONTROLLER] - NEWUSER endpoint called");
        UserDto returnUserDto = uService.addUser(userDto);
        return new ResponseEntity<>(returnUserDto, HttpStatus.CREATED);
    }

    //   LOGIN
    @RequestMapping(value = "api/login", method = RequestMethod.POST)
    public ResponseEntity<JwtResponseDto> login(@Valid @RequestBody JwtRequestDto jwtRequestDto) {
        log.info("[CONTROLLER] - LOGIN endpoint called");
        JwtResponseDto response = uService.login(jwtRequestDto);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put("message", errorMessage);
            log.error("[CONTROLLER] - "+ errorMessage);
        });
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DBException.class)
    @ResponseBody
    public Map<String, String> handleRunTimeException(DBException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("message", ex.getMessage());
        log.error("[CONTROLLER] - "+ ex.getMessage());
        return errors;
    }


}
