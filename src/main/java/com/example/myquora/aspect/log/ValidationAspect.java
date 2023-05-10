package com.example.myquora.aspect.log;

import com.example.myquora.security.jwt.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect
@Log4j2
@RequiredArgsConstructor
public class ValidationAspect {

    private final JwtUtils jwtUtils;
    private int reqCounter = 0;

    @Pointcut("within(com.example.myquora.controller..*)")
    private void inController() {}

    @Around("inController()")
    @Valid
    public Object aroundController(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        jwtUtils.setUsernameFromRequest(httpServletRequest);
        String method = httpServletRequest.getMethod();
        String requestURI = httpServletRequest.getRequestURI();
        log.info("Incoming request | #" + (++reqCounter) + " | resource: " + requestURI + " | HTTP Verb " + method);
        return pjp.proceed();
    }

}
