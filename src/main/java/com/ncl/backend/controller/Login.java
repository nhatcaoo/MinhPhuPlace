package com.ncl.backend.controller;

import com.ncl.backend.exception.ExistedException;
import com.ncl.backend.exception.NotFoundException;
import com.ncl.backend.model.LoginModel;
import com.ncl.backend.model.ServiceResult;
import com.ncl.backend.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/api/v1"})
public class Login {
    private final Logger logger = LoggerFactory.getLogger(Login.class);
    @Autowired
    private LoginService loginService;

    @PostMapping("/admin/change-password")
    public ResponseEntity<ServiceResult> changePassword(
            @RequestBody LoginModel loginModel) throws NotFoundException {
        logger.info("login");
        return new ResponseEntity(loginService.changePassword(loginModel), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<ServiceResult> login(
            @RequestBody LoginModel loginModel) {
        logger.info("login");
        return new ResponseEntity(loginService.login(loginModel.getUsername(), loginModel.getPassword()), HttpStatus.OK);
    }
    @PostMapping("/admin/register")
    public ResponseEntity<ServiceResult> register(
            @RequestBody LoginModel loginModel) throws ExistedException {
        logger.info("login");
        return new ResponseEntity(loginService.register(loginModel.getUsername(), loginModel.getPassword()), HttpStatus.OK);
    }
}
