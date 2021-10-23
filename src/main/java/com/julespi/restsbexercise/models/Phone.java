package com.julespi.restsbexercise.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "phones")
public class Phone { //Serializable??

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(unique = true)
    @Getter @Setter
    private String id;

    @Getter @Setter
    private String number;

    @Column(name = "citycode")
    @Getter @Setter
    private String cityCode;

    @Column(name = "countrycode")
    @Getter @Setter
    private String countryCode;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @Getter @Setter
    private User user;


}
