package com.example.myquora.controller;

import com.example.myquora.dto.CreateResponseDTO;
import com.example.myquora.dto.ResponsesDTO;
import com.example.myquora.dto.ThreadDTO;
import com.example.myquora.payload.request.CreateResponseRequest;
import com.example.myquora.security.jwt.JwtUtils;
import com.example.myquora.service.ResponseService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class ResponseController {
    private final ModelMapper modelMapper;
    private final JwtUtils jwtUtils;
    private final ResponseService responseService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/response")
    public ResponseEntity<?> addResponse(@RequestBody CreateResponseRequest createResponseRequest,
                                         @RequestParam("t") Long threadId,
                                         HttpServletRequest req) {
        CreateResponseDTO createResponseDTO = modelMapper.map(createResponseRequest, CreateResponseDTO.class);
        createResponseDTO.setUsername(jwtUtils.getUsernameFromRequest(req));
        createResponseDTO.setThreadId(threadId);

        ThreadDTO threadDTO = responseService.addResponse(createResponseDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(threadDTO);
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/response")
    public ResponseEntity<?> getAllThreadResponses(@RequestParam("t") Long threadId) {
        ResponsesDTO responsesDTO = responseService.getAllThreadResponses(threadId);

        return ResponseEntity.ok(responsesDTO);
    }
}
