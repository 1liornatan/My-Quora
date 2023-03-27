package com.example.myquora.controller;

import com.example.myquora.dto.CreateThreadDTO;
import com.example.myquora.dto.ThreadDTO;
import com.example.myquora.payload.request.CreateThreadRequest;
import com.example.myquora.payload.response.ThreadResponse;
import com.example.myquora.security.jwt.JwtUtils;
import com.example.myquora.service.ThreadService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class ThreadController {
    private final ThreadService threadService;
    private final ModelMapper modelMapper;
    private final JwtUtils jwtUtils;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/thread")
    public ResponseEntity<?> getThread(@RequestParam("t") Integer threadId) {
        ThreadDTO threadDTO = threadService.getThread(threadId);
        ThreadResponse threadResponse = modelMapper.map(threadDTO, ThreadResponse.class);

        return ResponseEntity.ok(threadResponse);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/thread")
    public ResponseEntity<?> createThread(@RequestBody CreateThreadRequest createThreadRequest, HttpServletRequest req) {
        CreateThreadDTO createThreadDTO = modelMapper.map(createThreadRequest, CreateThreadDTO.class);
        createThreadDTO.setUsername(jwtUtils.getUsernameFromRequest(req));
        ThreadDTO threadDTO = threadService.createThread(createThreadDTO);

        ThreadResponse threadResponse = modelMapper.map(threadDTO, ThreadResponse.class);
        return ResponseEntity.ok(threadResponse);
    }

}
