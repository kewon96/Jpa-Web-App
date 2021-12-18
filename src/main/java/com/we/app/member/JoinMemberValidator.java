package com.we.app.member;

import com.we.app.member.model.JoinMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor // final로 되있는 인스턴스만 따로 묶어서 생성자를 만들어줌
public class JoinMemberValidator implements Validator {

    private final MemberRepository memberRepository;
    

    @Override
    public boolean supports(Class<?> aClass) {
        // 어떤 타입의 인스턴스를 검증할 것인지
        return aClass.isAssignableFrom(JoinMember.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        // email, username이 중복되는지 검사
        JoinMember joinMember = (JoinMember) target;
        if(memberRepository.existsByEmail(joinMember.getEmail())) {
            errors.rejectValue("email", "duplicate email", new Object[]{joinMember.getEmail()}, "중복된 이메일입니다.");
        }

        if(memberRepository.existsByMemberName(joinMember.getMemberName())) {
            errors.rejectValue("memberName", "duplicate memberName", new Object[]{joinMember.getMemberName()}, "중복된 이름입니다.");
        }
    }
}
