package com.ncl.backend.controller;

import com.ncl.backend.exception.NullObjectException;
import com.ncl.backend.model.BookingRequest;
import com.ncl.backend.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@Controller
@RequestMapping(value = {"/api/v1"})
public class EmailController {
    @Autowired
    public MailService mailService;

    @ResponseBody
    @PostMapping("/all/send-email") //post get put delete
    public ResponseEntity sendSimpleEmail(@RequestBody BookingRequest bookingRequest) throws NullObjectException, MessagingException {
        return new ResponseEntity( mailService.sendDemoMail(bookingRequest), HttpStatus.OK);
    }
}
