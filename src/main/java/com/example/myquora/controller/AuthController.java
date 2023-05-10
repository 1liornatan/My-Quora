package com.example.myquora.controller;

import com.example.myquora.dto.CreateUserDTO;
import com.example.myquora.dto.LoginDTO;
import com.example.myquora.payload.request.LoginRequest;
import com.example.myquora.payload.request.SignUpRequest;
import com.example.myquora.security.jwt.JwtUtils;
import com.example.myquora.service.AuthService;
import com.example.myquora.util.Constants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final ModelMapper modelMapper;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        ResponseCookie cookie = authService.loginUser(modelMapper.map(loginRequest, LoginDTO.class));
        log.info(String.format("User '%s' logged in successfully", loginRequest.getUsername()));

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(Constants.MSG_LOGIN_SUCCESS);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        authService.registerUser(modelMapper.map(signUpRequest, CreateUserDTO.class));
        log.info(String.format("User '%s' registered successfully", signUpRequest.getUsername()));
        return ResponseEntity.status(HttpStatus.CREATED).body(Constants.MSG_CREATE_USER_SUCCESS);
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = authService.logoutUser();
        log.info(String.format("User '%s' logged out successfully", JwtUtils.getUsernameFromSession()));
        return ResponseEntity.ok().header(HttpHeaders.COOKIE, cookie.toString())
                .body(Constants.MSG_LOGOUT_SUCCESS);
    }
}