package com.ncl.backend.service;

import com.ncl.backend.exception.ExistedException;
import com.ncl.backend.exception.NotFoundException;
import com.ncl.backend.model.LoginModel;
import com.ncl.backend.model.ServiceResult;

public interface LoginService {
     ServiceResult changePassword(LoginModel loginModel) throws NotFoundException;
     void initAccount() ;
     ServiceResult login(String username, String password);
     ServiceResult register(String username, String password) throws ExistedException;
}
