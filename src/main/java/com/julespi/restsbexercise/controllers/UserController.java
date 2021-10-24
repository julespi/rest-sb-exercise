package com.julespi.restsbexercise.controllers;

import com.julespi.restsbexercise.dto.JwtResponseDto;
import com.julespi.restsbexercise.dto.UserDto;
import com.julespi.restsbexercise.dto.JwtRequestDto;
import com.julespi.restsbexercise.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
public class UserController {

    @Autowired
    private UserService uService;

    // TODO Borrar
    @RequestMapping(value = "api/initdb", method = RequestMethod.GET)
    public ResponseEntity<UserDto> init_db(){
        UserDto userDto = new UserDto();
        userDto.setName("Julian Spinelli");
        userDto.setEmail("julespi@gmail.com");
        userDto.setPassword("Password88");
        UserDto returnUserDto = uService.addUser(userDto);
        return new ResponseEntity<>(returnUserDto, HttpStatus.CREATED);
    }

    // DETAL
    @RequestMapping(value = "api/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserDto> detailUsers(@PathVariable String id){
        UserDto userDto = uService.getUser(id);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    // DELETE
    @RequestMapping(value = "api/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<UserDto> deletelUsers(@PathVariable String id){
        uService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    /*
    // TODO ver q onda esto
    @RequestMapping(value = "api/user", method = RequestMethod.GET)
    public ResponseEntity<UserDto> getUserByParam(@RequestParam Optional<String> email){
        UserDto userDto;
        if(id.isPresent()){
            userDto= uService.getUserById(id.get())
        }
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }*/

    // LIST
    @RequestMapping(value = "api/user", method = RequestMethod.GET)
    public ResponseEntity<List<UserDto>> listAllUsers(){
        return new ResponseEntity<>(uService.listAllUsers(), HttpStatus.OK);
    }

    // UPDATE
    @RequestMapping(value = "api/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable String id) {
        UserDto updatedUserDto = uService.updateUser(userDto, id);
        return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
    }

    //   NEW
    @RequestMapping(value = "api/user", method = RequestMethod.POST)
    public ResponseEntity<UserDto> createUsers(@Valid @RequestBody UserDto userDto){
        UserDto returnUserDto = uService.addUser(userDto);
        return new ResponseEntity<>(returnUserDto, HttpStatus.CREATED);
    }

    //   LOGIN
    @RequestMapping(value = "api/login", method = RequestMethod.POST)
    public ResponseEntity<JwtResponseDto> login(@Valid @RequestBody JwtRequestDto jwtRequestDto){
        JwtResponseDto response = uService.login(jwtRequestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Map<String, String> handleRunTimeException(RuntimeException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("message", ex.getMessage());
        return errors;
    }


}
