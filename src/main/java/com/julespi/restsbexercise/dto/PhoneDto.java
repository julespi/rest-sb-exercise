package com.julespi.restsbexercise.dto;


import com.julespi.restsbexercise.models.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

public class PhoneDto{

    @Getter @Setter
    private Long id;

    @Getter @Setter
    private String number;

    @Getter @Setter
    private String citycode;

    @Getter @Setter
    private String contrycode;
}
