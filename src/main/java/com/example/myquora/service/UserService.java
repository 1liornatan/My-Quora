package com.example.myquora.service;

import com.example.myquora.dto.CreateUserDTO;
import com.example.myquora.dto.LoginDTO;
import com.example.myquora.dto.MessageDTO;
import com.example.myquora.model.UserEntity;
import com.example.myquora.repository.UserRepository;
import com.example.myquora.util.Constants;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public MessageDTO createUser(CreateUserDTO data) {
        checkSignUpCredentials(data);
        UserEntity userEntity = modelMapper.map(data, UserEntity.class);
        userRepository.save(userEntity);

        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setMessage(Constants.MSG_CREATE_USER_SUCCESS);
        return messageDTO;
    }

    public MessageDTO loginUser(LoginDTO loginDTO) {
        String email = loginDTO.getEmail();
        String password = loginDTO.getPassword();

        checkPassword(password);
        checkEmail(email);

        List<UserEntity> userList = userRepository.findByEmail(email);
        checkUserExists(userList);

        UserEntity userEntity = userList.get(0);
        checkPasswordMatch(userEntity.getPassword(), password);

        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setMessage(Constants.MSG_LOGIN_SUCCESS);

        return messageDTO;
    }

    private void checkPasswordMatch(String firstPass, String secondPass) {
        if(!firstPass.equals(secondPass)) {
            // TODO: Exception
        }
    }

    private void checkUserExists(List<UserEntity> userList) {
        if(userList.size() <= 0) {
            // TODO: Exception
        }
    }

    private void checkSignUpCredentials(CreateUserDTO data) {
        String firstName = data.getFirstName();
        String lastName = data.getLastName();
        String password = data.getPassword();
        String email = data.getEmail();

        checkField(firstName);
        checkField(lastName);
        checkEmail(email);
        checkPassword(password);
    }

    private void checkField(String field) {
        if(field.length() < Constants.MIN_FIELD_LENGTH) {
            // TODO: Exception
        }
    }

    private void checkPassword(String password) {
        if(password.length() < Constants.MIN_PASS_LENGTH) {
            // TODO: Exception
        }
    }

    private void checkEmail(String email) {
        if(!email.contains("@") && !email.contains(".")) {
            // TODO: Exception
        }
    }
}
