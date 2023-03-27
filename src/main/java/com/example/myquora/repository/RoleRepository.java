package com.example.myquora.repository;

import com.example.myquora.model.RoleEntity;
import com.example.myquora.model.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    Optional<RoleEntity> findByName(Role name);
}