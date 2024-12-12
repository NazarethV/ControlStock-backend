package com.controlstock.service;

import com.controlstock.dto.MailBody;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendSimpleMessage(MailBody mailBody) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailBody.to()); //Destinatario del Correo
        message.setFrom("resanelisoa@gmail.com"); //Remitente
        message.setSubject(mailBody.subject()); //Asunto (proporcionado por mailBody)
        message.setText(mailBody.text());

        javaMailSender.send(message); //Enviar el correo
    }
}
