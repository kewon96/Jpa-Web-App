package com.we.app.member;

import com.we.app.common.BusinessException;
import com.we.app.common.ResMap;
import com.we.app.member.model.JoinMember;
import com.we.app.member.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Map;


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

        Member saveMember = memberService.processNewMember(joinMember);
        return ObjectUtils.isEmpty(saveMember) ? 0 : 1;
    }

    @GetMapping("/check-email-token")
    public ResMap checkEmailToken(String token, String email) {
        Member member = memberService.findByEmail(email);
        if(member == null) {
            throw BusinessException.create("해당 이메일은 존재하지 않습니다.");
        }

        if( !member.getEmailCheckToken().equals(token) ) {
            throw BusinessException.create("토큰이 같지않습니다.");
        }

        member.setEmailVerified(true);
        member.setJoinedAt(LocalDateTime.now());

        ResMap resultMap = new ResMap();
        resultMap.put("username", member.getUsername());
        resultMap.put("count", memberService.count());

        return resultMap;
    }
}
