package com.we.app.member;

import com.we.app.member.model.ConsoleMailSender;
import com.we.app.member.model.JoinMember;
import com.we.app.member.model.Member;
import com.we.app.member.model.Notify;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     * 유저생성 순서
     * @param joinMember 입력 Member
     * @return 최종적으로 Transaction이 끝난 Member
     */
    @Transactional
    public Member processNewMember(JoinMember joinMember) {
        Member saveMember = signUpSubmit(joinMember);
        saveMember.generateEmailCheckToken(); // 이메일 인증할 때 사용할 Token 생성(저장된 회원 기준)
        sendSignUpConfirmEmail(saveMember);

        return saveMember;
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
        mailMessage.setText("/member/check-email-token?token="+ saveMember.getEmailCheckToken() +"&email=" + saveMember.getEmail());
        consoleMailSender.send(mailMessage);
    }

    /**
     * email을 기준으로 Member 객체 가져오기
     * @param email 대상 email
     * @return 가져온 객체
     */
    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    /**
     * 몇번째 유저인지 확인
     */
    public long count() {
        return memberRepository.count();
    }
}
