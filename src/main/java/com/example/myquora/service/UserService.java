package com.example.myquora.service;

import com.example.myquora.dto.CreateUserDTO;
import com.example.myquora.dto.MessageDTO;
import com.example.myquora.model.UserEntity;
import com.example.myquora.repository.UserRepository;
import com.example.myquora.util.Constants;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public MessageDTO createUser(CreateUserDTO data) {
        checkCredentials(data);
        UserEntity userEntity = modelMapper.map(data, UserEntity.class);
        userRepository.save(userEntity);

        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setMessage(Constants.MSG_CREATE_USER_SUCCESS);
        return messageDTO;
    }

    private void checkCredentials(CreateUserDTO data) {
        String firstName = data.getFirstName();
        String lastName = data.getLastName();
        String password = data.getPassword();
        String email = data.getEmail();

        if(firstName.length() < Constants.MIN_FIELD_LENGTH) {
            // TODO: Exception
        }

        if(lastName.length() < Constants.MIN_FIELD_LENGTH) {
            // TODO: Exception
        }

        if(password.length() < Constants.MIN_PASS_LENGTH) {
            // TODO: Exception
        }

        if(!email.contains("@") && !email.contains(".")) {
            // TODO: Exception
        }
    }
}
