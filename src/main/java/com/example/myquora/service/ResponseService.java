package com.example.myquora.service;

import com.example.myquora.advice.exception.QuoraException;
import com.example.myquora.dto.CreateResponseDTO;
import com.example.myquora.dto.ResponseDTO;
import com.example.myquora.dto.ResponsesDTO;
import com.example.myquora.dto.ThreadDTO;
import com.example.myquora.model.ResponseEntity;
import com.example.myquora.model.ThreadEntity;
import com.example.myquora.model.UserEntity;
import com.example.myquora.repository.ResponseRepository;
import com.example.myquora.repository.ThreadRepository;
import com.example.myquora.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResponseService {
    private final ResponseRepository responseRepository;
    private final ThreadRepository threadRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    public ThreadDTO addResponse(CreateResponseDTO createResponseDTO) {
        Integer threadId = createResponseDTO.getThreadId();
        ThreadEntity threadEntity = getThreadEntity(threadId);
        assertThreadIsUnlocked(threadEntity);
        UserEntity userEntity = getUserEntity(createResponseDTO.getUsername());

        ResponseEntity responseEntity = ResponseEntity.builder()
                .author(userEntity)
                .content(createResponseDTO.getContent())
                .thread(threadEntity)
                .build();

        responseRepository.save(responseEntity);
        return modelMapper.map(getThreadEntity(threadId), ThreadDTO.class);
    }

    private ThreadEntity getThreadEntity(Integer threadId) {
        Optional<ThreadEntity> optionalThreadEntity = threadRepository.findById(threadId);

        if(optionalThreadEntity.isEmpty())
            throw new QuoraException(String.format("Thread with id '%d' was not found.", threadId));

        return optionalThreadEntity.get();
    }

    private void assertThreadIsUnlocked(ThreadEntity threadEntity) {
        if(threadEntity.getLocked())
            throw new QuoraException(String.format("Thread '%s' is locked.", threadEntity.getTitle()));
    }

    private UserEntity getUserEntity(String username) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByUsername(username);

        if(optionalUserEntity.isEmpty())
            throw new QuoraException(String.format("Username '%s' was not found.", username));

        return optionalUserEntity.get();
    }

    public ResponsesDTO getAllThreadResponses(Integer threadId) {
        ThreadEntity threadEntity = getThreadEntity(threadId);
        List<ResponseEntity> responseEntityList = responseRepository.findByThread(threadEntity);
        List<ResponseDTO> responseDTOList = responseEntityList.stream()
                .map(r -> modelMapper.map(r, ResponseDTO.class)).collect(Collectors.toList());

        ResponsesDTO responsesDTO = new ResponsesDTO();
        responsesDTO.setResponseDTOList(responseDTOList);

        return responsesDTO;
    }
}
