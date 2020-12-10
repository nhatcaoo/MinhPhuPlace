package com.ncl.backend.service.impl;

import com.ncl.backend.entity.Account;
import com.ncl.backend.exception.NotFoundException;
import com.ncl.backend.model.LoginModel;
import com.ncl.backend.model.ServiceResult;
import com.ncl.backend.repository.AccountRepository;
import com.ncl.backend.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AccountRepository accountRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public ServiceResult changePassword(LoginModel loginModel) throws NotFoundException {
        if (!accountRepository.existsById(loginModel.getId()))
            throw new NotFoundException("Tài khoản không tồn tại");
        Account account = accountRepository.findById(loginModel.getId()).get();
        account.setPassword(passwordEncoder.encode(loginModel.getPassword()));
        return new ServiceResult(account,ServiceResult.SUCCESS, "Tạo tài khoản thành công");
    }
}
