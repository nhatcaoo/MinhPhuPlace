package com.ncl.backend.service;

import com.ncl.backend.model.ServiceResult;

public interface MailService {
    ServiceResult sendDemoMail(String text);
}
