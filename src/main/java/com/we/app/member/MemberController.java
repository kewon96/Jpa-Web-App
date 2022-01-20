package com.we.app.member;

import com.we.app.common.BusinessException;
import com.we.app.common.mail.Mail;
import com.we.app.common.mail.MailService;
import com.we.app.member.model.JoinMember;
import com.we.app.member.model.Member;
import com.we.app.member.model.AuthTrialEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MailService mailService;
    private final JoinMemberValidator joinMemberValidator;


    @InitBinder("joinMember") // CamelCase를 따라감
    public void initBinder(WebDataBinder webDataBinder) {
        // validator추가
        webDataBinder.addValidators(joinMemberValidator);
    }

    @GetMapping(value = "/checkDuplMemberName")
    public boolean checkDuplMemberName(@RequestParam String memberName) {
        if(memberService.existsByMemberName(memberName)) {
            return false;
        }

        return true;
    }

    @CrossOrigin("*")
    @GetMapping(value = "/checkDuplEmail")
    public boolean checkDuplEmail(@RequestParam String email) {
        if(memberService.existsByEmail(email)) {
            return false;
        }

        return true;
    }

    @PostMapping("/createMember")
    public String createMember(@RequestBody @Valid JoinMember joinMember, Errors errorArr) {
        if(errorArr.hasErrors()) {
            String firstError = errorArr.getAllErrors().get(0).getDefaultMessage();

            throw BusinessException.create( firstError );
        }

        Member saveMember = memberService.createMember(joinMember);

        return saveMember.getEmailCheckToken();
    }

    @PostMapping("/sendAuthenticationCode")
    public void sendAuthenticationCode(@RequestBody Mail authMail) {
        authMail.setTitle("이메일인증번호입니다.");

        mailService.sendMail(authMail);
    }

    @PostMapping("/checkAuthCode")
    public String checkAuthCode(@RequestBody @Valid AuthTrialEmail authTrialEmail) {
        Member emailMatchMember = memberService.findByEmail(authTrialEmail.getTargetEmail());

        if(!authTrialEmail.getCertifiedCode().equals(emailMatchMember.getEmailCheckToken())) {
            return "인증코드가 맞지않습니다.";
        }

        memberService.successAuth(emailMatchMember);

        return "인증이 완료됐습니다";
    }
}
