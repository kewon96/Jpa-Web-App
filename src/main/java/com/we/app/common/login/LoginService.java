package com.we.app.common.login;

import com.we.app.common.BusinessException;
import com.we.app.common.login.model.Login;
import com.we.app.member.MemberRepository;
import com.we.app.member.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    final String THROW_MESSAGE = "아이디와 비밀번호를 확인해주세요.";

    public Member findByMatchEmail(Login login) {
        Optional<Member> matchMembername = memberRepository.findByEmail(login.getEmail());

        return matchMembername.orElseThrow(() -> {
            throw BusinessException.create(THROW_MESSAGE);
        });
    }

    public void checkPassword(Login login, Member matchMember) {
        if(!passwordEncoder.matches(login.getPassword(), matchMember.getPassword())) {
            throw BusinessException.create(THROW_MESSAGE);
        }
    }
}
