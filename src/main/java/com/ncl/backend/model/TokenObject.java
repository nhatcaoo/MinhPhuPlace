package com.ncl.backend.model;

import com.ncl.backend.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenObject {
    private String accessToken;
    private Account user;
//    private Object userInfo;
    private Object firstObject;
}
