package com.ncl.backend.controller;

import com.ncl.backend.mail.MyConstants;
import com.ncl.backend.model.ServiceResult;
import com.ncl.backend.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.spi.ServiceRegistry;

@Controller
@RequestMapping("mail")
public class SimpleEmailExampleController {
    @Autowired
    public MailService mailService;
    @ResponseBody
    @PostMapping("/send-simple-email") //post get put delete
    public ResponseEntity sendSimpleEmail(@RequestParam("text") String text ) {

        try {
            return new ResponseEntity( mailService.sendDemoMail(text), HttpStatus.OK);
        } catch (MailException e) {
            e.printStackTrace();
            return new ResponseEntity( new ServiceResult(null,"loi gui mail", ServiceResult.FAIL), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity( new ServiceResult(null,"loi hệ thống", ServiceResult.FAIL), HttpStatus.OK);
        }

    }
}
