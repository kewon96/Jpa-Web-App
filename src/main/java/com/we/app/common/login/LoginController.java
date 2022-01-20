package com.we.app.common.login;

import com.we.app.common.login.model.Login;
import com.we.app.common.util.JwtTokenUtil;
import com.we.app.member.model.Member;
import com.we.app.service.JwtUserDetailsService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService jwtUserDetailsService;

//    @PostMapping("/login")
//    public String login(@RequestBody @Valid Login login) {
//        Member matchMember = loginService.findByMatchMembername(login);
//
//        loginService.checkPassword(login, matchMember);
//
//        return JwtUtil.createToken(login);
//    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody @Valid Login login) throws Exception {
        Member member = jwtUserDetailsService.authenticateByEmailAndPassword(login.getEmail(), login.getPassword());
        String token = jwtTokenUtil.generateToken(member.getEmail());

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/checkToken")
    public void checkToken(@RequestBody Map<String, Object> token) {
        System.out.println(token);
    }

    @Getter @Setter
    @AllArgsConstructor
    class JwtResponse {
        private String token;
    }

}
