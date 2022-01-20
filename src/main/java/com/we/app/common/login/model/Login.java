package com.we.app.common.login.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter @Setter
@ToString
public class Login {

    @NotBlank
    private String email;
    @NotBlank
    private String password;

}
