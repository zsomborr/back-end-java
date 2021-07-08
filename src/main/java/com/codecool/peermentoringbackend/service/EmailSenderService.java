package com.codecool.peermentoringbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailSenderService {

    private JavaMailSender javaMailSender;

    @Autowired
    public EmailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(String email, String emailMessage, String subject){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@peermentor.com");
        message.setTo(email);
        message.setSubject(subject);
        message.setText(emailMessage);
        javaMailSender.send(message);
    }
}
