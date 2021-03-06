package com.we.app.member.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/** 회원가입할 때만 사용되는 Member */
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class JoinMember {

    @NotBlank
    @Length(min = 2, max = 10)
    @Pattern(regexp = "^[가-힣]{2,4}|[a-zA-Z]{2,15}$")
    private String memberName;

    @NotBlank
    @Pattern(regexp = "^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$")
    private String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,30}$")
    private String password;

    /** 이메일토큰값 */
    private String emailCheckToken;
}
