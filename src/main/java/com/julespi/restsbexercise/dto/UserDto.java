package com.julespi.restsbexercise.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDto {

    @Getter @Setter
    private String id;

    @NotBlank(message = "name is required")
    @Getter @Setter
    private String name;

    @NotBlank(message = "email is required")
    @Getter @Setter
    private String email;

    @NotBlank(message = "password is required")
    @Getter @Setter
    private String password;

    @Getter @Setter
    private Boolean isActive;

    @Getter @Setter
    private List<PhoneDto> phones;

    @Getter @Setter
    private Date created;

    @Getter @Setter
    private Date modified;

    @Getter @Setter
    private Date last_login;

    public UserDto() {
        this.phones = new ArrayList<>();
    }
}
