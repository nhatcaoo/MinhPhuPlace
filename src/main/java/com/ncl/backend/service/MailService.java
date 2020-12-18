package com.ncl.backend.service;

import com.ncl.backend.exception.NullObjectException;
import com.ncl.backend.model.BookingRequest;
import com.ncl.backend.model.ServiceResult;

import javax.mail.MessagingException;

public interface MailService {
    ServiceResult sendDemoMail(BookingRequest bookingRequest) throws NullObjectException, MessagingException;
}
