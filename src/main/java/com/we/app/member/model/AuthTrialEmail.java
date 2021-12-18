package com.we.app.member.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class AuthTrialEmail {
    @NotBlank
    private String targetEmail;

    @NotBlank
    private String certifiedCode;
}
