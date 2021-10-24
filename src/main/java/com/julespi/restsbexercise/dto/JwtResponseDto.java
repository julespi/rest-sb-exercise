package com.julespi.restsbexercise.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

public class JwtResponseDto {

    @Setter
    @Getter
    private Date timestamp = new Date();

    @Setter
    @Getter
    private String id;

    @Setter
    @Getter
    private String email;

    @Setter
    @Getter
    private String token;

}
