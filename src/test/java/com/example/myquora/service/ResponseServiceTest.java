package com.example.myquora.service;

import com.example.myquora.dto.CreateResponseDTO;
import com.example.myquora.dto.ThreadDTO;
import com.example.myquora.model.ResponseEntity;
import com.example.myquora.model.ThreadEntity;
import com.example.myquora.model.UserEntity;
import com.example.myquora.repository.ResponseRepository;
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


    @BeforeEach
    void init() {
        final String testUsername = "testUsername1";
        final String testContent = "testResponseContent";
        final String testEmail = "testEmail@outlook.com";
        final String testTitle = "testTitle";
        final String testFirstName = "testFirstName";
        final String testLastName = "testLastName";
        final String testThreadContent = "testThreadContent";

        ThreadContext.put(Constants.KEY_USERNAME, testUsername);

        createResponseDTO = CreateResponseDTO.builder()
                .content(testContent)
                .threadId(1L)
                .build();

        userEntity = UserEntity.builder()
                .id(1L)
                .username(testUsername)
                .firstName(testFirstName)
                .lastName(testLastName)
                .email(testEmail)
                .build();


        threadDTO = ThreadDTO.builder()
                .threadId(1L)
                .title(testTitle)
                .content(testThreadContent)
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
        Mockito.when(userRepository.findByUsername(userEntity.getUsername())).thenReturn(Optional.ofNullable(userEntity));
        Mockito.when(responseRepository.save(responseEntity)).thenReturn(responseEntity);
        Mockito.when(modelMapper.map(threadEntity, ThreadDTO.class)).thenReturn(threadDTO);

        ThreadDTO insertedThreadDTO = responseService.addResponse(createResponseDTO);

        Assertions.assertEquals(threadDTO, insertedThreadDTO);
    }


}
