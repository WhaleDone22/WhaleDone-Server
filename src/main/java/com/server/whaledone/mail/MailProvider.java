package com.server.whaledone.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailProvider {

    private final JavaMailSender javaMailSender;

    private static final String mailTitle = "[웨일던] 비밀번호 초기화 메일입니다.";

    public void mailSend(MailRequestDto dto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(dto.getEmail());
        message.setSubject(mailTitle);
        String tempPassword = dto.getMessage();
        message.setText("임시 비밀번호 입니다 : " + tempPassword);

        javaMailSender.send(message);
    }
}
