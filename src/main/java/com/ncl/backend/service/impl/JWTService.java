package com.ncl.backend.service.impl;

import com.ncl.backend.common.Constant;
import com.ncl.backend.entity.Account;
import com.ncl.backend.jwt.JWTConfiguration;
import com.ncl.backend.model.ServiceResult;
import com.ncl.backend.model.TokenObject;
import com.ncl.backend.repository.AccountRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import io.jsonwebtoken.security.WeakKeyException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Service
public class JWTService {
    private final Logger logger = LoggerFactory.getLogger(JWTService.class);
    @Autowired
    private JWTConfiguration jwtConfig;
    @Autowired
    private AccountRepository accountRepository;

    private Key getSecretKeySpec(String secretKey) {
        logger.info("start get key");
        SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;
        Key key = new SecretKeySpec(secretKey.getBytes(), algorithm.getJcaName());
        logger.info("end get key");

        return key;
    }

    private String createToken(long userID, long expireTime, String secretKey) {
        logger.info("start create token");
        Key key = getSecretKeySpec(secretKey);
        long now = System.currentTimeMillis();
        Date expiredDate = new Date(now + expireTime);
        JwtBuilder builder = Jwts.builder()
                .setSubject(userID + "")
                .setIssuedAt(new Date(now))
                .setExpiration(expiredDate)
                .signWith(key);
        logger.info("end get token");
        return builder.compact();
    }

    private ServiceResult decodeToken(String token, String secretKey) {
        logger.info("start decode token");
        ServiceResult result = new ServiceResult();
        if (StringUtils.isBlank(token)) {
            result.setStatus(ServiceResult.FAIL);
            result.setMessage(Constant.EMPTY_TOKEN);
            result.setData("");
        } else {
            try {
                Key key = getSecretKeySpec(secretKey);
                Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
                logger.info("JWT content: " + claims.getSubject());
                result.setStatus(ServiceResult.SUCCESS);
                result.setData(claims.getSubject());
                result.setMessage(Constant.DECODE_SUCCESS);
            } catch (ExpiredJwtException e) {
                logger.info("token expired");
                result.setStatus(ServiceResult.FAIL);
                result.setMessage(Constant.EXPIRED_TOKEN);
                result.setData("");
            } catch (UnsupportedJwtException e) {
                logger.info("not support jwt");
                e.printStackTrace();
                result.setStatus(ServiceResult.FAIL);
                result.setMessage(Constant.NOT_SUPPORT_JWT);
                result.setData("");
            } catch (MalformedJwtException e) {
                logger.info("Invalid Token form");
                e.printStackTrace();
                result.setStatus(ServiceResult.FAIL);
                result.setMessage(Constant.INVALID_TOKEN);
                result.setData("");
            } catch (SignatureException e) {
                logger.info("Wrong token key");
                e.printStackTrace();
                result.setStatus(ServiceResult.FAIL);
                result.setMessage(Constant.INVALID_TOKEN);
                result.setData("");
            } catch (IllegalArgumentException e) {
                logger.info("Token key not meet condition");
                e.printStackTrace();
                result.setStatus(ServiceResult.FAIL);
                result.setMessage(Constant.INVALID_TOKEN);
                result.setData("");
            } catch (WeakKeyException e) {
                if (e.getMessage().contains(SignatureAlgorithm.HS256.getValue())) {
                    logger.info("Server error - JWT");
                    e.printStackTrace();
                    result.setStatus(ServiceResult.FAIL);
                    result.setMessage(Constant.SERVER_CONFLICT);
                    result.setData("");
                } else {
                    logger.info("Wrong signature algorithm");
                    e.printStackTrace();
                    result.setStatus(ServiceResult.FAIL);
                    result.setMessage(Constant.INVALID_TOKEN);
                    result.setData("");
                }
            } catch (Exception e) {
                logger.info("Server error - JWT");
                e.printStackTrace();
                result.setStatus(ServiceResult.FAIL);
                result.setMessage(Constant.SERVER_CONFLICT);
                result.setData("");
            }
        }
        logger.info("end decode token");
        return result;
    }

    public TokenObject signToken(Account user) {
        logger.info("Start sign token");
        String accessToken = createToken(user.getId(), jwtConfig.getAccessExpire(), jwtConfig.getAccessSecret());
        TokenObject tokenObject = new TokenObject();
        tokenObject.setAccessToken(accessToken);
        logger.info("end sign token");
        return tokenObject;
    }

    public ServiceResult decodeAccess(String accessToken) {
        logger.info("start decode access");
        ServiceResult decodeAccessToken = decodeToken(accessToken, jwtConfig.getAccessSecret());
        if (decodeAccessToken.getStatus().equals(ServiceResult.SUCCESS)) {
            ServiceResult result = new ServiceResult();
            try {
                Optional<Account> userOptional = accountRepository.findById(Long.parseLong(decodeAccessToken.getData().toString()));
                if (userOptional.isPresent()) {
                    Account user = userOptional.get();
                    if (user == null) {
                        logger.info("User of token not found");
                        result.setMessage(Constant.NOTFOUND_USER);
                        result.setStatus(ServiceResult.FAIL);
                        result.setData("");
                    } else {
                        logger.info("User of token found");
                        result.setMessage(Constant.FOUND_USER);
                        result.setStatus(ServiceResult.SUCCESS);
                        result.setData(user);
                    }
                } else {
                    logger.info("User of token not found");
                    result.setMessage(Constant.NOTFOUND_USER);
                    result.setStatus(ServiceResult.FAIL);
                    result.setData("");
                }
            } catch (Exception e) {
                logger.info("Server conflict - JWT");
                e.printStackTrace();
                result.setStatus(ServiceResult.FAIL);
                result.setMessage(Constant.SERVER_CONFLICT);
                result.setData("");
            }
            logger.info("End decode access");
            return result;
        } else {
            logger.info("End decode access - error");
            return decodeAccessToken;
        }
    }
}
