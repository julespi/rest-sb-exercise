package com.julespi.restsbexercise.controllers;

import com.julespi.restsbexercise.models.User;
import com.julespi.restsbexercise.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public String createUsers(@RequestBody User user){
        uService.adduser(user);
        return "createUsers ";
    }


}
