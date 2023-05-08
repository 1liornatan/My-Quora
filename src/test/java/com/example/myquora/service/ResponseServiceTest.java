package com.example.myquora.service;

import com.example.myquora.dto.CreateResponseDTO;
import com.example.myquora.dto.ThreadDTO;
import com.example.myquora.model.ResponseEntity;
import com.example.myquora.model.ThreadEntity;
import com.example.myquora.model.UserEntity;
import com.example.myquora.repository.ResponseRepository;
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
public class ResponseServiceTest {

    @Mock
    ResponseRepository responseRepository;

    @Mock
    ThreadRepository threadRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    ModelMapper modelMapper;

    @InjectMocks
    ResponseService responseService;

    CreateResponseDTO createResponseDTO;
    ThreadDTO threadDTO;
    ResponseEntity responseEntity;
    ThreadEntity threadEntity;
    UserEntity userEntity;

    final String testUsername1 = "testUsername1";
    final String testContent = "testResponseContent";
    final String testEmail = "testEmail@outlook.com";
    final String testTitle = "testTitle";

    @BeforeEach
    void init() {

        createResponseDTO = CreateResponseDTO.builder()
                .content(testContent)
                .username(testUsername1)
                .threadId(1L)
                .build();

        userEntity = UserEntity.builder()
                .id(1L)
                .username(testUsername1)
                .firstName("testFirstName")
                .lastName("testLastName")
                .email(testEmail)
                .build();


        threadDTO = ThreadDTO.builder()
                .threadId(1L)
                .username(testUsername1)
                .title(testTitle)
                .content("testThreadContent")
                .email(testEmail)
                .build();

        threadEntity = ThreadEntity.builder()
                .owner(userEntity)
                .content(testContent)
                .locked(Boolean.FALSE)
                .title(testTitle)
                .build();

        responseEntity = ResponseEntity.builder()
                .content(testContent)
                .author(userEntity)
                .thread(threadEntity)
                .build();
    }

    @Test
    public void testAddResponse() {
        Mockito.when(threadRepository.findById(1L)).thenReturn(Optional.ofNullable(threadEntity));
        Mockito.when(userRepository.findByUsername(testUsername1)).thenReturn(Optional.ofNullable(userEntity));
        Mockito.when(responseRepository.save(responseEntity)).thenReturn(responseEntity);
        Mockito.when(modelMapper.map(threadEntity, ThreadDTO.class)).thenReturn(threadDTO);

        ThreadDTO insertedThreadDTO = responseService.addResponse(createResponseDTO);

        Assertions.assertEquals(threadDTO, insertedThreadDTO);
    }


}
