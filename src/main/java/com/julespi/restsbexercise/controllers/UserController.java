package com.julespi.restsbexercise.controllers;

import com.julespi.restsbexercise.dto.UserDto;
import com.julespi.restsbexercise.models.User;
import com.julespi.restsbexercise.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService uService;

    @RequestMapping(value = "api/users/{id}", method = RequestMethod.GET)
    public String detailUsers(@PathVariable String id){
        //new UserDaoImp().prueba();
        return "detailUsers "+id;
    }

    @RequestMapping(value = "api/users", method = RequestMethod.GET)
    public String listUsers(){
        return "getUsers ";
    }

    @RequestMapping(value = "api/users", method = RequestMethod.POST)
    //public ResponseEntity<User> createUsers(@Valid @RequestBody UserDto userDto){
    public UserDto createUsers(@Valid @RequestBody UserDto userDto){
        //User dbUser = uService.adduser(reqUser);
        //return new ResponseEntity<>(dbUser, HttpStatus.CREATED);
        return userDto;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put("message", errorMessage);
        });
        return errors;
    }


}
