package com.belaid.batch.services;

import javax.mail.MessagingException;

public interface PlanningMailSenderService {

    void send(String destination, String content) throws MessagingException;
}
