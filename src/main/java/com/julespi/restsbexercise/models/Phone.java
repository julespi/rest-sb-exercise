package com.julespi.restsbexercise.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "phones")
public class Phone { //Serializable??

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Getter @Setter
    private String number;

    @Column(name = "citycode")
    @Getter @Setter
    private String cityCode;

    @Column(name = "countrycode")
    @Getter @Setter
    private String countryCode;


    // ORIGINAL
    //@ManyToOne(fetch = FetchType.LAZY, optional = false)
    //@JoinColumn(name = "user_id", nullable = false)

    // SEGUNDO
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @Getter @Setter
    private User user;



}