package com.we.app.member;

import com.we.app.common.BusinessException;
import com.we.app.member.model.JoinMember;
import com.we.app.member.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final JoinMemberValidator joinMemberValidator;


    @InitBinder("signUpForm") // CamelCase를 따라감
    public void initBinder(WebDataBinder webDataBinder) {
        // validator추가
        webDataBinder.addValidators(joinMemberValidator);
    }

    @PostMapping(value = "/check/dupl/username")
    public boolean checkDuplUsername(@RequestBody Member member) {
        if(memberService.existsByUsername(member)) {
            throw BusinessException.create("이미 존재하는 아이디입니다.");
        }

        return true;
    }

    @PostMapping(value = "/check/dupl/email")
    public boolean checkDuplEmail(@RequestBody Member member) {
        if(memberService.existsByEmail(member)) {
            throw BusinessException.create("이미 존재하는 이메일입니다.");
        }

        return true;
    }

    @PostMapping("/signup/submit")
    public int signUpSubmit(@RequestBody @Valid JoinMember joinMember, Errors errors) throws Exception {
        if(errors.hasErrors()) {
            throw BusinessException.create("aaaaa");
        }

        Member saveMember = memberService.signUpSubmit(joinMember);
        saveMember.generateEmailCheckToken(); // 이메일 인증할 때 사용할 Token 생성(저장된 회원 기준)
        memberService.sendSignUpConfirmEmail(saveMember);

        return ObjectUtils.isEmpty(saveMember) ? 0 : 1;
    }
}
