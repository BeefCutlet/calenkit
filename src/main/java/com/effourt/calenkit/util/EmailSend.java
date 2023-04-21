package com.effourt.calenkit.util;

import com.effourt.calenkit.dto.EmailMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailSend {

    private final JavaMailSender javaMailSender;

    public void sendMail(EmailMessage emailMessage) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(emailMessage.getRecipient());
            mimeMessageHelper.setSubject(emailMessage.getSubject());
            mimeMessageHelper.setText(emailMessage.getMessage());
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("mail send error");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public String createAccessCode(String id, HttpSession session) {
        String accessCode = UUID.randomUUID().toString();
        session.setAttribute(accessCode, id + "ACCESS");
        session.setMaxInactiveInterval(300);
        return accessCode;
    }

}
