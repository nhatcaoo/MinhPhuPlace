//package com.ncl.backend.service.impl;
//
//import com.ncl.backend.common.Constant;
//import com.ncl.backend.entity.Account;
//import com.ncl.backend.exception.NotFoundException;
//import com.ncl.backend.repository.AccountRepository;
//import com.ncl.backend.service.AccountService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//
//public class AccountServiceImpl implements AccountService {
//    @Autowired
//    private AccountRepository accountRepository;
//    @Override
//    public UserDetails loadUserById(Long id) throws NotFoundException {
//        Account user = accountRepository.findById(id).orElseThrow(
//                () -> new NotFoundException(Constant.ACCOUNT_NOT_FOUND)
//        );
//
//        return null;
//    }
//}
