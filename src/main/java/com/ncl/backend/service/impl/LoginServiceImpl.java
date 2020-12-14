package com.ncl.backend.service.impl;

import com.ncl.backend.common.Constant;
import com.ncl.backend.entity.Account;
import com.ncl.backend.exception.NotFoundException;
import com.ncl.backend.model.LoginModel;
import com.ncl.backend.model.ServiceResult;
import com.ncl.backend.model.TokenObject;
import com.ncl.backend.repository.AccountRepository;
import com.ncl.backend.service.JWTService;
import com.ncl.backend.service.LoginService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AccountRepository accountRepository;

    private final Logger logger = LoggerFactory.getLogger(LoginService.class);
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTService jwtService;

    @Override
    public ServiceResult changePassword(LoginModel loginModel) throws NotFoundException {
        if (!accountRepository.existsById(loginModel.getId()))
            throw new NotFoundException(Constant.ACCOUNT_NOT_FOUND);
        Account account = accountRepository.findById(loginModel.getId()).get();
     //   account.setPassword(passwordEncoder.encode(loginModel.getPassword()));
        return new ServiceResult(null,ServiceResult.SUCCESS, Constant.ACCOUNT_CREATE_SUCCESSFUL);
    }

    @Override
    public ServiceResult login(String username, String password) {
        logger.info("Start login");
        ServiceResult result = new ServiceResult();
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            logger.info("Username or password blank");
            result.setMessage(Constant.EMPTY_MAIL_PASS);
            result.setStatus(ServiceResult.FAIL);
            result.setData("");
        } else {
            try {
                Account user = accountRepository.findByUsername(username);
                if (user == null) {
                    logger.info("Not found user");
                    result.setMessage(Constant.WRONG_MAIL_PASS);
                    result.setStatus(ServiceResult.FAIL);
                    result.setData("");
                } else {
                    logger.info("Found user, start get nescessary info");
                    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    TokenObject tokenObject = jwtService.signToken(user);
                    tokenObject.setFirstObject(null); //add dashboard information later
                    tokenObject.setUser(user);
                    result.setMessage(Constant.LOGIN_SUCCESS);
                    result.setStatus(ServiceResult.SUCCESS);
                    result.setData(tokenObject);
                    logger.info("End get info");
                }
            } catch (AuthenticationException e) {
                logger.info("Wrong username or password");
                result.setMessage(Constant.WRONG_MAIL_PASS);
                result.setStatus(ServiceResult.FAIL);
                result.setData("");
            } catch (Exception e) {
                logger.info("Server conflict - login");
                e.printStackTrace();
                result.setMessage(Constant.SERVER_CONFLICT);
                result.setStatus(ServiceResult.FAIL);
                result.setData("");
            }
        }
        logger.info("End login");
        return result;
    }
}
