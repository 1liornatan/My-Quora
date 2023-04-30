package com.example.myquora.service;

import com.example.myquora.dto.CreateThreadDTO;
import com.example.myquora.dto.ThreadDTO;
import com.example.myquora.model.ThreadEntity;
import com.example.myquora.model.UserEntity;
import com.example.myquora.repository.ThreadRepository;
import com.example.myquora.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ThreadServiceTest {

    @Mock
    ThreadRepository threadRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    ModelMapper modelMapper;

    @InjectMocks
    ThreadService threadService;

    CreateThreadDTO createThreadDTO;
    UserEntity userEntity;
    ThreadEntity threadEntity;
    ThreadDTO threadDTO;


    @BeforeEach
    void init() {
        createThreadDTO = CreateThreadDTO.builder()
                .content("Testing")
                .title("Test")
                .username("testUsername")
                .build();

        userEntity = UserEntity.builder()
                .id(1L)
                .username("testUsername")
                .email("testEmail@outlook.com")
                .firstName("testFirstName")
                .lastName("testLastName")
                .password("12345678")
                .build();

        threadEntity = ThreadEntity.builder()
                .owner(userEntity)
                .content(createThreadDTO.getContent())
                .title(createThreadDTO.getTitle())
                .build();

        threadDTO = ThreadDTO.builder()
                .threadId(1L)
                .content(createThreadDTO.getContent())
                .title(createThreadDTO.getTitle())
                .email(userEntity.getEmail())
                .username(createThreadDTO.getUsername())
                .build();
    }
    @Test
    public void testCreateThread() {
        // ensure no such user
        Mockito.when(userRepository.findByUsername(userEntity.getUsername())).thenReturn(Optional.ofNullable(userEntity));
        Mockito.when(threadRepository.save(threadEntity)).thenReturn(threadEntity);
        Mockito.when(modelMapper.map(threadEntity, ThreadDTO.class)).thenReturn(threadDTO);

        ThreadDTO savedThreadDTO = threadService.createThread(createThreadDTO);

        Assertions.assertNotNull(savedThreadDTO);

    }
}
