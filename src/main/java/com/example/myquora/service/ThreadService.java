package com.example.myquora.service;

import com.example.myquora.advice.exception.QuoraException;
import com.example.myquora.dto.CreateThreadDTO;
import com.example.myquora.dto.ThreadDTO;
import com.example.myquora.model.ThreadEntity;
import com.example.myquora.model.UserEntity;
import com.example.myquora.repository.ThreadRepository;
import com.example.myquora.repository.UserRepository;
import com.example.myquora.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ThreadService {
    private final ThreadRepository threadRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public ThreadDTO getThread(Long threadId) {
        ThreadEntity threadEntity = threadRepository.findById(threadId)
                .orElseThrow(() -> new QuoraException(String.format("Thread with id '%d' was not found.", threadId)));

        return modelMapper.map(threadEntity, ThreadDTO.class);
    }

    public ThreadDTO createThread(CreateThreadDTO createThreadDTO) {
        String username = JwtUtils.getUsernameFromSession();
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new QuoraException(String.format("User '%s' does not exist", username)));

        ThreadEntity threadEntity = buildThreadEntityUsingCreateThreadDTOAndUserEntity(createThreadDTO, userEntity);

        ThreadEntity insertedEntity = threadRepository.save(threadEntity);

        return modelMapper.map(insertedEntity, ThreadDTO.class);
    }

    private ThreadEntity buildThreadEntityUsingCreateThreadDTOAndUserEntity(CreateThreadDTO createThreadDTO, UserEntity userEntity) {
        return ThreadEntity.builder()
                .content(createThreadDTO.getContent())
                .title(createThreadDTO.getTitle())
                .owner(userEntity)
                .build();
    }
}
