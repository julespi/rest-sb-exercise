package com.julespi.restsbexercise.models;

import lombok.Getter;
import lombok.Setter;

public class Telephone {

    @Getter @Setter
    private String number;

    @Getter @Setter
    private String cityCode;

    @Getter @Setter
    private String countryCode;
}
