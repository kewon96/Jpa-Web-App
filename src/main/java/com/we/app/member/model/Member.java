package com.we.app.member.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity @ToString
@Builder @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Member {

    /** 순번 */
    @Id @GeneratedValue
    private Long id;

    /** 이메일 */
    @Column(unique = true)
    private String email;

    /** 사용자이름 */
    @Column(unique = true)
    private String memberName;

    /** 비밀번호 */
    @Column(nullable = false)
    private String password;

    /** 이메일인증여부 */
    private boolean emailVerified;

    /** 이메일토큰값 */
    private String emailCheckToken;

    /** 회원가입 일시 */
    private LocalDateTime joinedAt;

    /********** Profile */

    /** 자기소개 */
    private String bio;

    /** 웹사이트 url */
    private String url;

    /** 직업 */
    private String occupation;

    /** 지역 */
    private String location;

    /** 프로필 이미지 */
    @Lob @Basic(fetch = FetchType.EAGER) // 그때그때 가져올 수 있게...
    private String profileImage;

    /** 스터디가 만들어졌다는 알림 방식 */
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "isEmail", column = @Column(name = "study_created_by_email")),
        @AttributeOverride(name = "isWeb", column = @Column(name = "study_created_by_web"))
    })
    private Notify studyCreate;

    /** 스터디의 가입신청결과 알림 방식 */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "isEmail", column = @Column(name = "study_enrollment_result_by_email")),
            @AttributeOverride(name = "isWeb", column = @Column(name = "study_enrollment_result_by_web"))
    })
    private Notify studyEnrollment;

    /** 스터디의 갱신정보 알림 방식 */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "isEmail", column = @Column(name = "study_updated_by_email")),
            @AttributeOverride(name = "isWeb", column = @Column(name = "study_updated_by_web"))
    })
    private Notify studyUpdate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Member member = (Member) o;
        return id != null && Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
