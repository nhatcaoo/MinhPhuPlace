package com.ncl.backend.service;

import com.ncl.backend.exception.ExistedException;
import com.ncl.backend.exception.NotFoundException;
import com.ncl.backend.model.ServiceResult;
import org.springframework.security.core.Authentication;

public interface LoginService {
     ServiceResult changePassword(Authentication id, String newPassword, String password) throws NotFoundException;
     void initAccount() ;
     ServiceResult login(String username, String password);
     ServiceResult register(String username, String password) throws ExistedException;
}
