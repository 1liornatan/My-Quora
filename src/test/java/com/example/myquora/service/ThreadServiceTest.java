package com.example.myquora.service;

import com.example.myquora.dto.CreateThreadDTO;
import com.example.myquora.dto.ThreadDTO;
import com.example.myquora.model.ThreadEntity;
import com.example.myquora.model.UserEntity;
import com.example.myquora.repository.ThreadRepository;
import com.example.myquora.repository.UserRepository;
import com.example.myquora.util.Constants;
import org.apache.logging.log4j.ThreadContext;
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
        final String testUsername = "testUsername";

        ThreadContext.put(Constants.KEY_USERNAME, testUsername);
        createThreadDTO = CreateThreadDTO.builder()
                .content("Testing")
                .title("Test")
                .build();

        userEntity = UserEntity.builder()
                .username(testUsername)
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
                .content(createThreadDTO.getContent())
                .title(createThreadDTO.getTitle())
                .email(userEntity.getEmail())
                .build();
    }
    @Test
    public void testCreateThread() {
        Mockito.when(userRepository.findByUsername(userEntity.getUsername())).thenReturn(Optional.ofNullable(userEntity));
        Mockito.when(threadRepository.save(threadEntity)).thenReturn(threadEntity);
        Mockito.when(modelMapper.map(threadEntity, ThreadDTO.class)).thenReturn(threadDTO);

        ThreadDTO savedThreadDTO = threadService.createThread(createThreadDTO);

        Assertions.assertEquals(threadDTO, savedThreadDTO);
    }

    @Test
    public void testGetThread() {
        Mockito.when(threadRepository.findById(1L)).thenReturn(Optional.ofNullable(threadEntity));
        Mockito.when(modelMapper.map(threadEntity, ThreadDTO.class)).thenReturn(threadDTO);

        ThreadDTO insertedThreadDTO = threadService.getThread(1L);

        Assertions.assertEquals(threadDTO, insertedThreadDTO);
    }


}
