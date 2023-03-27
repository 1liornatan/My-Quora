package com.example.myquora.repository;

import com.example.myquora.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);

    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);
}