package com.we.app.member;

import com.we.app.member.model.JoinMember;
import com.we.app.member.model.Member;
import com.we.app.member.model.Notify;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    /**
     * 이름 중복여부
     * @param memberName 대상 Member
     * @return 있으면 true, 없으면 false
     */
    public boolean existsByMemberName(String memberName) {
        return memberRepository.existsByMemberName(memberName);
    }

    /**
     * 이메일 중복여부
     * @param email 대상 Member
     * @return 있으면 true, 없으면 false
     */
    public boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    /**
     * 사용자생성
     * @param joinMember 입력한 member
     * @return 생성된 member
     */
    @Transactional
    public Member createMember(JoinMember joinMember) {
        Notify notify = Notify.builder()
                .isWeb(true)
                .build();

        Member member = Member.builder()
                .email(joinMember.getEmail())
                .memberName(joinMember.getMemberName())
                .password( passwordEncoder.encode(joinMember.getPassword()) )
                .emailCheckToken(UUID.randomUUID().toString().substring(0, 6))
                .studyCreate(notify)
                .studyEnrollment(notify)
                .studyUpdate(notify)
                .build();

        return memberRepository.save(member);
    }

    /**
     * email을 기준으로 Member 객체 가져오기
     * @param email 대상 email
     * @return 가져온 객체
     */
    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Transactional
    public void successAuth(Member successMember) {
        successMember.setEmailVerified(true);

        memberRepository.save(successMember);
    }
}
