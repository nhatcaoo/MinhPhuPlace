package com.ncl.backend.jwt;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class JWTConfiguration {
    @Value("${access.secret}")
    private String accessSecret;
    @Value("${access.expire}")
    private long accessExpire;
}
