package com.example.myquora.repository;

import com.example.myquora.model.ResponseEntity;
import com.example.myquora.model.ThreadEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResponseRepository extends JpaRepository<ResponseEntity, Integer> {
    List<ResponseEntity> findByThread(ThreadEntity threadEntity);
}
