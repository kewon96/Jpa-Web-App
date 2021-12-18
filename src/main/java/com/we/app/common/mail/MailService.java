package com.we.app.common.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    public void sendMail(Mail mail) {
        try {
            MailHandler mailHandler = new MailHandler(mailSender);

            mailHandler.setTo(mail.getAddress());
            mailHandler.setSubject(mail.getTitle());
            mailHandler.setText(mail.getMessage());

            mailHandler.send();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
