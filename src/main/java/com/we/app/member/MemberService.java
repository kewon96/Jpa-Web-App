package com.we.app.member;

import com.we.app.member.model.ConsoleMailSender;
import com.we.app.member.model.JoinMember;
import com.we.app.member.model.Member;
import com.we.app.member.model.Notify;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final ConsoleMailSender consoleMailSender;
    private final PasswordEncoder passwordEncoder;


    /**
     * 이름 중복여부
     * @param member 대상 Member
     * @return 있으면 true, 없으면 false
     */
    public boolean existsByUsername(Member member) {
        return memberRepository.existsByUsername(member.getUsername());
    }

    /**
     * 이메일 중복여부
     * @param member 대상 Member
     * @return 있으면 true, 없으면 false
     */
    public boolean existsByEmail(Member member) {
        return memberRepository.existsByEmail(member.getEmail());
    }

    /**
     * 사용자생성
     * @param joinMember 입력한 member
     * @return 생성된 member
     */
    public Member signUpSubmit(JoinMember joinMember) {
        Notify notify = Notify.builder()
                .isWeb(true)
                .build();

        Member member = Member.builder()
                .email(joinMember.getEmail())
                .username(joinMember.getUsername())
                .password( passwordEncoder.encode(joinMember.getPassword()) )
                .studyCreate(notify)
                .studyEnrollment(notify)
                .studyUpdate(notify)
                .build();

        return memberRepository.save(member);
    }

    /**
     * 이메일 인증하라는 메일 전송
     * 생성된 토큰을 인증번호로 전송
     * @param saveMember 저장된 Member
     */
    public void sendSignUpConfirmEmail(Member saveMember) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(saveMember.getEmail());
        mailMessage.setSubject("스터디 회원가입 인증입니다.");
        mailMessage.setText("/check-email-token?token="+ saveMember.getEmailCheckToken() +"&email=" + saveMember.getEmail());
        consoleMailSender.send(mailMessage);
    }
}
