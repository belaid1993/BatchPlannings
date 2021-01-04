package com.belaid.batch.services;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class PlanningMailSenderServiceImpl implements PlanningMailSenderService {

    private JavaMailSender javaMailSender;

    public PlanningMailSenderServiceImpl(final JavaMailSender javaMailSender) {
        super();
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void send(String destination, String content) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        message.setContent(content,"text/html");
        helper.setTo(destination);
        helper.setSubject("Votre planning de formation");

        javaMailSender.send(message);

    }
}
