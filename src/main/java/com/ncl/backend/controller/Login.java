package com.ncl.backend.controller;

import com.ncl.backend.entity.Account;
import com.ncl.backend.exception.ExistedException;
import com.ncl.backend.exception.NotFoundException;
import com.ncl.backend.model.CustomUserDetail;
import com.ncl.backend.model.LoginModel;
import com.ncl.backend.model.ServiceResult;
import com.ncl.backend.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = {"/api/v1"})
public class Login {
    private final Logger logger = LoggerFactory.getLogger(Login.class);
    @Autowired
    private LoginService loginService;

    @PostMapping("/admin/change-password")
    public ResponseEntity<ServiceResult> changePassword(@RequestParam(name="old_password") String oldPassword,
                                                        @RequestParam(name="new_password") String newPassword) throws NotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

     // account.getUsername()
       // Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        logger.info("changePasss");
        return new ResponseEntity(loginService.changePassword(auth, newPassword, oldPassword), HttpStatus.OK);
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
