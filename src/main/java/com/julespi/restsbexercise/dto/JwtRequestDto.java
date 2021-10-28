package com.julespi.restsbexercise.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class JwtRequestDto {


    @NotBlank(message = "email is required")
    @Email(message = "email is not valid")
    @Getter
    @Setter
    private String email;

    @NotBlank(message = "password is required")
    @Getter
    @Setter
    private String password;

}
