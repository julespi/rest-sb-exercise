package com.julespi.restsbexercise.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private Long id;

    @NotBlank(message = "hola")
    @Getter @Setter
    private String name;

    @Getter @Setter
    private String email;

    @Getter @Setter
    private String password;

    @Getter @Setter
    private Boolean isActive;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Getter @Setter
    private Set<Telephone> phones;

}
