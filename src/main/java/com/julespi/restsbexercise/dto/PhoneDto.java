package com.julespi.restsbexercise.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

public class PhoneDto {

    @Getter
    @Setter
    private String id;

    @NotBlank(message = "phone number is required")
    @Getter
    @Setter
    private String number;

    @NotBlank(message = "cityCode number is required")
    @Getter
    @Setter
    private String citycode;

    @NotBlank(message = "countrycode number is required")
    @Getter
    @Setter
    private String contrycode;

    public PhoneDto(String number, String citycode, String contrycode) {
        this.number = number;
        this.citycode = citycode;
        this.contrycode = contrycode;
    }

    public PhoneDto() {
    }
}
