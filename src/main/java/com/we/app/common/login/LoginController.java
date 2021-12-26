package com.we.app.common.login;

import com.we.app.common.login.model.Login;
import com.we.app.common.util.JwtUtil;
import com.we.app.member.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public String login(@RequestBody @Valid Login login) {
        Member matchMember = loginService.findByMatchMembername(login);

        loginService.checkPassword(login, matchMember);

        return JwtUtil.createToken(login);
    }

}
