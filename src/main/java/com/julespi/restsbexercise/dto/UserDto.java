package com.julespi.restsbexercise.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDto {

    @Getter
    @Setter
    private String id;

    @NotBlank(message = "name is required")
    @Getter
    @Setter
    private String name;

    @NotBlank(message = "email is required")
    @Email(message = "email is not valid")
    @Getter
    @Setter
    private String email;

    @NotBlank(message = "password is required")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[0-9].*[0-9])(?=.*[a-z].*[a-z].*[a-z]).{6,}$",
            message = "Password must contain at least one uppercase, three lowercase and two numbers"
    )
    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private Boolean isActive;

    @NotNull(message = "at least one phone is required")
    @Size(min = 1, message = "at least one phone is required")
    @Valid
    @Getter
    @Setter
    private List<PhoneDto> phones;

    @Getter
    @Setter
    private Date created;

    @Getter
    @Setter
    private Date modified;

    @Getter
    @Setter
    private Date last_login;

    public UserDto() {
        this.phones = new ArrayList<>();
    }

    public UserDto(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phones = new ArrayList<>();
    }
}
