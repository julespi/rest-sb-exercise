package com.julespi.restsbexercise.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Getter @Setter
    private String name;

    @Column(unique = true)
    @Getter @Setter
    private String email;

    @Getter @Setter
    private String password;

    //@Column(columnDefinition="bit default 0")
    @Getter @Setter
    private Boolean isActive;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @Getter @Setter
    private Set<Phone> phones;

}
