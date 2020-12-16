package com.ncl.backend.service.impl;

import com.ncl.backend.entity.Account;
import com.ncl.backend.model.CustomUserDetail;
import com.ncl.backend.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    Logger logger = LoggerFactory.getLogger(CustomUserDetailService.class);
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        logger.info("Load user detail, username: " + s);
        Account gngUser = accountRepository.findByUsername(s);
        if (gngUser == null) {
            logger.info("Not found user");
            throw new UsernameNotFoundException(s);
        }
        logger.info("End load user detail");
        return new CustomUserDetail(gngUser);
    }
}
