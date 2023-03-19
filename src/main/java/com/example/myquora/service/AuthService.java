package com.example.myquora.service;

import com.example.myquora.dto.CreateUserDTO;
import com.example.myquora.dto.LoginDTO;
import com.example.myquora.model.RoleEntity;
import com.example.myquora.model.UserEntity;
import com.example.myquora.model.role.Role;
import com.example.myquora.repository.RoleRepository;
import com.example.myquora.repository.UserRepository;
import com.example.myquora.security.jwt.JwtUtils;
import com.example.myquora.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public void registerUser(CreateUserDTO createUserDTO) {
        if (userRepository.existsByUsername(createUserDTO.getUsername())) {
            // TODO: Throw Exception
        }

        if (userRepository.existsByEmail(createUserDTO.getEmail())) {
            // TODO: Throw Exception
        }

        // Create new user's account
        createUserInDatabase(createUserDTO);
    }

    private void createUserInDatabase(CreateUserDTO createUserDTO) {
        UserEntity user = UserEntity.builder()
                .email(createUserDTO.getEmail())
                .username(createUserDTO.getUsername())
                .firstName(createUserDTO.getFirstName())
                .lastName(createUserDTO.getLastName())
                .password(encoder.encode(createUserDTO.getPassword()))
                .build();

        Set<RoleEntity> roles = new HashSet<>();
        Role roleUser = Role.ROLE_USER;
        Optional<RoleEntity> optionalRoleEntity = roleRepository.findByName(roleUser);
        roles.add(optionalRoleEntity.get());

        user.setRoles(roles);
        userRepository.save(user);
    }

    public ResponseCookie loginUser(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());



        return jwtCookie;

    }

    public ResponseCookie logoutUser() {
        return jwtUtils.getCleanJwtCookie();
    }
}
