package com.julespi.restsbexercise.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "telephones")
public class Telephone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private Long id;

    @Getter @Setter
    private String number;

    @Getter @Setter
    private String cityCode;

    @Getter @Setter
    private String countryCode;

}
