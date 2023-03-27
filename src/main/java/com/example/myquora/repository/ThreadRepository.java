package com.example.myquora.repository;

import com.example.myquora.model.ThreadEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThreadRepository extends JpaRepository<ThreadEntity, Integer> {

}
