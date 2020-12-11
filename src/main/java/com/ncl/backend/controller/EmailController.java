package com.ncl.backend.controller;

import com.ncl.backend.exception.NotFoundException;
import com.ncl.backend.exception.NullObjectException;
import com.ncl.backend.mail.MyConstants;
import com.ncl.backend.model.BookingRequest;
import com.ncl.backend.model.LoginModel;
import com.ncl.backend.model.ServiceResult;
import com.ncl.backend.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.spi.ServiceRegistry;
import javax.mail.MessagingException;

@Controller
@RequestMapping("mail")
public class EmailController {
    @Autowired
    public MailService mailService;

    @ResponseBody
    @PostMapping("/send-email") //post get put delete
    public ResponseEntity sendSimpleEmail(@RequestBody BookingRequest bookingRequest) throws NullObjectException, MessagingException {

        return new ResponseEntity( mailService.sendDemoMail(bookingRequest), HttpStatus.OK);

    }
}
