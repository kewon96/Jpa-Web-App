package com.we.app.member.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/** 알림방식 */
@Embeddable
@Getter @Setter
@Builder
@NoArgsConstructor @AllArgsConstructor
public class Notify {

    @Column(columnDefinition = "스터디생성 시 Email알림방식 활성화 여부")
    private boolean isEmail;

    @Column(columnDefinition = "스터디생성 시 Web알림방식 활성화 여부")
    private boolean isWeb;

}
