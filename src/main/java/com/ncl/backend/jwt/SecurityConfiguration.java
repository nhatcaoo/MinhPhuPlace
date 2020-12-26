package com.ncl.backend.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ncl.backend.common.Constant;
import com.ncl.backend.filter.JWTFilter;
import com.ncl.backend.model.ServiceResult;
import com.ncl.backend.service.CustomUserDetailService;
import com.ncl.backend.service.JWTService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private JWTService jwtService;
    @Autowired
    private CustomUserDetailService customUserDetailService;

    private final Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

    @Override
    public void configure(HttpSecurity http) throws Exception {
        logger.info("Start configure");
        http
                .csrf().disable()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .authorizeRequests()
                .antMatchers(Constant.API_PREFIX+"/login").permitAll()
                .antMatchers(Constant.API_PREFIX+"/all/**").permitAll()
                .antMatchers("/api/swagger-ui").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/api/swagger-ui").permitAll()
                .antMatchers("/v2/swagger-ui.html").permitAll()
                .antMatchers(Constant.API_PREFIX+"/swagger-ui").permitAll()
//                .antMatchers(Constant.API_PREFIX+"/ackpoint").permitAll()
//                .antMatchers(Constant.API_PREFIX+"/get-ads-list").permitAll()
                .antMatchers(Constant.API_PREFIX+"admin/**").hasAuthority("MANAGER")
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JWTFilter(jwtService), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .accessDeniedHandler(setAccessDeniedHandler())
                .authenticationEntryPoint(setAuthenEntry());
        logger.info("End configure");
    }

    public CorsConfigurationSource corsConfigurationSource() {
        logger.info("Start cors configure");
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
//        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        logger.info("End cors configure");
        return source;
    }

    public AuthenticationEntryPoint setAuthenEntry() {
        return new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
                logger.info("start authen fail");
                ServiceResult serviceResult = new ServiceResult();
                serviceResult.setMessage(Constant.FORBIDDEN_MESSAGE);
                serviceResult.setStatus(ServiceResult.FAIL);
                serviceResult.setData("");
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().print(new ObjectMapper().writeValueAsString(serviceResult));
                response.getWriter().flush();
                logger.info("end authen fail");
            }
        };
    }

    public AccessDeniedHandler setAccessDeniedHandler() {
        return new AccessDeniedHandler() {
            @Override
            public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
                logger.info("start access fail");
                ServiceResult serviceResult = new ServiceResult();
                serviceResult.setMessage(Constant.FORBIDDEN_MESSAGE);
                serviceResult.setStatus(ServiceResult.FAIL);
                serviceResult.setData("");
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().print(new ObjectMapper().writeValueAsString(serviceResult));
                response.getWriter().flush();
                logger.info("end access fail");
            }
        };
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        logger.info("start config authen");
        auth.userDetailsService(customUserDetailService)
                .passwordEncoder(passwordEncoder());
        logger.info("end config authen");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        logger.info("set password encoder");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager getAuthenticationManager() throws Exception {
        logger.info("set authentication manager");
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/**", "/swagger-ui.html", "/webjars/**", "/null/**", "/error");
    }
}
