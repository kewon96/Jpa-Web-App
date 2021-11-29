package com.we.app.member;

import com.we.app.member.model.ConsoleMailSender;
import com.we.app.member.model.JoinMember;
import com.we.app.member.model.Member;
import com.we.app.member.model.Notify;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
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
    private final ConsoleMailSender consoleMailSender;

    @InitBinder("signUpForm") // CamelCase를 따라감
    public void initBinder(WebDataBinder webDataBinder) {
        // validator추가
        webDataBinder.addValidators(joinMemberValidator);
    }

    @PostMapping("/signup/submit")
    public int signUpSubmit(@RequestBody @Valid JoinMember joinMember, Errors errors) throws Exception {
        if(errors.hasErrors()) {
//            return 0;
            throw new Exception("aaaaa");
        }

        /* 아래의 코드를 나름 줄이는 방법으로 InitBinder를 사용한다.(정확하겐 적절한 위치로 조정해주는 것)
        signUpFormValidator.validate(signUpForm, errors);
        // 위의 validate에서 충족하지 않는다면 errors에 rejectValue가 추가되어 아래의 hasErrors()에 걸리게 된다.
        if(errors.hasErrors()) {
            return 0;
        }
        */

        Notify notify = Notify.builder()
                .isWeb(true)
                .build();

        Member member = Member.builder()
                .email(joinMember.getEmail())
                .username(joinMember.getUsername())
                .password(joinMember.getPassword())
                .studyCreate(notify)
                .studyEnrollment(notify)
                .studyUpdate(notify)
                .build();

        Member saveMember = memberService.signUpSubmit(member);

        // 이메일 인증할 때 사용할 Token 생성(저장된 회원 기준)
        saveMember.generateEmailCheckToken();

        // 이메일 인증하라는 메일 전송
        // 이때 Token을 생성해서
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(saveMember.getEmail());
        mailMessage.setSubject("스터디 회원가입 인증입니다.");
        mailMessage.setText("/check-email-token?token="+ saveMember.getEmailCheckToken() +"&email=" + saveMember.getEmail());
        consoleMailSender.send(mailMessage);

        return ObjectUtils.isEmpty(saveMember) ? 0 : 1;
    }

    @PostMapping(value = "/check/dupl/username")
    public boolean checkDuplUsername(@RequestBody Member member) {
        boolean b = memberService.existsByUsername(member);
        System.out.println(b);
        return b;
    }
}
