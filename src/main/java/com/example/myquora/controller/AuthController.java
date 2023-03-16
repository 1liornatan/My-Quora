package com.example.myquora.controller;

import com.example.myquora.dto.MessageDTO;
import com.example.myquora.model.RoleEntity;
import com.example.myquora.model.UserEntity;
import com.example.myquora.model.role.Role;
import com.example.myquora.repository.RoleRepository;
import com.example.myquora.repository.UserRepository;
import com.example.myquora.request.LoginRequest;
import com.example.myquora.request.SignUpRequest;
import com.example.myquora.security.jwt.JwtUtils;
import com.example.myquora.security.service.UserDetailsImpl;
import com.example.myquora.util.Constants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(Constants.MSG_LOGIN_SUCCESS);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Email is already in use!");
        }

        // Create new user's account
        UserEntity user = UserEntity.builder()
                .email(signUpRequest.getEmail())
                .firstName(signUpRequest.getFirstName())
                .lastName(signUpRequest.getLastName())
                .password(encoder.encode(signUpRequest.getPassword()))
                .build();

        Set<RoleEntity> roles = new HashSet<>();
        Role roleUser = Role.ROLE_USER;
        Optional<RoleEntity> optionalRoleEntity = roleRepository.findByName(roleUser);
        roles.add(optionalRoleEntity.get());

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(Constants.MSG_CREATE_USER_SUCCESS);
    }
}