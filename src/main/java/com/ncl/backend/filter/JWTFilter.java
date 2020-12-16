package com.ncl.backend.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ncl.backend.common.Constant;
import com.ncl.backend.entity.Account;
import com.ncl.backend.model.CustomUserDetail;
import com.ncl.backend.model.ServiceResult;
import com.ncl.backend.service.JWTService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTFilter extends GenericFilterBean {
    private final Logger logger = LoggerFactory.getLogger(com.ncl.backend.filter.JWTFilter.class);
    private JWTService jwtService;

    public JWTFilter(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String bearerToken = request.getHeader("Authorization");
        logger.info("Token: " + bearerToken);
        logger.info("URL: " + request.getServletPath());
        if (StringUtils.isBlank(bearerToken)) {
            logger.info("Token blank");
            SecurityContextHolder.getContext().setAuthentication(null);
            filterChain.doFilter(request, response);
        } else if (!bearerToken.startsWith(Constant.TOKEN_PREFIX)) {
            logger.info("Token not Bearer");
            ServiceResult serviceResult = new ServiceResult();
            serviceResult.setStatus(ServiceResult.FAIL);
            serviceResult.setData("");
            serviceResult.setMessage(Constant.INVALID_TOKEN);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(200);
            response.getWriter().print(new ObjectMapper().writeValueAsString(serviceResult));
        } else {
            logger.info("Token pass");
            String token = bearerToken.replaceFirst(Constant.TOKEN_PREFIX, "");
            ServiceResult decodeAccessToken = jwtService.decodeAccess(token);
            if (decodeAccessToken.getStatus().equals(ServiceResult.SUCCESS)) {
                Account user = (Account) decodeAccessToken.getData();
                UserDetails customUserDetail = new CustomUserDetail(user);
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(new UsernamePasswordAuthenticationToken(customUserDetail, null,
                                customUserDetail.getAuthorities()));
                filterChain.doFilter(request, response);
            } else {
                logger.info("Decode fail");
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                if (decodeAccessToken.getMessage().equals(Constant.SERVER_CONFLICT)) {
                    logger.info("Server conflict");
                    response.setStatus(500);
                } else {
                    logger.info("Exception about jwt");
                    response.setStatus(401);
                }
                response.getWriter().print(new ObjectMapper().writeValueAsString(decodeAccessToken));
            }
        }
    }
}
