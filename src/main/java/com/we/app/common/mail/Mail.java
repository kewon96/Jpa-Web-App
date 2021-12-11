package com.we.app.common.mail;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class Mail {

    private String address;
    private String title;
    private String message;

}
