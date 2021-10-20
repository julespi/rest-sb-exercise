package com.julespi.restsbexercise.dto;

import com.julespi.restsbexercise.models.Phone;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Set;

public class UserDto {

    @Getter @Setter
    private Long id;

    @NotBlank(message = "name is required")
    @Getter @Setter
    private String name;

    @NotBlank(message = "email is required")
    @Getter @Setter
    private String email;

    @NotBlank(message = "password is required")
    @Getter @Setter
    private String password;

    //@PreUpdate or @PrePersist
    @Getter @Setter
    private Boolean isActive;

    @Getter @Setter
    private PhoneDto[] phones;
}
