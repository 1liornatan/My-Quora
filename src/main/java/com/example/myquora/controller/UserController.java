package com.example.myquora.controller;

import com.example.myquora.dto.CreateUserDTO;
import com.example.myquora.dto.LoginDTO;
import com.example.myquora.dto.MessageDTO;
import com.example.myquora.request.LoginRequest;
import com.example.myquora.request.SignUpRequest;
import com.example.myquora.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignUpRequest request) {
        CreateUserDTO createUserDTO = modelMapper.map(request, CreateUserDTO.class);
        MessageDTO messageDTO = userService.createUser(createUserDTO);


        return ResponseEntity.ok(messageDTO.getMessage());
    }

/*    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        LoginDTO loginDTO = modelMapper.map(request, LoginDTO.class);
        MessageDTO messageDTO = userService.loginUser(loginDTO);

        return ResponseEntity.ok(messageDTO.getMessage());
    }*/

    @RequestMapping("/")
    public String helloAdmin() {
        return "hello user!";
    }
}
