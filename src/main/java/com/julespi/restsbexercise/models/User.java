package com.julespi.restsbexercise.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

public class User {

    @Getter @Setter
    private Long id;

    @Getter @Setter
    private String name;

    @Getter @Setter
    private String email;

    @Getter @Setter
    private String password;

    @Getter @Setter
    private Set<Telephone> phones;

}
