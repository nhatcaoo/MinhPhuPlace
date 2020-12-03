package com.ncl.backend.service.impl;

import com.ncl.backend.mail.MyConstants;
import com.ncl.backend.model.ServiceResult;
import com.ncl.backend.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    public JavaMailSender emailSender;
    @Override
    public ServiceResult sendDemoMail(String text) {
        SimpleMailMessage message = null;
        message = new SimpleMailMessage();
        message.setTo(MyConstants.FRIEND_EMAIL);
        message.setSubject("Test Simple Email" + text);
        message.setText("Hello, Im testing Simple Email");
        // Send Message!
        this.emailSender.send(message);
        return new ServiceResult(message,"ok ngon", ServiceResult.SUCCESS);
    }
}
