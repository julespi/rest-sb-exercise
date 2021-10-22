package com.julespi.restsbexercise.dto;

import lombok.Getter;
import lombok.Setter;

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
