package com.example.myquora.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class HelloController {

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/hello")
    public String hello() {
        return "Hello!";
    }
}
