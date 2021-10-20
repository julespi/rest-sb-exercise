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

    @Getter @Setter
    private String cityCode;

    @Getter @Setter
    private String countryCode;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @Getter @Setter
    private User user;

}
