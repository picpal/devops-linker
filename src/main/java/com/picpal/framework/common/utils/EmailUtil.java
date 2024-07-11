package com.picpal.framework.common.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailUtil {
    private final JavaMailSender mailSender;

    /**
     * 이메일 알림을 보내는 메서드입니다.
     *
     * @param subject 이메일 제목
     * @param text    이메일 본문
     * @param to      수신자 이메일 주소
     */
    public void sendEmailNotification(String subject, String text, String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ksk6545@naver.com"); // 발신자 이메일 주소 설정
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}
