package com.we.app.member.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Builder @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Member {

    @Id @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    private boolean emailVerified;

    private String emailCheckToken;

    private LocalDateTime joinedAt;

    /** Profile */

    private String bio;

    private String url;

    private String occupation;

    private String location;

    @Lob @Basic(fetch = FetchType.EAGER) // 그때그때 가져올 수 있게...
    private String profileImage;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "isEmail", column = @Column(name = "study_created_by_email")),
        @AttributeOverride(name = "isWeb", column = @Column(name = "study_created_by_web"))
    })
    private Notify studyCreate;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "isEmail", column = @Column(name = "study_enrollment_result_by_email")),
            @AttributeOverride(name = "isWeb", column = @Column(name = "study_enrollment_result_by_web"))
    })
    private Notify studyEnrollment;

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

    public void generateEmailCheckToken() {
        this.emailCheckToken = UUID.randomUUID().toString();
    }
}
