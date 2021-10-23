package com.julespi.restsbexercise.controllers;

import com.julespi.restsbexercise.dto.UserDto;
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

    @RequestMapping(value = "api/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserDto> detailUsers(@PathVariable String id){
        UserDto userDto = uService.getUser(id);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
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

    //   Alta
    @RequestMapping(value = "api/user", method = RequestMethod.POST)
    public ResponseEntity<UserDto> createUsers(@Valid @RequestBody UserDto userDto){
        UserDto returnUserDto = uService.addUser(userDto);
        return new ResponseEntity<>(returnUserDto, HttpStatus.CREATED);
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
