package com.example.myquora.aspect.log;

import jakarta.validation.Valid;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class ValidationAspect {

    @Pointcut("within(com.example.myquora.controller..*)")
    private void inController() {}

    @Around("inController()")
    @Valid
    public Object aroundController(ProceedingJoinPoint pjp) throws Throwable {
        return pjp.proceed();
    }

}
