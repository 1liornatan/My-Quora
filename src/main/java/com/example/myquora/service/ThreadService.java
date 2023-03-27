package com.example.myquora.service;

import com.example.myquora.advice.exception.QuoraException;
import com.example.myquora.dto.CreateThreadDTO;
import com.example.myquora.dto.ThreadDTO;
import com.example.myquora.model.ThreadEntity;
import com.example.myquora.model.UserEntity;
import com.example.myquora.repository.ThreadRepository;
import com.example.myquora.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ThreadService {
    private final ThreadRepository threadRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public ThreadDTO getThread(Integer threadId) {
        Optional<ThreadEntity> optionalThreadEntity = threadRepository.findById(threadId);
        if(optionalThreadEntity.isEmpty())
            throw new QuoraException(String.format("Thread with id '%d' was not found.", threadId));

        ThreadEntity threadEntity = optionalThreadEntity.get();

        return modelMapper.map(threadEntity, ThreadDTO.class);
    }

    public ThreadDTO createThread(CreateThreadDTO createThreadDTO) {
        String username = createThreadDTO.getUsername();
        Optional<UserEntity> optionalUserEntity = userRepository.findByUsername(username);

        if(optionalUserEntity.isEmpty())
            throw new QuoraException(String.format("User '%s' does not exist", username));

        UserEntity userEntity = optionalUserEntity.get();
        ThreadEntity threadEntity = ThreadEntity.builder()
                .content(createThreadDTO.getContent())
                .title(createThreadDTO.getTitle())
                .owner(userEntity)
                .build();

        ThreadEntity insertedEntity = threadRepository.save(threadEntity);

        return modelMapper.map(insertedEntity, ThreadDTO.class);
    }
}
